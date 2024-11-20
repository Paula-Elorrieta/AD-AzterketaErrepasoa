package xpath;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Main {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("libreria.xml");
        
        XPath xpath = XPathFactory.newInstance().newXPath();
        
        // 1. Obtener el nombre de la biblioteca
        String nombreBiblioteca = (String) xpath.evaluate("/biblioteca/informacion/nombre", document, XPathConstants.STRING);
        System.out.println("Nombre de la biblioteca: " + nombreBiblioteca);

        // 2. Obtener la ubicación de la biblioteca
        String ubicacion = (String) xpath.evaluate("/biblioteca/informacion/ubicacion", document, XPathConstants.STRING);
        System.out.println("Ubicación: " + ubicacion);

        // 3. Obtener el año de fundación de la biblioteca
        String anioFundacion = (String) xpath.evaluate("/biblioteca/informacion/anioFundacion", document, XPathConstants.STRING);
        System.out.println("Año de fundación: " + anioFundacion);

        // 4. Obtener todos los títulos de los libros disponibles
        NodeList titulosDisponibles = (NodeList) xpath.evaluate("/biblioteca/libros/libro[@disponible='true']/titulo", document, XPathConstants.NODESET);
        System.out.println("Títulos de libros disponibles:");
        for (int i = 0; i < titulosDisponibles.getLength(); i++) {
            System.out.println(titulosDisponibles.item(i).getTextContent());
        }

        // 5. Obtener el autor del libro con id="1"
        String autorLibro1 = (String) xpath.evaluate("/biblioteca/libros/libro[@id='1']/autor/nombre", document, XPathConstants.STRING);
        System.out.println("Autor del libro con id 1: " + autorLibro1);

        // 6. Obtener los géneros del libro con id="2"
        NodeList generosLibro2 = (NodeList) xpath.evaluate("/biblioteca/libros/libro[@id='2']/generos/genero", document, XPathConstants.NODESET);
        System.out.println("Géneros del libro con id 2:");
        for (int i = 0; i < generosLibro2.getLength(); i++) {
            System.out.println(generosLibro2.item(i).getTextContent());
        }

        // 7. Obtener el nombre del bibliotecario
        String nombreBibliotecario = (String) xpath.evaluate("/biblioteca/personal/miembro[@rol='bibliotecario']/nombre", document, XPathConstants.STRING);
        System.out.println("Nombre del bibliotecario: " + nombreBibliotecario);

        // 8. Obtener los miembros contratados después del año 2010
        NodeList miembrosPost2010 = (NodeList) xpath.evaluate("/biblioteca/personal/miembro[anioContratacion > 2010]", document, XPathConstants.NODESET);
        System.out.println("Miembros contratados después del 2010:");
        for (int i = 0; i < miembrosPost2010.getLength(); i++) {
            System.out.println("ID: " + miembrosPost2010.item(i).getAttributes().getNamedItem("id").getTextContent());
        }

        // 9. Obtener todos los libros que no están disponibles
        NodeList librosNoDisponibles = (NodeList) xpath.evaluate("/biblioteca/libros/libro[@disponible='false']", document, XPathConstants.NODESET);
        System.out.println("Libros no disponibles:");
        for (int i = 0; i < librosNoDisponibles.getLength(); i++) {
            System.out.println(librosNoDisponibles.item(i).getChildNodes().item(1).getTextContent());  // Título del libro
        }

        // 10. Obtener todos los libros publicados después de 2000
        NodeList librosPost2000 = (NodeList) xpath.evaluate("/biblioteca/libros/libro[anioPublicacion > 2000]", document, XPathConstants.NODESET);
        System.out.println("Libros publicados después del 2000:");
        for (int i = 0; i < librosPost2000.getLength(); i++) {
            System.out.println(librosPost2000.item(i).getChildNodes().item(1).getTextContent());  // Título del libro
        }
        
        // Cuantos libros hay en la biblioteca
        String expresion = "count(/biblioteca/libros/libro)";
        Double result = (Double) xpath.evaluate(expresion, document, XPathConstants.NUMBER);
        System.out.println("\nLiburu kopurua: " + result.intValue());

	}

}
