import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

class Interpreter {
    public static void main (String args[]) {
        Scanner s;

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
        
        ArrayList<Character> program;
        try {
            program = Preprocessor.instance().getProgram(s);
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }
        
        s.close();

        s = new Scanner(System.in);
    }
}
