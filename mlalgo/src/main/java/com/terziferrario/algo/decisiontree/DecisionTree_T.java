package com.terziferrario.algo.decisiontree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

public class DecisionTree_T extends DecisionTree {

    private InnerDecisionTree_T root;

    public DecisionTree_T() {
        root = null;
    }

    public InnerDecisionTree_T getRoot() {
        return root;
    }

    /**
     * Create a decision tree with the ID3 algorithm
     * 
     * @param dataset the dataset
     * @param target  the target column
     */
    @Override
    public void ID3(Object[][] dataset, Object[] target) {

        List<Integer> attributes = new ArrayList<>();

        if (dataset.length == 0 || target.length == 0) {
            return;
        }

        if (entropy(target) == 0) {
            root = new InnerDecisionTree_T(target[0], -1, dataset, target[0].toString());
            return;
        }

        int attribute = information_gain(dataset, target);

        attributes.add(attribute);

        HashMap<Object, Object[][]> split = split(dataset, attribute);

        root = new InnerDecisionTree_T(null, attribute, dataset, null);

        for (Object key : split.keySet()) {
            Object[][] newDataset = split.get(key);
            Object[] newTarget = new Object[newDataset.length];

            for (int i = 0; i < newDataset.length; i++) {
                newTarget[i] = newDataset[i][attribute];
            }

            InnerDecisionTree_T child = new InnerDecisionTree_T(key, attribute, newDataset, key.toString());

            root.addChild(child);
        }

        // Manca la parte ricorsiva/iterativa della creazione dell'albero
    }

    /**
     * Split a dataset by a column
     * 
     * @param dataset   the dataset to split
     * @param attribute the column to split
     * @return a hashmap with the value of the column as key and the dataset as
     *         value
     */
    private HashMap<Object, Object[][]> split(Object[][] dataset, int attribute) {
        HashMap<Object, List<Object[]>> temp = new HashMap<>();

        for (int i = 0; i < dataset.length; i++) {
            if (temp.containsKey(dataset[i][attribute])) {
                temp.get(dataset[i][attribute]).add(dataset[i]);
            } else {
                List<Object[]> list = new LinkedList<>();
                list.add(dataset[i]);
                temp.put(dataset[i][attribute], list);
            }
        }

        HashMap<Object, Object[][]> res = new HashMap<>();

        for (Object key : temp.keySet()) {
            List<Object[]> list = temp.get(key);
            Object[][] newDataset = new Object[list.size()][];
            newDataset = list.toArray(newDataset);

            res.put(key, newDataset);

        }

        return res;
    }

    /**
     * Calculate the information gain of a dataset
     * 
     * @param dataset the dataset to calculate the information gain
     * @param target  the target column
     * @return the index of the column with the highest information gain
     */
    private int information_gain(Object[][] dataset, Object[] target) {
        double[] gain = new double[dataset.length];

        for (int i = 0; i < dataset[0].length; i++) {
            gain[i] = entropy(target) - entropy(dataset, i);
        }

        double max = 0;
        int index = 0;
        for (int i = 0; i < gain.length; i++) {
            if (gain[i] > max) {
                max = gain[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * Calculate the entropy of a column
     * 
     * @param target the column
     * @return the entropy of the column
     */
    private double entropy(Object[] target) {
        HashMap<Object, Double> prob = new HashMap<>();

        for (int i = 0; i < target.length; i++) {
            if (prob.containsKey(target[i])) {
                prob.put(target[i], prob.get(target[i]) + 1);
            } else {
                prob.put(target[i], 1.0);
            }
        }

        double res = 0;

        for (Object key : prob.keySet()) {
            double p = prob.get(key) / target.length;
            res += p * Math.log(p) / Math.log(2);
        }

        return res;
    }

    /**
     * Calculate the entropy of a column in a dataset
     * 
     * @param dataset   the dataset
     * @param attribute the column to calculate the entropy
     * @return the entropy of the column or -1 if the column is not in the dataset
     */
    private double entropy(Object[][] dataset, int attribute) {
        if (attribute >= dataset.length || attribute < 0)
            return -1;

        Object[] columns = new Object[dataset.length];

        for (int i = 0; i < dataset.length; i++) {
            columns[i] = dataset[i][attribute];
        }

        return entropy(columns);
    }

    private class InnerDecisionTree_T {

        private List<InnerDecisionTree_T> children;
        private Object value;
        private int attribute;
        private Object[][] dataset;
        private String label;

        public InnerDecisionTree_T(Object value, int attribute, Object[][] dataset, String label) {
            this.children = new LinkedList<InnerDecisionTree_T>();
            this.value = value;
            this.attribute = attribute;
            this.dataset = dataset;
            this.label = label;
        }

        public void addChild(InnerDecisionTree_T child) {
            this.children.add(child);
        }

        public void removeChild(InnerDecisionTree_T child) {
            this.children.remove(child);
        }

        public int getAttribute() {
            return attribute;
        }

        public List<InnerDecisionTree_T> getChildren() {
            return children;
        }

        public String getLabel() {
            return label;
        }

        public Object[][] getDataset() {
            return dataset;
        }

        public Object getValue() {
            return value;
        }
    }
}
