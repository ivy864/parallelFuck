
class Interpreter {
    public static void main (String args[]) {
        java.util.Scanner s = new java.util.Scanner(System.in);
        String in;
        InstructionHandler instHandler = InstructionHandler.instance();

        s.useDelimiter("");
        
        while (s.hasNext()) {
            in = s.next();
            instHandler.handleInstruction(in); 
        }

        s.close();
    }

    static void printChar(String charecter) {
        System.out.print(charecter);
    }
}
