package com.niupiao.niupiao.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Created by kevinchen on 2/23/15.
 */
public class DateUtils {

    public static final String TAG = DateUtils.class.getSimpleName();

    /**
     * The format (US locale) of Rails DateTime objects, which corresponds to:
     * "%Y-%m-%dT%H:%M:%S%Z" in Rails.
     * <code>DateTime.parse(DateTime.now.iso8601).strftime("%Y-%m-%dT%H:%M:%S%Z")</code>
     * <p/>
     * Example: 2015-02-23T22:34:02-05:00
     */
    public static final String FORMAT_RAILS_DATETIME = "yyyy-MM-d'T'HH:m:sZZ";

    /**
     * Example: 10:34 PM
     */
    public static final String FORMAT_TIME = "h:mm a";

    /**
     * Example: February 23, 2015
     */
    public static final String FORMAT_DATE = "MMMMM d, y";

    /**
     * Example: Monday
     */
    public static final String FORMAT_DAY = "EEEEE";

    /**
     *
     * @param isShort If true, "Feb". If false, "February".
     */
    public static String getMonth(DateTime dateTime, boolean isShort) {
        DateTime.Property monthOfYear = dateTime.monthOfYear();
        return isShort ? monthOfYear.getAsShortText() : monthOfYear.getAsText();
    }

    public static String getMonth(DateTime dateTime) {
        return getMonth(dateTime, false);
    }

    public static String getDayOfMonth(DateTime dateTime) {
        return dateTime.dayOfMonth().getAsText();
    }

    public static String getYear(DateTime dateTime) {
        DateTime.Property year = dateTime.year();
        return year.getAsText();
    }

    public static String format(String dateTime, String format) {
        return format(getDateTimeFromString(dateTime), format);
    }

    public static String format(DateTime dateTime, String format) {
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern(format);
        return dtfOut.print(dateTime);
    }

    public static DateTime getDateTimeFromString(String railsRepresentation) {
        DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis();
        DateTime dateTime = null;
        try {
            dateTime = dateTimeFormatter.parseDateTime(railsRepresentation);
        } catch (Exception e) {
            System.out.println("rails date: " + railsRepresentation);
        }
        return dateTime;
    }


    public static void main(String[] args) {
        String dateTime1 = "2015-02-24T03:00:13.825Z";
        String dateTime2 = "2015-02-23T22:34:02-05:00";
        DateTime dt1 = getDateTimeFromString(dateTime1);
        DateTime dt2 = getDateTimeFromString(dateTime2);
        System.out.println(format(dt1, FORMAT_DAY));
        System.out.println(format(dt1, FORMAT_DATE));
        System.out.println(format(dt1, FORMAT_TIME));
    }
}
