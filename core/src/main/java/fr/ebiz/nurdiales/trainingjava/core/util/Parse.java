package fr.ebiz.nurdiales.trainingjava.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parse {
    private static final Logger LOGGER = LoggerFactory.getLogger(Parse.class);

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

    /**
     * Function to transform string to a Date.
     * @param s Date in string.
     * @return Date from the param
     */
    public static LocalDate stringToLocalDate(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        try {
            return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            LOGGER.info("ERROR StringToDate, DateTimeParseException");
            return null;
        }
    }
}
