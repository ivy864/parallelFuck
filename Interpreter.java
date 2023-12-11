import java.util.ArrayList;

class Interpreter {
    public static void main (String args[]) {
        java.util.Scanner s = new java.util.Scanner(System.in);
        String in;
        Tape tape = new Tape();

        s.useDelimiter("");

        while (s.hasNext()) {
            in = s.next();
        }

        s.close();
    }
}
