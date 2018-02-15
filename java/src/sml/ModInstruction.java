package sml;

/*
 *   This is a test instruction to verify that reflection works for
 *   the translator. This instruction adds the Modulus functionality
 *   to the SML without needing to specify it inside the translator class.
 *   As long as the naming convention remains the same (mod -> ModInstruction),
 *   the operation should be accepted and work.
 */
public class ModInstruction extends Instruction {
    private int result;
    private int op1;
    private int op2;

    public ModInstruction(String label, String opcode) {
        super(label, opcode);
    }

    public ModInstruction(String label, int result, int op1, int op2){
        this(label, "mod");
        this.result = result;
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public void execute(Machine m) {
        int value1 = m.getRegisters().getRegister(op1);
        int value2 = m.getRegisters().getRegister(op2);
        m.getRegisters().setRegister(result, value1 % value2);
    }

    @Override
    public String toString() {
        return super.toString() + " " + op1 + " % " + op2 + " = " + result;
    }
}
