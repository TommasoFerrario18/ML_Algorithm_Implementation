package com.terziferrario.algo.conceptlearning;

import com.terziferrario.algo.DatasetException;
import com.terziferrario.algo.conceptlearning.ConceptLearning;

import java.util.Arrays;

public class ConceptLearning_T extends ConceptLearning {

    /*
     * ? sostituito con il valore 2
     * \emptyset sostituito con il valore -1
     */

    /**
     * The following method implements the find-s algorithm for finding the most
     * general hypothesis that is consistent with the dataset provided as input.
     * 
     * @param dataset The dataset with which to go about creating the hypothesis
     * @param target  Contains the target variables of the dataset
     * @return Hypothesis
     * @throws DatasetException It is launched when the size of the dataset
     *                          and target are not equal
     */
    public byte[] find_s(byte[][] dataset, byte[] target)
            throws DatasetException {
        byte[] hypothesis = init_hypothesis(dataset.length);

        if (dataset[0].length == target.length) {
            for (int i = 0; i < dataset[0].length; i++) {
                if (target[i] == 1) {
                    update_hypothesis(hypothesis, dataset[i]);
                }
            }

        } else {
            throw new DatasetException(("Dataset size and Target " +
                    "size are not equals"));
        }

        return hypothesis;
    }

    /**
     * Initialize the hypothesis by making it as restrictive as possible.
     * 
     * @param size Dimension of hypothesis
     * @return hypothesis
     */
    private byte[] init_hypothesis(int size) {
        byte[] hypothesis = new byte[size];

        Arrays.fill(hypothesis, (byte) -1);

        return hypothesis;
    }

    /**
     * Update the hypothesis by following the rules of the find-s algorithm.
     *
     * @param current     Current hypothesis
     * @param dataset_row Row of the dataset used to update the hypothesis
     */
    private void update_hypothesis(byte[] current, byte[] dataset_row) {
        for (int i = 0; i < current.length; i++) {
            if (current[i] == -1) {
                current[i] = dataset_row[i];
            } else if (current[i] == 0 && dataset_row[i] == 1) {
                current[i] = 2;
            } else if (current[i] == 1 && dataset_row[i] == 0) {
                current[i] = 2;
            }
        }
    }
}
