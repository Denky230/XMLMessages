/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librosxml;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author mfontana
 */
public class LibrosXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Fábrica de DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Características opcionales que le podemos poner
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            // Nos traemos el DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Y ahora cargamos el DOM de libros.XML
            File f = new File("libros.xml");
            Document doc = builder.parse(f);
            // Vamos a recorrer el DOM y a mostrarlo
            Node raiz = doc.getFirstChild();
            // A esta altura tenemos el nodo libros
            System.out.println(raiz.getNodeName());
            // Nos traemos todos los hijos (libro)
            NodeList hijos = raiz.getChildNodes();
            // Recorremos los libros y de cada libro sus datos
            for (int i = 0; i < hijos.getLength(); i++) {
                Node libroActual = hijos.item(i);
                if (libroActual.getNodeType() == Node.ELEMENT_NODE) {
                    System.out.println("Hemos encontrado un libro!");
                    NodeList contenidoLibro = libroActual.getChildNodes();
                    for (int j = 0; j < contenidoLibro.getLength(); j++) {
                        Node actual = contenidoLibro.item(j);
                        if (actual.getNodeType() == Node.ELEMENT_NODE) {
                            System.out.println(actual.getTextContent());
                            if (actual.getTextContent().equals("Don Quitoje de la Mancha")) {
                                while (!actual.getNodeName().equals("pags")) {
                                    actual = actual.getNextSibling();
                                }
                                actual.setTextContent("1888");
                            }
                        }
                    }
                }
            }
            // Añadir un nodo al dom (libro)
            // Creamos el nodo libro
            Node libro = doc.createElement("libro");
            // Creamos nodo titulo
            Node titulo = doc.createElement("titulo");
            // Creamos nodo con el texto del titulo
            Node textoTitulo = doc.createTextNode("El resplandor");
            // Añadimos al titulo su texto
            titulo.appendChild(textoTitulo);
            // Añadimos al libro su nodo titulo
            libro.appendChild(titulo);
            // Añadimos el libro a nuestro arbol (dentro de libros)
            raiz.appendChild(libro);

            // Realizar consultas en XML con XPath
            // Clase para poder hacer consultas con XPath
            XPath xpath = XPathFactory.newInstance().newXPath();
            // Clase para poder definir la consulta y ejecutarla
            XPathExpression exp = xpath.compile("/libros/libro/autor");
            // Ejecutamos la consulta (nos devuelve un Object, como 
            // le indicamos que lo queremos de tipo NODESET podemos
            // hacer el cast a NodeList
            NodeList resultado = (NodeList) exp.evaluate(doc, XPathConstants.NODESET);
            // Mostramos el resultado
            System.out.println("Resultado de la consulta");
            for (int i = 0; i < resultado.getLength(); i++) {
                System.out.println(resultado.item(i).getTextContent());
            }
            // Guardamos el dom modificado en el fichero xml
            // Clase para dar formato xml a la serialización
            OutputFormat format = new OutputFormat(doc);
            // Propiedad para indicar que lo queremos tabulado
            format.setIndenting(true);
            // Clase para escribir el contenido (Serializar) en el fichero
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(f), format);
            // Escribimos en el fichero
            serializer.serialize(doc);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println("Error al cargar el DOM: " + ex.getMessage());
        } catch (XPathExpressionException ex) {
            System.out.println("Error al realizar la consulta: " + ex.getMessage());
        }
    }

}
