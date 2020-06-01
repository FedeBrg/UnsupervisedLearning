package oja2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuron {
    private List<Double> weights = new ArrayList<>();

    public Neuron(int dim){
        Random r = new Random();
        for (int i = 0; i< dim;i++){
//            weights.add(0.1);
            weights.add(r.nextDouble());
        }
    }

    public double calculateY(List<Double> input){
        double y = 0.0;
        for(int i = 0; i<input.size();i++){
            y += input.get(i)*weights.get(i);
        }

        return y;
    }


    public void updateWeights(List<Double> input){
        double dw,w;
        double y = calculateY(input);
        for(int i = 0; i<input.size();i++){
            dw = 0.0001*((input.get(i)*y) - (y*y*weights.get(i)));
            w = weights.get(i);
            weights.set(i,w+dw);
        }
    }

    public List<Double> getWeights() {
        return weights;
    }
}
