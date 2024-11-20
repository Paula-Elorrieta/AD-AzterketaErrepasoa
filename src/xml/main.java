package xml;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class main {

    public static void main(String[] args) throws SAXException, IOException, TransformerException {
        
    	File xmlFile = new File("libreria.xml");
		// Las importaciones que se hacen para esto son las de org.w3c.dom
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			// Coger el nombre del elemento raiz
			doc.getDocumentElement().getNodeName();
            System.out.println("Elemento raiz: " + doc.getDocumentElement().getNodeName());
            
            NodeList informacionList = doc.getElementsByTagName("informacion");
			for (int i = 0; i < informacionList.getLength(); i++) {
                Node node = informacionList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println("Nombre: " + element.getElementsByTagName("nombre").item(0).getTextContent());
                    System.out.println("Ubicacion: " + element.getElementsByTagName("ubicacion").item(0).getTextContent());
                    System.out.println("Año de fundacion: " + element.getElementsByTagName("anioFundacion").item(0).getTextContent());
                }
			}
			
			// Coger los datos de los libros, DE TODOS LOS LIBROS
			NodeList librosList = doc.getElementsByTagName("libro");
			for (int i = 0; i < librosList.getLength(); i++) {
				Node node = librosList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					System.out.println("Titulo: " + element.getElementsByTagName("titulo").item(0).getTextContent());
					System.out.println("Autor: " + element.getElementsByTagName("autor").item(0).getTextContent());
					System.out.println("Año de publicacion: "
							+ element.getElementsByTagName("anioPublicacion").item(0).getTextContent());
				}
			}
			
			// Leer los datos de los autores que son una sublase dentro de libro > autor > nombre, anioNacimiento, nacionalidad
			for (int i = 0; i < librosList.getLength(); i++) {
			    Node node = librosList.item(i);
			    if (node.getNodeType() == Node.ELEMENT_NODE) {
			        Element element = (Element) node;
			        
			        // Acceder al nodo autor
			        Element autorElement = (Element) element.getElementsByTagName("autor").item(0);
			        
			        // Obtener los detalles del autor
			        String nombreAutor = autorElement.getElementsByTagName("nombre").item(0).getTextContent();
			        String anioNacimientoAutor = autorElement.getElementsByTagName("anioNacimiento").item(0).getTextContent();
			        String nacionalidadAutor = autorElement.getElementsByTagName("nacionalidad").item(0).getTextContent();
			        
			        // Imprimir información del autor
			        System.out.println("Autor: " + nombreAutor);
			        System.out.println("Año de nacimiento: " + anioNacimientoAutor);
			        System.out.println("Nacionalidad: " + nacionalidadAutor);
			    }
			}

            Element nuevoLibro = doc.createElement("libro");
            nuevoLibro.setAttribute("id", "5");
            nuevoLibro.setAttribute("disponible", "true");

            Element titulo = doc.createElement("titulo");
            titulo.appendChild(doc.createTextNode("El señor de los anillos"));
            nuevoLibro.appendChild(titulo);

            // Como es un subnodo, se tiene que crear el nodo autor y luego añadirle los subnodos
            Element autor = doc.createElement("autor");
            Element nombreAutor = doc.createElement("nombre");
            nombreAutor.appendChild(doc.createTextNode("J.R.R. Tolkien"));
            Element anioNacimientoAutor = doc.createElement("anioNacimiento");
            anioNacimientoAutor.appendChild(doc.createTextNode("1892"));
            Element nacionalidadAutor = doc.createElement("nacionalidad");
            nacionalidadAutor.appendChild(doc.createTextNode("Reino Unido"));
            autor.appendChild(nombreAutor);
            autor.appendChild(anioNacimientoAutor);
            autor.appendChild(nacionalidadAutor);
            nuevoLibro.appendChild(autor);

            Element anioPublicacion = doc.createElement("anioPublicacion");
            anioPublicacion.appendChild(doc.createTextNode("2001"));
            nuevoLibro.appendChild(anioPublicacion);

            Node librosNode = doc.getElementsByTagName("libros").item(0);
            librosNode.appendChild(nuevoLibro);

            System.out.println("Nuevo libro añadido: " + nuevoLibro.getElementsByTagName("titulo").item(0).getTextContent());

            // IMPORTANTE: Guardar los cambios en el fichero
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("libreria.xml"));
            transformer.transform(source, result);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
