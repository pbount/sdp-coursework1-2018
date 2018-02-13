package sml;

public class OutInstruction extends Instruction{
    private int register;

    public OutInstruction(String label, String opcode) {
        super(label, opcode);
    }

    public OutInstruction(String label, int register){
        this(label, "out");
        this.register = register;
    }

    @Override
    public void execute(Machine m) {
        int value1 = m.getRegisters().getRegister(register);
        System.out.println("R" + register + ": " + value1);
    }

    @Override
    public String toString() {
      //  return super.toString() + result;
        return super.toString();
    }
}
