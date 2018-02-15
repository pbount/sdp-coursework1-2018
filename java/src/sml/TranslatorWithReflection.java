package sml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TranslatorWithReflection {

    public Instruction reflect(String label, String inst, String registers) throws NoSuchMethodException {
        /*
         *  InstructionClassName contains the name of the class to be instantiated. The class
         *  name is generated based on the inst variable by taking the first character of
         *  the string and capitalizing in as well as appending the string "Instruction"
         *  to it. After receiving "add" for example the end result should be
         *  "AddInstruction"
         */
        String instructionClassName = inst.substring(0,1).toUpperCase() + inst.substring(1).toLowerCase()+"Instruction";
        Class<?> instructionClass;

        /*
         *  Create the registers to use for the constructor
         */

        String[] bd = concatArrays(new String[]{label}, registers.substring(1).split(" "));


        /*
         *  instructionClass contains the class to be instantiated and the instructionClassConstructors
         *  contains its constructor.
         */
        try {
            instructionClass = Class.forName("sml."+instructionClassName); //Using the fully qualified name (including the package).

            Object[] argumentTypes = getInitArgs(bd);

            Constructor<?> instructionConstructor = getConstructor(instructionClass, getTypes(bd));

            Instruction result = (Instruction)instructionConstructor.newInstance(getInitArgs(bd));

            return result;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     *  Receives a class and a class array of arguments and returns the proper constructor
     *  based on the aguments provided or throws an exception if the constructor
     *  is not found.
     */
    public Constructor<?> getConstructor(Class<?> cls, Class<?>[] arguments) throws NoSuchMethodException {
        Constructor ct = cls.getConstructor(arguments);
        return ct;
    }

    /*
     *  Determines whether the string is a number or not.
     *  returns true for strings like: "1234" and false when:
     *  "a1234", " ",  "", "abc"...
     */
    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

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

    public String[] concatArrays(String[] input1, String[] input2){
        String[] result = new String[input1.length + input2.length];
        for (int i = 0; i < input1.length; i++){
            result[i] = input1[i];
        }
        for (int j = input1.length; j < result.length; j++){
            result[j] = input2[j - input1.length];
        }
        return result;
    }

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
