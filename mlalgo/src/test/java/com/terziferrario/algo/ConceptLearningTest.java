package com.terziferrario.algo;

import com.terziferrario.algo.conceptlearning.ConceptLearning;
import com.terziferrario.algo.conceptlearning.ConceptLearning_T;
import org.junit.*;


public class ConceptLearningTest {

    @Test
    public void findSTest() {
        
        byte[][] dataset = {{0, 1, 0, 1, 0}, {1, 1, 1, 1, 0}, {1, 1, 0, 0, 1 }};

        byte[] target = { 0, 1, 0, 0, 0 };

        ConceptLearning algo = new ConceptLearning_T();

        byte[] hypothesis_algo;
        try {
            hypothesis_algo = algo.find_s(dataset, target);
        } catch (DatasetException e) {
            throw new RuntimeException(e);
        }

        byte[] hypothesis_real = {1, 1, 1};

        Assert.assertArrayEquals(hypothesis_real,hypothesis_algo);
    }

}
