package org.mifi.entyti;

import java.util.Date;

public class Forecasts {
    private Parts parts;
    private Date date;

    public Date getDate() {
        return date;
    }

    public int getTempAvgDay() {
        return parts.day.temp_avg;
    }

    static class Parts {
        Day day;

        static class Day {
            int temp_avg;
        }
    }
}
