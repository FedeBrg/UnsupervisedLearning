package kohonen;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

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

        List<String> countries = k.readCountries();

        SOMap map = new SOMap(3,3,7,0.1,1);
//        map.initializeNeuronsRandom();
        map.initializeNeuronsExamples(normalize);
        Random r = new Random();
        for(int i = 0; i<1000;i++){
            for(int j = 0; j<normalize.size();j++){
                map.train(normalize.get(j));
            }
        }

        int[][] out = new int[3][3];

        Map<String,List<String>> m = new HashMap<>();

        for(int i = 0; i< normalize.size();i++){
            Neuron n = map.getWinner(normalize.get(i));
            String key = String.format("%d%d",n.getX(),n.getY());
            if(!m.containsKey(key)){
                List<String> a  = new ArrayList<>();
                a.add(countries.get(i));
                m.put(key,a);
            }
            else{
                m.get(key).add(countries.get(i));
            }
//            System.out.printf("%d %d\n",n.getX(),n.getY());
            out[n.getX()][n.getY()] +=1;
        }

        System.out.println(m);

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

    private List<String> readCountries() throws FileNotFoundException {
        File inputFile = new File("countries");

        Scanner inputReader = new Scanner(inputFile);

        List<String> l = new ArrayList<>();

        while(inputReader.hasNext()){
            l.add(inputReader.nextLine());
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
