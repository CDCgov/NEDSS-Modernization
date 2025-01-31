/**
 * HL7WSStub.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.3 Built on : Jun 27, 2015
 * (11:17:49 BST)
 */
package org.immregistries.smm.tester.connectors.ks;


/*
 * HL7WSStub java implementation
 */
@SuppressWarnings({"all"})
public class HL7WSStub extends org.apache.axis2.client.Stub {
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
  public HL7WSStub(org.apache.axis2.context.ConfigurationContext configurationContext,
      java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
    this(configurationContext, targetEndpoint, false);
  }

  /**
   * Constructor that takes in a configContext and useseperate listner
   */
  public HL7WSStub(org.apache.axis2.context.ConfigurationContext configurationContext,
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
  public HL7WSStub(org.apache.axis2.context.ConfigurationContext configurationContext)
      throws org.apache.axis2.AxisFault {
    this(configurationContext, "https://kanphixtrain.kdhe.state.ks.us/hl7services/HL7WS.asmx");
  }

  /**
   * Default Constructor
   */
  public HL7WSStub() throws org.apache.axis2.AxisFault {
    this("https://kanphixtrain.kdhe.state.ks.us/hl7services/HL7WS.asmx");
  }

  /**
   * Constructor taking the target endpoint
   */
  public HL7WSStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
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
    _service = new org.apache.axis2.description.AxisService("HL7WS" + getUniqueSuffix());
    addAnonymousOperations();

    // creating the operations
    org.apache.axis2.description.AxisOperation __operation;

    _operations = new org.apache.axis2.description.AxisOperation[1];

    __operation = new org.apache.axis2.description.OutInAxisOperation();

    __operation.setName(new javax.xml.namespace.QName("http://tempuri.org/", "processHL7Message"));
    _service.addOperation(__operation);

    _operations[0] = __operation;
  }

  // populates the faults
  private void populateFaults() {}

  /**
   * Auto generated method signature Consumer supplies userid, password, agency, and HL7 message and
   * the web service response with OK or ERROR. Production Version 1.0.0.0
   * 
   * @see org.tempuri.HL7WS#processHL7Message
   * @param request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_Registry0
   */
  public org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSResult processHL7Message(
      org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSRequest request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_Registry0)
      throws java.rmi.RemoteException {
    org.apache.axis2.context.MessageContext _messageContext = null;

    try {
      org.apache.axis2.client.OperationClient _operationClient =
          _serviceClient.createClient(_operations[0].getName());
      _operationClient.getOptions().setAction(
          "http://tempuri.org/Request or Post Information from the Immunization Registry");
      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

      addPropertyToOperationClient(_operationClient,
          org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

      // create a message context
      _messageContext = new org.apache.axis2.context.MessageContext();

      // create SOAP envelope with that payload
      org.apache.axiom.soap.SOAPEnvelope env = null;

      env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
          request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_Registry0,
          optimizeContent(
              new javax.xml.namespace.QName("http://tempuri.org/", "processHL7Message")),
          new javax.xml.namespace.QName("http://tempuri.org/", "processHL7Message"));

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
          org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSResult.class,
          getEnvelopeNamespaces(_returnEnv));

      return (org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSResult) object;
    } catch (org.apache.axis2.AxisFault f) {
      org.apache.axiom.om.OMElement faultElt = f.getDetail();

      if (faultElt != null) {
        if (faultExceptionNameMap.containsKey(
            new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "ProcessHL7Message"))) {
          // make the fault by reflection
          try {
            java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap.get(
                new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "ProcessHL7Message"));
            java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
            java.lang.reflect.Constructor constructor =
                exceptionClass.getConstructor(java.lang.String.class);
            java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());

            // message class
            java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(
                new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "ProcessHL7Message"));
            java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
            java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
            java.lang.reflect.Method m =
                exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] {messageClass});
            m.invoke(ex, new java.lang.Object[] {messageObject});

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
   * Auto generated method signature for Asynchronous Invocations Consumer supplies userid,
   * password, agency, and HL7 message and the web service response with OK or ERROR. Production
   * Version 1.0.0.0
   * 
   * @see org.tempuri.HL7WS#startprocessHL7Message
   * @param request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_Registry0
   */
  public void startprocessHL7Message(
      org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSRequest request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_Registry0,
      final org.immregistries.smm.tester.connectors.ks.HL7WSCallbackHandler callback)
      throws java.rmi.RemoteException {
    org.apache.axis2.client.OperationClient _operationClient =
        _serviceClient.createClient(_operations[0].getName());
    _operationClient.getOptions()
        .setAction("http://tempuri.org/Request or Post Information from the Immunization Registry");
    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

    addPropertyToOperationClient(_operationClient,
        org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

    // create SOAP envelope with that payload
    org.apache.axiom.soap.SOAPEnvelope env = null;
    final org.apache.axis2.context.MessageContext _messageContext =
        new org.apache.axis2.context.MessageContext();

    // Style is Doc.
    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
        request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_Registry0,
        optimizeContent(new javax.xml.namespace.QName("http://tempuri.org/", "processHL7Message")),
        new javax.xml.namespace.QName("http://tempuri.org/", "processHL7Message"));

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
              org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSResult.class,
              getEnvelopeNamespaces(resultEnv));
          callback.receiveResultprocessHL7Message(
              (org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSResult) object);
        } catch (org.apache.axis2.AxisFault e) {
          callback.receiveErrorprocessHL7Message(e);
        }
      }

      public void onError(java.lang.Exception error) {
        if (error instanceof org.apache.axis2.AxisFault) {
          org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
          org.apache.axiom.om.OMElement faultElt = f.getDetail();

          if (faultElt != null) {
            if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(
                faultElt.getQName(), "ProcessHL7Message"))) {
              // make the fault by reflection
              try {
                java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
                    .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
                        "ProcessHL7Message"));
                java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                java.lang.reflect.Constructor constructor =
                    exceptionClass.getConstructor(java.lang.String.class);
                java.lang.Exception ex =
                    (java.lang.Exception) constructor.newInstance(f.getMessage());

                // message class
                java.lang.String messageClassName =
                    (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
                        faultElt.getQName(), "ProcessHL7Message"));
                java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
                java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                    new java.lang.Class[] {messageClass});
                m.invoke(ex, new java.lang.Object[] {messageObject});

                callback.receiveErrorprocessHL7Message(
                    new java.rmi.RemoteException(ex.getMessage(), ex));
              } catch (java.lang.ClassCastException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorprocessHL7Message(f);
              } catch (java.lang.ClassNotFoundException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorprocessHL7Message(f);
              } catch (java.lang.NoSuchMethodException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorprocessHL7Message(f);
              } catch (java.lang.reflect.InvocationTargetException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorprocessHL7Message(f);
              } catch (java.lang.IllegalAccessException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorprocessHL7Message(f);
              } catch (java.lang.InstantiationException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorprocessHL7Message(f);
              } catch (org.apache.axis2.AxisFault e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorprocessHL7Message(f);
              }
            } else {
              callback.receiveErrorprocessHL7Message(f);
            }
          } else {
            callback.receiveErrorprocessHL7Message(f);
          }
        } else {
          callback.receiveErrorprocessHL7Message(error);
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
          callback.receiveErrorprocessHL7Message(axisFault);
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
      org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSRequest param, boolean optimizeContent)
      throws org.apache.axis2.AxisFault {
    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSRequest.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSResult param, boolean optimizeContent)
      throws org.apache.axis2.AxisFault {
    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSResult.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
      org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSRequest param, boolean optimizeContent,
      javax.xml.namespace.QName methodQName) throws org.apache.axis2.AxisFault {
    try {
      org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
      emptyEnvelope.getBody().addChild(param.getOMElement(
          org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSRequest.MY_QNAME, factory));

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
      if (org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSRequest.class.equals(type)) {
        return org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSRequest.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());
      }

      if (org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSResult.class.equals(type)) {
        return org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSResult.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());
      }
    } catch (java.lang.Exception e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }

    return null;
  }

  // https://kanphixtrain.kdhe.state.ks.us/hl7services/HL7WS.asmx
  public static class ExtensionMapper {
    public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
        java.lang.String typeName, javax.xml.stream.XMLStreamReader reader)
        throws java.lang.Exception {
      throw new org.apache.axis2.databinding.ADBException(
          "Unsupported type " + namespaceURI + " " + typeName);
    }
  }

  public static class KSResult implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME =
        new javax.xml.namespace.QName("http://tempuri.org/",
            "Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResponse",
            "ns1");

    /**
     * field for
     * Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult
     */
    protected java.lang.String localRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult;

    /*
     * This tracker boolean wil be used to detect whether the user called the set method for this
     * attribute. It will be used to determine whether to include this field in the serialized XML
     */
    protected boolean localRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResultTracker =
        false;

    public boolean isRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResultSpecified() {
      return localRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResultTracker;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getResult() {
      return localRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult;
    }

    /**
     * Auto generated setter method
     * 
     * @param param
     *        Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult
     */
    public void setRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult(
        java.lang.String param) {
      localRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResultTracker =
          param != null;

      this.localRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult =
          param;
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
        java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://tempuri.org/");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              namespacePrefix
                  + ":Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResponse",
              xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              "Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResponse",
              xmlWriter);
        }
      }

      if (localRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResultTracker) {
        namespace = "http://tempuri.org/";
        writeStartElement(null, namespace,
            "Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult",
            xmlWriter);

        if (localRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult == null) {
          // write the nil attribute
          throw new org.apache.axis2.databinding.ADBException(
              "Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult cannot be null!!");
        } else {
          xmlWriter.writeCharacters(
              localRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult);
        }

        xmlWriter.writeEndElement();
      }

      xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("http://tempuri.org/")) {
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

      if (localRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResultTracker) {
        elementList.add(new javax.xml.namespace.QName("http://tempuri.org/",
            "Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult"));

        if (localRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult != null) {
          elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
              localRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult));
        } else {
          throw new org.apache.axis2.databinding.ADBException(
              "Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult cannot be null!!");
        }
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
      public static KSResult parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        KSResult object = new KSResult();

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

              if (!"Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResponse"
                  .equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);

                return (KSResult) ExtensionMapper.getTypeObject(nsUri, type, reader);
              }
            }
          }

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          reader.next();

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement() && new javax.xml.namespace.QName("http://tempuri.org/",
              "Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException("The element: "
                  + "Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult"
                  + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object
                .setRequest_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_RegistryResult(
                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

            reader.next();
          } // End of if for expected property start element

          else {
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

  public static class KSRequest implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME =
        new javax.xml.namespace.QName("http://tempuri.org/",
            "Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_Registry",
            "ns1");

    /**
     * field for PASSWORD
     */
    protected java.lang.String localPASSWORD;

    /*
     * This tracker boolean wil be used to detect whether the user called the set method for this
     * attribute. It will be used to determine whether to include this field in the serialized XML
     */
    protected boolean localPASSWORDTracker = false;

    /**
     * field for FACILITYID
     */
    protected java.lang.String localFACILITYID;

    /*
     * This tracker boolean wil be used to detect whether the user called the set method for this
     * attribute. It will be used to determine whether to include this field in the serialized XML
     */
    protected boolean localFACILITYIDTracker = false;

    /**
     * field for MESSAGEDATA
     */
    protected javax.activation.DataHandler localMESSAGEDATA;

    /*
     * This tracker boolean wil be used to detect whether the user called the set method for this
     * attribute. It will be used to determine whether to include this field in the serialized XML
     */
    protected boolean localMESSAGEDATATracker = false;

    public boolean isPASSWORDSpecified() {
      return localPASSWORDTracker;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getPASSWORD() {
      return localPASSWORD;
    }

    /**
     * Auto generated setter method
     * 
     * @param param PASSWORD
     */
    public void setPASSWORD(java.lang.String param) {
      localPASSWORDTracker = param != null;

      this.localPASSWORD = param;
    }

    public boolean isFACILITYIDSpecified() {
      return localFACILITYIDTracker;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getFACILITYID() {
      return localFACILITYID;
    }

    /**
     * Auto generated setter method
     * 
     * @param param FACILITYID
     */
    public void setFACILITYID(java.lang.String param) {
      localFACILITYIDTracker = param != null;

      this.localFACILITYID = param;
    }

    public boolean isMESSAGEDATASpecified() {
      return localMESSAGEDATATracker;
    }

    /**
     * Auto generated getter method
     * 
     * @return javax.activation.DataHandler
     */
    public javax.activation.DataHandler getMESSAGEDATA() {
      return localMESSAGEDATA;
    }

    /**
     * Auto generated setter method
     * 
     * @param param MESSAGEDATA
     */
    public void setMESSAGEDATA(javax.activation.DataHandler param) {
      localMESSAGEDATATracker = param != null;

      this.localMESSAGEDATA = param;
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
        java.lang.String namespacePrefix = registerPrefix(xmlWriter, "http://tempuri.org/");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              namespacePrefix
                  + ":Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_Registry",
              xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              "Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_Registry",
              xmlWriter);
        }
      }

      if (localPASSWORDTracker) {
        namespace = "http://tempuri.org/";
        writeStartElement(null, namespace, "PASSWORD", xmlWriter);

        if (localPASSWORD == null) {
          // write the nil attribute
          throw new org.apache.axis2.databinding.ADBException("PASSWORD cannot be null!!");
        } else {
          xmlWriter.writeCharacters(localPASSWORD);
        }

        xmlWriter.writeEndElement();
      }

      if (localFACILITYIDTracker) {
        namespace = "http://tempuri.org/";
        writeStartElement(null, namespace, "FACILITYID", xmlWriter);

        if (localFACILITYID == null) {
          // write the nil attribute
          throw new org.apache.axis2.databinding.ADBException("FACILITYID cannot be null!!");
        } else {
          xmlWriter.writeCharacters(localFACILITYID);
        }

        xmlWriter.writeEndElement();
      }

      if (localMESSAGEDATATracker) {
        namespace = "http://tempuri.org/";
        writeStartElement(null, namespace, "MESSAGEDATA", xmlWriter);

        if (localMESSAGEDATA != null) {
          try {
            org.apache.axiom.util.stax.XMLStreamWriterUtils.writeDataHandler(xmlWriter,
                localMESSAGEDATA, null, true);
          } catch (java.io.IOException ex) {
            throw new javax.xml.stream.XMLStreamException(
                "Unable to read data handler for MESSAGEDATA", ex);
          }
        } else {
        }

        xmlWriter.writeEndElement();
      }

      xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("http://tempuri.org/")) {
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

      if (localPASSWORDTracker) {
        elementList.add(new javax.xml.namespace.QName("http://tempuri.org/", "PASSWORD"));

        if (localPASSWORD != null) {
          elementList
              .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPASSWORD));
        } else {
          throw new org.apache.axis2.databinding.ADBException("PASSWORD cannot be null!!");
        }
      }

      if (localFACILITYIDTracker) {
        elementList.add(new javax.xml.namespace.QName("http://tempuri.org/", "FACILITYID"));

        if (localFACILITYID != null) {
          elementList.add(
              org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFACILITYID));
        } else {
          throw new org.apache.axis2.databinding.ADBException("FACILITYID cannot be null!!");
        }
      }

      if (localMESSAGEDATATracker) {
        elementList.add(new javax.xml.namespace.QName("http://tempuri.org/", "MESSAGEDATA"));

        elementList.add(localMESSAGEDATA);
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
      public static KSRequest parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        KSRequest object = new KSRequest();

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

              if (!"Request_x0020_or_x0020_Post_x0020_Information_x0020_from_x0020_the_x0020_Immunization_x0020_Registry"
                  .equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);

                return (KSRequest) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
              && new javax.xml.namespace.QName("http://tempuri.org/", "PASSWORD")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "PASSWORD" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setPASSWORD(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

            reader.next();
          } // End of if for expected property start element

          else {
          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("http://tempuri.org/", "FACILITYID")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "FACILITYID" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setFACILITYID(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

            reader.next();
          } // End of if for expected property start element

          else {
          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("http://tempuri.org/", "MESSAGEDATA")
                  .equals(reader.getName())) {
            object.setMESSAGEDATA(
                org.apache.axiom.util.stax.XMLStreamReaderUtils.getDataHandlerFromElement(reader));

            reader.next();
          } // End of if for expected property start element

          else {
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
