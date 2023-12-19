import java.util.Hashtable;

class UniversalTape {
    static UniversalTape theInstance;
    
    private Hashtable<Integer, Integer> tape;

    private UniversalTape() {
        this.tape = new Hashtable<Integer, Integer>();
    }

    public static UniversalTape instance() {
        if (theInstance == null) {
            theInstance = new UniversalTape();
        }

        return theInstance;
    }

    int getValue(int index) {
        return this.tape.get(index);
    }
    void setValue(int index, int val) {
        this.tape.put(index, val);
    }
}
