/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Tools {
    public static int getFirstDayIndex(int displayedYear, int displayedMonth) {
        int firstDay;
        java.util.Date javaDate = new java.util.Date();
        java.util.Calendar cal = new GregorianCalendar(displayedYear, displayedMonth, 1);
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        
        firstDay = cal.get(java.util.Calendar.DAY_OF_WEEK);
        // posun na 0-6 a pondelim zacina tyden
        if (firstDay == java.util.Calendar.SUNDAY) return 6;
        else return firstDay-2;
    }
    
    public static int getLastDay(int displayedYear, int displayedMonth) {
        java.util.Calendar cal = new GregorianCalendar(displayedYear, displayedMonth, 1);
        return cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
    }
    
    public static int getLastDayIndex(int displayedYear, int displayedMonth) {
        return getFirstDayIndex(displayedYear, displayedMonth) + getLastDay(displayedYear, displayedMonth) - 1;
    }
    
    public static int getPreviousMonth(int displayedMonth) {
        if (displayedMonth == 0) return 11;
        else return displayedMonth-1;
    }
    public static int getNextMonth(int displayedMonth) { return (displayedMonth+1)%12; }
    
    public static ArrayList getDaysGrid(int displayedYear, int displayedMonth) {
        ArrayList days = new ArrayList<Date>();
        int gridSize = 6*7;
        int firstIndex = getFirstDayIndex(displayedYear, displayedMonth);
        int lastIndex = getLastDayIndex(displayedYear, displayedMonth);
        
        java.util.Calendar cal = new GregorianCalendar(displayedYear, displayedMonth, 1);
        cal.add(java.util.Calendar.DATE, -1-firstIndex); // posunu se do predchoziho mesice
        
        for (int i=0; i<gridSize; i++) {
            cal.add(java.util.Calendar.DATE, 1);
            days.add(cal.getTime());
        }
        return days;
    }
    
    public static int getWeekNumber(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date date;
        try {
            date = df.parse(dateString);
        } catch (ParseException ex) {
            return -1;
        }

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        return cal.get(java.util.Calendar.WEEK_OF_YEAR);
    }
    
    public static String getDateString(Date date) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(date);
    }
    
    public static Date now() { return java.util.Calendar.getInstance().getTime(); }
    
    public static Date stringToTime(String string) {
        DateFormat format = new SimpleDateFormat("kk:mm");
        try {
            Date date = format.parse(string);
            return date;
        } catch (ParseException ex) {
            return null;
        }
    }
    
    public static Date stringToDate(String string) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date date = format.parse(string);
            return date;
        } catch (ParseException ex) {
            return null;
        }
    }
    
    public static Date stringToDateTime(String string) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy kk:mm");
        try {
            Date date = format.parse(string);
            return date;
        } catch (ParseException ex) {
            return null;
        }
    }
    
    public static String getDayNumberString(Date date) {
        DateFormat df = new SimpleDateFormat("dd");
        String s = df.format(date);
        return Integer.toString(Integer.parseInt(s));
    }
    
    public static String monthToName(int monthInt) {
        switch (monthInt) {
            case 0: return "Leden";
            case 1: return "Únor";
            case 2: return "Březen";
            case 3: return "Duben";
            case 4: return "Květen";
            case 5: return "Červen";
            case 6: return "Červenec";
            case 7: return "Srpen";
            case 8: return "Září";
            case 9: return "Říjen";
            case 10: return "Listopad";
            case 11: return "Prosinec";
            default: return "-";
        }
    }
    
}
