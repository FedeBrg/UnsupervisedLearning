import java.util.ArrayList;
import java.util.List;

public class Neuron {

    private int x;
    private int y;
    private List<Double> weights;
    private double output;

    public Neuron(int x, int y){
        this.x = x;
        this.y = y;
        weights = new ArrayList<>();
    }


    public double distance(Neuron other){
        int dx = x - other.x;
        int dy = y - other.y;

        return Math.sqrt(dx*dx + dy*dy);
    }

    public double getWeight(int j){
        return weights.get(j);
    }

    public void setWeight(int j, double w){
        this.weights.set(j,w);
    }

    public List<Double> getWeights(){
        return weights;
    }

    public double getOutput(){
        return output;
    }



}
