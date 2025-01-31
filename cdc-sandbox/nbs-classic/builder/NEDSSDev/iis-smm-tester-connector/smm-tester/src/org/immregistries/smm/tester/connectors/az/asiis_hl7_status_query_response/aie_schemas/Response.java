
/**
 * Response.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.7.4 Built on : Oct 21, 2016
 * (10:48:01 BST)
 */


package org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas;


/**
 * Response bean class
 */
@SuppressWarnings({"all"})

public class Response implements org.apache.axis2.databinding.ADBBean {

  public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
      "http://AIE_Schemas.ASIIS_HL7_Status_Query_Response", "Response", "ns4");



  /**
   * field for Message This was an Array!
   */


  protected org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Message_type0[] localMessage;

  /*
   * This tracker boolean wil be used to detect whether the user called the set method for this
   * attribute. It will be used to determine whether to include this field in the serialized XML
   */
  protected boolean localMessageTracker = false;

  public boolean isMessageSpecified() {
    return localMessageTracker;
  }



  /**
   * Auto generated getter method
   * 
   * @return asiis_hl7_status_query_response.aie_schemas.Message_type0[]
   */
  public org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Message_type0[] getMessage() {
    return localMessage;
  }



  /**
   * validate the array for Message
   */
  protected void validateMessage(
      org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Message_type0[] param) {

  }


  /**
   * Auto generated setter method
   * 
   * @param param Message
   */
  public void setMessage(
      org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Message_type0[] param) {

    validateMessage(param);

    localMessageTracker = param != null;

    this.localMessage = param;
  }



  /**
   * Auto generated add method for the array for convenience
   * 
   * @param param asiis_hl7_status_query_response.aie_schemas.Message_type0
   */
  public void addMessage(
      org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Message_type0 param) {
    if (localMessage == null) {
      localMessage =
          new org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Message_type0[] {};
    }


    // update the setting tracker
    localMessageTracker = true;


    java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localMessage);
    list.add(param);
    this.localMessage =
        (org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Message_type0[]) list
            .toArray(
                new org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Message_type0[list
                    .size()]);

  }


  /**
   * field for CredentialsValid
   */


  protected java.lang.String localCredentialsValid;

  /*
   * This tracker boolean wil be used to detect whether the user called the set method for this
   * attribute. It will be used to determine whether to include this field in the serialized XML
   */
  protected boolean localCredentialsValidTracker = false;

  public boolean isCredentialsValidSpecified() {
    return localCredentialsValidTracker;
  }



  /**
   * Auto generated getter method
   * 
   * @return java.lang.String
   */
  public java.lang.String getCredentialsValid() {
    return localCredentialsValid;
  }



  /**
   * Auto generated setter method
   * 
   * @param param CredentialsValid
   */
  public void setCredentialsValid(java.lang.String param) {
    localCredentialsValidTracker = param != null;

    this.localCredentialsValid = param;


  }



  /**
   *
   * @param parentQName
   * @param factory
   * @return org.apache.axiom.om.OMElement
   */
  public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName,
      final org.apache.axiom.om.OMFactory factory)
      throws org.apache.axis2.databinding.ADBException {



    return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, MY_QNAME));

  }

  public void serialize(final javax.xml.namespace.QName parentQName,
      javax.xml.stream.XMLStreamWriter xmlWriter)
      throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {
    serialize(parentQName, xmlWriter, false);
  }

  public void serialize(final javax.xml.namespace.QName parentQName,
      javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
      throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {



    java.lang.String prefix = null;
    java.lang.String namespace = null;


    prefix = parentQName.getPrefix();
    namespace = parentQName.getNamespaceURI();
    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);

    if (serializeType) {


      java.lang.String namespacePrefix =
          registerPrefix(xmlWriter, "http://AIE_Schemas.ASIIS_HL7_Status_Query_Response");
      if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
        writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
            namespacePrefix + ":Response", xmlWriter);
      } else {
        writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "Response",
            xmlWriter);
      }


    }
    if (localMessageTracker) {
      if (localMessage != null) {
        for (int i = 0; i < localMessage.length; i++) {
          if (localMessage[i] != null) {
            localMessage[i].serialize(new javax.xml.namespace.QName("", "Message"), xmlWriter);
          } else {

            // we don't have to do any thing since minOccures is zero

          }

        }
      } else {

        throw new org.apache.axis2.databinding.ADBException("Message cannot be null!!");

      }
    }
    if (localCredentialsValidTracker) {
      namespace = "";
      writeStartElement(null, namespace, "CredentialsValid", xmlWriter);


      if (localCredentialsValid == null) {
        // write the nil attribute

        throw new org.apache.axis2.databinding.ADBException("CredentialsValid cannot be null!!");

      } else {


        xmlWriter.writeCharacters(localCredentialsValid);

      }

      xmlWriter.writeEndElement();
    }
    xmlWriter.writeEndElement();


  }

  private static java.lang.String generatePrefix(java.lang.String namespace) {
    if (namespace.equals("http://AIE_Schemas.ASIIS_HL7_Status_Query_Response")) {
      return "ns4";
    }
    return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
  }

  /**
   * Utility method to write an element start tag.
   */
  private void writeStartElement(java.lang.String prefix, java.lang.String namespace,
      java.lang.String localPart, javax.xml.stream.XMLStreamWriter xmlWriter)
      throws javax.xml.stream.XMLStreamException {
    java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
    if (writerPrefix != null) {
      xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
    } else {
      if (namespace.length() == 0) {
        prefix = "";
      } else if (prefix == null) {
        prefix = generatePrefix(namespace);
      }

      xmlWriter.writeStartElement(prefix, localPart, namespace);
      xmlWriter.writeNamespace(prefix, namespace);
      xmlWriter.setPrefix(prefix, namespace);
    }
  }

  /**
   * Util method to write an attribute with the ns prefix
   */
  private void writeAttribute(java.lang.String prefix, java.lang.String namespace,
      java.lang.String attName, java.lang.String attValue,
      javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
    java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
    if (writerPrefix != null) {
      xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
    } else {
      xmlWriter.writeNamespace(prefix, namespace);
      xmlWriter.setPrefix(prefix, namespace);
      xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
    }
  }

  /**
   * Util method to write an attribute without the ns prefix
   */
  private void writeAttribute(java.lang.String namespace, java.lang.String attName,
      java.lang.String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
      throws javax.xml.stream.XMLStreamException {
    if (namespace.equals("")) {
      xmlWriter.writeAttribute(attName, attValue);
    } else {
      xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
    }
  }


  /**
   * Util method to write an attribute without the ns prefix
   */
  private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
      javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter)
      throws javax.xml.stream.XMLStreamException {

    java.lang.String attributeNamespace = qname.getNamespaceURI();
    java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
    if (attributePrefix == null) {
      attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
    }
    java.lang.String attributeValue;
    if (attributePrefix.trim().length() > 0) {
      attributeValue = attributePrefix + ":" + qname.getLocalPart();
    } else {
      attributeValue = qname.getLocalPart();
    }

    if (namespace.equals("")) {
      xmlWriter.writeAttribute(attName, attributeValue);
    } else {
      registerPrefix(xmlWriter, namespace);
      xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
    }
  }

  /**
   * method to handle Qnames
   */

  private void writeQName(javax.xml.namespace.QName qname,
      javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
    java.lang.String namespaceURI = qname.getNamespaceURI();
    if (namespaceURI != null) {
      java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
      if (prefix == null) {
        prefix = generatePrefix(namespaceURI);
        xmlWriter.writeNamespace(prefix, namespaceURI);
        xmlWriter.setPrefix(prefix, namespaceURI);
      }

      if (prefix.trim().length() > 0) {
        xmlWriter.writeCharacters(
            prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
      } else {
        // i.e this is the default namespace
        xmlWriter.writeCharacters(
            org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
      }

    } else {
      xmlWriter
          .writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
    }
  }

  private void writeQNames(javax.xml.namespace.QName[] qnames,
      javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

    if (qnames != null) {
      // we have to store this data until last moment since it is not possible to write any
      // namespace data after writing the charactor data
      java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
      java.lang.String namespaceURI = null;
      java.lang.String prefix = null;

      for (int i = 0; i < qnames.length; i++) {
        if (i > 0) {
          stringToWrite.append(" ");
        }
        namespaceURI = qnames[i].getNamespaceURI();
        if (namespaceURI != null) {
          prefix = xmlWriter.getPrefix(namespaceURI);
          if ((prefix == null) || (prefix.length() == 0)) {
            prefix = generatePrefix(namespaceURI);
            xmlWriter.writeNamespace(prefix, namespaceURI);
            xmlWriter.setPrefix(prefix, namespaceURI);
          }

          if (prefix.trim().length() > 0) {
            stringToWrite.append(prefix).append(":").append(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
          } else {
            stringToWrite.append(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
          }
        } else {
          stringToWrite
              .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
        }
      }
      xmlWriter.writeCharacters(stringToWrite.toString());
    }

  }


  /**
   * Register a namespace prefix
   */
  private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter,
      java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
    java.lang.String prefix = xmlWriter.getPrefix(namespace);
    if (prefix == null) {
      prefix = generatePrefix(namespace);
      javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
      while (true) {
        java.lang.String uri = nsContext.getNamespaceURI(prefix);
        if (uri == null || uri.length() == 0) {
          break;
        }
        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
      }
      xmlWriter.writeNamespace(prefix, namespace);
      xmlWriter.setPrefix(prefix, namespace);
    }
    return prefix;
  }



  /**
   * Factory class that keeps the parse method
   */
  public static class Factory {
    private static org.apache.commons.logging.Log log =
        org.apache.commons.logging.LogFactory.getLog(Factory.class);



    /**
     * static method to create the object Precondition: If this object is an element, the current or
     * next start element starts this object and any intervening reader events are ignorable If this
     * object is not an element, it is a complex type and the reader is at the event just after the
     * outer start element Postcondition: If this object is an element, the reader is positioned at
     * its end element If this object is a complex type, the reader is positioned at the end element
     * of its outer element
     */
    public static Response parse(javax.xml.stream.XMLStreamReader reader)
        throws java.lang.Exception {
      Response object = new Response();

      int event;
      javax.xml.namespace.QName currentQName = null;
      java.lang.String nillableValue = null;
      java.lang.String prefix = "";
      java.lang.String namespaceuri = "";
      try {

        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next();

        currentQName = reader.getName();

        if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
          java.lang.String fullTypeName =
              reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
          if (fullTypeName != null) {
            java.lang.String nsPrefix = null;
            if (fullTypeName.indexOf(":") > -1) {
              nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
            }
            nsPrefix = nsPrefix == null ? "" : nsPrefix;

            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

            if (!"Response".equals(type)) {
              // find namespace for the prefix
              java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (Response) org.immregistries.smm.tester.connectors.az.gov.azdhs.www.ExtensionMapper
                  .getTypeObject(nsUri, type, reader);
            }


          }


        }



        // Note all attributes that were handled. Used to differ normal attributes
        // from anyAttributes.
        java.util.Vector handledAttributes = new java.util.Vector();



        reader.next();

        java.util.ArrayList list1 = new java.util.ArrayList();


        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "Message").equals(reader.getName())
            || new javax.xml.namespace.QName("", "Message").equals(reader.getName())) {



          // Process the array and step past its final element's end.

          list1.add(
              org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Message_type0.Factory
                  .parse(reader));

          // loop until we find a start element that is not part of this array
          boolean loopDone1 = false;
          while (!loopDone1) {
            // We should be at the end element, but make sure
            while (!reader.isEndElement())
              reader.next();
            // Step out of this element
            reader.next();
            // Step to next element event.
            while (!reader.isStartElement() && !reader.isEndElement())
              reader.next();
            if (reader.isEndElement()) {
              // two continuous end elements means we are exiting the xml structure
              loopDone1 = true;
            } else {
              if (new javax.xml.namespace.QName("", "Message").equals(reader.getName())) {
                list1.add(
                    org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Message_type0.Factory
                        .parse(reader));

              } else {
                loopDone1 = true;
              }
            }
          }
          // call the converter utility to convert and set the array

          object.setMessage(
              (org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Message_type0[]) org.apache.axis2.databinding.utils.ConverterUtil
                  .convertToArray(
                      org.immregistries.smm.tester.connectors.az.asiis_hl7_status_query_response.aie_schemas.Message_type0.class,
                      list1));

        } // End of if for expected property start element

        else {

        }


        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next();

        if (reader.isStartElement()
            && new javax.xml.namespace.QName("", "CredentialsValid").equals(reader.getName())
            || new javax.xml.namespace.QName("", "CredentialsValid").equals(reader.getName())) {

          nillableValue =
              reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
          if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
            throw new org.apache.axis2.databinding.ADBException(
                "The element: " + "CredentialsValid" + "  cannot be null");
          }


          java.lang.String content = reader.getElementText();

          object.setCredentialsValid(
              org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

          reader.next();

        } // End of if for expected property start element

        else {

        }

        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next();

        if (reader.isStartElement())
          // 2 - A start element we are not expecting indicates a trailing invalid property

          throw new org.apache.axis2.databinding.ADBException(
              "Unexpected subelement " + reader.getName());



      } catch (javax.xml.stream.XMLStreamException e) {
        throw new java.lang.Exception(e);
      }

      return object;
    }

  }// end of factory class



}

