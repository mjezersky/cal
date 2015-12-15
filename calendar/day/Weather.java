package calendar.day;

import javafx.scene.web.WebEngine;
import org.w3c.dom.NodeList;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


public class Weather{

    public String getTemperature(){ return getTemp(); }

    private String getTemp() {

        final WebEngine webEngine = new WebEngine("http://weather.yahooapis.com/forecastrss?w=786869&u=c");

        String temp;

        NodeList meteo = webEngine.getDocument().getElementsByTagNameNS("http://xml.weather.yahoo.com/ns/rss/1.0", "condition");
        System.out.println(meteo);
        temp = meteo.item(0).getAttributes().getNamedItem("temp").getNodeValue() + "°C" ;

        return temp;

    }

}
