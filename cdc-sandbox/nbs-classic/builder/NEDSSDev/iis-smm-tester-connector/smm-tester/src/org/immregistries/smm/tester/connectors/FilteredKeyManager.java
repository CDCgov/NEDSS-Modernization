package org.immregistries.smm.tester.connectors;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509KeyManager;

public class FilteredKeyManager implements X509KeyManager {

  private final X509KeyManager originatingKeyManager;
  private final X509Certificate[] x509Certificates;

  public FilteredKeyManager(X509KeyManager originatingKeyManager,
      X509Certificate[] x509Certificates) {
    this.originatingKeyManager = originatingKeyManager;
    this.x509Certificates = x509Certificates;
  }

  public X509Certificate[] getCertificateChain(String alias) {
    return x509Certificates;
  }

  public String[] getClientAliases(String keyType, Principal[] issuers) {
    return new String[] {"ihs"};
  }

  public String chooseClientAlias(String[] arg0, Principal[] arg1, Socket arg2) {
    return originatingKeyManager.chooseClientAlias(arg0, arg1, arg2);
  }

  public String chooseServerAlias(String arg0, Principal[] arg1, Socket arg2) {
    return originatingKeyManager.chooseServerAlias(arg0, arg1, arg2);
  }

  public PrivateKey getPrivateKey(String arg0) {
    return originatingKeyManager.getPrivateKey(arg0);
  }

  public String[] getServerAliases(String arg0, Principal[] arg1) {
    // TODO Auto-generated method stub
    return originatingKeyManager.getServerAliases(arg0, arg1);
  }
}
