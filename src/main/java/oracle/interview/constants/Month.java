package oracle.interview.constants;

public enum Month {
    JANUARY(1, "Jan"),
    FEBRUARY(2, "Feb"),
    MARCH(3, "Mar"),
    APRIL(4, "Apr"),
    MAY(5, "May"),
    JUNE(6, "Jun"),
    JULY(7, "Jul"),
    AUGUST(8, "Aug"),
    SEPTEMBER(9, "Sep"),
    OCTOBER(10, "Oct"),
    NOVEMBER(11, "Nov"),
    DECEMBER(12, "Dec");

    private int number;
    private String abbreviation;

    /**
     * Return month number as string with 2 digits
     *
     * @return Month number fixed to 2 digits as String
     */
    public String getNumberAsString() {
        if(number < 10)
            return "0".concat(Integer.toString(number));
        else
            return Integer.toString(number);
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    Month(int number, String abbreviation) {
        this.number = number;
        this.abbreviation = abbreviation;
    }

    /**
     * Get the month name abbreviated.
     *
     * @return corresponding Month abbreviated, or null if no match
     */
    public static Month fromName(String abbreviationMonth) {
        for (Month month : Month.values()) {
            if (month.getAbbreviation().equals(abbreviationMonth)) {
                return month;
            }
        }
        return null;
    }
}
