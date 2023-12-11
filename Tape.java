import java.util.ArrayList;

class Tape {

    public static class IllegalValueException extends Exception {
        public IllegalValueException(String e) {
            super(e);
        }
    }

    private ArrayList<Integer> tape;
    private int pointer;
    private static Tape theInstance;
    
    public static Tape instance() {
        return theInstance;
    }

    private Tape() { 
        init();
    }

    private void init() {
        this.tape = new ArrayList<Integer>();
        this.pointer = 0;

        this.tape.add(0);
    }

    void incrementPointer() {
        pointer += 1;
        if (tape.size() - 1 != pointer) {
            tape.add(0);
        }
    }

    void decrementPointer() {
        if (pointer != 0) {
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
    void decrementValue() throws IllegalValueException {
        int val = tape.get(pointer);

        if (val != 0) {
            tape.set(pointer, val - 1);
        }
        else {
            throw new IllegalValueException("Can't decrement value at pointer when value is 0!");
        }
    }

    void printChar() {
        int val = tape.get(pointer);
        Interpreter.printChar("" + ((char) val));
    }

    void setValue(int val) {
        tape.set(pointer, val);
    }
}
