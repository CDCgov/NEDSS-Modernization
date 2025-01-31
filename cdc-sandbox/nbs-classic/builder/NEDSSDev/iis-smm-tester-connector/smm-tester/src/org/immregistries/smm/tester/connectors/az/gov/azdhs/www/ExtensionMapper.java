
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.7.4 Built on : Oct 21, 2016
 * (10:48:01 BST)
 */


package org.immregistries.smm.tester.connectors.az.gov.azdhs.www;

/**
 * ExtensionMapper class
 */
@SuppressWarnings({"all"})

public class ExtensionMapper {

  public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
      java.lang.String typeName, javax.xml.stream.XMLStreamReader reader)
      throws java.lang.Exception {


    if ("http://AIE_Schemas.ASIIS_Request".equals(namespaceURI)
        && "Credentials_type2".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.az.asiis_request.aie_schemas.Credentials_type2.Factory
          .parse(reader);


    }


    if ("http://AIE_Schemas.ASIIS_HL7_Status_Query".equals(namespaceURI)
        && "Credentials_type3".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query.aie_schemas.Credentials_type3.Factory
          .parse(reader);


    }


    if ("http://AIE_Schemas.ASIIS_HL7_Status_Query".equals(namespaceURI)
        && "Query_type0".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query.aie_schemas.Query_type0.Factory
          .parse(reader);


    }


    if ("http://AIE_Schemas.ASIIS_Request".equals(namespaceURI)
        && "Request_type0".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.az.asiis_request.aie_schemas.Request_type0.Factory
          .parse(reader);


    }


    if ("http://AIE_Schemas.ASIIS_Request".equals(namespaceURI)
        && "Credentials_type0".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.az.asiis_request.aie_schemas.Credentials_type0.Factory
          .parse(reader);


    }


    if ("http://AIE_Schemas.ASIIS_HL7_Status_Query".equals(namespaceURI)
        && "Credentials_type1".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query.aie_schemas.Credentials_type1.Factory
          .parse(reader);


    }


    if ("http://AIE_Schemas.ASIIS_HL7_Status_Query_Response".equals(namespaceURI)
        && "Message_type1".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Message_type1.Factory
          .parse(reader);


    }


    if ("http://AIE_Schemas.ASIIS_HL7_Status_Query_Response".equals(namespaceURI)
        && "Message_type0".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Message_type0.Factory
          .parse(reader);


    }


    if ("http://AIE_Schemas.ASIIS_HL7_Status_Query_Response".equals(namespaceURI)
        && "Response_type0".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Response_type0.Factory
          .parse(reader);


    }


    if ("http://AIE_Schemas.ASIIS_Response".equals(namespaceURI)
        && "Response_type1".equals(typeName)) {

      return org.immregistries.smm.tester.connectors.az.asiis_response.aie_schemas.Response_type1.Factory
          .parse(reader);


    }


    throw new org.apache.axis2.databinding.ADBException(
        "Unsupported type " + namespaceURI + " " + typeName);
  }

}
