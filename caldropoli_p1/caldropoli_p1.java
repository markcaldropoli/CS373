import java.io.*;
import java.util.*;

public class caldropoli_p1 {
    public static void main(String[] args) {
        NFA nfa = new NFA();
        String file = "";
        String input = "";

        //Check for correct args
        if(args.length == 2) {
            file = args[0];
            input = args[1];
        } else {
            System.out.println("Invalid Input - Use java caldropoli_p1 <text-file> <input-string>");
            return;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            
            //Parse text file by line
            while((line = br.readLine()) != null) {
                String[] split = line.split("\\s+");
                String type = split[0];
                String state = split[1];

                if(type.equals("state")) {
                    if(split.length == 3) {
                        String type2 = split[2];
                        if(type2.equals("acceptstart")) {
                            nfa.setAccept(state);
                            nfa.setStart(state, input);
                        } else if(type2.equals("accept")) {
                            nfa.setAccept(state);
                        } else if(type2.equals("start")) {
                            nfa.setStart(state, input);
                        } else {
                            System.out.println("Invalid state type \"" + type2 + "\"");
                            return;
                        }
                    } else {
                        if(split[2].equals("start") && split[3].equals("accept")) {
                            nfa.setAccept(state);
                            nfa.setStart(state, input);
                        } else {
                            System.out.println("Invalid state type \"" + split[2] + " " + split[3] + "\"");
                            return;
                        }
                    }
                } else if(type.equals("transition")) {
                    nfa.setTransition(split[1] + split[2], split[3]);
                } else {
                    System.out.println("Invalid input type - Can only be state or transition");
                    return;
                }
            }

            //Build NFA
            nfa.createNFA();

            //Print Result
            nfa.print();

        } catch(IOException e) {
            System.out.println("File not found");
        }
    }
}
