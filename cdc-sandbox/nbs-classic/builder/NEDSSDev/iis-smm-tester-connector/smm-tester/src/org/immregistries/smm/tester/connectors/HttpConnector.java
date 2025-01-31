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
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * 
 * @author nathan
 */
public class HttpConnector extends Connector {

  public static enum AuthenticationMethod {
    FORM, HEADER, BASIC
  };

  public static final String FIELD_USERID = "USERID";
  public static final String FIELD_PASSWORD = "PASSWORD";
  public static final String FIELD_FACILITYID = "FACILITYID";
  public static final String FIELD_MESSAGEDATA = "MESSAGEDATA";
  public static final String FIELD_OTHERID = "OTHERID";
  public static int USERID = 0;
  public static int PASSWORD = 1;
  public static int FACILITYID = 2;
  public static int MESSAGEDATA = 3;
  public static int OTHERID = 4;
  private boolean stripXML = false;
  private boolean deduplicate = false;
  private AuthenticationMethod authenticationMethod = AuthenticationMethod.FORM;
  private String[] fieldNames =
      {FIELD_USERID, FIELD_PASSWORD, FIELD_FACILITYID, FIELD_MESSAGEDATA, FIELD_OTHERID};

  public AuthenticationMethod getAuthenticationMethod() {
    return authenticationMethod;
  }

  public void setAuthenticationMethod(AuthenticationMethod authenticationMethod) {
    this.authenticationMethod = authenticationMethod;
  }

  protected HttpConnector(String label, String url, String type) {
    super(label, type);
    this.url = url;
  }

  public HttpConnector(String label, String url) {
    super(label, "POST");
    this.url = url;
  }

  public HttpConnector setFieldName(int field, String fieldName) {
    fieldNames[field] = fieldName;
    return this;
  }

  public HttpConnector stripXML() {
    stripXML = true;
    return this;
  }

  public HttpConnector deduplicate() {
    deduplicate = true;
    return this;
  }

  @Override
  public String submitMessage(String message, boolean debug) throws Exception {
    ClientConnection cc = new ClientConnection();
    cc.setUserId(userid);
    cc.setPassword(password);
    cc.setFacilityId(facilityid);
    cc.setUrl(url);
    cc.setOtherId(otherid);
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

    if (stripXML) {
      boolean foundMSH = false;
      String msh = "";
      StringBuffer sbuf = new StringBuffer(result.length());
      boolean inTag = false;
      boolean escaped = false;
      String escapedString = "";
      for (char c : result.toCharArray()) {
        if (c == '<') {
          inTag = true;
        } else if (c == '>') {
          inTag = false;
        } else if (c == '&') {
          escaped = true;
          escapedString = "";
        } else if (escaped) {
          if (c == ';') {
            escaped = false;
            if (escapedString.equals("amp")) {
              sbuf.append("&");
            }
          } else {
            escapedString += c;
          }
        } else if (!inTag && foundMSH) {
          sbuf.append(c);
        } else {
          if (msh.equals("") && c == 'M') {
            msh = msh + c;
          } else if (msh.equals("M") && c == 'S') {
            msh = msh + c;
          } else if (msh.equals("MS") && c == 'H') {
            msh = msh + c;
            sbuf.append(msh);
            foundMSH = true;
          }
        }
      }
      if (sbuf.length() > 0) {
        result = sbuf.toString();
      }
    }
    while (result != null && result.length() > 0 && result.charAt(0) < ' ') {
      result = result.substring(1);
    }
    return result;

  }

  public String sendRequest(String request, ClientConnection conn, boolean debug)
      throws IOException {
    StringBuilder debugLog = null;
    if (debug) {
      debugLog = new StringBuilder();
    }
    try {
      SSLSocketFactory factory = setupSSLSocketFactory(debug, debugLog);

      HttpURLConnection urlConn;
      DataOutputStream printout;
      InputStreamReader input = null;
      URL url = new URL(conn.getUrl());

      urlConn = (HttpURLConnection) url.openConnection();
      if (factory != null && urlConn instanceof HttpsURLConnection) {
        ((HttpsURLConnection) urlConn).setSSLSocketFactory(factory);
      }

      urlConn.setRequestMethod("POST");

      urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      urlConn.setDoInput(true);
      urlConn.setDoOutput(true);
      urlConn.setUseCaches(false);
      String content;
      if (authenticationMethod == AuthenticationMethod.HEADER) {
        if (debug) {
          debugLog.append(">> Sending credentials using HTTP headers to " + conn.getUrl() + "\r");
          debugLog.append(">> " + fieldNames[USERID] + " = '" + conn.getUserId() + "' \r");
          debugLog.append(">> " + fieldNames[PASSWORD] + " = '" + conn.getPassword() + "' \r");
          debugLog.append(">> " + fieldNames[FACILITYID] + " = '" + conn.getFacilityId() + "' \r");
        }
        content = request;
        urlConn.setRequestProperty(fieldNames[USERID], conn.getUserId());
        urlConn.setRequestProperty(fieldNames[PASSWORD], conn.getPassword());
        urlConn.setRequestProperty(fieldNames[FACILITYID], conn.getFacilityId());
      } else if (authenticationMethod == AuthenticationMethod.BASIC) {
        if (debug) {
          debugLog.append(
              ">> Sending credentials using HTTP Basic Authentication to " + conn.getUrl() + "\r");
          debugLog.append(">> Username = '" + conn.getUserId() + "' \r");
          debugLog.append(">> Password = '" + conn.getPassword() + "' \r");
        }
        urlConn.setRequestProperty("Authorization",
            "Basic " + URLEncoder.encode(conn.getUserId() + ":" + conn.getPassword(), "UTF-8"));
        content = request;
      } else {
        if (debug) {
          debugLog.append(
              ">> Sending credentials using standard XML Encoded form to " + conn.getUrl() + "\r");
          debugLog.append(">> " + fieldNames[USERID] + " = '" + conn.getUserId() + "' \r");
          debugLog.append(">> " + fieldNames[PASSWORD] + " = '" + conn.getPassword() + "' \r");
          debugLog.append(">> " + fieldNames[FACILITYID] + " = '" + conn.getFacilityId() + "' \r");
          debugLog.append(">> " + fieldNames[MESSAGEDATA] + " \r");
        }
        StringBuilder sb = new StringBuilder();

        if (deduplicate) {
          sb.append("deduplicate=deduplicate&");
        }

        sb.append(fieldNames[USERID]);
        sb.append("=");
        sb.append(URLEncoder.encode(conn.getUserId(), "UTF-8"));

        sb.append("&");
        sb.append(fieldNames[PASSWORD]);
        sb.append("=");
        sb.append(URLEncoder.encode(conn.getPassword(), "UTF-8"));

        sb.append("&");
        sb.append(fieldNames[FACILITYID]);
        sb.append("=");
        sb.append(URLEncoder.encode(conn.getFacilityId(), "UTF-8"));

        if (conn.getOtherId() != null && !conn.getOtherId().equals("")) {
          sb.append("&");
          sb.append(fieldNames[OTHERID]);
          sb.append("=");
          sb.append(URLEncoder.encode(conn.getOtherId(), "UTF-8"));
        }

        sb.append("&");
        sb.append(fieldNames[MESSAGEDATA]);
        sb.append("=");
        sb.append(URLEncoder.encode(request, "UTF-8"));

        content = sb.toString();
      }
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
      if (debug) {
        response.append("\r");
        response.append("DEBUG LOG: \r");
        response.append(debugLog);
      }
      return response.toString();
    } catch (IOException e) {
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

  protected SSLSocketFactory setupSSLSocketFactory(boolean debug, StringBuilder debugLog) {
    SSLSocketFactory factory = null;

    KeyStore keyStore = getKeyStore();
    if (keyStore != null) {
      if (debug) {
        debugLog.append("Key store defined, looking to load it for use on this connection \r");
      }
      try {

        String algorithm = KeyManagerFactory.getDefaultAlgorithm();
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);

        kmf.init(keyStore, getKeyStorePassword().toCharArray());

        TrustManagerFactory tmf =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);
        X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
        if (debug) {
          doDebug(debugLog, keyStore, defaultTrustManager);
        }

        TrustManager[] trustAllCerts = null;
        if (disableServerCertificateCheck) {
          trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
              return new X509Certificate[0];
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
                String authType) {}

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
                String authType) {}
          }};
        }

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(kmf.getKeyManagers(), trustAllCerts, null);
        factory = context.getSocketFactory();
        if (debug) {
          debugLog.append("Key store loaded \r");
        }
      } catch (Exception e) {
        e.printStackTrace();
        if (debug) {
          debugLog.append("Unable to load key store: " + e.getMessage() + " \r");
        }
      }
    } else {
      if (debug) {
        debugLog.append("Key store was not defined, using default for this connection \r");
      }
    }
    return factory;
  }

  public void doDebug(StringBuilder debugLog, KeyStore keyStore,
      X509TrustManager defaultTrustManager) throws KeyStoreException {
    debugLog.append("Trusted certificates: \r");
    for (X509Certificate cert : defaultTrustManager.getAcceptedIssuers()) {
      String certStr = "S:" + cert.getSubjectDN().getName() + " I:" + cert.getIssuerDN().getName();
      debugLog.append(" + " + certStr + " \r");
    }
    Enumeration<String> enumeration = keyStore.aliases();
    while (enumeration.hasMoreElements()) {
      String alias = (String) enumeration.nextElement();
      debugLog.append(" - " + alias);
      Certificate certificate = keyStore.getCertificate(alias);
      debugLog.append(certificate.toString());
    }
  }

  @Override
  public String connectivityTest(String message) throws Exception {
    return "Connectivity test not supported for HTTPS POST connections";
  }

  @Override
  protected void setupFields(List<String> fields) {
    for (String field : fields) {
      if (field.startsWith("Set FieldName USERID:")) {
        setFieldName(HttpConnector.USERID, readValue(field));
      } else if (field.startsWith("Set FieldName PASSWORD:")) {
        setFieldName(HttpConnector.PASSWORD, readValue(field));
      } else if (field.startsWith("Set FieldName FACILITYID:")) {
        setFieldName(HttpConnector.FACILITYID, readValue(field));
      } else if (field.startsWith("Set FieldName MESSAGEDATA:")) {
        setFieldName(HttpConnector.MESSAGEDATA, readValue(field));
      } else if (field.startsWith("Set FieldName OTHERID:")) {
        setFieldName(HttpConnector.OTHERID, readValue(field));
      } else if (field.startsWith("Strip XML:")) {
        stripXML = true;
      } else if (field.startsWith("Deduplicate:")) {
        deduplicate = true;
      } else if (field.startsWith("Authentication Method:")) {
        String s = readValue(field);
        if (s.equalsIgnoreCase(AuthenticationMethod.HEADER.toString())) {
          authenticationMethod = AuthenticationMethod.HEADER;
        } else if (s.equalsIgnoreCase(AuthenticationMethod.BASIC.toString())) {
          authenticationMethod = AuthenticationMethod.BASIC;
        }
      }
    }

  }

  @Override
  protected void makeScriptAdditions(StringBuilder sb) {
    if (!fieldNames[USERID].equals(FIELD_USERID)) {
      sb.append("Set FieldName USERID: ");
      sb.append(fieldNames[USERID]);
      sb.append("\n");
    }
    if (!fieldNames[PASSWORD].equals(FIELD_PASSWORD)) {
      sb.append("Set FieldName PASSWORD: ");
      sb.append(fieldNames[PASSWORD]);
      sb.append("\n");
    }
    if (!fieldNames[FACILITYID].equals(FIELD_FACILITYID)) {
      sb.append("Set FieldName FACILITYID: ");
      sb.append(fieldNames[FACILITYID]);
      sb.append("\n");
    }
    if (!fieldNames[MESSAGEDATA].equals(FIELD_MESSAGEDATA)) {
      sb.append("Set FieldName MESSAGEDATA: ");
      sb.append(fieldNames[MESSAGEDATA]);
      sb.append("\n");
    }
    if (!fieldNames[OTHERID].equals(FIELD_OTHERID)) {
      sb.append("Set FieldName OTHERID: ");
      sb.append(fieldNames[OTHERID]);
      sb.append("\n");
    }
    if (stripXML) {
      sb.append("Strip XML: true\n");
    }
    if (deduplicate) {
      sb.append("Deduplicate: true\n");
    }
    if (authenticationMethod != null && authenticationMethod != AuthenticationMethod.FORM) {
      sb.append("Authentication Method: " + authenticationMethod);
    }
  }
}
