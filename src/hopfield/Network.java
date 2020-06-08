package hopfield;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Network {

    private List<Neuron> neurons;

    public Network(int size){
        neurons = new ArrayList<>();
        for(int i = 0; i < size; i++){
            neurons.add(new Neuron(i));
        }
    }

    public void initialize(List<List<Integer>> input){
        List<Double> weights = new ArrayList<>();
        double sum = 0.0;
        for(Neuron n1 : neurons){
            for(Neuron n2 : neurons){
                if(n1.equals(n2)){
                    weights.add(0.0);
                }

                else{
                    for(List<Integer> l : input){
                        sum += (l.get(n1.getId())*l.get(n2.getId()));
                    }
                    weights.add(sum/neurons.size());
                    sum = 0;
                }

            }
            n1.setWeights(weights);
            weights = new ArrayList<>();
        }

    }

    public void loadInput(List<Integer> input){

        for(int i = 0; i < input.size(); i++){
            neurons.get(i).setState(input.get(i));
        }

    }

    public void update(){
        double sum = 0.0;

        for(Neuron n : neurons){
            for(int i = 0; i < neurons.size(); i++){
                sum += (neurons.get(i).getState() * n.getWeights().get(i));
            }

            if(sum != 0){
                n.setState(sum>0? 1 : -1);
            }

            sum = 0;
        }
    }

    public List<Integer> getCurrentStatus(){
        List<Integer> status = new ArrayList<>();

        for(Neuron n : neurons){
            status.add(n.getState());
        }

        return status;
    }



    public List<Integer> noisyLetter(List<Integer> letter){
        List<Integer> noisy = new ArrayList<>();
        double p = 0.1;
        Random  r = new Random();

        for(Integer i : letter){
            if(r.nextDouble()>p){
                noisy.add(i);
            }
            else{
                noisy.add(i*-1);
            }
        }

        return noisy;
    }

}
