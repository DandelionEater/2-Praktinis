package lt.viko.eif.dgenzuras.librarysystemws;

import lt.viko.eif.dgenzuras.librarysystemws.endpoint.LibraryEndpoint;

import javax.xml.ws.Endpoint;

/**
 * Main class starts up the store endpoint and awaits requests.
 *
 * @author dainius.genzuras@stud.viko.lt
 * @see LibraryEndpoint
 * @since 1.0
 */

public class Main {
	public static void main(String[] args) {
		System.out.println("Starting web endpoint on address http://localhost:2439/libraryendpoint?wsdl");
		System.out.println("Use this address in SoapUI");

		Endpoint.publish("http://localhost:2439/libraryendpoint", new LibraryEndpoint());

		System.out.println("Endpoint started successfully");
	}
}