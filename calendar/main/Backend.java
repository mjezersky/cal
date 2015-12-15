/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.main;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import org.xml.sax.SAXException;
import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;


/**
 *
 * @author cepin
 */
public  class Backend {
   
    public boolean EventOnDay(String date) throws SAXException, ParserConfigurationException, IOException  {
        File f = new File("src/events.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(f);
        doc.getDocumentElement().normalize();	
        Element day=doc.getElementById(date); //pridat kontrolu, jestli v nalezenem day je nejaky event
        return day != null;}
    
    
  public void saveEvent(Event e)  throws ParserConfigurationException, SAXException, IOException, TransformerException {
        Element day;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("src/events.xml");
        Element root = document.getDocumentElement();
        day=document.getElementById(e.date);
        if (day==null){
            day = document.createElement("day");
        }
        Element event = document.createElement("event");

        event.setAttribute("time",e.time);
        Element text = document.createElement("text");
        text.appendChild(document.createTextNode(e.text));
        day.appendChild(text);

        event.appendChild(text);
        
        root.appendChild(day);
        DOMSource source = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        System.out.println("Saving");
        StreamResult result = new StreamResult("contacts.xml");
        transformer.transform(source, result);
    }

    
    
    
  public ArrayList getEvents(String date)  throws ParserConfigurationException, SAXException, IOException {
        ArrayList l=new ArrayList<Event>();   
        String text, time;
        File f = new File("src/events.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(f);
        doc.getDocumentElement().normalize();			
	Element day = (Element) doc.getElementById(date);
        if (day==null) return null;
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