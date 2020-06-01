package oja;

import java.util.ArrayList;
import java.util.List;

public class Network {

    List<Neuron> neurons = new ArrayList<>();

    public Network(int size, int dim){
        for(int i = 0; i< size; i++){
            neurons.add(new Neuron(dim));
        }
    }


    public double calculateY(List<List<Double>> inputs){
        double y = 0;
        for(int i = 0; i<inputs.size();i++){
            y += neurons.get(i).calculateY(inputs.get(i));
        }
        return y;
    }

    public void train(List<List<Double>> inputs){
        for(int i = 0; i< inputs.size();i++){
            double y = calculateY(inputs);

            neurons.get(i).updateWeights(inputs.get(i),y);
        }

    }

    public List<Neuron> getNeurons() {
        return neurons;
    }
}
