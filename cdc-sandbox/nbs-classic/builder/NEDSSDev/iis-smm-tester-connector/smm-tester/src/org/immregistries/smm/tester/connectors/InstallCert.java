package org.immregistries.smm.tester.connectors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/*
 * Copyright 2006 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * - Neither the name of Sun Microsystems nor the names of its contributors may be used to endorse
 * or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * Originally from: http://blogs.sun.com/andreas/resource/InstallCert.java Use: java InstallCert
 * hostname Example: % java InstallCert ecc.fedora.redhat.com
 */
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Class used to add the server's certificate to the KeyStore with your trusted certificates.
 */
public class InstallCert {

  private String host = "";
  private int port = 443;
  private File file = null;
  private char[] passphrase = "changeit".toCharArray();
  private X509Certificate[] chain = null;
  private int chainPos = 0;
  private KeyStore keyStore = null;

  public KeyStore getKeyStore() {
    return keyStore;
  }

  public void setKeyStore(KeyStore ks) {
    this.keyStore = ks;
  }

  public int getChainPos() {
    return chainPos;
  }

  public void setChainPos(int chainPos) {
    this.chainPos = chainPos;
  }

  public X509Certificate[] getChain() {
    return chain;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public InstallCert() {
    // default
  }

  public void findCert(PrintWriter logOut) throws Exception {
    logOut.println("Loading KeyStore " + file);
    keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

    File loadFile = file;
    if (loadFile.isFile() == false) {
      logOut.println("Key store file does not exist, loading system one ");
      char SEP = File.separatorChar;
      File dir = new File(System.getProperty("java.home") + SEP + "lib" + SEP + "security");
      loadFile = new File(dir, "jssecacerts");
      if (loadFile.isFile() == false) {
        loadFile = new File(dir, "cacerts");
      }
    }

    if (loadFile.exists()) {
      logOut.println("Reading KeyStore " + loadFile);
      InputStream in = new FileInputStream(loadFile);
      keyStore.load(in, passphrase);
      in.close();
    }

    SSLContext context = SSLContext.getInstance("TLS");
    TrustManagerFactory tmf =
        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    tmf.init(keyStore);
    X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
    SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
    context.init(null, new TrustManager[] {tm}, null);
    SSLSocketFactory factory = context.getSocketFactory();

    logOut.println("Opening connection to " + host + ":" + port + "...");
    SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
    socket.setSoTimeout(10000);
    try {
      logOut.println("Starting SSL handshake...");
      socket.startHandshake();
      socket.close();
      logOut.println();
      logOut.println("No errors, certificate is already trusted");
    } catch (SSLException e) {
      logOut.println();
      e.printStackTrace(logOut);
    }

    chain = tm.chain;
    if (chain == null) {
      logOut.println("Could not obtain server certificate chain");
      return;
    }

    logOut.println();
    logOut.println("Server sent " + chain.length + " certificate(s):");
    logOut.println();
    MessageDigest sha1 = MessageDigest.getInstance("SHA1");
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    for (int i = 0; i < chain.length; i++) {
      X509Certificate cert = chain[i];
      logOut.println(" Cert #" + (i + 1));
      logOut.println(" Subject " + cert.getSubjectDN());
      logOut.println(" Issuer " + cert.getIssuerDN());
      sha1.update(cert.getEncoded());
      logOut.println(" sha1 " + toHexString(sha1.digest()));
      md5.update(cert.getEncoded());
      logOut.println(" md5 " + toHexString(md5.digest()));
      logOut.println();
    }

  }

  private void addCert(PrintWriter logOut) throws KeyStoreException, FileNotFoundException,
      IOException, NoSuchAlgorithmException, CertificateException {
    X509Certificate cert = chain[chainPos];
    String alias = host + "-" + (chainPos + 1);
    keyStore.setCertificateEntry(alias, cert);

    OutputStream outStream = new FileOutputStream(file);
    keyStore.store(outStream, passphrase);
    outStream.close();

    logOut.println();
    logOut.println(cert);
    logOut.println();
    logOut.println(
        "Added certificate to keystore '" + file.getName() + "' using alias '" + alias + "'");
  }

  public void saveCert(PrintWriter logOut) throws Exception {
    addCert(logOut);
  }

  private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();

  private static String toHexString(byte[] bytes) {
    StringBuilder sb = new StringBuilder(bytes.length * 3);
    for (int b : bytes) {
      b &= 0xff;
      sb.append(HEXDIGITS[b >> 4]);
      sb.append(HEXDIGITS[b & 15]);
      sb.append(' ');
    }
    return sb.toString();
  }

  private static class SavingTrustManager implements X509TrustManager {

    private final X509TrustManager tm;
    private X509Certificate[] chain;

    SavingTrustManager(X509TrustManager tm) {
      this.tm = tm;
    }

    public X509Certificate[] getAcceptedIssuers() {
      throw new UnsupportedOperationException();
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
      throw new UnsupportedOperationException();
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
      this.chain = chain;
      tm.checkServerTrusted(chain, authType);
    }
  }
}
