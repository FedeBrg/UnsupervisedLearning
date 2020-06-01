package oja2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
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


            for (int i = 0; i < 10000; i++) {
                for(int j = 0;j<input.size();j++) {

                n.updateWeights(normalize.get(j));
                    System.out.println(n.getWeights());

            }
        }

        System.out.println(n.getWeights());

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
}
