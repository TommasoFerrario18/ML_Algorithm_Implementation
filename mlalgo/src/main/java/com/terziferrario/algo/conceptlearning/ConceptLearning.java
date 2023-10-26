package com.terziferrario.algo.conceptlearning;

import com.terziferrario.algo.DatasetException;

public abstract class ConceptLearning {

    public abstract byte[] find_s(byte[][] dataset, byte[] target) throws DatasetException;

    public boolean predict(byte[] instance, byte[] hypothesis){
        boolean prediction = true;

        for (int i = 0; i < instance.length; i++){
            if (hypothesis[i] == -1)
                return false;

            if ((hypothesis[i] == 1 && instance[i] == 0) || (hypothesis[i] == 0 && instance[i] == 1)){
                return false;
            }
        }

        return prediction;
    }
}
