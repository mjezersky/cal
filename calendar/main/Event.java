/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.main;

/**
 *
 * @author cepin
 */
public class Event {
    public String text;
    public String date;
    public String time;
    public Event(String tText, String dDate, String tTime){
        time=tTime;
        date=dDate;
        text=tText;
    }    
}
