package hopfield;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hopfield {

    private StringBuilder str = new StringBuilder();

    public static void main(String[] args) throws FileNotFoundException {
        Hopfield h = new Hopfield();
        Network n = new Network(25);
        PrintWriter writer = null;

        try {
            writer = new PrintWriter("noisy.txt");
        } catch(FileNotFoundException ignored){}

        List<List<Integer>> inputs = new ArrayList<>();

        inputs.add(h.readLetter("A"));
        inputs.add(h.readLetter("J"));
        inputs.add(h.readLetter("G"));
        inputs.add(h.readLetter("M"));

        n.initialize(inputs);

        /*List<Integer> notACharacter = h.readLetter("NotACharacter");
        n.loadInput(notACharacter);
        h.printLetter(notACharacter);
        for(int i = 0; i < 20; i++){
            n.update();
        }
        h.printLetter(n.getCurrentStatus());*/

        for(int i = 0; i < inputs.size(); i++){
            System.out.println("-----------------------");
            List<Integer> noisy = n.noisyLetter(inputs.get(i));
            n.loadInput(noisy);
            h.printLetter(noisy);
            h.addToString(noisy,i);
            List<Integer> status = n.getCurrentStatus();
            for(int j = 0; j < 50; j++){
                n.update();
                h.addToString(n.getCurrentStatus(), i);
                if(h.compare(n.getCurrentStatus(), status)){
                    break;
                }
            }

            h.printLetter(n.getCurrentStatus());
        }

        System.out.println("-----------------------");
    }

    private void addToString(List<Integer> status, int color){
        str.append("25\n\n");
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                str.append(String.format("%d\t%d\t%d\t%d\n", i, j, status.get(5*i+j), color));
            }
        }

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("noisy.txt", true));
            out.write(str.toString());
            out.close();
        } catch (IOException ignored) {}
    }

    private boolean compare(List<Integer> list1, List<Integer> list2) {
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(!list1.get(5 * i + j).equals(list2.get(5 * i + j))){
                    return true;
                }
            }
        }

        return false;
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
