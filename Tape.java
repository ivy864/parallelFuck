import java.util.ArrayList;

class Tape {

    private ArrayList<Integer> tape;
    private int pointer;
    private static Tape theInstance;
    
    public static Tape instance() {
        if (theInstance == null) {
            theInstance = new Tape();
        }

        return theInstance;
    }

    private Tape() { 
        this.init();
    }

    private void init() {
        this.tape = new ArrayList<Integer>();
        this.pointer = 0;

        this.tape.add(0);
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
