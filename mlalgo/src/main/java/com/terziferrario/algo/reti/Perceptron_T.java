package com.terziferrario.algo.reti;

public class Perceptron_T {

    private double[] weights;
    private double bias;
    private double learningRate;

    /**
     * Create a perceptron with n weights
     * 
     * @param n the number of weights
     */
    public Perceptron_T(int n) {
        this.weights = new double[n];
        this.bias = 0;
        this.learningRate = 0.1;
    }

    /**
     * Create a perceptron with n weights, bias and learning rate
     * 
     * @param n            the number of weights
     * @param bias         the bias
     * @param learningRate the learning rate
     */
    public Perceptron_T(int n, double bias, double learningRate) {
        this.weights = new double[n];
        this.bias = bias;
        this.learningRate = learningRate;
    }

    public double[] getWeights() {
        return weights;
    }

    /**
     * Train the perceptron
     * 
     * @param dataset the dataset
     * @param target  the target
     * @return the number of errors
     */
    public int train(double[][] dataset, int[] target) {
        int error = 0;

        for (int i = 0; i < dataset.length; i++) {
            int prediction = predict(dataset[i]);
            int delta = target[i] - prediction;

            if (delta != 0) {
                error++;
            }

            for (int j = 0; j < weights.length; j++) {
                weights[j] += learningRate * delta * dataset[i][j];
            }

            bias += learningRate * delta;
        }

        return error;
    }

    /**
     * Predict the output of the perceptron
     * 
     * @param input the input
     * @return the output
     */
    public int predict(double[] input) {
        double sum = 0;

        for (int i = 0; i < input.length; i++) {
            sum += input[i] * weights[i];
        }

        sum += bias;

        return activation(sum);
    }

    /**
     * Update the weights of the perceptron
     * 
     * @param input  the input
     * @param target the target
     */
    private int activation(double sum) {
        if (sum > 0) {
            return 1;
        } else {
            return 0;
        }
    }

}