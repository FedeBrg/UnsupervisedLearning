import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SOMap {

    List<Neuron> neurons;
    private int rows;
    private int cols;
    private int inputSize;

    private double learningRate;


    public SOMap(int rows,int cols, int inputSize){
        this.rows = rows;
        this.cols = cols;
        this.inputSize = inputSize;
        this.neurons = new ArrayList<>();


        for(int i = 0; i<rows;i++){
            for(int j = 0; j<cols; j++){
                Neuron n = new Neuron(i,j);
                this.neurons.add(n);
            }
        }
    }

    public Neuron getNeuronAt(int row, int col){
        return this.neurons.get(row*cols + col);
    }

    public void initializeNeuronsRandom(){
        Random r = new Random();
        for(Neuron n : neurons){
            for (int i = 0; i < n.getWeights().size();i++){
                n.setWeight(i,r.nextDouble());
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
                if(d<10){ // DEBERIA SER EL RADIO VARIABLE SEGUN EPOCA
                    for (int i = 0; i<inputSize;i++){
                        double newWeight=(input.get(i) - n.getWeight(i)) * learningRate; //DEBERIA SER LR VARIABLE SEGUN EPOCA
                        n.setWeight(i, newWeight);
                    }
                }
            }
        }


    }



}
