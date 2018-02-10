package sml;

import org.junit.Test;

import static org.junit.Assert.*;

public class TranslatorTest {

    @Test
    public void basicInstruction() {
        Instruction expectedInstruction = new AddInstruction("L1", 30, 100,200);
        assertEquals(expectedInstruction.toString(), "L1: add 100 + 200 to 30");
    }

}