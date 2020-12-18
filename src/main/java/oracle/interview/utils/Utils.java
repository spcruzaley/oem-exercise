package oracle.interview.utils;

import oracle.interview.constants.Month;

import java.time.Instant;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class Utils {

    public static final String DASH = "-";
    public static final String INSTANT_SUFFIX = "T00:00:00Z";
    private static final Logger log = Logger.getLogger(Utils.class.getName());

    /**
     * String format received from metrics_data.xml file as dd-Mmm-yyyy wich means
     *  dd - Number for day of month
     *  Mmm - Three first month letters
     *  yyyy - Year number
     *
     * @param stringTime
     * @return Instant object
     */
    public static Instant parseStringToInstant(String stringTime) {
        StringBuffer buffer = new StringBuffer();
        String[] splitDate = new String[3];
        int cont = 0;
        StringTokenizer tokenizer = new StringTokenizer(stringTime, "-");

        while (tokenizer.hasMoreElements()) {
            splitDate[cont] = tokenizer.nextElement().toString();
            if(splitDate[cont].length() == 1)
                splitDate[cont] = "0".concat(splitDate[cont]);

            cont++;
        }

        try {
            buffer.append(splitDate[2]); //year
            buffer.append(DASH);
            buffer.append(Month.fromName(splitDate[1]).getNumberAsString()); // Month
            buffer.append(DASH);
            buffer.append(splitDate[0]); // day
            buffer.append(INSTANT_SUFFIX);
        } catch (StringIndexOutOfBoundsException e) {
            log.warning("Error while parsing time: " + stringTime + ". " + e.getMessage());
        }

        return Instant.parse(buffer.toString());
    }

}