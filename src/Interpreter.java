import java.io.FileReader;
import java.util.Scanner;

class Interpreter {
    static Scanner s;

    public static void main (String args[]) {

        if (args.length != 1) {
            System.out.println("Usage: Interpreter program.bf");
            return;
        }
        else if (!args[0].endsWith(".bf")) {
            System.out.println("Usage: Interpreter program.bf");
            return;
        }

        try {
            s = new Scanner(new FileReader(args[0]));
        }
        catch(Exception e) {
            e.printStackTrace(); 
            return;
        }
        
        Tape main;
        try {
            main = Preprocessor.instance().getProgram(s);
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }
        
        main.run();

        s.close();

        s = new Scanner(System.in);
    }
}
