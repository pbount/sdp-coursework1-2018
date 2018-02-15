package sml;

public class BnzInstruction extends Instruction{
    private int register;
    private String jumpTo;
    private int position;

    public BnzInstruction(String label, String opcode) {
        super(label, opcode);
    }

    public BnzInstruction(String label, int register, String jumpTo){
        this(label, "bnz");
        this.register = register;
        this.jumpTo = jumpTo;
    }

    @Override
    public void execute(Machine m) {
        if (m.getRegisters().getRegister(register) != 0) {
            position = m.getLabels().indexOf(jumpTo);
            m.setPc(position);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " " + register + " jump to " + jumpTo;
    }
}
