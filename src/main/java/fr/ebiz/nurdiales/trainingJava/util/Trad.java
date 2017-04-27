package fr.ebiz.nurdiales.trainingJava.util;

/**
 * Created by ebiz on 25/04/17.
 */
public class Trad {
    /**
     * Method to convert a string corresponding to a int (or float) to a int.
     * @param s String to convert.
     * @return return corresponding int.
     */
    public static int stringToInt(String s) {
        if (s != null && s.matches("[0-9]+([0-9]*|(.[0-9]*))")) {
            try {
                float tmp = Float.parseFloat(s);
                return (int) ((tmp < 0) ? 0 : tmp);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }
}
