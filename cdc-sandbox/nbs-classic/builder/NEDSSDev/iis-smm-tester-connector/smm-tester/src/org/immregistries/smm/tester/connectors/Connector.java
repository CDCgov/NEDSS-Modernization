/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package org.immregistries.smm.tester.connectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.immregistries.smm.mover.AckAnalyzer;
import org.immregistries.smm.tester.PasswordEncryptUtil;

/**
 * 
 * @author nathan
 */
public abstract class Connector {

  public static final String PURPOSE_GENERAL = "General";
  public static final String PURPOSE_UPDATE = "Update";
  public static final String PURPOSE_QUERY = "Query";

  private boolean setupGlobalKeyStore = true;

  public boolean isSetupGlobalKeyStore() {
    return setupGlobalKeyStore;
  }

  public void setSetupGlobalKeyStore(boolean setupGlobalKeyStore) {
    this.setupGlobalKeyStore = setupGlobalKeyStore;
  }

  protected abstract void setupFields(List<String> fields);

  protected static Connector addConnector(String label, String type, String url, String userid,
      String otherid, String facilityid, String password, String keyStorePassword,
      String enableTimeStart, String enableTimeEnd, AckAnalyzer.AckType ackType,
      TransferType transferType, List<String> fields, String customTransformations,
      String assesmentTransformations, List<Connector> connectors, String purpose,
      int tchForecastTesterSoftwareId, int tchForecastTesterTaskGroupId, String rxaFilterFacilityId,
      Set<String> queryResponseFieldsNotReturnedSet, Map<String, String> scenarioTransformationsMap,
      boolean disableServerCertificateCheck, String aartPublicIdCode, String aartAccessPasscode)
      throws Exception {
    if (!label.equals("") && !type.equals("")) {
      Connector connector = null;
      if (type.equals(ConnectorFactory.TYPE_SOAP)) {
        connector = new SoapConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_POST)) {
        connector = new HttpConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_MLLP)) {
            connector = new MLLPConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_RAW)) {
        connector = new HttpRawConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_HI_SOAP)) {
        connector = new HISoapConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_ENVISION_SOAP)) {
        connector = new EnvisionConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_ENVISION_SOAP11)) {
        connector = new EnvisionConnector(label, url, true);
      } else if (type.equals(ConnectorFactory.TYPE_OR_SOAP)) {
        connector = new ORConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_WI_SOAP)) {
        connector = new WIConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_IL_WS)) {
        connector = new ILConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_IL_SOAP)) {
        connector = new ILSoapConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_MA_SOAP)) {
        connector = new MAConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_MO_SOAP)) {
        connector = new MOConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_NJ_SOAP)) {
        connector = new NJConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_CA_SOAP)) {
        connector = new CASoapConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_SC_SOAP)) {
        connector = new SCSoapConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_AL_SOAP)) {
        connector = new ALSoapConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_CO_SOAP)) {
        connector = new COSoapConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_FL_SOAP)) {
        connector = new FLSoapConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_KS_SOAP)) {
        connector = new KSSoapConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_AZ_SOAP)) {
        connector = new AZSoapConnector(label, url);
      } else if (type.equals(ConnectorFactory.TYPE_ND_SOAP)) {
        connector = new NDSoapConnector(label, url);
      } else {
        connector = new HttpConnector(label, url);
      }
      connector.setUserid(userid);
      connector.setPurpose(purpose);
      connector.setOtherid(otherid);
      connector.setFacilityid(facilityid);
      connector.setPassword(password);
      connector.setupFields(fields);
      connector.setCustomTransformations(customTransformations);
      connector.setAssessmentTransformations(assesmentTransformations);
      connector.setKeyStorePassword(keyStorePassword);
      connector.setAckType(ackType);
      connector.setTransferType(transferType);
      connector.setDisableServerCertificateCheck(disableServerCertificateCheck);
      connector.setEnableTimeStart(enableTimeStart);
      connector.setEnableTimeEnd(enableTimeEnd);
      connector.setTchForecastTesterSoftwareId(tchForecastTesterSoftwareId);
      connector.setTchForecastTesterTaskGroupId(tchForecastTesterTaskGroupId);
      connector.setQueryResponseFieldsNotReturnedSet(queryResponseFieldsNotReturnedSet);
      connector.setScenarioTransformationsMap(scenarioTransformationsMap);
      connector.setRxaFilterFacilityId(rxaFilterFacilityId);
      connector.setAartPublicIdCode(aartPublicIdCode);
      connector.setAartAccessPasscode(aartAccessPasscode);
      connectors.add(connector);

      return connector;
    }
    return null;
  }

  public static enum TransferType {
    NEAR_REAL_TIME_LINK, RECIPROCAL_BATCH_UPDATE, MANUAL
  };

  protected String label = "";
  protected String type = "";
  protected String userid = "";
  protected String otherid = "";
  protected String password = "";
  protected String facilityid = "";
  protected String url = "";
  protected String currentFilename = "";
  protected String currentControlId = "";
  protected String enableTimeStart = "";
  protected String enableTimeEnd = "";
  protected boolean disableServerCertificateCheck = false;
  protected TransferType transferType = TransferType.NEAR_REAL_TIME_LINK;
  private String customTransformations = "";
  private String assessmentTransformations = "";
  private String[] quickTransformations;
  private KeyStore keyStore = null;
  private String keyStorePassword = null;
  private AckAnalyzer.AckType ackType = AckAnalyzer.AckType.DEFAULT;
  private Map<String, Connector> otherConnectorMap = new HashMap<String, Connector>();
  private String purpose = "";
  private Set<String> queryResponseFieldsNotReturnedSet = null;
  private Map<String, String> scenarioTransformationsMap = new HashMap<String, String>();
  private String segmentSeparator = "\r";
  private String rxaFilterFacilityId = "";
  private String aartPublicIdCode = "";
  private String aartAccessPasscode = "";

  public String getAssessmentTransformations() {
    return assessmentTransformations;
  }

  public void setAssessmentTransformations(String assessmentTransformations) {
    this.assessmentTransformations = assessmentTransformations;
  }

  public String getAartPublicIdCode() {
    return aartPublicIdCode;
  }

  public void setAartPublicIdCode(String aartPublicIdCode) {
    this.aartPublicIdCode = aartPublicIdCode;
  }

  public String getAartAccessPasscode() {
    return aartAccessPasscode;
  }

  public void setAartAccessPasscode(String aartAccessPasscode) {
    this.aartAccessPasscode = aartAccessPasscode;
  }

  public String getRxaFilterFacilityId() {
    return rxaFilterFacilityId;
  }

  public void setRxaFilterFacilityId(String rxaFilterFacilityId) {
    this.rxaFilterFacilityId = rxaFilterFacilityId;
  }

  public boolean isRxaFilter() {
    return rxaFilterFacilityId != null && !rxaFilterFacilityId.equals("");
  }
  
  public void shutdown() {
	  System.out.println("Shutting down " + label);
  }

  public Connector(Connector copy) {
    this.label = copy.label;
    this.type = copy.type;
    this.userid = copy.userid;
    this.otherid = copy.otherid;
    this.password = copy.password;
    this.facilityid = copy.facilityid;
    this.url = copy.url;
    this.currentFilename = copy.currentFilename;
    this.currentControlId = copy.currentControlId;
    this.enableTimeStart = copy.enableTimeStart;
    this.enableTimeEnd = copy.enableTimeEnd;
    this.disableServerCertificateCheck = copy.disableServerCertificateCheck;
    this.transferType = copy.transferType;
    this.customTransformations = copy.customTransformations;
    this.assessmentTransformations = copy.assessmentTransformations;
    this.quickTransformations = copy.quickTransformations;
    this.keyStore = copy.keyStore;
    this.keyStorePassword = copy.keyStorePassword;
    this.ackType = copy.ackType;
    this.otherConnectorMap = copy.otherConnectorMap;
    this.purpose = copy.purpose;
    this.queryResponseFieldsNotReturnedSet = copy.queryResponseFieldsNotReturnedSet;
    this.scenarioTransformationsMap = copy.scenarioTransformationsMap;
    this.segmentSeparator = copy.segmentSeparator;
    this.rxaFilterFacilityId = copy.rxaFilterFacilityId;
  }

  public String getSegmentSeparator() {
    return segmentSeparator;
  }

  public void setSegmentSeparator(String segmentSeparator) {
    this.segmentSeparator = segmentSeparator;
  }

  public boolean isDisableServerCertificateCheck() {
    return disableServerCertificateCheck;
  }

  public void setDisableServerCertificateCheck(boolean disableServerCertificateCheck) {
    this.disableServerCertificateCheck = disableServerCertificateCheck;
  }

  public void setScenarioTransformationsMap(Map<String, String> scenarioTransformationsMap) {
    this.scenarioTransformationsMap = scenarioTransformationsMap;
  }

  public Map<String, String> getScenarioTransformationsMap() {
    return scenarioTransformationsMap;
  }

  public void setQueryResponseFieldsNotReturnedSet(Set<String> queryResponseFieldsNotReturnedSet) {
    this.queryResponseFieldsNotReturnedSet = queryResponseFieldsNotReturnedSet;
  }

  public Set<String> getQueryResponseFieldsNotReturnedSet() {
    return queryResponseFieldsNotReturnedSet;
  }

  private int tchForecastTesterSoftwareId = 0;
  private int tchForecastTesterTaskGroupId = 0;

  public int getTchForecastTesterTaskGroupId() {
    return tchForecastTesterTaskGroupId;
  }

  public void setTchForecastTesterTaskGroupId(int tchForecastTesterTaskGroupId) {
    this.tchForecastTesterTaskGroupId = tchForecastTesterTaskGroupId;
  }

  public int getTchForecastTesterSoftwareId() {
    return tchForecastTesterSoftwareId;
  }

  public void setTchForecastTesterSoftwareId(int tchForecastTesterSoftwareId) {
    this.tchForecastTesterSoftwareId = tchForecastTesterSoftwareId;
  }

  public Map<String, Connector> getOtherConnectorMap() {
    return otherConnectorMap;
  }

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public String getOtherid() {
    return otherid;
  }

  public void setOtherid(String otherid) {
    this.otherid = otherid;
  }

  public String getCurrentControlId() {
    return currentControlId;
  }

  public void setCurrentControlId(String currentControlId) {
    this.currentControlId = currentControlId;
  }

  public String getEnableTimeStart() {
    return enableTimeStart;
  }

  public void setEnableTimeStart(String enableTimeStart) {
    this.enableTimeStart = enableTimeStart;
  }

  public String getEnableTimeEnd() {
    return enableTimeEnd;
  }

  public void setEnableTimeEnd(String enableTimeEnd) {
    this.enableTimeEnd = enableTimeEnd;
  }

  public TransferType getTransferType() {
    return transferType;
  }

  public void setTransferType(TransferType transferType) {
    this.transferType = transferType;
  }

  protected boolean throwExceptions = false;

  public String getCurrentFilename() {
    return currentFilename;
  }

  public void setCurrentFilename(String currentFilename) {
    this.currentFilename = currentFilename;
  }

  public AckAnalyzer.AckType getAckType() {
    return ackType;
  }

  public void setAckType(AckAnalyzer.AckType ackType) {
    this.ackType = ackType;
  }

  public boolean isThrowExceptions() {
    return throwExceptions;
  }

  public void setThrowExceptions(boolean throwExceptions) {
    this.throwExceptions = throwExceptions;
  }

  public String getKeyStorePassword() {
    return keyStorePassword;
  }

  public void setKeyStorePassword(String keyStorePassword) {
    this.keyStorePassword = keyStorePassword;
  }

  public KeyStore getKeyStore() {
    return keyStore;
  }

  public void setKeyStore(KeyStore keyStore) {
    this.keyStore = keyStore;
  }

  public String[] getQuickTransformations() {
    return quickTransformations;
  }

  public void setQuickTransformations(String[] quickTransformations) {
    this.quickTransformations = quickTransformations;
  }

  public String getCustomTransformations() {
    return customTransformations;
  }

  public void setCustomTransformations(String customTransformations) {
    this.customTransformations = customTransformations;
  }

  public void addCustomTransformation(String customTransformation) {
    if (this.customTransformations == null) {
      this.customTransformations = customTransformation + "/n";
    } else {
      this.customTransformations += customTransformation + "/n";
    }
  }

  public void addAsssementTransformation(String assessmentTransformation) {
    if (this.assessmentTransformations == null) {
      this.assessmentTransformations = assessmentTransformations + "/n";
    } else {
      this.assessmentTransformations += assessmentTransformations + "/n";
    }
  }

  public String getUrl() {
    return url;
  }

  public String getUrlShort() {
    if (url != null && url.length() > 28) {
      return url.substring(0, 28) + "..";
    }
    return url;
  }

  public String getLabelDisplay() {
    if (purpose.equals("")) {
      return label + " (" + type + ")";
    }
    return label + " - " + purpose + " (" + type + ")";
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getFacilityid() {
    return facilityid;
  }

  public void setFacilityid(String facilityid) {
    this.facilityid = facilityid;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getLabel() {
    return label;
  }

  public Connector(String label, String type) {
    this.label = label;
    this.type = type;
  }

  public abstract String submitMessage(String message, boolean debug) throws Exception;

  public abstract String connectivityTest(String message) throws Exception;

  public String getScript() {
    StringBuilder sb = new StringBuilder();
    sb.append("-----------------------------------------\n");
    sb.append("Connection\n");
    sb.append("Label: " + label + "\n");
    if (!purpose.equals("")) {
      sb.append("Purpose: " + purpose);
    }
    sb.append("Type: " + type + "\n");
    sb.append("URL: " + url + "\n");
    sb.append("User Id: " + userid + "\n");
    if (otherid != null && !otherid.equals("")) {
      sb.append("Other Id: " + otherid + "\n");
    }
    try {
      sb.append("Password: " + PasswordEncryptUtil.encrypt(password) + "\n");
    } catch (Exception e) {
      sb.append("Password: \n");
      e.printStackTrace();
    }
    sb.append("Facility Id: " + facilityid + "\n");
    if (keyStorePassword != null && keyStorePassword.length() > 0) {
      try {
        sb.append("Key Store Password: " + PasswordEncryptUtil.encrypt(keyStorePassword) + "\n");
      } catch (Exception e) {
        sb.append("Key Store Password: \n");
        e.printStackTrace();
      }
    }
    sb.append("Ack Type: " + ackType + "\n");
    sb.append("Transfer Type: " + transferType + "\n");
    if (!enableTimeStart.equals("")) {
      sb.append("Enable Time Start: " + enableTimeStart + "\n");
    }
    if (!enableTimeEnd.equals("")) {
      sb.append("Enable Time End: " + enableTimeEnd + "\n");
    }
    if (disableServerCertificateCheck) {
      sb.append("Disable Certificate Check: true\n");
    }
    if (queryResponseFieldsNotReturnedSet != null) {
      sb.append("Query Response Fields Not Returned: ");
      boolean first = true;
      for (String value : queryResponseFieldsNotReturnedSet) {
        if (!first) {
          sb.append(", ");
        }
        sb.append(value);
        first = false;
      }
      sb.append("\n");
    }
    if (customTransformations != null && customTransformations.length() > 0) {
      sb.append("Custom Transformations: \n");
      printTransformString(sb, customTransformations);
    }
    if (assessmentTransformations != null && assessmentTransformations.length() > 0) {
      sb.append("Assessment Transformations: \n");
      printTransformString(sb, assessmentTransformations);
    }
    for (String scenarioTransformName : scenarioTransformationsMap.keySet()) {
      sb.append("Scenario Transform for " + scenarioTransformName + ": \n");
      printTransformString(sb, scenarioTransformationsMap.get(scenarioTransformName));
    }
    makeScriptAdditions(sb);
    return sb.toString();
  }

  public void printTransformString(StringBuilder sb, String transformString) {
    try {
      BufferedReader inTransform = new BufferedReader(new StringReader(transformString));
      String line;
      while ((line = inTransform.readLine()) != null) {
        line = line.trim();
        sb.append(" + " + line + "\n");
      }
    } catch (IOException ioe) {
      // IOException not expected when reading a string
      throw new RuntimeException("Exception while reading string", ioe);
    }
  }

  protected abstract void makeScriptAdditions(StringBuilder sb);

  public static List<Connector> makeConnectors(String script) throws Exception {
    List<Connector> connectors = new ArrayList<Connector>();
    String label = "";
    String purpose = "";
    String type = "";
    String userid = "";
    String otherid = "";
    String password = "";
    String facilityid = "";
    String url = "";
    String customTransformations = "";
    String assesmentTransformations = "";
    String keyStorePassword = "";
    String enableTimeStart = "";
    String enableTimeEnd = "";
    String aartPublicIdCode = "";
    String aartAccessPasscode = "";
    boolean disableServerCertificateCheck = false;
    Set<String> queryResponseFieldsNotReturnedSet = null;
    Map<String, String> scenarioTransformationsMap = new HashMap<String, String>();
    int tchForecastTesterSoftwareId = 0;
    int tchForecastTesterTaskGroupId = 0;
    String rxaFilterFacilityId = "";
    TransferType transferType = TransferType.NEAR_REAL_TIME_LINK;
    AckAnalyzer.AckType ackType = AckAnalyzer.AckType.DEFAULT;
    List<String> fields = new ArrayList<String>();
    BufferedReader in = new BufferedReader(new StringReader(script));
    String line;
    String lastList = "";
    while ((line = in.readLine()) != null) {
      line = line.trim();
      if (line.startsWith("Connection")) {
        addConnector(label, type, url, userid, otherid, facilityid, password, keyStorePassword,
            enableTimeStart, enableTimeEnd, ackType, transferType, fields, customTransformations,
            assesmentTransformations, connectors, purpose, tchForecastTesterSoftwareId,
            tchForecastTesterTaskGroupId, rxaFilterFacilityId, queryResponseFieldsNotReturnedSet,
            scenarioTransformationsMap, disableServerCertificateCheck, aartPublicIdCode,
            aartAccessPasscode);
        label = "";
        purpose = "";
        type = "";
        url = "";
        userid = "";
        otherid = "";
        facilityid = "";
        password = "";
        enableTimeStart = "";
        enableTimeEnd = "";
        ackType = AckAnalyzer.AckType.DEFAULT;
        transferType = TransferType.NEAR_REAL_TIME_LINK;
        customTransformations = "";
        assesmentTransformations = "";
        keyStorePassword = "";
        tchForecastTesterSoftwareId = 0;
        tchForecastTesterTaskGroupId = 0;
        rxaFilterFacilityId = "";
        fields = new ArrayList<String>();
      } else if (line.startsWith("Label:")) {
        label = readValue(line);
      } else if (line.startsWith("Type:")) {
        type = readValue(line);
      } else if (line.startsWith("URL:")) {
        url = readValue(line);
      } else if (line.startsWith("Purpose:")) {
        purpose = readValue(line);
      } else if (line.startsWith("User Id:")) {
        userid = readValue(line);
      } else if (line.startsWith("Other Id:")) {
        otherid = readValue(line);
      } else if (line.startsWith("Ack Type:")) {
        ackType = AckAnalyzer.AckType.valueOf(readValue(line));
      } else if (line.startsWith("Enable Time Start:")) {
        enableTimeStart = readValue(line);
      } else if (line.startsWith("Enable Time End:")) {
        enableTimeEnd = readValue(line);
      } else if (line.startsWith("Transfer Type:")) {
        transferType = TransferType.valueOf(readValue(line));
      } else if (line.startsWith("Password:")) {
        password = PasswordEncryptUtil.decrypt(readValue(line));
      } else if (line.startsWith("AART Public Id Code:")) {
        aartPublicIdCode = readValue(line);
      } else if (line.startsWith("AART Access Passcode:")) {
        aartAccessPasscode = readValue(line);
      } else if (line.startsWith("Facility Id:")) {
        facilityid = readValue(line);
      } else if (line.startsWith("Disable Certificate Check:")) {
        String s = readValue(line);
        disableServerCertificateCheck =
            s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("true");
      } else if (line.startsWith("Key Store Password:")) {
        keyStorePassword = PasswordEncryptUtil.decrypt(readValue(line));
      } else if (line.startsWith("Cause Issues:")) {
        lastList = "CI";
      } else if (line.startsWith("Query Response Fields Not Returned: ")) {
        queryResponseFieldsNotReturnedSet = new HashSet<String>();
        String[] values = readValue(line).split("\\,");
        for (String value : values) {
          if (value != null) {
            value = value.trim();
            if (value.length() > 0) {
              queryResponseFieldsNotReturnedSet.add(value);
            }
          }
        }
      } else if (line.startsWith("Custom Transformations:")) {
        lastList = "CT";
      } else if (line.startsWith("Assessment Transformations:")) {
        lastList = "ST";
      } else if (line.startsWith("Scenario Transform for ")) {
        int endPos = line.lastIndexOf(':');
        if (endPos == -1) {
          endPos = line.length();
        }
        lastList = line.substring("Scenario Transform for ".length(), endPos).trim();
      } else if (line.startsWith("Test Case Transform for ")) {
        int endPos = line.lastIndexOf(':');
        if (endPos == -1) {
          endPos = line.length();
        }
        lastList = line.substring("Test Case Transform for ".length(), endPos).trim();
      } else if (line.startsWith("TCH Forecast Tester Software Id:")) {
        try {
          tchForecastTesterSoftwareId = Integer.parseInt(readValue(line));
        } catch (NumberFormatException nfe) {
          // ignore, can't read
        }
      } else if (line.startsWith("TCH Forecast Tester Task Group Id:")) {
        try {
          tchForecastTesterTaskGroupId = Integer.parseInt(readValue(line));
        } catch (NumberFormatException nfe) {
          // ignore, can't read
        }
      } else if (line.startsWith("RXA Filter Facility Id:")) {
        rxaFilterFacilityId = readValue(line);
      } else if (line.startsWith("+")) {
        String ctLine = line.substring(1).trim() + "\n";
        if (lastList.equals("CT")) {
          customTransformations += ctLine;
        } else if (lastList.equals("ST")) {
          assesmentTransformations += ctLine;
        } else {
          String transform = scenarioTransformationsMap.get(lastList);
          if (transform == null) {
            transform = ctLine;
          } else {
            transform += ctLine;
          }
          scenarioTransformationsMap.put(lastList, transform);
        }
      } else {
        fields.add(line);
      }

    }
    addConnector(label, type, url, userid, otherid, facilityid, password, keyStorePassword,
        enableTimeStart, enableTimeEnd, ackType, transferType, fields, customTransformations,
        assesmentTransformations, connectors, purpose, tchForecastTesterSoftwareId,
        tchForecastTesterTaskGroupId, rxaFilterFacilityId, queryResponseFieldsNotReturnedSet,
        scenarioTransformationsMap, disableServerCertificateCheck, aartPublicIdCode,
        aartAccessPasscode);
    return connectors;
  }

  protected static String readValue(String line) {
    int pos = line.indexOf(":");
    if (pos == -1) {
      return "";
    }
    return line.substring(pos + 1).trim();
  }

  protected static class SavingTrustManager implements X509TrustManager {

    private final X509TrustManager tm;

    SavingTrustManager(X509TrustManager tm) {
      this.tm = tm;
    }

    public X509Certificate[] getAcceptedIssuers() {
      return tm.getAcceptedIssuers();
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
      throw new UnsupportedOperationException();
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
      tm.checkServerTrusted(chain, authType);
    }

  }

  protected static TrustManager[] trustAllCerts;

  protected static String replaceAmpersand(String s) {
    String s2 = "";
    int pos = s.indexOf("&");
    while (pos != -1) {
      s2 = s2 + s.substring(0, pos);
      s2 = s2 + "&amp;";
      s = s.substring(pos + 1);
      pos = s.indexOf("&");
    }
    s2 = s2 + s;
    return s2;
  }
  
  public StringBuilder extractResponse(StringBuilder response, String startTag, String stopTag) {

    String responseString = response.toString();
    int startPos = responseString.indexOf(startTag);
    int endPos = responseString.indexOf(stopTag);
    if (startPos > 0 && endPos > startPos) {
      responseString =
          responseString.substring(startPos + startTag.length(), endPos);
      responseString = responseString.replaceAll("\\Q&amp;\\E", "&");
      response = new StringBuilder(responseString);
      
    }
    return response;
  }

}
