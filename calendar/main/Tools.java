/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Date;

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
    
    public static int getWeekNumber(int displayedYear, int displayedMonth, int displayedDay) {
        java.util.Date javaDate = new java.util.Date();
        java.util.Calendar cal = new GregorianCalendar(displayedYear, displayedMonth, displayedDay);
        return cal.get(java.util.Calendar.WEEK_OF_YEAR);
    }
    
    public static String getDateString(Date date) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(date);
    }
    
    
}
