/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package org.immregistries.smm.tester.connectors;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
public class HttpRawConnector extends Connector {

  protected HttpRawConnector(String label, String url, String type) {
    super(label, type);
    this.url = url;
  }

  public HttpRawConnector(String label, String url) {
    super(label, "RAW");
    this.url = url;
  }

  @Override
  protected void setupFields(List<String> fields) {}

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

      urlConn.setRequestProperty("Content-Type", "text/plain");
      urlConn.setDoInput(true);
      urlConn.setDoOutput(true);
      urlConn.setUseCaches(false);
      StringBuilder content = new StringBuilder();
      // if (false) {
      // urlConn.setRequestProperty(fieldNames[USERID], conn.getUserId());
      // urlConn.setRequestProperty(fieldNames[PASSWORD], conn.getPassword());
      // urlConn.setRequestProperty(fieldNames[FACILITYID],
      // conn.getFacilityId());
      // }
      {
        StringReader stringReader = new StringReader(request);
        BufferedReader bufReader = new BufferedReader(stringReader);
        String line;
        while ((line = bufReader.readLine()) != null) {
          content.append(line);
          content.append('\r');
        }
        bufReader.close();
      }

      printout = new DataOutputStream(urlConn.getOutputStream());
      printout.writeBytes(content.toString());
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
  protected void makeScriptAdditions(StringBuilder sb) {
    // TODO Auto-generated method stub

  }

}
