package sml;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class OutInstructionTest {

    @Test
    public void OutInstructionTest() {
        Machine m = new Machine();

        ArrayList<Instruction> prog = new ArrayList<>();
        prog.add(new LinInstruction("L1", 1, 20));
        prog.add(new LinInstruction("L2", 2, 30));
        prog.add(new OutInstruction("L3", 1));
        prog.add(new OutInstruction("L4", 2));
        prog.add(new OutInstruction("L5", 3));

        m.setProg(prog);
        m.execute();

        assertEquals(20, m.getRegisters().getRegister(1));
        assertEquals(30, m.getRegisters().getRegister(2));
        assertEquals(0, m.getRegisters().getRegister(3));
        assertEquals(0, m.getRegisters().getRegister(4));
    }
}