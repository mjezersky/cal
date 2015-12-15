/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.main;

import java.util.ArrayList;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 *
 * @author ASUS
 */
public class Contacts {
    private final ListView container;
    private final ObservableList contactNames;
    private final ArrayList contactData;
    
    public Contacts(GuiController controller) {
        container = controller.getContactsContainer();
        contactNames = FXCollections.observableList(new ArrayList<String>());
        contactData = new ArrayList<ContactData>();
        
        addContact();
        addContact();
    }
    
    private void addContact() {
        contactNames.add("test");
        contactData.add(new ContactData("jmeno", "123", "brno"));
        refresh();
    }
    
    public void refresh() {
        container.setItems(contactNames);
    }
    
}
