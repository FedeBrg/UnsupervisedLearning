package oja;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Oja {

    public static void main(String[] args) throws FileNotFoundException {

        Oja o = new Oja();
        List<List<Double>> input = o.readFiles();
        List<Double> avgs = new ArrayList<>();
        List<Double> stds = new ArrayList<>();

        for(int i = 0; i<input.get(0).size();i++){
            double sum = 0.0;
            for(int j = 0; j<input.size();j++){
                sum += input.get(j).get(i);
            }
            avgs.add(sum/input.size());
        }

        for(int i = 0; i<input.get(0).size();i++){
            double sum = 0.0;
            for(int j = 0; j<input.size();j++){
                sum += Math.pow(input.get(j).get(i)-avgs.get(i),2);
            }
            stds.add(Math.sqrt(sum/(input.size()-1)));
        }

        List<List<Double>> normalize = new ArrayList<>();
        List<Double> tmp;

        for(List<Double> l : input){
            tmp = new ArrayList<>();
            for(int i = 0; i< l.size(); i++){
                tmp.add((l.get(i)-avgs.get(i))/stds.get(i));
            }
            normalize.add(tmp);
        }

        Neuron n = new Neuron(normalize.get(0).size());
        Random r = new Random();

        List<String> countries= o.readCountries();

        for (int i = 0; i < 100000; i++) {
            n.updateWeights(normalize.get(r.nextInt(normalize.size())),i+1);
        }

        System.out.println("-- Result (Y1) --");
        for(int i = 0; i < n.getWeights().size(); i++){
            System.out.println("X" + i + " = " + n.getWeights().get(i));
        }

        for(int i = 0; i< normalize.size();i++){
            double sum = 0;
            for(int j = 0;j<n.getWeights().size();j++){
                sum+=normalize.get(i).get(j)*n.getWeights().get(j);
            }
            System.out.printf("%s: %f\n",countries.get(i),sum);
        }

    }


    private List<List<Double>> readFiles() throws FileNotFoundException {

        File inputFile = new File("europe");

        Scanner inputReader = new Scanner(inputFile);

        List<List<Double>> l = new ArrayList<>();
        List<Double> a;

        while(inputReader.hasNext()){
            a = new ArrayList<>();
            for(int i = 0;i<7;i++){
                a.add(inputReader.nextDouble());
            }
            l.add(a);
        }

        return l;

    }

    private List<String> readCountries() throws FileNotFoundException {
        File inputFile = new File("countries");

        Scanner inputReader = new Scanner(inputFile);

        List<String> l = new ArrayList<>();

        while(inputReader.hasNext()){
            l.add(inputReader.nextLine());
        }

        return l;
    }
}
