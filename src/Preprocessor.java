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

        // enum might be better here? idk
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

    /**
     * 
     */
    public Tape getProgram(Scanner s) throws BracketMatchingException {

        String inst;
        s.useDelimiter("");
        Tape mainTape = new Tape();
        Stack<String> brackets = new Stack<String>();

        Stack<Tape> tapeStack = new Stack<Tape>();
        tapeStack.push(mainTape);

        while (s.hasNext()) {
            inst = s.next();

            if (this.instructions.get(inst) != null) {
                try {
                    tapeStack.peek().addInstruction(inst.charAt(0));
                }
                catch(EmptyStackException e) {
                }

                if (inst.equals("(")) {
                    brackets.push("(");

                    Tape newTape = new Tape();
                    tapeStack.peek().addThread(newTape);
                    tapeStack.push(newTape);
                }
                else if (inst.equals("[")) {
                    brackets.push("[");
                }

                else if (inst.equals(")")) {
                    // I don't like this
                    try {
                        if (!brackets.pop().equals("(")) {
                            throw new BracketMatchingException("Thread closed, but not started!");
                        }
                    }
                    catch (EmptyStackException e) {
                        throw new BracketMatchingException("Thread closed, but not started!");
                    }

                    tapeStack.pop();
                }
                else if (inst.equals("]")) {
                //&& (brackets.empty() || 
                //    !brackets.pop().equals("["))) {
                    try {
                        if (!brackets.pop().equals("[")) {
                            throw new BracketMatchingException("Loop closed, but not started!");
                        }
                    }
                    catch (EmptyStackException e) {
                        throw new BracketMatchingException("Loop closed, but not started!");
                    }
                }

            }
        }
        
        if (!brackets.empty()) {
            String bracket = brackets.pop();
            if (bracket.equals("[")) {
                throw new BracketMatchingException("Loop opened, but not closed!");
            }
            else if (bracket.equals("(")) {
                throw new BracketMatchingException("Thread opened, but not closed!");
            }
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
