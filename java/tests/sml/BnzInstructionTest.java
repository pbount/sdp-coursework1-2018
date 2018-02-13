package sml;

import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BnzInstructionTest {

    @Test
    public void bnzInstructionTest() {
        Machine m = new Machine();

        ArrayList<Instruction> prog = new ArrayList<>();
        prog.add(new LinInstruction("L1", 1, 20));
        prog.add(new LinInstruction("L2", 2, 30));
        prog.add(new LinInstruction("L3", 2, 30));
        prog.add(new LinInstruction("L4", 2, 30));
        prog.add(new LinInstruction("L5", 2, 30));
        prog.add(new BnzInstruction("L6", 3, "L4"));

        Labels l = new Labels();
        l.addLabel("L1");
        l.addLabel("L2");
        l.addLabel("L3");
        l.addLabel("L4");
        l.addLabel("L5");
        l.addLabel("L6");

        m.setProg(prog);
        m.setLabels(l);

        m.execute();

    }
}