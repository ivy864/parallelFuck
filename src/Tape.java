import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

class Tape implements Runnable {

    public class TransferException extends RuntimeException {
        TransferException (String s){
            super(s);
        }
    }
    public class ThreadException extends RuntimeException {
        ThreadException (String s) {
            super(s);
        }
    }

    private ArrayList<Integer> tape;
    private ArrayList<Character> program;
    private Hashtable<Integer, Tape> tapes;
    private Stack<Thread> threads;
    private int pointer;
    static Scanner s;
    private int transferCell;
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
        Interpreter.s.useDelimiter("");
        UniversalTape universalTape = UniversalTape.instance();
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
                this.putChar(Interpreter.s.next().charAt(0)); // bad way 2 do this prolly
            }

            else if (instruction == '(') {
                Thread thread = new Thread(this.tapes.get(pointer));
                threads.push(thread);
                thread.start();

            }
            else if (instruction == '_') {
                try {
                    threads.pop().join();
                }
                catch (EmptyStackException e) {
                    throw new ThreadException("There is no thread to join on!");
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new ThreadException("idk what happened");
                }
            }
            else if (instruction == '&') {
                this.transferCell = this.getVal();
                this.setVal(0);
            }
            else if (instruction == '^') {
                if (transferCell == -1) {
                    throw new TransferException(
                        "Cannot send null to universal tape!");
                }
                universalTape.setValue(this.getVal(), 
                    this.transferCell); 
                this.transferCell = -1;
            }
            else if (instruction == '*') {
                int cell = this.getVal();
                int val; 
                try {
                    val = UniversalTape.instance().getValue(cell);
                }
                catch (NullPointerException e) {
                    throw new TransferException("Value at position " + cell + " of universal tape is null!");
                }
                
                this.setVal(val);
            }
            /*
            else if (instruction == '%') {
                int val = universalTape.getValue(this.getVal());
                if (val == -1) {
                    throw new TransferException("Value at position " + 
                    this.getVal() + " of universal tape is null!");
                }

                this.transferCell = val;
            }
            */

            pointer++;
        }
    }

    Tape(ArrayList<Character> program, Scanner s) { 
        this.init();
        this.program = program;
    }

    Tape () {
        this.init();
        this.program = new ArrayList<Character>();
    }

    private void init() {
        this.tape = new ArrayList<Integer>();
        this.tapes = new Hashtable<Integer, Tape>();
        this.threads = new Stack<Thread>();
        this.pointer = 0;
        this.transferCell = -1;

        this.tape.add(0);
    }

    public void addInstruction(char instruction) {
        this.program.add(instruction);
    }
    public void addThread(Tape tape) {
        this.tapes.put(this.program.size() - 1, tape);
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
    private void setVal(int val) {
        tape.set(pointer, val);
    }

    void putChar(char val) {
        tape.set(pointer, (int)val);
    }
}
