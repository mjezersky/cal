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
public class Contact{
    public String name;
    public String desc;

    public Contact(String nName){
        name=nName;

    }
    
    @Override
    public String toString() {
        return name;
    }
}
