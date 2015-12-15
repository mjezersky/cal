/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.main;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
/**
 *
 * @author cepin
 */
public  class Backend {

  public ArrayList getEvents(String date)  throws ParserConfigurationException, SAXException, IOException {
        ArrayList l=new ArrayList<Event>();   
        String text, time;
        File f = new File("src/events.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(f);
        doc.getDocumentElement().normalize();			
	Element day = (Element) doc.getElementById(date);
	if (day.hasChildNodes()) {

            NodeList nl=day.getChildNodes();
            for (int temp = 0; temp < nl.getLength(); temp++) {
		Node nNode = nl.item(temp);				
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        Event e;
                       if (nNode.getNodeName()=="event"){  
                            e=new Event(eElement.getTextContent(),eElement.getAttribute("time"),date);
                           // System.out.println("event : v " +eElement.getAttribute("time") + " casu" + eElement.getTextContent());
                            l.add(e);
                        }
                }
            }

	}		
        
    return (l);
  }
 
  
  
    public String getNotes(String date)  throws ParserConfigurationException, SAXException, IOException {

        String notes=""; 
        File f = new File("src/events.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(f);
        doc.getDocumentElement().normalize();			
	Element day = (Element) doc.getElementById(date);
	if (day.hasChildNodes()) {

            NodeList nl=day.getChildNodes();
            for (int temp = 0; temp < nl.getLength(); temp++) {
		Node nNode = nl.item(temp);				
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        Event e;
                        if (nNode.getNodeName()=="note"){  
                            //System.out.println("note : " + eElement.getTextContent());
                            notes+=eElement.getTextContent();
                        }

                }
            }

	}		
        
    return notes;
    }
    
 
  public ArrayList getContacts()  throws ParserConfigurationException, SAXException, IOException {
        ArrayList l=new ArrayList<Contact>();   
        String name;
        Contact c;
        File f = new File("src/contacts.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(f);
        doc.getDocumentElement().normalize();			
	NodeList contacts =  doc.getElementsByTagName("contact");
        for (int temp = 0; temp < contacts.getLength(); temp++) {

		Node contact = contacts.item(temp);	
                if (contact.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) contact;
                    c=new Contact(eElement.getAttribute("name"));
                    l.add(c);
                    //System.out.println(eElement.getTextContent());
                    if (contact.hasChildNodes()) {

                            NodeList nl=contact.getChildNodes();
                            for (int i = 0; i < nl.getLength(); i++) {
                                Node n=nl.item(i);
                                if (n.getNodeType() == Node.ELEMENT_NODE) {
                                    Element e = (Element) n;
                                    if (e.getNodeName()=="desc"){  
                                        c.desc=eElement.getTextContent();
                                        //System.out.println("desc: "+eElement.getTextContent());
    
                                    }
                                }
                            }
                    }
                }
            }
        return(l);
	}		
        
    
}