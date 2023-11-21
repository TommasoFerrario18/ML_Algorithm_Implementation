package com.terziferrario.algo.decisiontree;

public abstract class DecisionTree {

    public abstract void ID3(Object[][] dataset, Object[] target);

    public Object predict(Object[] instance){
        return null;
    }
}
