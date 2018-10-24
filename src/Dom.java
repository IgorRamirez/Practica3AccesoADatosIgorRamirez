
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author igor ramirez bonevic
 */
public class Dom {

    Document doc = null;

    public int abrir_XML_DOM(File fichero) {                                        //doc representa al arbol dommmm

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  //Se crea un objeto DocumentBuider Factory
            factory.setIgnoringElementContentWhitespace(true);                      //Ignora los espacios en blanco
            DocumentBuilder builder = factory.newDocumentBuilder();                   //Crea un objeto DocumentBuider para cargar en el la estructura de arbol dom a partir del xml seleccionado
            doc = builder.parse(fichero);                                           //Interpreta (parsear) el documento xml(file) y genera el dom  equivalente
            return 0;                                                           //Ahora doc apunta el arbol dom listo para ser recorrido
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String recorrerDOMyMostrar(Document doc) {

        String datos_nodo[] = null;
        String salida = "";
        Node node;

        Node raiz = doc.getFirstChild();                                          //obtiene el primer nodo del DOM(primer hijo)
        NodeList nodelist = raiz.getChildNodes();                                 //obtiene una lista de nodos con todos los nodos hjo del raiz
        for (int i = 0; i < nodelist.getLength(); i++) {                              //Procesa los nodos hijo
            node = nodelist.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                datos_nodo = procesarLibro(node);                               //Es un nodo libro
                salida = salida + "\n" + "Publicado en:" + datos_nodo[0];
                salida = salida + "\n" + "El autor es:" + datos_nodo[2];
                salida = salida + "\n" + "El titulo es:" + datos_nodo[1];
                salida = salida + "\n-----------";
            }
        }
        return salida;

    }

    protected String[] procesarLibro(Node n) {
        String datos[] = new String[3];
        Node ntemp = null;
        int contador = 1;

        datos[0] = n.getAttributes().item(0).getNodeValue();                      //obtiene el valor del primer atributo del nodo(uno en este ejempo)
        NodeList nodos = n.getChildNodes();                                     //obtiene los hijos del libro (titulo y autor) 

        for (int i = 0; i < nodos.getLength(); i++) {
            ntemp = nodos.item(i);

            if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                datos[contador] = ntemp.getChildNodes().item(0).getNodeValue();  //IMPORTANTE para obtener el texto con el titulo y el autor se accede al nodo TEXT hijo de ntemp y se saca su valor
                contador++;
            }
        }
        return datos;
    }

    public int annadirDOM(Document doc, String titulo, String autor, String anno) {
        try {
            Node ntitulo = doc.createElement("Titulo");                         //Se crea un nodo tipo element con nombre 'titulo'(<Titulo>)
            Node ntitulo_text = doc.createTextNode(titulo);                       //Se crea un nodo tipo texto con el titulo del libro

            ntitulo.appendChild(ntitulo_text);                                  //Se añade el nodo de texto con el titulo como hijo del elemento titulo

            Node nautor = doc.createElement("Autor");                           //Se ace lo mismo que con titulo a autor(<Autor>)
            Node nautor_text = doc.createTextNode(autor);

            nautor.appendChild(nautor_text);

            Node nlibro = doc.createElement("Libro");                             //Se crea un nodo de tipo elemento(<libro>) 
            ((Element) nlibro).setAttribute("publicado_en", anno);               //Al nuevo nodo libro se le añade un atributo publicado_en
            nlibro.appendChild(ntitulo);                                        //Se añade a libro el nodo autor y titulo creados antes
            nlibro.appendChild(nautor);

            Node raiz = doc.getChildNodes().item(0);                            //Se obtiene el primer nodo del documento y a el se le añade como hijo el nodo libro que ya tiene colgando todos sus hijos y atributos creados antes
            raiz.appendChild(nlibro);

            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int guardarDOMcomoFILE() {
        try {
            File archivo_xml = new File("salida.xml");                              //Crea un fichero llamado salida.xml
            OutputFormat format = new OutputFormat(doc);                             //Especifica el formato de salida
            format.setIndenting(true);                                              //Especifica que la salida este  indentada
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream //Escribe el contenido e el file    
                    (archivo_xml), format);

            serializer.serialize(doc);

            return 0;
        } catch (Exception e) {
            return -1;
        }

    }
}
