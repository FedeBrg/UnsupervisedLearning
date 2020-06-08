package kohonen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SOMap {

    List<Neuron> neurons;
    private int rows;
    private int cols;
    private int inputSize;
    private double learningRate;
    private double radius;

    public SOMap(int rows, int cols, int inputSize, double learningRate, double radius){
        this.rows = rows;
        this.cols = cols;
        this.inputSize = inputSize;
        this.learningRate = learningRate;
        this.radius = radius;
        this.neurons = new ArrayList<>();

        for(int i = 0; i < rows;i++){
            for(int j = 0; j < cols; j++){
                Neuron n = new Neuron(i,j);
                neurons.add(n);
            }
        }
    }



    public void initializeNeuronsRandom(){
        Random r = new Random();
        for(Neuron n : neurons){
            for (int i = 0; i < inputSize;i++){
                n.getWeights().add(r.nextDouble());
            }
        }
    }

    public void initializeNeuronsExamples(List<List<Double>> inputs){
        Random r = new Random();
        for(Neuron n : neurons){
            n.setWeights(inputs.get(r.nextInt(inputs.size())));
        }
    }

    public void train(List<Double> input, int epoch){
        Neuron winner = neurons.get(0);
        double max = Double.POSITIVE_INFINITY;

        for(Neuron n : neurons){
            double sum = 0;

            for(int j = 0; j < inputSize; j++) {
                double d = input.get(j) - n.getWeight(j);
                sum += d * d;
            }

            sum = Math.sqrt(sum);

            if(sum < max) {
                winner = n;
                max = sum;
            }
        }

        for(Neuron n : neurons){
            double d = n.distance(winner);

            if(d <= getRadius(epoch)){
                for (int i = 0; i < inputSize; i++){
                    double newWeight = n.getWeight(i) + (input.get(i) - n.getWeight(i)) * getLearningRate(epoch);
                    n.setWeight(i, newWeight);
                }
            }
        }

    }

    public Neuron getWinner(List<Double> input){
        Neuron winner = neurons.get(0);
        double min = Double.POSITIVE_INFINITY;

        for(Neuron n : neurons){
            double sum = 0;

            for(int j = 0; j < inputSize; j++) {
                double d = input.get(j) - n.getWeight(j);
                sum += d * d;
            }

            sum = Math.sqrt(sum);

            if(sum < min) {
                winner = n;
                min = sum;
            }
        }

        return winner;
    }

    public double getRadius(int epoch){
        double lambda = (500 * 28)/Math.log(radius);
        return radius * Math.exp(-(epoch/lambda));
    }

    private double getLearningRate(int epoch){
        double lr = learningRate;

        if (1.0/(Math.sqrt(epoch)+1) < learningRate){
            lr = 1.0/(Math.sqrt(epoch)+1);
        }


        return lr;
    }

    /* Just for testing */
    private double getLearningRateWithLamda(int epoch){
        double lambda = (500 * inputSize)/Math.log(radius);
        double toReturn = learningRate * Math.exp(-(epoch/lambda));

        return (toReturn <= 0.0)? 0.000001 : toReturn;
    }
}
