class InstructionHandler {
    static InstructionHandler theInstance;
    private Tape tape;

    private InstructionHandler() {
        this.tape = Tape.instance();
    }

    public static InstructionHandler instance() {
        if (theInstance == null) {
            theInstance = new InstructionHandler();
        }

        return theInstance;
    }

    public void handleInstruction(String instruction) 
        throws Tape.IllegalValueException {

        if (instruction.equals(">")) {
            tape.incrementPointer();
        }
        else if (instruction.equals("<")) {
            tape.decrementPointer();
        }
        else if (instruction.equals("+")) {
            tape.incrementValue();
        }
        else if (instruction.equals("-")) {
            tape.decrementValue();
        }
        else if (instruction.equals(".")) {
            tape.printChar();
        }
    }
}
