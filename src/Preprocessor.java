import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

class Preprocessor {
    
    private Hashtable<String, Integer> instructions;

    public static class IllegalLoopException extends Exception {
        public IllegalLoopException(String e) {
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
        this.instructions.put("(", 2);
        this.instructions.put(")", -2);
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
}
