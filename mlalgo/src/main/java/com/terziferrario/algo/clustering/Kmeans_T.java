package com.terziferrario.algo.clustering;

import java.util.Arrays;
import java.util.Random;

public class Kmeans_T extends Kmeans {

    public Kmeans_T(int k, int maxIterations, DistanceType distanceType) {
        super(k, maxIterations, distanceType);
    }

    /**
     * Fit the model to the data
     * 
     * @param data data to fit the model to
     */
    @Override
    public void fit(double[][] data) {
        if (!super.isCentroidsInitialized())
            initCentroids(data);

        int[] labels = new int[data.length];

        int iteration = 0;

        while (iteration < super.getMaxIterations()){
            labels = predict(data);

            updateCentroids(data, labels);
            
            iteration++;

        }
    }

    /**
     * Predict the cluster for each data point
     * 
     * @param data data whose cluster is to be predicted
     * @return array of cluster labels
     * @throws IllegalStateException    if centroids are not initialized
     * @throws IllegalArgumentException if centroids and data have different number
     *                                  of features
     */
    @Override
    public int[] predict(double[][] data) throws IllegalStateException, IllegalArgumentException {
        int[] labels = new int[data.length];

        double[][] centroids = super.getCentroids();

        if (centroids == null) {
            throw new IllegalStateException("Centroids must be initialized before calling predict method");
        }

        if (centroids.length != super.getK()) {
            throw new IllegalStateException("Centroids length must be equal to k");
        }

        if (centroids[0].length != data[0].length) {
            throw new IllegalArgumentException("Centroids and data must have the same number of features");
        }

        for (int i = 0; i < data.length; i++) {
            double minDistance = Double.MAX_VALUE;
            int minIndex = -1;

            for (int j = 0; j < centroids.length; j++) {
                double distance = super.distance(data[i], centroids[j]);

                if (distance < minDistance) {
                    minDistance = distance;
                    minIndex = j;
                }
            }

            labels[i] = minIndex;
        }

        return labels;
    }

    @Override
    public double[] silhouette(double[][] data, int[] labels) throws IllegalArgumentException {
        if (labels == null) {
            throw new IllegalArgumentException("Labels cannot be null");
        }

        if (labels.length != data.length) {
            throw new IllegalArgumentException("Labels length must be equal to data length");
        }

        double a = 0;
        double b = 0;

        double[] silhouette = new double[getK()];

        for(int cluster = 0; cluster < super.getK(); cluster++){
            a = intraClusterDistance(data, labels, cluster);
            b = interClusterDistance(data, labels, cluster);

            silhouette[cluster] = silhouetteCoefficient(a, b);
        }

        return silhouette;
    }

    /**
     * Initialize centroids by randomly selecting k data points
     * 
     * @param data data from which to select the centroids
     */
    private void initCentroids(double[][] data) {
        double[][] centroids = new double[super.getK()][data[0].length];

        Random random = new Random();
        double min = getMinValue(data);
        double max = getMaxValue(data);

        for (int i = 0; i < centroids.length; i++) {
            for (int j = 0; j < centroids[i].length; j++) {
                centroids[i][j] = random.nextDouble() * (max - min) + min;
            }
        }

        super.setCentroids(centroids);
    }

    /**
     * Update centroids based on the data and the labels
     * 
     * @param data   data used to update the centroids
     * @param labels labels used to update the centroids
     * @throws IllegalStateException    if centroids are not initialized
     * @throws IllegalArgumentException if centroids and data have different number
     *                                  of features
     */
    private void updateCentroids(double[][] data, int[] labels) throws IllegalStateException, IllegalArgumentException {
        double[][] centroids = super.getCentroids();

        if (centroids == null) {
            throw new IllegalStateException("Centroids must be initialized before calling predict method");
        }

        if (centroids.length != super.getK()) {
            throw new IllegalStateException("Centroids length must be equal to k");
        }

        if (centroids[0].length != data[0].length) {
            throw new IllegalArgumentException("Centroids and data must have the same number of features");
        }

        double[][] newCentroids = new double[centroids.length][centroids[0].length];

        int[] counts = new int[centroids.length];

        for (int i = 0; i < data.length; i++) {
            int label = labels[i];
            counts[label]++;

            for (int j = 0; j < data[i].length; j++) {
                newCentroids[label][j] += data[i][j];
            }
        }

        for (int i = 0; i < newCentroids.length; i++) {
            for (int j = 0; j < newCentroids[i].length; j++) {
                newCentroids[i][j] /= counts[i];
            }
        }

        super.setCentroids(newCentroids);
    }

    
    private double interClusterDistance(double[][] data, int[] labels, int cluster) {
        double distance = 0;
        int[] counts = new int[super.getK()];

        for (int i = 0; i < data.length; i++) {
            if (labels[i] != cluster) {
                distance += super.distance(data[i], super.getCentroids()[cluster]);
                counts[labels[i]]++;
            }
        }

        counts[cluster] = Integer.MAX_VALUE;

        Arrays.sort(data);

        return distance / counts[0];
    }

    private double intraClusterDistance(double[][] data, int[] labels, int cluster) {
        double distance = 0;
        int count = 0;

        for (int i = 0; i < data.length; i++) {
            if (labels[i] == cluster) {
                distance += super.distance(data[i], super.getCentroids()[cluster]);
                count++;
            }
        }

        return distance / count;
    }

    private double silhouetteCoefficient(double a, double b) {
        return (b - a) / Math.max(a, b);
    }

    private double getMinValue(double[][] data) {
        double min = Double.MAX_VALUE;
        for (double[] datum : data) {
            for (double v : datum) {
                if (v < min) {
                    min = v;
                }
            }
        }
        return min;
    }

    private double getMaxValue(double[][] data) {
        double max = Double.MIN_VALUE;
        for (double[] datum : data) {
            for (double v : datum) {
                if (v > max) {
                    max = v;
                }
            }
        }
        return max;
    }

}
