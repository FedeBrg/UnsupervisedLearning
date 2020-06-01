package kohonen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SOMap {

    List<Neuron> neurons;
    private int rows;
    private int cols;
    private int inputSize;
    private int epoch = 0;

    private double learningRate;
    private double radius;

    public SOMap(int rows,int cols, int inputSize, double learningRate, double radius){
        this.rows = rows;
        this.cols = cols;
        this.inputSize = inputSize;
        this.learningRate = learningRate;
        this.radius = radius;

        this.neurons = new ArrayList<>();

        for(int i = 0; i<rows;i++){
            for(int j = 0; j<cols; j++){
                Neuron n = new Neuron(i,j);
                neurons.add(n);
            }
        }
    }

    public Neuron getNeuronAt(int row, int col){
        return this.neurons.get(row*cols + col);
    }

    public void initializeNeuronsRandom(){
        Random r = new Random();
        for(Neuron n : neurons){
            for (int i = 0; i < inputSize;i++){
                n.getWeights().add(r.nextDouble());
//                n.setWeight(i,r.nextDouble());
            }
        }
    }


    public void train(List<Double> input){

        Neuron winner = null;
        double max = Double.POSITIVE_INFINITY;

        for(Neuron n : neurons){
            double sum=0;
            for(int j=0; j< inputSize; j++) {
                double d=input.get(j) - n.getWeight(j);
                sum+=d*d;
            }
            sum = Math.sqrt(sum);
            if(sum < max) {
                winner=n;
                max=sum;
            }

        }

        for(Neuron n : neurons){
            if(!n.equals(winner)){
                double d = n.distance(winner);
                if(d<=3){ // DEBERIA SER EL RADIO VARIABLE SEGUN EPOCA
                    for (int i = 0; i<inputSize;i++){
                        double newWeight=(input.get(i) - n.getWeight(i)) * getLearningRate(epoch);
                        n.setWeight(i, newWeight);
                    }
                }
            }
        }

        epoch++;

    }

    public Neuron getWinner(List<Double> input){
        Neuron winner = null;
        double max = Double.POSITIVE_INFINITY;

        for(Neuron n : neurons){
            double sum=0;
            for(int j=0; j< inputSize; j++) {
                double d=input.get(j) - n.getWeight(j);
                sum+=d*d;
            }
            sum = Math.sqrt(sum);
            if(sum < max) {
                winner=n;
                max=sum;
            }

        }

        return winner;
    }


    private double getRadius(int epoch){

        return 0;
    }

    private double getLearningRate(int epoch){
        double lr = learningRate;
        if (1.0/epoch < learningRate){
            lr = 1.0/epoch;
        }
        return lr;
    }



}
