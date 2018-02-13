package sml;

public abstract class Instruction {
    protected String label;
    protected String opcode;

    // Constructor: an instruction with label l and opcode op
    // (op must be an operation of the language)

    public Instruction(String label, String opcode) {
        this.label = label;
        this.opcode = opcode;
    }

    // = the representation "label: opcode" of this Instruction

    @Override
    public String toString() {
        return label + ": " + opcode;
    }

    // Execute this instruction on machine m.

    public abstract void execute(Machine m);
}
