
import com.sun.org.apache.xerces.internal.parsers.SAXParser;
import java.io.File;
import javax.xml.parsers.SAXParserFactory;
import jdk.internal.org.xml.sax.helpers.DefaultHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author xp
 */
public class Sax {
    
        SAXParser parser;
        File ficheroXML;
    
    public int abrir_XML_SAX(File fichero) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();                                              //Se crea un objeto SAXParser para interpretar el documento xml
            sh = new ManejadorSAX();                                                      //Se crea una instancia del manejador que sera el que recorra el 
            //documento xml secuencialmente
            ficheroXML = fichero;
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    class ManejadorSax extends DefaultHandler{
    
    int ultimoelement;
    String cadena_resultado="";
    
    public ManejadorSAX(){
        ultimoelement=0;
    }
    @Override public void startElement(String uri, String localName, String qName, 
            Attributes atts) throws SAXException {
        if(qName.equals("Libro")){
        cadena_resultado=cadena_resultado + "\nPublicado en:"
                +atts.getValue(atts.getQName(0))+"\n";
        ultimoelement=1;
                }
        else if(qName.equals("Titulo")){
        ultimoelement=2;
        cadena_resultado=cadena_resultado + "\nEl título es:";
        }
        else if(qName.equals("Autor")){
        ultimoelement=3;
        cadena_resultado= cadena_resultado + "\nEl autor es:";
        }
    }
    
    
    @Override public void endElement(String uri, Strintg localName, String qName
    throws SAXException){};
    }
    
}
