package com.terziferrario.algo;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.terziferrario.algo.clustering.Kmeans;
import com.terziferrario.algo.clustering.Kmeans_T;

public class KmeansTest {
    @Test
    public void testKmeans_T() {
        double[][] data = { { 2, 10 }, { 2, 5 }, { 8, 4 }, { 5, 8 }, { 7, 5 }, { 6, 4 }, { 1, 2 }, { 4, 9 } };

        double[][] centroids = { { 2, 10 }, { 5, 8 }, { 1, 2 } };

        int k = 3;

        int maxIterations = 1;

        Kmeans algo = new Kmeans_T(k, maxIterations, Kmeans.DistanceType.EUCLIDEAN);

        algo.setCentroids(centroids);

        algo.fit(data);

        int[] labels = algo.predict(data);

        int[] labels_real = {0, 2, 1, 1, 1, 1, 2, 1 };

        System.out.println("Labels: ");
        for (int i = 0; i < labels.length; i++) {
            System.out.print(labels[i] + " \t | \t");
        }

        System.out.println("\n Centroids: ");
        centroids = algo.getCentroids();
        for (int i = 0; i < centroids.length; i++) {
            System.out.print(centroids[i][0] + " \t | \t");
            System.out.print(centroids[i][1] + " \t | \t");
            System.out.println();
        }

        assertTrue(Arrays.equals(labels_real, labels));
    }
}
