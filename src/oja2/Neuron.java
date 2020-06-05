package oja2;

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
        double dw,w;
        double y = calculateY(input);
        //System.out.println(y);
        for(int i = 0; i<input.size();i++){
            w = weights.get(i);
            double x = input.get(i)*y;
            double z = Math.pow(y,2)*w;
            dw = (0.5/a)*(x-z);
//            dw = (0.5)*(input.get(i)*y - Math.pow(y,2)*w);
            //System.out.println(weights);
            weights.set(i,w+dw);
           // System.out.println(weights);
        }

//        double mod = getWeightsMod();
//        for(int i = 0; i< weights.size();i++){
//            w = weights.get(i);
//            weights.set(i,w/mod);
//        }
    }

    public double getWeightsMod(){
        double sum = 0.0;
        for(Double d : weights){
            sum+=(d*d);
        }
        return Math.sqrt(sum);
    }
    public List<Double> getWeights() {
        return weights;
    }
}
