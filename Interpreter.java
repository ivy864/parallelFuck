import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

class Interpreter {
    public static void main (String args[]) {
        Scanner s;

        if (args.length != 1) {
            System.out.println("Usage: Interpreter program.bf");
            return;
        }
        else if (!args[0].endsWith(".bf")) {
            System.out.println("Usage: Interpreter program.bf");
            return;
        }

        try {
            s = new Scanner(new FileReader(args[0]));
        }
        catch(Exception e) {
            e.printStackTrace(); 
            return;
        }
        
        ArrayList<Character> program;
        try {
            program = Preprocessor.instance().getProgram(s);
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }
        
        s.close();

        s = new Scanner(System.in);
        int pointer = 0;

        Tape tape = Tape.instance();
        
        Stack<Integer> loops = new Stack<Integer>();

        while (program.size() > pointer) {
            char instruction = program.get(pointer);
            if (instruction == '>') {
                tape.incrementPointer();
            }
            else if (instruction == '<') {
                tape.decrementPointer();
            }
            else if (instruction == '+') {
                tape.incrementValue();
            }
            else if (instruction == '-') {
                tape.decrementValue();
            }
            else if (instruction == '[') {
                loops.push(pointer);
            }
            else if (instruction == ']') {
                if (tape.getVal() == 0) {
                    loops.pop();
                }
                else {
                    pointer = loops.peek();
                }
            }
            else if (instruction == '.') {
                System.out.print("" + tape.getChar());
            }
            else if (instruction == ',') {
                tape.putChar(s.nextLine().charAt(0)); // bad way 2 do this prolly
            }

            pointer++;
        }
    }
}
