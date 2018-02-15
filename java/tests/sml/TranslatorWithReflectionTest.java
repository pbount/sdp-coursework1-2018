package sml;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.junit.Assert.*;

public class TranslatorWithReflectionTest {
    private TranslatorWithReflection tr = new TranslatorWithReflection();

    @Before
    public void setup(){

    }

    @Test
    public void isNumericTest() {

        assertEquals(false, tr.isNumeric("Test"));
        assertEquals(true, tr.isNumeric("1234"));
        assertEquals(false, tr.isNumeric("T1234"));
        assertEquals(false, tr.isNumeric(""));
    }

    @Test
    public void getInitArgsTest(){
        assertArrayEquals(new Object[]{"String",1234},tr.getInitArgs(new String[]{"String","1234"}));
        assertArrayEquals(new Object[]{"String","Number"},tr.getInitArgs(new String[]{"String","Number"}));
        assertArrayEquals(new Object[]{},tr.getInitArgs(new String[]{}));
        assertArrayEquals(new Object[]{"String","Number1234"},tr.getInitArgs(new String[]{"String","Number1234"}));
    }

    @Test
    public void concatArraysTest(){
        assertArrayEquals(new String[]{}, tr.concatArrays(new String[]{}, new String[]{}));
        assertArrayEquals(new String[]{"Test"}, tr.concatArrays(new String[]{}, new String[]{"Test"}));
        assertArrayEquals(new String[]{"Test2"}, tr.concatArrays(new String[]{"Test2"}, new String[]{}));
        assertArrayEquals(new String[]{"Test","Test2"}, tr.concatArrays(new String[]{"Test"}, new String[]{"Test2"}));
        assertArrayEquals(new String[]{"Test","Test2","Test3","Test4","Test5"}, tr.concatArrays(new String[]{"Test","Test2","Test3"}, new String[]{"Test4","Test5"}));
    }

    @Test
    public void getTypesTest(){
        assertArrayEquals(new Class<?>[]{String.class}, tr.getTypes(new Object[]{"Test"}));
        assertArrayEquals(new Class<?>[]{int.class}, tr.getTypes(new Object[]{1234}));
        assertArrayEquals(new Class<?>[]{String.class,int.class}, tr.getTypes(new Object[]{"T1234", 1234}));
        assertArrayEquals(new Class<?>[]{String.class,int.class,int.class}, tr.getTypes(new Object[]{"T1234", 1234,"1234"}));
    }

    @Test
    public void getConstructorTest() throws NoSuchMethodException {

        Constructor base = AddInstruction.class.getConstructor(String.class, int.class, int.class, int.class);

        Class<?>[] consArgs = {String.class, int.class, int.class, int.class};
        Class<?> addInsShouldWork = AddInstruction.class;
        Constructor addConsShouldWork = tr.getConstructor(addInsShouldWork,consArgs);

    }

    @Test(expected = NoSuchMethodException.class)
    public void getConstructorNoSuchMethodExceptionTest() throws NoSuchMethodException {

        Constructor base = AddInstruction.class.getConstructor(String.class, int.class, int.class, int.class);

        Class<?>[] consArgs1 = {String.class, int.class, int.class};
        Class<?> addInsShouldNotWork = AddInstruction.class;
        Constructor addConsShouldNotWork = tr.getConstructor(addInsShouldNotWork,consArgs1);

        assertEquals(base,addInsShouldNotWork);
        assertEquals("",addConsShouldNotWork);
    }

    @Test
    public void millionDollarTest() throws NoSuchMethodException {
        Instruction testInstruction;

        Instruction addInstruction = new AddInstruction("L1", 1,2,3);
        testInstruction = tr.reflect("L1","add"," 1 2 3");
        assertEquals(addInstruction.toString(), testInstruction.toString());

        Instruction subInstruction = new SubInstruction("L1", 1,2,3);
        testInstruction = tr.reflect("L1","sub"," 1 2 3");
        assertEquals(subInstruction.toString(), testInstruction.toString());

        Instruction mulInstruction = new MulInstruction("L1", 1,2,3);
        testInstruction = tr.reflect("L1","mul"," 1 2 3");
        assertEquals(mulInstruction.toString(), testInstruction.toString());

        Instruction divInstruction = new DivInstruction("L1", 1,2,3);
        testInstruction = tr.reflect("L1","div"," 1 2 3");
        assertEquals(divInstruction.toString(), testInstruction.toString());

        Instruction outInstruction = new OutInstruction("L1", 1);
        testInstruction = tr.reflect("L1","out"," 1");
        assertEquals(outInstruction.toString(), testInstruction.toString());

        Instruction linInstruction = new LinInstruction("L1", 1,2);
        testInstruction = tr.reflect("L1","lin"," 1 2");
        assertEquals(linInstruction.toString(), testInstruction.toString());

        Instruction bnzInstruction = new BnzInstruction("L1", 1, "L2");
        testInstruction = tr.reflect("L1", "bnz"," 1 L2");
        assertEquals(bnzInstruction.toString(), testInstruction.toString());

    }
}