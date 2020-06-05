package hopfield;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hopfield {

    public static void main(String[] args) throws FileNotFoundException {
        Hopfield h = new Hopfield();
        Network n = new Network(25);

        List<List<Integer>> inputs = new ArrayList<>();

        inputs.add(h.readLetter("A"));
        inputs.add(h.readLetter("J"));
        inputs.add(h.readLetter("C"));
        inputs.add(h.readLetter("M"));

        n.initialize(inputs);



        List<Integer> noisy = n.noisyLetter(inputs.get(0));
        n.loadInput(noisy);
        h.printLetter(noisy);
        System.out.println("-----------------------");
        for(int i = 0; i< 100;i++){
            n.update();
            h.printLetter(n.getCurrentStatus());

        }


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

}
