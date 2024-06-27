package lt.viko.eif.dgenzuras.librarysystemws.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import lt.viko.eif.dgenzuras.librarysystemws.Main;
import lt.viko.eif.dgenzuras.librarysystemws.model.Account;
import lt.viko.eif.dgenzuras.librarysystemws.model.Book;
import lt.viko.eif.dgenzuras.librarysystemws.model.Reader;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.URL;
import java.util.Objects;

/**
 * LibraryEndpoint class handles requests coming from the WSDL endpoint.
 *
 * @author dainius.genzuras@stud.viko.lt
 * @see Main
 * @since 1.0
 */

@WebService
public class LibraryEndpoint {

	private Reader reader1;

	public LibraryEndpoint() {
		// Generate the data for use
		reader1 = new Reader(0, "Dainius", "Genzuras", 1001);
		Account account1 = new Account(0, "Saiyandeffa", "gibbi352");
		Book book1 = new Book(00140, "Java for dummies", "Jack 'o Niel", 2005, "Learning", 6);
		Book book2 = new Book(05542, "World's birds", "Kendrick Nomad", 2015, "Nature", 3);
		Book book3 = new Book(78009, "Carpentry 101", "Jack Adams", 2003, "Building", 1);
		Book book4 = new Book(56801, "Car engine guy", "Nicolas Cage", 2020, "Engineering", 20);

		reader1.setAccount(account1);
		reader1.getBookList().add(book1);
		reader1.getBookList().add(book2);
		reader1.getBookList().add(book3);
		reader1.getBookList().add(book4);
	}

	@WebMethod
	public String getBook(int bookID) throws FileNotFoundException {
		Book book = reader1.getBookList().get(bookID);

		String plaintext = "";

		try { // Marshal
			plaintext = marshal(Book.class, book);
		} catch (JAXBException e) {
			return e.toString();
		}

		try { // Validate
			if(!validate(plaintext, getResource("schema.xsd")))
				return null;
		} catch (Exception e) {
			return e.toString();
		}

		String result = "";

		plaintext = plaintext.replace(" standalone=\"yes\"", "").trim();

		try { // Transform
			result = transformToHTML(plaintext, getResource("schema.xsl"));
		} catch (Exception e) {
			System.err.println(e.getCause());

			return null;
		}

		return result;
	}

	private boolean validate(String xmlFile, String xsdFile) {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			Schema schema = schemaFactory.newSchema(new File(xsdFile));

			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(xmlFile)));
			return true;
		} catch (SAXException | IOException e) {
			e.printStackTrace();

			System.out.println("XML validation failed... Terminating");
			return false;
		}
	}

	private String getResource(String filename) throws FileNotFoundException {
		URL resource = getClass().getClassLoader().getResource(filename);
		Objects.requireNonNull(resource);

		return resource.getFile();
	}

	private String marshal(Class source, Object data) throws JAXBException {
		StringWriter output = new StringWriter(); // create output
		JAXBContext context = JAXBContext.newInstance(source); // set up serializer
		Marshaller marshaller = context.createMarshaller(); // initalize serializer
		marshaller.marshal(data, output); // convert the class to xml

		return output.toString();
	}

	public static String transformToHTML(String xmlFile, String xslFile) throws Exception {
		StringWriter output = new StringWriter(); // create output
		StreamResult res = new StreamResult(output);

		TransformerFactory factory = TransformerFactory.newInstance();
		Source xslt = new StreamSource(new File(xslFile));
		Transformer transformer = factory.newTransformer(xslt);

		Source text = new StreamSource(new StringReader(xmlFile));
		transformer.transform(text, res);

		return output.toString();
	}

	public static void transformToPDF(String xmlFile, String xslFoFile, String outputPdfFile) throws Exception {
		File outputFile = new File(outputPdfFile);
		outputFile.getParentFile().mkdirs();

		FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
		OutputStream out = new FileOutputStream(outputFile);
		try {
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xslFoFile)));
			Source src = new StreamSource(new File(xmlFile));
			Result res = new SAXResult(fop.getDefaultHandler());
			transformer.transform(src, res);
		} finally {
			out.close();
		}
	}
}
