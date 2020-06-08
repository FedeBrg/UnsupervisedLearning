package hopfield;

import java.util.List;

public class Neuron {

    private List<Double> weights;
    private int id;
    private int state;

    public Neuron(int id){
        this.id = id;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }


}
