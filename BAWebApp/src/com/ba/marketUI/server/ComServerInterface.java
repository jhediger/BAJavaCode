package com.ba.marketUI.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ba.marketUI.client.ComClientInterface;
/**
 * 
 * @author Jessica Hediger
 *
 */
@SuppressWarnings("serial")
public class ComServerInterface extends RemoteServiceServlet implements
		ComClientInterface {

	public void myMethod(String message, String fileName) throws IOException {
		// Do something interesting with 's' here on the server.

		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		userAgent = userAgent + "language: "
				+ getThreadLocalRequest().getHeader("accept-language");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		message = escapeHtml(message);
		userAgent = escapeHtml(userAgent);

		w(message, userAgent, fileName);

		return;
	}

	public void w(String i, String userAgent, String fileName) throws IOException {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName
					 + ".txt", true));
			out.newLine();
			out.write(" userAgent: " + userAgent + " GameInfo: " + i);
			out.close();
		} catch (IOException e) {
			throw new IOException("filewritingdoesntwork");
		}

		return;

	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}