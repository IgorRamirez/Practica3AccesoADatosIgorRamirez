
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
            parser = factory.newSAXParser();                                    //Se crea un objeto SAXParser para interpretar el documento xml
            sh = new ManejadorSAX();                                            //Se crea una instancia del manejador que sera el que recorra el 
            //documento xml secuencialmente                                     //documento xml secuencialmente
            ficheroXML = fichero;
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    class ManejadorSax extends DefaultHandler {

        int ultimoelement;
        String cadena_resultado = "";

        public ManejadorSAX() {
            ultimoelement = 0;
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                Attributes atts) throws SAXException {
            if (qName.equals("Libro")) {
                cadena_resultado = cadena_resultado + "\nPublicado en:"
                        + atts.getValue(atts.getQName(0)) + "\n";
                ultimoelement = 1;
            } else if (qName.equals("Titulo")) {
                ultimoelement = 2;
                cadena_resultado = cadena_resultado + "\nEl título es:";
            } else if (qName.equals("Autor")) {
                ultimoelement = 3;
                cadena_resultado = cadena_resultado + "\nEl autor es:";
            }
        }

        @Override                                                               //Cuando en esste ejemplo se detecta el final de un elemento<libro>
        public void endElement(String uri, String localName, String qName)      //se pone ina línea dicontinua en la salida
                throws SAXException {

            if (qName.equals("Libro")) {
                System.out.println("He encontrado el final de  un elemento.");
                cadena_resultado = cadena_resultado + "\n ---------";
            }
        }

        @Override                                                               //Cuando se detecta una cadena de texto posterior a uno de los elementos
        public void characters(char[] ch, int start, int length) throws SAXException {//<titulo> o <autor> entonces se guarda ese texto en la variable correspondiente

            if (ultimoelement == 2) {
                for (int i = start; <length + start; i++) {
                    cadena_resultado = cadena_resultado + ch[i];
                }
            } else if (ultimoelement == 3) {
                for (int i = start; i < length + start; i++) {
                    cadena_resultado = cadena_resultado + ch[i];
                }
            }
        }

    }

    public String recorrerSAX(File fXML, ManejadorSAX sh, SAXParser parser) {
        try {
            parser.parse(fXML, sh);
            return sh.cadena_resultado;
        } catch (SAXException e) {
            e.printStackTrace();
            return "Error añ parsear con SAX";
        } catch (Exception e) {
              e.printStackTrace();
            return "Error al parsear con SAX";
        }
    }

}
