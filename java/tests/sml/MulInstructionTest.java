package sml;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class MulInstructionTest {
    @Test
    public void MulInstructionTest() {
        Machine m = new Machine();

        ArrayList<Instruction> prog = new ArrayList<>();
        prog.add(new LinInstruction("L1", 1, 10));
        prog.add(new LinInstruction("L2", 2, 20));
        prog.add(new MulInstruction("L3", 3, 2,1));

        m.setProg(prog);
        m.execute();

        assertEquals(10, m.getRegisters().getRegister(1));
        assertEquals(20, m.getRegisters().getRegister(2));
        assertEquals(200,m.getRegisters().getRegister(3));
        assertEquals(0,m.getRegisters().getRegister(4));
    }

    @Test
    public void SubInstructionWithMul(){
        Machine m = new Machine();

        ArrayList<Instruction> prog = new ArrayList<>();
        prog.add(new LinInstruction("L1", 1, 10));
        prog.add(new LinInstruction("L2", 2, 20));
        prog.add(new MulInstruction("L3", 3, 2,1));
        prog.add(new LinInstruction("L4", 4, 50));
        prog.add(new SubInstruction("L5", 5, 3, 4));

        m.setProg(prog);
        m.execute();

        assertEquals(10, m.getRegisters().getRegister(1));
        assertEquals(20, m.getRegisters().getRegister(2));
        assertEquals(200,m.getRegisters().getRegister(3));
        assertEquals(50,m.getRegisters().getRegister(4));
        assertEquals(150,m.getRegisters().getRegister(5));
        assertEquals(0,m.getRegisters().getRegister(6));
    }
}