package com.ba.marketUI.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ba.marketUI.client.ComClientInterface;
import com.ba.marketUI.client.pages.GameParameter;

/**
 * 
 * @author Jessica Hediger
 * 
 */
@SuppressWarnings("serial")
public class ComServerInterface extends RemoteServiceServlet implements
		ComClientInterface{

	private String pfad = "";//"/home/user/hediger/tomcat/";//"C:\\Users\\JH\\Desktop\\HS2012\\s\\";

	public String myMethod(Boolean read, String message, String fileName) throws IOException {
		if(read){
			return FileReader(fileName);
		}else{
			Writer(message,fileName);
			return "";
		}
		
	}

	private void Writer(String message, String fileName) throws IOException {
		message = escapeHtml(message);
		// Do something interesting with 's' here on the server.
		if (fileName.equals(GameParameter.Worker)
				|| fileName.equals(GameParameter.MTurk)) {
			String userAgent = getThreadLocalRequest().getHeader("User-Agent");
			userAgent = userAgent + "language: "
					+ getThreadLocalRequest().getHeader("accept-language");
			userAgent = escapeHtml(userAgent);
			w(message, " userAgent: " + userAgent, fileName);
		}
		int i = 0;
		while (message.indexOf("nnnnm", i) != -1) {
			int end = 0;
			if (message.indexOf("nnnnm", i + 4) == -1) {
				end = message.length() - 1;
			} else {
				end = message.indexOf("nnnnm", i + 4);
			}
			w("GameInfo: " + message.substring(i, end), "", fileName);
			i = end;
		}
		return;
	}

	public void w(String i, String userAgent, String fileName)
			throws IOException {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					pfad+fileName + ".txt", true));
					//TODO"outputFiles/" + fileName + ".txt", true));
			out.newLine();
			out.write(userAgent + i);
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

	public String FileReader(String fileName) {
		StringBuffer datei = new StringBuffer();
		try {
			String s = null;
			java.io.BufferedReader in = new java.io.BufferedReader(
					new java.io.FileReader( pfad+fileName));
					//TODO new java.io.FileReader("inputFiles/" + fileName));
			while ((s = in.readLine()) != null) {
				datei.append(s).append(
						in.readLine() + System.getProperty("line.separator"));
			}
			in.close();

		} catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String t = datei.substring(0, datei.length());
		if (t == null) {
			return "fehler";
		}
		return t;
	}
}