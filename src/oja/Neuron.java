package oja;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuron {
    private List<Double> weights = new ArrayList<>();

    public Neuron(int dim){
        Random r = new Random();
        for (int i = 0; i< dim;i++){
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

    public void updateWeights(List<Double> input,int a){
        double dw, w;
        double y = calculateY(input);

        for(int i = 0; i<input.size();i++){
            w = weights.get(i);
            double x = input.get(i)*y;
            double z = Math.pow(y,2)*w;
            dw = (0.5/a)*(x-z);
            weights.set(i,w+dw);
        }
    }



    public List<Double> getWeights() {
        return weights;
    }
}
