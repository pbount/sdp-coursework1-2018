package sml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
 *  The translator of an SML program.
 */
public class Translator {

    private static final String PATH = System.getProperty("user.dir") + "/sml/";

    /*
     *  word + line is the part of the current line that's not yet processed.
     *  word has no whitespace. If word and line are not empty, line begins WITH whitespace!
     */
    private String line = "";
    private Labels labels;                  // The labels of the program being translated
    private ArrayList<Instruction> program; // The program to be created
    private String fileName;                // source file of SML code

    public Translator(String fileName) {
        this.fileName = PATH + fileName;
    }



    /**
     *   Translates the small program in the file, into lab (the labels) and
     *   prog (the program). returns true if no errors are detected.
     *
     * @param lab       The labels of the SML probram in order.
     * @param prog      The ArrayList of instructions representing the program in order.
     * @return          True if no problems are found and False otherwise.
     * @throws NoSuchMethodException
     */
    public boolean readAndTranslate(Labels lab, ArrayList<Instruction> prog) throws NoSuchMethodException {

        try (Scanner sc = new Scanner(new File(fileName))) {
            // Scanner attached to the file chosen by the user
            labels = lab;
            labels.reset();
            program = prog;
            program.clear();

            try {
                line = sc.nextLine();
            } catch (NoSuchElementException ioE) {
                return false;
            }

            // Each iteration processes line and reads the next line into line
            while (line != null) {
                // Store the label in label
                String label = scan();

                if (label.length() > 0) {
                    Instruction ins = getInstruction(label);
                    if (ins != null) {
                        labels.addLabel(label);
                        program.add(ins);
                    }
                }

                try {
                    line = sc.nextLine();
                } catch (NoSuchElementException ioE) {
                    return false;
                }
            }
        } catch (IOException ioE) {
            System.out.println("File: IO error " + ioE.getMessage());
            System.exit(-1);
            return false;
        }
        return true;
    }

    /**
     *  line should consist of an SML instruction, with its label already
     *  removed. Translate line into an instruction with label "label"
     *  and return the instruction.
     *
     * @param label         Receives a label which represents the label of the instruction
     *                      inside the program.
     * @return              Returns a properly instantiated instruction using the label,
     *                      the instruction name and the remaining line which should
     *                      contain the registers for the operation.
     * @throws NoSuchMethodException
     */
    public Instruction getInstruction(String label) throws NoSuchMethodException {
        int s1; // Possible operands of the instruction
        int s2;
        int r;
        String x;

        if (line.equals(""))
            return null;

        String ins = scan();

        TranslatorWithReflection t = new TranslatorWithReflection();

        /*
        try {

            t.reflect(label,ins, line);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        switch (ins) {
            case "add":
                r = scanInt();
                s1 = scanInt();
                s2 = scanInt();
                return new AddInstruction(label, r, s1, s2);
            case "lin":
                r = scanInt();
                s1 = scanInt();
                return new LinInstruction(label, r, s1);
            case "sub":
                r = scanInt();
                s1 = scanInt();
                s2 = scanInt();
                return new SubInstruction(label, r, s1, s2);
            case "mul":
                r = scanInt();
                s1 = scanInt();
                s2 = scanInt();
                return new MulInstruction(label, r, s1, s2);
            case "div":
                r = scanInt();
                s1 = scanInt();
                s2 = scanInt();
                return new DivInstruction(label, r, s1, s2);
            case "out":
                r = scanInt();
                return new OutInstruction(label, r);
            case "bnz":
                r = scanInt();
                x = scan();
                return new BnzInstruction(label, r, x);
        }
        */

        return t.reflect(label, ins, line);
    }

    /**
     * Returns the first word of line and removes the returned word from line. If there is no
     * word, returns ""
     *
     * @return          Returns the first word of the String "line" and removes it
     *                  from the variable. If no words are found returns "".
     */
    private String scan() {
        line = line.trim();
        if (line.length() == 0)
            return "";

        int i = 0;
        while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
            i = i + 1;
        }
        String word = line.substring(0, i);
        line = line.substring(i);
        return word;
    }

    /**
     * Returns the first word of line as an integer and removes the returned word
     * from line. If there is no word, returns Integer.MAX_VALUE
     *
     * @return          Returns the first word of the String "line" as an integer and removes it
     *                  from the variable. If no words are found returns Integer.MAX_VALUE.
     */
    private int scanInt() {
        String word = scan();
        if (word.length() == 0) {
            return Integer.MAX_VALUE;
        }

        try {
            return Integer.parseInt(word);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
}
