/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package org.immregistries.smm.tester.connectors;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * 
 * @author nathan
 */
public class WIConnector extends HttpConnector {

  protected WIConnector(String label, String url, String type) {
    super(label, url, type);
    this.url = url;
  }

  public WIConnector(String label, String url) {
    super(label, url, "WI SOAP");
    this.url = url;
  }

  @Override
  public String submitMessage(String message, boolean debug) throws Exception {
    ClientConnection cc = new ClientConnection();
    cc.setUrl(url);
    String result = "";
    try {
      result = sendRequest(message, cc, debug);
    } catch (Exception e) {
      if (throwExceptions) {
        throw e;
      }
      return "Unable to relay message, received this error: " + e.getMessage();
    }

    StringBuffer sbuf = new StringBuffer(result.length());
    boolean inTag = false;
    for (char c : result.toCharArray()) {
      if (c == '<') {
        inTag = true;
      } else if (c == '>') {
        inTag = false;
      } else if (!inTag) {
        sbuf.append(c);
      }
    }
    if (sbuf.length() > 0) {
      result = sbuf.toString();
    }

    while (result != null && result.length() > 0 && result.charAt(0) < ' ') {
      result = result.substring(1);
    }
    return result;

  }

  public String sendRequest(String request, ClientConnection conn, boolean debug)
      throws IOException {
    StringBuilder debugLog = null;
    URLConnection urlConn = null;
    if (debug) {
      debugLog = new StringBuilder();
    }
    try {
      SSLSocketFactory factory = setupSSLSocketFactory(debug, debugLog);
      DataOutputStream printout;
      InputStreamReader input = null;
      URL url = new URL(conn.getUrl());
      urlConn = url.openConnection();
      if (factory != null && urlConn instanceof HttpsURLConnection) {
        if (debug) {
          debugLog.append("Using custom factory for SSL \r");
        }
        ((HttpsURLConnection) urlConn).setSSLSocketFactory(factory);
      }

      ((HttpURLConnection) urlConn).setRequestMethod("POST");

      urlConn.setRequestProperty("Content-Type",
          "application/soap+xml;charset=UTF-8;action=\"urn:cdc:iisb:2011:submitSingleMessage\"");
      urlConn.setDoInput(true);
      urlConn.setDoOutput(true);
      urlConn.setUseCaches(false);
      String content;

      StringBuilder sb = new StringBuilder();
      sb.append(
          "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:urn=\"urn:cdc:iisb:2011\">");
      sb.append("  <soap:Header/>");
      sb.append("  <soap:Body>");
      sb.append("   <urn:submitSingleMessage>");
      sb.append("      <urn:username>" + userid + "</urn:username>");
      sb.append("      <urn:password>" + password + "</urn:password>");
      sb.append("      <urn:facilityID>" + facilityid + "</urn:facilityID>");
      sb.append("      <urn:hl7Message>");
      sb.append(request.replaceAll("\\&", "&amp;"));
      sb.append("</urn:hl7Message>");
      sb.append("   </urn:submitSingleMessage>");
      sb.append("   </soap:Body>");
      sb.append(" </soap:Envelope>");
      content = sb.toString();

      printout = new DataOutputStream(urlConn.getOutputStream());
      printout.writeBytes(content);
      printout.flush();
      printout.close();
      input = new InputStreamReader(urlConn.getInputStream());
      StringBuilder response = new StringBuilder();
      BufferedReader in = new BufferedReader(input);
      String line;
      while ((line = in.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      input.close();
      String responseString = getResponse(response);
      if (debug) {
        response = new StringBuilder(responseString);
        response.append("\r");
        response.append("DEBUG LOG: \r");
        response.append(debugLog);
        responseString = response.toString();
      }
      return responseString;
    } catch (IOException e) {
      e.printStackTrace(System.out);
      if (urlConn != null) {
        try {
          InputStreamReader input =
              new InputStreamReader(((HttpURLConnection) urlConn).getErrorStream());
          BufferedReader in = new BufferedReader(input);
          String line;
          while ((line = in.readLine()) != null) {
            System.out.println(line);
          }
          input.close();
        } catch (Exception ei) {
          ei.printStackTrace(System.err);
        }
      }
      if (debug) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter);
        out.println("Unable to complete request");
        e.printStackTrace(out);
        out.println("DEBUG LOG: \r");
        out.println(debugLog);
        out.close();
        return stringWriter.toString();
      } else {
        throw e;
      }
    }

  }

  @Override
  public String connectivityTest(String message) throws Exception {
    StringBuilder debugLog = null;
    URLConnection urlConn = null;
    try {
      SSLSocketFactory factory = setupSSLSocketFactory(false, debugLog);
      DataOutputStream printout;
      InputStreamReader input = null;
      URL url = new URL(getUrl());
      urlConn = url.openConnection();
      if (factory != null && urlConn instanceof HttpsURLConnection) {
        ((HttpsURLConnection) urlConn).setSSLSocketFactory(factory);
      }

      ((HttpURLConnection) urlConn).setRequestMethod("POST");

      urlConn.setRequestProperty("Content-Type",
          "application/soap+xml;charset=UTF-8;action=\"urn:cdc:iisb:2011:connectivityTest\"");
      urlConn.setDoInput(true);
      urlConn.setDoOutput(true);
      urlConn.setUseCaches(false);
      String content;

      StringBuilder sb = new StringBuilder();
      sb.append(
          "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:urn=\"urn:cdc:iisb:2011\">");
      sb.append("  <soap:Header/>");
      sb.append("    <soap:Body>");
      sb.append("      <urn:connectivityTest>");
      sb.append("         <urn:echoBack>" + message + "</urn:echoBack>");
      sb.append("      </urn:connectivityTest>");
      sb.append("   </soap:Body>");
      sb.append("</soap:Envelope>");
      content = sb.toString();

      printout = new DataOutputStream(urlConn.getOutputStream());
      printout.writeBytes(content);
      printout.flush();
      printout.close();
      input = new InputStreamReader(urlConn.getInputStream());
      StringBuilder response = new StringBuilder();
      BufferedReader in = new BufferedReader(input);
      String line;
      while ((line = in.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      input.close();
      String responseString = getResponse(response);
      return responseString;
    } catch (IOException e) {
      e.printStackTrace(System.err);
      if (urlConn != null) {
        try {
          InputStreamReader input =
              new InputStreamReader(((HttpURLConnection) urlConn).getErrorStream());
          BufferedReader in = new BufferedReader(input);
          String line;
          while ((line = in.readLine()) != null) {
            System.out.println(line);
          }
          input.close();
        } catch (Exception ei) {
          ei.printStackTrace(System.err);
        }
      }
    }
    return "";
  }

  public String getResponse(StringBuilder response) {
    String responseString = response.toString();
    int startPos = responseString.indexOf("return>");
    if (startPos > 0) {
      int endPos = responseString.indexOf("</", startPos);
      if (endPos > startPos) {
        responseString = responseString.substring(startPos + "return>".length(), endPos);
        response = new StringBuilder(responseString);
      }
      responseString = responseString.replaceAll("\\Q&amp;\\E", "&").replaceAll("&#xD;", "\r");
    }
    return responseString;
  }

  @Override
  protected void setupFields(List<String> fields) {
      // nothing to do
  }

  @Override
  protected void makeScriptAdditions(StringBuilder sb) {
    // nothing to do
  }

}
