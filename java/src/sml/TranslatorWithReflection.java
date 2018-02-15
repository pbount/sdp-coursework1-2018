package sml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TranslatorWithReflection {

    /**
     *   Receives a label, an operation and a string of registers seperated with a space and returns
     *   an instruction object.
     *
     * @param label         The label of the instruction.
     * @param inst          The instruction name ("add", "sub" etc...)
     * @param registers     The registers for the instruction ("1 2 3", "1 2", "1" etc...)
     *
     * @return              An instantiated Instruction object.
     *
     * @throws NoSuchMethodException        Exception thrown if method not found.
     *
     */
    public Instruction reflect(String label, String inst, String registers) throws NoSuchMethodException {
        /*
         *  InstructionClassName contains the name of the class to be instantiated. The class
         *  name is generated based on the inst variable by taking the first character of
         *  the string and capitalizing it, as well as appending the string "Instruction"
         *  to it. After receiving "add" for example the end result should be
         *  "AddInstruction"
         */
        String instructionClassName = inst.substring(0,1).toUpperCase() + inst.substring(1).toLowerCase()+"Instruction";
        Class<?> instructionClass;

        /*
         *  Create the registers to use for the constructor
         */
        String[] initArgs = concatArrays(new String[]{label}, registers.substring(1).split(" "));


        /*
         *  instructionClass contains the class to be instantiated and the instructionClassConstructors
         *  contains its constructor.
         */
        try {
            instructionClass = Class.forName("sml."+instructionClassName); //Using the fully qualified name (including the package).
            Constructor<?> instructionConstructor = getConstructor(instructionClass, getTypes(initArgs));
            return (Instruction)instructionConstructor.newInstance(getInitArgs(initArgs));
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *
     *  Receives a class (Class</?>) and a class array (Class</?>[]) of arguments and returns the
     *  proper constructor based on the arguments provided or throws an exception if the constructor
     *  is not found.
     *
     * @param cls               The Class</?> from which the constructor will be extracted.
     * @param arguments         The parameters which the constructor should be able to receive.
     *
     * @return                  Returns a constructor Constructor</?>.
     *
     * @throws NoSuchMethodException        Exception thrown if the method is not found.
     */
    public Constructor<?> getConstructor(Class<?> cls, Class<?>[] arguments) throws NoSuchMethodException {
        return cls.getConstructor(arguments);
    }


    /**
     *
     *  Determines whether the string is a number or not.
     *  Returns true for strings like: "1234" and false for strings like:
     *  "a1234", " ",  "", "abc"...
     *
     * @param str           Receives a string.
     * @return              Returns true if the string is a number and false otherwise.
     */
    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    /**
     *
     *  Receives an array of Strings and returns an array of objects
     *  which contains strings and ints. For example an array like this: ["12" , "T", "T12"]
     *  would become: [12, "T", "T12"].
     *
     * @param input         Receives an Array of Strings.
     *
     * @return              Returns the same array converted into an Object array.
     *
     */
    public Object[] getInitArgs(String[] input){
        Object result[] = new Object[input.length];
        for (int i = 0; i < input.length; i++){
            if (isNumeric(input[i])){
                result[i] = Integer.parseInt(input[i]);
            }else{
                result[i] = input[i];
            }
        }
        return result;
    }


    /**
     *
     *   Receives two String arrays and produces a single string array
     *   consisting of the first array and the second appended to it.
     *   example: ["1","2"]  +  ["2","4"]  =  ["1","2","3","4"]
     *
     * @param input1        An array of strings.
     * @param input2        An array of strings to be appended to the first array.
     *
     * @return              The two arrays combined.
     */
    public String[] concatArrays(String[] input1, String[] input2){
        String[] result = new String[input1.length + input2.length];
        System.arraycopy(input1, 0, result, 0, input1.length);
        System.arraycopy(input2, 0, result, input1.length, result.length - input1.length);
        return result;
    }


    /**
     *
     *  Receives an array of objects and returns an array of class types
     *  of the objects in their corresponding order.
     *
     * @param input         Receives an array of objects.
     * @return              Returns an array of Class types of the received objects in the correct order.
     */
    public Class<?>[] getTypes(Object[] input){

        Class<?>[] parameters = new Class<?>[input.length];
        for (int i = 0; i < parameters.length; i++){
            if (isNumeric(input[i].toString())){
                parameters[i] = int.class;
            }else{
                parameters[i] = String.class;
            }
        }
        return parameters;
    }
}
