package kohonen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kohonen {

    public static void main(String[] args) throws FileNotFoundException {

        Kohonen k = new Kohonen();
        List<List<Double>> input = k.readFiles();
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
            stds.add(Math.sqrt(sum/input.size()));
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

        SOMap map = new SOMap(7,7,7,0.1,1);
        map.initializeNeuronsRandom();

        for(int i = 0; i<10000;i++){
            for(List<Double> l : normalize){
                map.train(l);
            }
        }

        int[][] out = new int[7][7];

        for(List<Double> l : normalize){
            Neuron n = map.getWinner(l);
            out[n.getX()][n.getY()] +=1;
        }

        for(int [] a : out){
            for(int b : a){
                System.out.printf("%d\t",b);
            }
            System.out.println();
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


    private double calculateStandardDev(List<Double> input)
    {
        double sum = 0.0, standardDeviation = 0.0;
        int length = input.size();

        for(double num : input) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: input) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
    }

    private double calculateAverage(List<Double> input){
        double sum = 0.0;
        int length = input.size();

        for(double num : input) {
            sum += num;
        }

        return sum/length;
    }



}
