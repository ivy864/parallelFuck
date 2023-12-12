import java.util.ArrayList;

class Preprocessor {

    public static class IllegalLoopException extends Exception {
        public IllegalLoopException(String e) {
            super(e);
        }
    }

    static Preprocessor theInstance;

    private Preprocessor() {}

    public static Preprocessor instance() {
        if (theInstance == null) {
            theInstance = new Preprocessor();
        }
        return theInstance;
    }


    public ArrayList<Character> getProgram() throws IllegalLoopException {
        return new ArrayList<Character>();
    }
}
