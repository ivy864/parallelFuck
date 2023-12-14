import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

class Preprocessor {
    
    private Hashtable<String, Integer> instructions;

    public static class BracketMatchingException extends Exception {
        public BracketMatchingException(String e) {
            super(e);
        }
    }

    static Preprocessor theInstance;

    private Preprocessor() {
        instructions = new Hashtable<String, Integer>();

        this.instructions.put(">", 0);
        this.instructions.put("<", 0);
        this.instructions.put("+", 0);
        this.instructions.put("-", 0);
        this.instructions.put("[", 1);
        this.instructions.put("]", -1);
        this.instructions.put(".", 0);
        this.instructions.put(",", 0);

        // new things
        this.instructions.put("(", 0);
        this.instructions.put(")", 0);
        this.instructions.put("_", 0);
        

        this.instructions.put("&", 0);
        this.instructions.put("^", 0);
        this.instructions.put("*", 0);
        this.instructions.put("%", 0);
    }

    public static Preprocessor instance() {
        if (theInstance == null) {
            theInstance = new Preprocessor();
        }
        return theInstance;
    }

    public Tape getProgram(Scanner s) throws BracketMatchingException {

        String inst;
        s.useDelimiter("");
        Tape mainTape = new Tape();
        int loops = 0;

        Stack<Tape> tapeStack = new Stack<Tape>();
        tapeStack.push(mainTape);

        while (s.hasNext()) {
            inst = s.next();
            if (this.instructions.get(inst) != null) {
                try {
                    tapeStack.peek().addInstruction(inst.charAt(0));
                }
                catch(EmptyStackException e) {
                    throw new BracketMatchingException("Thread ended, but not started!");
                }

                if (inst.equals("(")) {
                    Tape newTape = new Tape();
                    tapeStack.peek().addThread(newTape);
                    tapeStack.push(newTape);
                }
                else if (inst.equals(")")) {
                    tapeStack.pop();
                }
                loops += this.instructions.get(inst);
            }
        }
        
        if (loops < 0) {
            throw new BracketMatchingException("Loop ended but not started!");
        }
        else if (loops > 0) {
            throw new BracketMatchingException("Loop started but not ended!");
        }

        try {
            if (tapeStack.pop() != mainTape) {
                throw new BracketMatchingException("Thread started, but not ended!");
            }
        }
        catch(EmptyStackException e) {
                throw new BracketMatchingException("Thread ended, but not started!");
        }

        return mainTape;
    }

    /*
    public ArrayList<Character> getProgram(Scanner s) throws IllegalLoopException {
        ArrayList<Character> program = new ArrayList<Character>();
        
        String inst;
        s.useDelimiter("");
        int loops = 0;
        while (s.hasNext()) {
            inst = s.next();
            if (this.instructions.get(inst) != null) {
                program.add(inst.charAt(0));
                loops += this.instructions.get(inst);
            }
        }

        if (loops < 0) {
            throw new IllegalLoopException("Loop ended but not started!");
        }
        else if (loops > 0) {
            throw new IllegalLoopException("Loop started but not ended!");
        }

        return program;
    }
    */
}
