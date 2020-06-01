package oja;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuron {

    private List<Double> weights = new ArrayList<>();
    private double lr = 0.0001;

    public Neuron(int inputSize){
        Random r = new Random();
        for(int i = 0; i< inputSize;i++){
            weights.add(r.nextDouble());
        }
    }


    public double calculateY(List<Double> input){
        double y = 0;
            for(int i = 0;i<input.size();i++){
                y+= (input.get(i)*weights.get(i));
            }

        return y;
    }



    public void updateWeights(List<Double> input,double yg){
        double dw,w;
        double y = calculateY(input);
        for(int i = 0; i<input.size();i++){
            w = weights.get(i);
            dw = lr*((input.get(i)*yg) - (yg*yg*w));
            weights.set(i,w+dw);
        }
    }

    public List<Double> getWeights() {
        return weights;
    }
}
