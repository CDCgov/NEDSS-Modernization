package org.immregistries.smm.tester.connectors;

public class ConnectorFactory {

  public static final String TYPE_SOAP = "SOAP";
  public static final String TYPE_POST = "POST";
  public static final String TYPE_MLLP = "MLLP";
  public static final String TYPE_RAW = "RAW";
  public static final String TYPE_AL_SOAP = "AL SOAP";
  public static final String TYPE_AZ_SOAP = "AZ SOAP";
  public static final String TYPE_FL_SOAP = "FL SOAP";
  public static final String TYPE_CO_SOAP = "CO SOAP";
  public static final String TYPE_KS_SOAP = "KS SOAP";
  public static final String TYPE_IL_WS = "IL WS";
  public static final String TYPE_IL_SOAP = "IL SOAP";
  public static final String TYPE_MA_SOAP = "MA SOAP";
  public static final String TYPE_MO_SOAP = "MO SOAP";
  public static final String TYPE_NM_SOAP = "NM SOAP";
  public static final String TYPE_NJ_SOAP = "NJ SOAP";
  public static final String TYPE_HI_SOAP = "HI SOAP";
  public static final String TYPE_ENVISION_SOAP = "Envision SOAP";
  public static final String TYPE_ENVISION_SOAP11 = "Envision SOAP11";
  public static final String TYPE_OR_SOAP = "OR ALERT SOAP";
  public static final String TYPE_CA_SOAP = "CA SOAP";
  public static final String TYPE_SC_SOAP = "SC SOAP";
  public static final String TYPE_ND_SOAP = "ND SOAP";
  public static final String TYPE_WI_SOAP = "WI SOAP";

  public static final String[][] TYPES = { { TYPE_SOAP, "SOAP" }, { TYPE_POST, "POST" }, { TYPE_MLLP, "MLLP" },{ TYPE_AL_SOAP, "AL SOAP" },
      { TYPE_AZ_SOAP, "AZ SOAP" },
      { TYPE_CA_SOAP, "CA SOAP" }, { TYPE_CO_SOAP, "CO SOAP" }, { TYPE_KS_SOAP, "KS SOAP" },
      { TYPE_MA_SOAP, "MA SOAP" }, { TYPE_MO_SOAP, "MO SOAP" }, { TYPE_ND_SOAP, "ND SOAP" },
      { TYPE_NJ_SOAP, "NJ SOAP" }, { TYPE_NM_SOAP, "NM SOAP" }, { TYPE_ENVISION_SOAP, "Envision SOAP" },
      { TYPE_ENVISION_SOAP, "Envision SOAP 1.1" }, { TYPE_HI_SOAP, "HI SOAP (same standard as SOAP)" },
      { TYPE_IL_WS, "IL WS" }, {TYPE_WI_SOAP, "WS SOAP"} };

  public static Connector getConnector(String type, String label, String url) throws Exception {
    Connector connector = null;
    if (type.equals(TYPE_SOAP)) {
      connector = new SoapConnector(label, url);
    } else if (type.equals(TYPE_MLLP)) {
        connector = new MLLPConnector(label, url);
    } else if (type.equals(TYPE_NM_SOAP)) {
      connector = new NMSoapConnector(label, url);
    } else if (type.equals(TYPE_NJ_SOAP)) {
      connector = new NJConnector(label, url);
    } else if (type.equals(TYPE_HI_SOAP)) {
      connector = new HISoapConnector(label, url);
    } else if (type.equals(TYPE_POST)) {
      connector = new HttpConnector(label, url);
    } else if (type.equals(TYPE_RAW)) {
      connector = new HttpRawConnector(label, url);
    } else if (type.equals(TYPE_ENVISION_SOAP)) {
      connector = new EnvisionConnector(label, url);
    } else if (type.equals(TYPE_ENVISION_SOAP11)) {
      connector = new EnvisionConnector(label, url, true);
    } else if (type.equals(TYPE_OR_SOAP)) {
      connector = new ORConnector(label, url);
    } else if (type.equals(TYPE_WI_SOAP)) {
      connector = new WIConnector(label, url);
    } else if (type.equals(TYPE_IL_WS)) {
      connector = new ILConnector(label, url);
    } else if (type.equals(TYPE_IL_SOAP)) {
      connector = new ILSoapConnector(label, url);
    } else if (type.equals(TYPE_MA_SOAP)) {
      connector = new MAConnector(label, url);
    } else if (type.equals(TYPE_MO_SOAP)) {
      connector = new MOConnector(label, url);
    } else if (type.equals(TYPE_CA_SOAP)) {
      connector = new CASoapConnector(label, url);
    } else if (type.equals(TYPE_SC_SOAP)) {
      connector = new SCSoapConnector(label, url);
    } else if (type.equals(TYPE_AL_SOAP)) {
      connector = new ALSoapConnector(label, url);
    } else if (type.equals(TYPE_CO_SOAP)) {
      connector = new COSoapConnector(label, url);
    } else if (type.equals(TYPE_FL_SOAP)) {
      connector = new FLSoapConnector(label, url);
    } else if (type.equals(TYPE_KS_SOAP)) {
      connector = new KSSoapConnector(label, url);
    } else if (type.equals(TYPE_AZ_SOAP)) {
      connector = new AZSoapConnector(label, url);
    } else if (type.equals(TYPE_ND_SOAP)) {
      connector = new NDSoapConnector(label, url);
    }
    return connector;
  }
}
