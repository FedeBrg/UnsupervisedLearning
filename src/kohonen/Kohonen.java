package kohonen;

import java.io.*;
import java.util.*;

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

        System.out.println(normalize);

        SOMap map = new SOMap(rows,cols,inputSize,0.8, Math.sqrt(rows * cols));
//        map.initializeNeuronsExamples(normalize);
        map.initializeNeuronsRandom();
        Random r = new Random();
        for(int i = 0; i < 500 * 28; i++){
//            for(List<Double> doubles : normalize) {
//                map.train(doubles,i);
                map.train(normalize.get(r.nextInt(normalize.size())),i);
//            }



            List<Neuron> neurons = map.neurons;
            double distance;
            double totalSum = 0.0;
            int totalDistances = 0;
            double radius = map.getRadius(i);
            //System.out.println(radius);
            StringBuilder str = new StringBuilder();
            str.append(rows * cols).append("\n\n");

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
        List<String> countries = k.readCountries();
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


        StringBuilder str = new StringBuilder();
        str.append(rows * cols).append("\n\n");

        /* Just for result */
        try {
            writer = new PrintWriter("result.txt");
        } catch(FileNotFoundException ignored){}

        System.out.println("Kohonen network -- Output:");

        //int i = 0, j = 0;
        for(int i = 0; i< rows;i++){
            for(int j = 0;j<cols;j++){
                str.append(String.format("%d\t%d\t%d\n", i, j, out[i][j]));
                System.out.printf("%d\t",out[i][j]);
            }
            System.out.println();
        }
        System.out.println(m);


//        for(int [] a : out){
//            for(int b : a){
//                str.append(String.format("%d\t%d\t%d\n", i, j, b));
//                j++;
//                System.out.printf("%d\t",b);
//            }
//            j = 0;
//            i++;
//            System.out.println();
//        }

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

    private List<String> readCountries() throws FileNotFoundException {
        File inputFile = new File("countries");

        Scanner inputReader = new Scanner(inputFile);

        List<String> l = new ArrayList<>();

        while(inputReader.hasNext()){
            l.add(inputReader.nextLine());
        }

        return l;
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
