package kohonen;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kohonen {

    public static void main(String[] args) throws FileNotFoundException {
        Kohonen k = new Kohonen();
        List<List<Double>> input = k.readFiles();
        int inputSize = input.get(0).size();
        System.out.printf("Input file -- %d rows, %d cols\n", input.size(), inputSize);

        int rows = 3;
        int cols = 3;
        System.out.printf("Kohonen network -- %d rows, %d cols\n", rows, cols);

        PrintWriter writer = null;

        try {
            writer = new PrintWriter("average.txt");
        } catch(FileNotFoundException ignored){}

        if(writer != null){
            writer.print("");
            writer.close();
        }

        List<Double> avgs = new ArrayList<>();
        List<Double> stds = new ArrayList<>();

        for(int i = 0; i < input.get(0).size(); i++){
            double sum = 0.0;

            for(List<Double> doubles : input) {
                sum += doubles.get(i);
            }

            avgs.add(sum/input.size());
        }

        for(int i = 0; i < input.get(0).size(); i++){
            double sum = 0.0;

            for(List<Double> doubles : input) {
                sum += Math.pow(doubles.get(i) - avgs.get(i), 2);
            }

            stds.add(Math.sqrt(sum/(input.size()-1)));
        }

        List<List<Double>> normalize = new ArrayList<>();
        List<Double> tmp;

        for(List<Double> l : input){
            tmp = new ArrayList<>();

            for(int i = 0; i < l.size(); i++){
                tmp.add((l.get(i)-avgs.get(i))/stds.get(i));
            }

            normalize.add(tmp);
        }

        SOMap map = new SOMap(rows,cols,inputSize,0.8, Math.sqrt(rows * cols));
        map.initializeNeuronsExamples(normalize);

        for(int i = 0; i < 500 * inputSize; i++){
            for(List<Double> doubles : normalize) {
                map.train(doubles,i);
            }

            List<Neuron> neurons = map.neurons;
            double distance;
            double totalSum = 0.0;
            int totalDistances = 0;
            double radius = map.getRadius(i);
            System.out.println(radius);
            StringBuilder str = new StringBuilder();
            str.append("9\n\n");

            for(Neuron n1 : neurons){
                totalDistances = 0;
                totalSum = 0.0;

                for(Neuron n2 : neurons){
                    distance = n1.distance(n2);
                    if(distance != 0 && distance < radius){
                        totalSum += k.calculateDistance(n1,n2);
                        totalDistances++;
                    }
                }

                str.append(String.format("%d\t%d\t%f\n", n1.getX(), n1.getY(), totalSum/totalDistances));
            }

            /* Just for average */
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("average.txt", true));
                out.write(str.toString());
                out.close();
            } catch (IOException ignored) {}
        }

        int[][] out = new int[rows][cols];

        for(List<Double> l : normalize){
            Neuron n = map.getWinner(l);
            out[n.getX()][n.getY()] += 1;
        }

        StringBuilder str = new StringBuilder();
        str.append("9\n\n");

        /* Just for result */
        try {
            writer = new PrintWriter("result.txt");
        } catch(FileNotFoundException ignored){}

        System.out.println("Kohonen network -- Output:");

        int i = 0, j = 0;

        for(int [] a : out){
            for(int b : a){
                str.append(String.format("%d\t%d\t%d\n", i, j, b));
                j++;
                System.out.printf("%d\t",b);
            }
            j = 0;
            i++;
            System.out.println();
        }

        try {
            BufferedWriter o = new BufferedWriter(new FileWriter("result.txt", true));
            o.write(str.toString());
            o.close();
        } catch (IOException ignored) {}
    }

    private double calculateDistance(Neuron n1, Neuron n2) {
        double acum = 0.0;

        for(int i = 0; i < n1.getWeights().size(); i++){
            acum += Math.pow((n1.getWeight(i) - n2.getWeight(i)), 2);
        }

        return Math.sqrt(acum);
    }

    private List<List<Double>> readFiles() throws FileNotFoundException {
        File inputFile = new File("europe");
        Scanner inputReader = new Scanner(inputFile);

        List<List<Double>> l = new ArrayList<>();
        List<Double> a;

        while(inputReader.hasNext()){
            a = new ArrayList<>();

            for(int i = 0; i < 7; i++){
                a.add(inputReader.nextDouble());
            }

            l.add(a);
        }

        return l;
    }
}
