import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

class Tape implements Runnable{

    private ArrayList<Integer> tape;
    private ArrayList<Character> program;
    private ArrayList<Tape> threads;
    private int pointer;
    private Scanner s;
    //private static Tape theInstance;

        
    
    /*
    public static Tape instance() {
        if (theInstance == null) {
            theInstance = new Tape();
        }

        return theInstance;
    }
    */

    public void run() {
        s.useDelimiter("");

        int pointer = 0;
        
        Stack<Integer> loops = new Stack<Integer>();

        while (program.size() > pointer) {
            char instruction = program.get(pointer);
            if (instruction == '>') {
                this.incrementPointer();
            }
            else if (instruction == '<') {
                this.decrementPointer();
            }
            else if (instruction == '+') {
                this.incrementValue();
            }
            else if (instruction == '-') {
                this.decrementValue();
            }
            else if (instruction == '[') {
                loops.push(pointer);
            }
            else if (instruction == ']') {
                if (this.getVal() == 0) {
                    loops.pop();
                }
                else {
                    pointer = loops.peek();
                }
            }
            else if (instruction == '.') {
                System.out.print("" + this.getChar());
            }
            else if (instruction == ',') {
                this.putChar(s.next().charAt(0)); // bad way 2 do this prolly
            }

            pointer++;
        }
    }

    Tape(ArrayList<Character> program, Scanner s) { 
        this.init();
        this.program = program;
        this.s = s;
    }

    Tape () {
        this.init();
        this.program = new ArrayList<Character>();
    }

    private void init() {
        this.tape = new ArrayList<Integer>();
        this.pointer = 0;

        this.tape.add(0);
    }

    public void addInstruction(char instruction) {
        this.program.add(instruction);
    }

    void incrementPointer() {
        pointer += 1;

        if (tape.size() == pointer) {
            tape.add(0);
        }
    }

    void decrementPointer() {
        if (pointer > 0) {
            pointer -= 1;
        }
        else {
            tape.add(0, 0);
        }
    }

    void incrementValue() {
        int val = tape.get(pointer);
        tape.set(pointer, val + 1);
    }
    void decrementValue() {
        int val = tape.get(pointer);
        tape.set(pointer, val - 1);
    }

    char getChar() {
        int val = tape.get(pointer);

        return ((char) val);
    }
    int getVal() {
        return tape.get(pointer);
    }

    void putChar(char val) {
        tape.set(pointer, (int)val);
    }
}
