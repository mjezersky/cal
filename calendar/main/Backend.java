/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.main;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import org.xml.sax.SAXException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;


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
    
    
  public void saveNote(String str,String date) {
         Element day,note;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder=null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document document=null;
        try {
            document = documentBuilder.parse("src/events.xml");
        } catch (SAXException | IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        Element root = document.getDocumentElement();
        System.out.println(root.getTagName());

        day=document.getElementById(date);
        if (day==null){
            day = document.createElement("day");
            day.setAttribute("date", date);
        }
        NodeList notes=day.getElementsByTagName("note");
        if (notes.getLength()==0){
            note = document.createElement("note");

        }
        else {        
            note = (Element) notes.item(0);
            System.out.println("ASDASFASDF"+note.getTextContent());

        }
        if (str.equals("")){
               day.removeChild(note);
            //neexistuji eventy a poznamka je prazdna
            if (day.getChildNodes().getLength()==0) {
                System.out.println("Empty day");
                root.removeChild(day);
            }
        }
        else {
             note.setTextContent(str);
             day.appendChild(note);
             root.appendChild(day);
        }

        XMLSerializer serializer = new XMLSerializer();
        try {
            serializer.setOutputCharStream(new java.io.FileWriter("src/events.xml"));
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutputFormat format = new OutputFormat();
        format.setStandalone(true);
        serializer.setOutputFormat(format);
        try {
            serializer.serialize(document);
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

  
    public void deleteEvent(String date, String time){
        Element day,e;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder=null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document document=null;
        try {
            document = documentBuilder.parse("src/events.xml");
        } catch (SAXException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        Element root = document.getDocumentElement();

        day=document.getElementById(date);
        if (day==null){
            return;
        }
        if (day.hasChildNodes()){
            NodeList childNodes=day.getChildNodes();
            for (int i=0;i<childNodes.getLength();i++) {
                Node cn=childNodes.item(i);
                if (cn.getNodeType() == Node.ELEMENT_NODE) {
                    e=(Element) cn;
                    if (e.getAttribute("time").equals(time)){
                        System.out.println("Deleting event");
                        day.removeChild(cn);
                    }
                    
                }
            }
        }
        if (!day.hasChildNodes()){root.removeChild(day);}
        XMLSerializer serializer = new XMLSerializer();
        try {
            serializer.setOutputCharStream(new java.io.FileWriter("src/events.xml"));
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutputFormat format = new OutputFormat();
        format.setStandalone(true);
        serializer.setOutputFormat(format);
        try {
            serializer.serialize(document);
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
  public void saveEvent(Event e) {
        Element day;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder=null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document document=null;
        try {
            document = documentBuilder.parse("src/events.xml");
        } catch (SAXException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        Element root = document.getDocumentElement();

        day=document.getElementById(e.date);
        if (day==null){
            day = document.createElement("day");
            day.setAttribute("date", e.date);
        }
        Element event = document.createElement("event");

        event.setAttribute("time",e.time);
        Element text = document.createElement("text");
        text.setTextContent(e.text);
        day.appendChild(event);

        event.appendChild(text);
        
        root.appendChild(day);
      
        XMLSerializer serializer = new XMLSerializer();
        try {
            serializer.setOutputCharStream(new java.io.FileWriter("src/events.xml"));
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutputFormat format = new OutputFormat();
        format.setStandalone(true);
        serializer.setOutputFormat(format);
        try {
            serializer.serialize(document);
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    
  public ArrayList getEvents(String date) {
        ArrayList l=new ArrayList<Event>();   
        String text, time;
        File f = new File("src/events.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
	Document doc = null;
        try {
            doc = dBuilder.parse(f);
        } catch (SAXException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                            e=new Event(eElement.getTextContent(),date,eElement.getAttribute("time"));
                           // System.out.println("event : v " +eElement.getAttribute("time") + " casu" + eElement.getTextContent());
                            l.add(e);
                        }
                }
            }

	}		
        
    return (l);
  }
 
  
  
    public String getNotes(String date){

        String notes=""; 
        File f = new File("src/events.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
	Document doc = null;
        try {
            doc = dBuilder.parse(f);
        } catch (SAXException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                        if (nNode.getNodeName()=="note"){  
                            //System.out.println("note : " + eElement.getTextContent());
                            notes+=eElement.getTextContent();
                        }

                }
            }

	}		
        
    return notes;
    }
    
   public void deleteContact(String name){

           Element contact;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder=null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document document=null;
        try {
            document = documentBuilder.parse("src/contacts.xml");
        } catch (SAXException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        Element root = document.getDocumentElement();

        contact=document.getElementById(name);
        if (contact==null){
            return;
        }
        root.removeChild(contact);
               
            
        
        XMLSerializer serializer = new XMLSerializer();
        try {
            serializer.setOutputCharStream(new java.io.FileWriter("src/contacts.xml"));
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutputFormat format = new OutputFormat();
        format.setStandalone(true);
        serializer.setOutputFormat(format);
        try {
            serializer.serialize(document);
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    
  public void addContact(Contact c){
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder=null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document document=null;
        try {
            document = documentBuilder.parse("src/contacts.xml");
        } catch (SAXException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        Element root = document.getDocumentElement();

        
        if (document.getElementById(c.name)!=null){
            System.out.println("Contact already exists");
            return;
        }
        Element contact = document.createElement("contact");

        contact.setAttribute("name",c.name);
        Element desc = document.createElement("desc");
        desc.setTextContent(c.desc);
        contact.appendChild(desc);
        
        root.appendChild(contact);
      
        XMLSerializer serializer = new XMLSerializer();
        try {
            serializer.setOutputCharStream(new java.io.FileWriter("src/contacts.xml"));
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutputFormat format = new OutputFormat();
        format.setStandalone(true);
        serializer.setOutputFormat(format);
        try {
            serializer.serialize(document);
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }
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