package hopfield;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Hopfield {

    public static void main(String[] args) throws FileNotFoundException {
        Hopfield h = new Hopfield();
        Network n = new Network(25);

        h.findBestLetters();

//        List<List<Integer>> inputs = new ArrayList<>();
//
//        inputs.add(h.readLetter("A"));
//        inputs.add(h.readLetter("J"));
//        inputs.add(h.readLetter("C"));
//        inputs.add(h.readLetter("M"));
//
//        n.initialize(inputs);
//
//
//
//        List<Integer> noisy = n.noisyLetter(inputs.get(0));
//        n.loadInput(noisy);
//        h.printLetter(noisy);
//        System.out.println("-----------------------");
//        for(int i = 0; i< 100;i++){
//            n.update();
//            h.printLetter(n.getCurrentStatus());
//
//        }


    }




    public List<Integer> readLetter(String letter) throws FileNotFoundException {

        File inputFile = new File(letter);

        Scanner inputReader = new Scanner(inputFile);

        List<Integer> l = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            l.add(inputReader.nextInt());
        }

        return l;

    }

    public void printLetter(List<Integer> letter){
        for(int i = 0;i<25;i++){
            if(i!=0 && i%5==0){
                System.out.println();
            }
            if(letter.get(i).equals(1)){
                System.out.print("*");
            }
            else{
                System.out.print(" ");
            }

        }
        System.out.println();
        System.out.println();
    }

    private void findBestLetters() throws FileNotFoundException {

        List<List<Integer>> letters = new ArrayList<>();
        List<String> l = Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z");

        for(String s : l){
            letters.add(readLetter(s));
        }

//        G K T V 8


        for(int i = 0;i<letters.size();i++){
            for(int j = 0; j<letters.size();j++) {
                System.out.printf("%s %s %d\n",l.get(i),l.get(j),getProd(letters.get(i),letters.get(j)));
            }
        }

//        for(int i = 0;i<letters.size();i++){
//            for(int j = 0; j<letters.size();j++){
//                for(int k = 0; k<letters.size();k++){
//                    for (int p = 0; p<letters.size();p++){
//                        if(i!=j && i!=k && i!=p && j!=k && j!=p && k!=p){
//                            int s1 = Math.abs(getProd(letters.get(i),letters.get(j)));
//                            int s2 = Math.abs(getProd(letters.get(i),letters.get(k)));
//                            int s3 = Math.abs(getProd(letters.get(i),letters.get(p)));
//                            int s4 = Math.abs(getProd(letters.get(j),letters.get(k)));
//                            int s5 = Math.abs(getProd(letters.get(j),letters.get(p)));
//                            int s6 = Math.abs(getProd(letters.get(k),letters.get(p)));
//
//                            int s = s1+s2+s3+s4+s5+s6;
//                            if(s<10)
//                            System.out.printf("%s %s %s %s %d\n",l.get(i),l.get(j),l.get(k),l.get(p),s);
//                        }
//
//                    }
//                }
//            }
//        }


    }

    private int getProd(List<Integer> l1, List<Integer> l2){
        int sum = 0;
        for(int i = 0; i<l1.size();i++){
            sum+= l1.get(i)*l2.get(i);
        }

        return sum;
    }


}
