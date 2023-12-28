package com.terziferrario.algo.clustering;

import java.util.ArrayList;
import java.util.List;

public abstract class Kmeans {

    public enum DistanceType {
        EUCLIDEAN,
        MANHATTAN
    }

    /**
     * Number of clusters
     */
    private int k;

    /**
     * Maximum number of iterations
     */
    private int maxIterations;

    /**
     * Distance type
     */
    private DistanceType distanceType;

    /**
     * Centroids
     */
    private double[][] centroids;

    private boolean centroidsInitialized = false;

    public Kmeans(int k, int maxIterations, DistanceType distanceType) {
        this.k = k;
        this.maxIterations = maxIterations;
        this.distanceType = distanceType;
        this.centroids = new double[k][];
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public DistanceType getDistanceType() {
        return distanceType;
    }

    public void setDistanceType(DistanceType distanceType) {
        this.distanceType = distanceType;
    }

    public double[][] getCentroids() {
        return centroids;
    }

    public void setCentroids(double[][] centroids) {
        this.centroidsInitialized = true;
        this.centroids = centroids;
    }

    public boolean isCentroidsInitialized() {
        return centroidsInitialized;
    }

    public void setCentroidsInitialized(boolean centroidsInitialized) {
        this.centroidsInitialized = centroidsInitialized;
    }

    public abstract void fit(double[][] data);

    public abstract int[] predict(double[][] data) throws IllegalStateException, IllegalArgumentException;

    public abstract double[] silhouette(double[][] data, int[] labels) throws IllegalArgumentException;

    /**
     * Calculate the distance between two vectors
     * 
     * @param a the first vector
     * @param b the second vector
     * @return the distance between the two vectors
     * @throws IllegalArgumentException if the vectors have different lengths
     */
    protected double distance(double[] a, double[] b) throws IllegalArgumentException {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vectors must have the same length");
        }

        switch (this.distanceType) {
            case EUCLIDEAN:
                return distanceEuclidean(a, b);
            case MANHATTAN:
                return distanceManhattan(a, b);
            default:
                throw new IllegalArgumentException("Unknown distance type");
        }
    }

    private double distanceEuclidean(double[] a, double[] b) {
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }

    private double distanceManhattan(double[] a, double[] b) {
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.abs(a[i] - b[i]);
        }
        return sum;
    }
}
