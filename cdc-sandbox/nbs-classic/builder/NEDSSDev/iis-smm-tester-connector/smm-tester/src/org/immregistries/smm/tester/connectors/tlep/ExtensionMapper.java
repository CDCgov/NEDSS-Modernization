
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.2 Built on : Apr 17, 2012
 * (05:34:40 IST)
 */


package org.immregistries.smm.tester.connectors.tlep;

/**
 * ExtensionMapper class
 */
@SuppressWarnings({"all"})

public class ExtensionMapper {

  public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
      java.lang.String typeName, javax.xml.stream.XMLStreamReader reader)
      throws java.lang.Exception {


    if ("urn:cdc:iisb:2011".equals(namespaceURI) && "soapFaultType".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.tlep.SoapFaultType.Factory.parse(reader);


    }


    if ("urn:cdc:iisb:2011".equals(namespaceURI)
        && "submitSingleMessageResponseType".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.tlep.SubmitSingleMessageResponseType.Factory
          .parse(reader);


    }


    if ("urn:cdc:iisb:2011".equals(namespaceURI)
        && "connectivityTestRequestType".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.tlep.ConnectivityTestRequestType.Factory
          .parse(reader);


    }


    if ("urn:cdc:iisb:2011".equals(namespaceURI) && "SecurityFaultType".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.tlep.SecurityFaultType.Factory.parse(reader);


    }


    if ("urn:cdc:iisb:2011".equals(namespaceURI)
        && "submitSingleMessageRequestType".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.tlep.SubmitSingleMessageRequestType.Factory
          .parse(reader);


    }


    if ("urn:cdc:iisb:2011".equals(namespaceURI) && "MessageTooLargeFaultType".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.tlep.MessageTooLargeFaultType.Factory
          .parse(reader);


    }


    if ("urn:cdc:iisb:2011".equals(namespaceURI)
        && "connectivityTestResponseType".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.tlep.ConnectivityTestResponseType.Factory
          .parse(reader);


    }


    if ("urn:cdc:iisb:2011".equals(namespaceURI)
        && "UnsupportedOperationFaultType".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.tlep.UnsupportedOperationFaultType.Factory
          .parse(reader);


    }


    throw new org.apache.axis2.databinding.ADBException(
        "Unsupported type " + namespaceURI + " " + typeName);
  }

}
