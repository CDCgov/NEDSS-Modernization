/**
 * Test_adphhieStub.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.3 Built on : Jun 27, 2015
 * (11:17:49 BST)
 */
package org.immregistries.smm.tester.connectors.al;


/*
 * Test_adphhieStub java implementation
 */
@SuppressWarnings({"all"})
public class Test_adphhieStub extends org.apache.axis2.client.Stub {
  private static int counter = 0;
  protected org.apache.axis2.description.AxisOperation[] _operations;

  // hashmaps to keep the fault mapping
  private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
  private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
  private java.util.HashMap faultMessageMap = new java.util.HashMap();
  private javax.xml.namespace.QName[] opNameArray = null;

  /**
   * Constructor that takes in a configContext
   */
  public Test_adphhieStub(org.apache.axis2.context.ConfigurationContext configurationContext,
      java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
    this(configurationContext, targetEndpoint, false);
  }

  /**
   * Constructor that takes in a configContext and useseperate listner
   */
  public Test_adphhieStub(org.apache.axis2.context.ConfigurationContext configurationContext,
      java.lang.String targetEndpoint, boolean useSeparateListener)
      throws org.apache.axis2.AxisFault {
    // To populate AxisService
    populateAxisService();
    populateFaults();

    _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext, _service);

    _serviceClient.getOptions()
        .setTo(new org.apache.axis2.addressing.EndpointReference(targetEndpoint));
    _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);

    // Set the soap version
    _serviceClient.getOptions()
        .setSoapVersionURI(org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
  }

  /**
   * Default Constructor
   */
  public Test_adphhieStub(org.apache.axis2.context.ConfigurationContext configurationContext)
      throws org.apache.axis2.AxisFault {
    this(configurationContext,
        "https://test-interface1.adph.state.al.us:443/services/Test_adphhie.Test_adphhieHttpsSoap12Endpoint");
  }

  /**
   * Default Constructor
   */
  public Test_adphhieStub() throws org.apache.axis2.AxisFault {
    this(
        "https://test-interface1.adph.state.al.us:443/services/Test_adphhie.Test_adphhieHttpsSoap12Endpoint");
  }

  /**
   * Constructor taking the target endpoint
   */
  public Test_adphhieStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
    this(null, targetEndpoint);
  }

  private static synchronized java.lang.String getUniqueSuffix() {
    // reset the counter if it is greater than 99999
    if (counter > 99999) {
      counter = 0;
    }

    counter = counter + 1;

    return java.lang.Long.toString(java.lang.System.currentTimeMillis()) + "_" + counter;
  }

  private void populateAxisService() throws org.apache.axis2.AxisFault {
    // creating the Service with a unique name
    _service = new org.apache.axis2.description.AxisService("Test_adphhie" + getUniqueSuffix());
    addAnonymousOperations();

    // creating the operations
    org.apache.axis2.description.AxisOperation __operation;

    _operations = new org.apache.axis2.description.AxisOperation[1];

    __operation = new org.apache.axis2.description.OutInAxisOperation();

    __operation
        .setName(new javax.xml.namespace.QName("https://test-hie.adph.state.al.us", "aliiashl7"));
    _service.addOperation(__operation);

    _operations[0] = __operation;
  }

  // populates the faults
  private void populateFaults() {
    faultExceptionNameMap
        .put(
            new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName(
                "https://test-hie.adph.state.al.us", "aliiashl7Fault"), "aliiashl7"),
            "us.al.state.adph.test_hie.Aliiashl7FaultException");
    faultExceptionClassNameMap
        .put(
            new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName(
                "https://test-hie.adph.state.al.us", "aliiashl7Fault"), "aliiashl7"),
            "us.al.state.adph.test_hie.Aliiashl7FaultException");
    faultMessageMap
        .put(
            new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName(
                "https://test-hie.adph.state.al.us", "aliiashl7Fault"), "aliiashl7"),
            "us.al.state.adph.test_hie.Test_adphhieStub$Aliiashl7Fault");
  }

  /**
   * Auto generated method signature hl7 messages for web services
   * 
   * @see us.al.state.adph.test_hie.Test_adphhie#aliiashl7
   * @param aliiashl70
   * @throws org.immregistries.smm.tester.connectors.al.Aliiashl7FaultException :
   */
  public org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Response aliiashl7(
      org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7 aliiashl70)
      throws java.rmi.RemoteException,
      org.immregistries.smm.tester.connectors.al.Aliiashl7FaultException {
    org.apache.axis2.context.MessageContext _messageContext = null;

    try {
      org.apache.axis2.client.OperationClient _operationClient =
          _serviceClient.createClient(_operations[0].getName());
      _operationClient.getOptions().setAction("urn:aliishl7");
      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

      addPropertyToOperationClient(_operationClient,
          org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

      // create a message context
      _messageContext = new org.apache.axis2.context.MessageContext();

      // create SOAP envelope with that payload
      org.apache.axiom.soap.SOAPEnvelope env = null;

      env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), aliiashl70,
          optimizeContent(
              new javax.xml.namespace.QName("https://test-hie.adph.state.al.us", "aliiashl7")),
          new javax.xml.namespace.QName("https://test-hie.adph.state.al.us", "aliiashl7"));

      // adding SOAP soap_headers
      _serviceClient.addHeadersToEnvelope(env);
      // set the message context with that soap envelope
      _messageContext.setEnvelope(env);

      // add the message contxt to the operation client
      _operationClient.addMessageContext(_messageContext);

      // execute the operation client
      _operationClient.execute(true);

      org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
          .getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
      org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

      java.lang.Object object = fromOM(_returnEnv.getBody().getFirstElement(),
          org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Response.class,
          getEnvelopeNamespaces(_returnEnv));

      return (org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Response) object;
    } catch (org.apache.axis2.AxisFault f) {
      org.apache.axiom.om.OMElement faultElt = f.getDetail();

      if (faultElt != null) {
        if (faultExceptionNameMap.containsKey(
            new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "aliiashl7"))) {
          // make the fault by reflection
          try {
            java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
                .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "aliiashl7"));
            java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
            java.lang.reflect.Constructor constructor =
                exceptionClass.getConstructor(java.lang.String.class);
            java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());

            // message class
            java.lang.String messageClassName = (java.lang.String) faultMessageMap
                .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "aliiashl7"));
            java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
            java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
            java.lang.reflect.Method m =
                exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] {messageClass});
            m.invoke(ex, new java.lang.Object[] {messageObject});

            if (ex instanceof org.immregistries.smm.tester.connectors.al.Aliiashl7FaultException) {
              throw (org.immregistries.smm.tester.connectors.al.Aliiashl7FaultException) ex;
            }

            throw new java.rmi.RemoteException(ex.getMessage(), ex);
          } catch (java.lang.ClassCastException e) {
            // we cannot intantiate the class - throw the original Axis fault
            throw f;
          } catch (java.lang.ClassNotFoundException e) {
            // we cannot intantiate the class - throw the original Axis fault
            throw f;
          } catch (java.lang.NoSuchMethodException e) {
            // we cannot intantiate the class - throw the original Axis fault
            throw f;
          } catch (java.lang.reflect.InvocationTargetException e) {
            // we cannot intantiate the class - throw the original Axis fault
            throw f;
          } catch (java.lang.IllegalAccessException e) {
            // we cannot intantiate the class - throw the original Axis fault
            throw f;
          } catch (java.lang.InstantiationException e) {
            // we cannot intantiate the class - throw the original Axis fault
            throw f;
          }
        } else {
          throw f;
        }
      } else {
        throw f;
      }
    } finally {
      if (_messageContext.getTransportOut() != null) {
        _messageContext.getTransportOut().getSender().cleanup(_messageContext);
      }
    }
  }

  /**
   * Auto generated method signature for Asynchronous Invocations hl7 messages for web services
   * 
   * @see us.al.state.adph.test_hie.Test_adphhie#startaliiashl7
   * @param aliiashl70
   */
  public void startaliiashl7(
      org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7 aliiashl70,
      final org.immregistries.smm.tester.connectors.al.Test_adphhieCallbackHandler callback)
      throws java.rmi.RemoteException {
    org.apache.axis2.client.OperationClient _operationClient =
        _serviceClient.createClient(_operations[0].getName());
    _operationClient.getOptions().setAction("urn:aliishl7");
    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

    addPropertyToOperationClient(_operationClient,
        org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

    // create SOAP envelope with that payload
    org.apache.axiom.soap.SOAPEnvelope env = null;
    final org.apache.axis2.context.MessageContext _messageContext =
        new org.apache.axis2.context.MessageContext();

    // Style is Doc.
    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), aliiashl70,
        optimizeContent(
            new javax.xml.namespace.QName("https://test-hie.adph.state.al.us", "aliiashl7")),
        new javax.xml.namespace.QName("https://test-hie.adph.state.al.us", "aliiashl7"));

    // adding SOAP soap_headers
    _serviceClient.addHeadersToEnvelope(env);
    // create message context with that soap envelope
    _messageContext.setEnvelope(env);

    // add the message context to the operation client
    _operationClient.addMessageContext(_messageContext);

    _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
      public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
        try {
          org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();

          java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
              org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Response.class,
              getEnvelopeNamespaces(resultEnv));
          callback.receiveResultaliiashl7(
              (org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Response) object);
        } catch (org.apache.axis2.AxisFault e) {
          callback.receiveErroraliiashl7(e);
        }
      }

      public void onError(java.lang.Exception error) {
        if (error instanceof org.apache.axis2.AxisFault) {
          org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
          org.apache.axiom.om.OMElement faultElt = f.getDetail();

          if (faultElt != null) {
            if (faultExceptionNameMap.containsKey(
                new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "aliiashl7"))) {
              // make the fault by reflection
              try {
                java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
                    .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "aliiashl7"));
                java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                java.lang.reflect.Constructor constructor =
                    exceptionClass.getConstructor(java.lang.String.class);
                java.lang.Exception ex =
                    (java.lang.Exception) constructor.newInstance(f.getMessage());

                // message class
                java.lang.String messageClassName = (java.lang.String) faultMessageMap
                    .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "aliiashl7"));
                java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
                java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                    new java.lang.Class[] {messageClass});
                m.invoke(ex, new java.lang.Object[] {messageObject});

                if (ex instanceof org.immregistries.smm.tester.connectors.al.Aliiashl7FaultException) {
                  callback.receiveErroraliiashl7(
                      (org.immregistries.smm.tester.connectors.al.Aliiashl7FaultException) ex);

                  return;
                }

                callback.receiveErroraliiashl7(new java.rmi.RemoteException(ex.getMessage(), ex));
              } catch (java.lang.ClassCastException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErroraliiashl7(f);
              } catch (java.lang.ClassNotFoundException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErroraliiashl7(f);
              } catch (java.lang.NoSuchMethodException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErroraliiashl7(f);
              } catch (java.lang.reflect.InvocationTargetException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErroraliiashl7(f);
              } catch (java.lang.IllegalAccessException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErroraliiashl7(f);
              } catch (java.lang.InstantiationException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErroraliiashl7(f);
              } catch (org.apache.axis2.AxisFault e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErroraliiashl7(f);
              }
            } else {
              callback.receiveErroraliiashl7(f);
            }
          } else {
            callback.receiveErroraliiashl7(f);
          }
        } else {
          callback.receiveErroraliiashl7(error);
        }
      }

      public void onFault(org.apache.axis2.context.MessageContext faultContext) {
        org.apache.axis2.AxisFault fault =
            org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
        onError(fault);
      }

      public void onComplete() {
        try {
          _messageContext.getTransportOut().getSender().cleanup(_messageContext);
        } catch (org.apache.axis2.AxisFault axisFault) {
          callback.receiveErroraliiashl7(axisFault);
        }
      }
    });

    org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;

    if ((_operations[0].getMessageReceiver() == null)
        && _operationClient.getOptions().isUseSeparateListener()) {
      _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
      _operations[0].setMessageReceiver(_callbackReceiver);
    }

    // execute the operation client
    _operationClient.execute(false);
  }

  /**
   * A utility method that copies the namepaces from the SOAPEnvelope
   */
  private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env) {
    java.util.Map returnMap = new java.util.HashMap();
    java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();

    while (namespaceIterator.hasNext()) {
      org.apache.axiom.om.OMNamespace ns =
          (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
      returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
    }

    return returnMap;
  }

  private boolean optimizeContent(javax.xml.namespace.QName opName) {
    if (opNameArray == null) {
      return false;
    }

    for (int i = 0; i < opNameArray.length; i++) {
      if (opName.equals(opNameArray[i])) {
        return true;
      }
    }

    return false;
  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7 param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {
    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Response param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {
    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Response.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Fault param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {
    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Fault.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
      org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7 param,
      boolean optimizeContent, javax.xml.namespace.QName methodQName)
      throws org.apache.axis2.AxisFault {
    try {
      org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
      emptyEnvelope.getBody().addChild(param.getOMElement(
          org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7.MY_QNAME, factory));

      return emptyEnvelope;
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  /* methods to provide back word compatibility */

  /**
   * get the default envelope
   */
  private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory) {
    return factory.getDefaultEnvelope();
  }

  private java.lang.Object fromOM(org.apache.axiom.om.OMElement param, java.lang.Class type,
      java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault {
    try {
      if (org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7.class
          .equals(type)) {
        return org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());
      }

      if (org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Fault.class
          .equals(type)) {
        return org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Fault.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());
      }

      if (org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Response.class
          .equals(type)) {
        return org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Response.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());
      }
    } catch (java.lang.Exception e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }

    return null;
  }

  // https://test-interface1.adph.state.al.us:443/services/Test_adphhie.Test_adphhieHttpsSoap12Endpoint
  public static class ExtensionMapper {
    public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
        java.lang.String typeName, javax.xml.stream.XMLStreamReader reader)
        throws java.lang.Exception {
      throw new org.apache.axis2.databinding.ADBException(
          "Unsupported type " + namespaceURI + " " + typeName);
    }
  }

  public static class Aliiashl7Fault implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME =
        new javax.xml.namespace.QName("https://test-hie.adph.state.al.us", "aliiashl7Fault", "ns1");

    /**
     * field for Payload
     */
    protected java.lang.String localPayload;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getPayload() {
      return localPayload;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Payload
     */
    public void setPayload(java.lang.String param) {
      this.localPayload = param;
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
      org.apache.axiom.om.OMDataSource dataSource =
          new org.apache.axis2.databinding.ADBDataSource(this, MY_QNAME);

      return factory.createOMElement(dataSource, MY_QNAME);
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
            registerPrefix(xmlWriter, "https://test-hie.adph.state.al.us");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              namespacePrefix + ":aliiashl7Fault", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              "aliiashl7Fault", xmlWriter);
        }
      }

      namespace = "https://test-hie.adph.state.al.us";
      writeStartElement(null, namespace, "payload", xmlWriter);

      if (localPayload == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("payload cannot be null!!");
      } else {
        xmlWriter.writeCharacters(localPayload);
      }

      xmlWriter.writeEndElement();

      xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("https://test-hie.adph.state.al.us")) {
        return "ns1";
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
        xmlWriter.writeStartElement(namespace, localPart);
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
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      }

      xmlWriter.writeAttribute(namespace, attName, attValue);
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
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
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
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
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
          xmlWriter.writeCharacters(prefix + ":"
              + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        } else {
          // i.e this is the default namespace
          xmlWriter.writeCharacters(
              org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        }
      } else {
        xmlWriter.writeCharacters(
            org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
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
            stringToWrite.append(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
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

          if ((uri == null) || (uri.length() == 0)) {
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
     * databinding method to get an XML representation of this object
     *
     */
    public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
        throws org.apache.axis2.databinding.ADBException {
      java.util.ArrayList elementList = new java.util.ArrayList();
      java.util.ArrayList attribList = new java.util.ArrayList();

      elementList
          .add(new javax.xml.namespace.QName("https://test-hie.adph.state.al.us", "payload"));

      if (localPayload != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPayload));
      } else {
        throw new org.apache.axis2.databinding.ADBException("payload cannot be null!!");
      }

      return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,
          elementList.toArray(), attribList.toArray());
    }

    /**
     * Factory class that keeps the parse method
     */
    public static class Factory {
      /**
       * static method to create the object Precondition: If this object is an element, the current
       * or next start element starts this object and any intervening reader events are ignorable If
       * this object is not an element, it is a complex type and the reader is at the event just
       * after the outer start element Postcondition: If this object is an element, the reader is
       * positioned at its end element If this object is a complex type, the reader is positioned at
       * the end element of its outer element
       */
      public static Aliiashl7Fault parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        Aliiashl7Fault object = new Aliiashl7Fault();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";

        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
              "type") != null) {
            java.lang.String fullTypeName =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");

            if (fullTypeName != null) {
              java.lang.String nsPrefix = null;

              if (fullTypeName.indexOf(":") > -1) {
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
              }

              nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

              java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

              if (!"aliiashl7Fault".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);

                return (Aliiashl7Fault) ExtensionMapper.getTypeObject(nsUri, type, reader);
              }
            }
          }

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          reader.next();

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("https://test-hie.adph.state.al.us", "payload")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "payload" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setPayload(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

            reader.next();
          } // End of if for expected property start element

          else {
            // A start element we are not expecting indicates an invalid parameter was passed
            throw new org.apache.axis2.databinding.ADBException(
                "Unexpected subelement " + reader.getName());
          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()) {
            // A start element we are not expecting indicates a trailing invalid property
            throw new org.apache.axis2.databinding.ADBException(
                "Unexpected subelement " + reader.getName());
          }
        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }
    } // end of factory class
  }

  public static class Aliiashl7 implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME =
        new javax.xml.namespace.QName("https://test-hie.adph.state.al.us", "aliiashl7", "ns1");

    /**
     * field for Payload
     */
    protected java.lang.String localPayload;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getPayload() {
      return localPayload;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Payload
     */
    public void setPayload(java.lang.String param) {
      this.localPayload = param;
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
      org.apache.axiom.om.OMDataSource dataSource =
          new org.apache.axis2.databinding.ADBDataSource(this, MY_QNAME);

      return factory.createOMElement(dataSource, MY_QNAME);
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
            registerPrefix(xmlWriter, "https://test-hie.adph.state.al.us");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              namespacePrefix + ":aliiashl7", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "aliiashl7",
              xmlWriter);
        }
      }

      namespace = "https://test-hie.adph.state.al.us";
      writeStartElement(null, namespace, "payload", xmlWriter);

      if (localPayload == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("payload cannot be null!!");
      } else {
        xmlWriter.writeCharacters(localPayload);
      }

      xmlWriter.writeEndElement();

      xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("https://test-hie.adph.state.al.us")) {
        return "ns1";
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
        xmlWriter.writeStartElement(namespace, localPart);
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
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      }

      xmlWriter.writeAttribute(namespace, attName, attValue);
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
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
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
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
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
          xmlWriter.writeCharacters(prefix + ":"
              + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        } else {
          // i.e this is the default namespace
          xmlWriter.writeCharacters(
              org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        }
      } else {
        xmlWriter.writeCharacters(
            org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
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
            stringToWrite.append(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
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

          if ((uri == null) || (uri.length() == 0)) {
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
     * databinding method to get an XML representation of this object
     *
     */
    public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
        throws org.apache.axis2.databinding.ADBException {
      java.util.ArrayList elementList = new java.util.ArrayList();
      java.util.ArrayList attribList = new java.util.ArrayList();

      elementList
          .add(new javax.xml.namespace.QName("https://test-hie.adph.state.al.us", "payload"));

      if (localPayload != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPayload));
      } else {
        throw new org.apache.axis2.databinding.ADBException("payload cannot be null!!");
      }

      return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,
          elementList.toArray(), attribList.toArray());
    }

    /**
     * Factory class that keeps the parse method
     */
    public static class Factory {
      /**
       * static method to create the object Precondition: If this object is an element, the current
       * or next start element starts this object and any intervening reader events are ignorable If
       * this object is not an element, it is a complex type and the reader is at the event just
       * after the outer start element Postcondition: If this object is an element, the reader is
       * positioned at its end element If this object is a complex type, the reader is positioned at
       * the end element of its outer element
       */
      public static Aliiashl7 parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        Aliiashl7 object = new Aliiashl7();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";

        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
              "type") != null) {
            java.lang.String fullTypeName =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");

            if (fullTypeName != null) {
              java.lang.String nsPrefix = null;

              if (fullTypeName.indexOf(":") > -1) {
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
              }

              nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

              java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

              if (!"aliiashl7".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);

                return (Aliiashl7) ExtensionMapper.getTypeObject(nsUri, type, reader);
              }
            }
          }

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          reader.next();

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("https://test-hie.adph.state.al.us", "payload")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "payload" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setPayload(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

            reader.next();
          } // End of if for expected property start element

          else {
            // A start element we are not expecting indicates an invalid parameter was passed
            throw new org.apache.axis2.databinding.ADBException(
                "Unexpected subelement " + reader.getName());
          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()) {
            // A start element we are not expecting indicates a trailing invalid property
            throw new org.apache.axis2.databinding.ADBException(
                "Unexpected subelement " + reader.getName());
          }
        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }
    } // end of factory class
  }

  public static class Aliiashl7Response implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
        "https://test-hie.adph.state.al.us", "aliiashl7Response", "ns1");

    /**
     * field for Payload
     */
    protected java.lang.String localPayload;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getPayload() {
      return localPayload;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Payload
     */
    public void setPayload(java.lang.String param) {
      this.localPayload = param;
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
      org.apache.axiom.om.OMDataSource dataSource =
          new org.apache.axis2.databinding.ADBDataSource(this, MY_QNAME);

      return factory.createOMElement(dataSource, MY_QNAME);
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
            registerPrefix(xmlWriter, "https://test-hie.adph.state.al.us");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              namespacePrefix + ":aliiashl7Response", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              "aliiashl7Response", xmlWriter);
        }
      }

      namespace = "https://test-hie.adph.state.al.us";
      writeStartElement(null, namespace, "payload", xmlWriter);

      if (localPayload == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("payload cannot be null!!");
      } else {
        xmlWriter.writeCharacters(localPayload);
      }

      xmlWriter.writeEndElement();

      xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("https://test-hie.adph.state.al.us")) {
        return "ns1";
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
        xmlWriter.writeStartElement(namespace, localPart);
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
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      }

      xmlWriter.writeAttribute(namespace, attName, attValue);
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
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
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
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
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
          xmlWriter.writeCharacters(prefix + ":"
              + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        } else {
          // i.e this is the default namespace
          xmlWriter.writeCharacters(
              org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        }
      } else {
        xmlWriter.writeCharacters(
            org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
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
            stringToWrite.append(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
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

          if ((uri == null) || (uri.length() == 0)) {
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
     * databinding method to get an XML representation of this object
     *
     */
    public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
        throws org.apache.axis2.databinding.ADBException {
      java.util.ArrayList elementList = new java.util.ArrayList();
      java.util.ArrayList attribList = new java.util.ArrayList();

      elementList
          .add(new javax.xml.namespace.QName("https://test-hie.adph.state.al.us", "payload"));

      if (localPayload != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPayload));
      } else {
        throw new org.apache.axis2.databinding.ADBException("payload cannot be null!!");
      }

      return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,
          elementList.toArray(), attribList.toArray());
    }

    /**
     * Factory class that keeps the parse method
     */
    public static class Factory {
      /**
       * static method to create the object Precondition: If this object is an element, the current
       * or next start element starts this object and any intervening reader events are ignorable If
       * this object is not an element, it is a complex type and the reader is at the event just
       * after the outer start element Postcondition: If this object is an element, the reader is
       * positioned at its end element If this object is a complex type, the reader is positioned at
       * the end element of its outer element
       */
      public static Aliiashl7Response parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        Aliiashl7Response object = new Aliiashl7Response();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";

        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
              "type") != null) {
            java.lang.String fullTypeName =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");

            if (fullTypeName != null) {
              java.lang.String nsPrefix = null;

              if (fullTypeName.indexOf(":") > -1) {
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
              }

              nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

              java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

              if (!"aliiashl7Response".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);

                return (Aliiashl7Response) ExtensionMapper.getTypeObject(nsUri, type, reader);
              }
            }
          }

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          reader.next();

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("https://test-hie.adph.state.al.us", "payload")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "payload" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setPayload(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

            reader.next();
          } // End of if for expected property start element

          else {
            // A start element we are not expecting indicates an invalid parameter was passed
            throw new org.apache.axis2.databinding.ADBException(
                "Unexpected subelement " + reader.getName());
          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()) {
            // A start element we are not expecting indicates a trailing invalid property
            throw new org.apache.axis2.databinding.ADBException(
                "Unexpected subelement " + reader.getName());
          }
        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }
    } // end of factory class
  }
}
