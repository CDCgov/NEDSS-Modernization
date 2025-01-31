/**
 * Client_ServiceStub.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.3 Built on : Jun 27, 2015
 * (11:17:49 BST)
 */
package org.immregistries.smm.tester.connectors.co;


/*
 * Client_ServiceStub java implementation
 */
@SuppressWarnings("all")
public class Client_ServiceStub extends org.apache.axis2.client.Stub {
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
  public Client_ServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
      java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
    this(configurationContext, targetEndpoint, false);
  }

  /**
   * Constructor that takes in a configContext and useseperate listner
   */
  public Client_ServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
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
  public Client_ServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext)
      throws org.apache.axis2.AxisFault {
    this(configurationContext, "https://cigtest.state.co.us/CIGservice/ImmunizationService");
  }

  /**
   * Default Constructor
   */
  public Client_ServiceStub() throws org.apache.axis2.AxisFault {
    this("https://cigtest.state.co.us/CIGservice/ImmunizationService");
  }

  /**
   * Constructor taking the target endpoint
   */
  public Client_ServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
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
    _service = new org.apache.axis2.description.AxisService("Client_Service" + getUniqueSuffix());
    addAnonymousOperations();

    // creating the operations
    org.apache.axis2.description.AxisOperation __operation;

    _operations = new org.apache.axis2.description.AxisOperation[2];

    __operation = new org.apache.axis2.description.OutInAxisOperation();

    __operation.setName(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "submitSingleMessage"));
    _service.addOperation(__operation);

    _operations[0] = __operation;

    __operation = new org.apache.axis2.description.OutInAxisOperation();

    __operation.setName(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "connectivityTest"));
    _service.addOperation(__operation);

    _operations[1] = __operation;
  }

  // populates the faults
  private void populateFaults() {
    faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "MessageTooLargeFault"),
        "submitSingleMessage"), "_2011.iisb.cdc.MessageTooLargeFaultMessage");
    faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "MessageTooLargeFault"),
        "submitSingleMessage"), "_2011.iisb.cdc.MessageTooLargeFaultMessage");
    faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "MessageTooLargeFault"),
        "submitSingleMessage"), "_2011.iisb.cdc.Client_ServiceStub$MessageTooLargeFault");

    faultExceptionNameMap.put(
        new org.apache.axis2.client.FaultMapKey(
            new javax.xml.namespace.QName("urn:cdc:iisb:2011", "fault"), "submitSingleMessage"),
        "_2011.iisb.cdc.UnknownFaultMessage");
    faultExceptionClassNameMap.put(
        new org.apache.axis2.client.FaultMapKey(
            new javax.xml.namespace.QName("urn:cdc:iisb:2011", "fault"), "submitSingleMessage"),
        "_2011.iisb.cdc.UnknownFaultMessage");
    faultMessageMap.put(
        new org.apache.axis2.client.FaultMapKey(
            new javax.xml.namespace.QName("urn:cdc:iisb:2011", "fault"), "submitSingleMessage"),
        "_2011.iisb.cdc.Client_ServiceStub$Fault");

    faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "SecurityFault"), "submitSingleMessage"),
        "_2011.iisb.cdc.SecurityFaultMessage");
    faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "SecurityFault"), "submitSingleMessage"),
        "_2011.iisb.cdc.SecurityFaultMessage");
    faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "SecurityFault"), "submitSingleMessage"),
        "_2011.iisb.cdc.Client_ServiceStub$SecurityFault");

    faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "UnsupportedOperationFault"),
        "connectivityTest"), "_2011.iisb.cdc.UnsupportedOperationFaultMessage");
    faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "UnsupportedOperationFault"),
        "connectivityTest"), "_2011.iisb.cdc.UnsupportedOperationFaultMessage");
    faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "UnsupportedOperationFault"),
        "connectivityTest"), "_2011.iisb.cdc.Client_ServiceStub$UnsupportedOperationFault");

    faultExceptionNameMap.put(
        new org.apache.axis2.client.FaultMapKey(
            new javax.xml.namespace.QName("urn:cdc:iisb:2011", "fault"), "connectivityTest"),
        "_2011.iisb.cdc.UnknownFaultMessage");
    faultExceptionClassNameMap.put(
        new org.apache.axis2.client.FaultMapKey(
            new javax.xml.namespace.QName("urn:cdc:iisb:2011", "fault"), "connectivityTest"),
        "_2011.iisb.cdc.UnknownFaultMessage");
    faultMessageMap.put(
        new org.apache.axis2.client.FaultMapKey(
            new javax.xml.namespace.QName("urn:cdc:iisb:2011", "fault"), "connectivityTest"),
        "_2011.iisb.cdc.Client_ServiceStub$Fault");
  }

  /**
   * Auto generated method signature
   *
   * @see org.immregistries.smm.tester.connectors.hi.Client_Service#submitSingleMessage
   * @param submitSingleMessage0
   * @throws org.immregistries.smm.tester.connectors.co.MessageTooLargeFaultMessage :
   * @throws org.immregistries.smm.tester.connectors.co.UnknownFaultMessage :
   * @throws org.immregistries.smm.tester.connectors.co.SecurityFaultMessage :
   */
  public org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessageResponse submitSingleMessage(
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessage submitSingleMessage0)
      throws java.rmi.RemoteException,
      org.immregistries.smm.tester.connectors.co.MessageTooLargeFaultMessage,
      org.immregistries.smm.tester.connectors.co.UnknownFaultMessage,
      org.immregistries.smm.tester.connectors.co.SecurityFaultMessage {
    org.apache.axis2.context.MessageContext _messageContext = null;

    try {
      org.apache.axis2.client.OperationClient _operationClient =
          _serviceClient.createClient(_operations[0].getName());
      _operationClient.getOptions().setAction("urn:cdc:iisb:2011:submitSingleMessage");
      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

      addPropertyToOperationClient(_operationClient,
          org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

      // create a message context
      _messageContext = new org.apache.axis2.context.MessageContext();

      // create SOAP envelope with that payload
      org.apache.axiom.soap.SOAPEnvelope env = null;

      env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
          submitSingleMessage0,
          optimizeContent(
              new javax.xml.namespace.QName("urn:cdc:iisb:2011", "submitSingleMessage")),
          new javax.xml.namespace.QName("urn:cdc:iisb:2011", "submitSingleMessage"));

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
          org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessageResponse.class,
          getEnvelopeNamespaces(_returnEnv));

      return (org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessageResponse) object;
    } catch (org.apache.axis2.AxisFault f) {
      org.apache.axiom.om.OMElement faultElt = f.getDetail();

      if (faultElt != null) {
        if (faultExceptionNameMap.containsKey(
            new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "submitSingleMessage"))) {
          // make the fault by reflection
          try {
            java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
                .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
                    "submitSingleMessage"));
            java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
            java.lang.reflect.Constructor constructor =
                exceptionClass.getConstructor(java.lang.String.class);
            java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());

            // message class
            java.lang.String messageClassName =
                (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
                    faultElt.getQName(), "submitSingleMessage"));
            java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
            java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
            java.lang.reflect.Method m =
                exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] {messageClass});
            m.invoke(ex, new java.lang.Object[] {messageObject});

            if (ex instanceof org.immregistries.smm.tester.connectors.co.MessageTooLargeFaultMessage) {
              throw (org.immregistries.smm.tester.connectors.co.MessageTooLargeFaultMessage) ex;
            }

            if (ex instanceof org.immregistries.smm.tester.connectors.co.UnknownFaultMessage) {
              throw (org.immregistries.smm.tester.connectors.co.UnknownFaultMessage) ex;
            }

            if (ex instanceof org.immregistries.smm.tester.connectors.co.SecurityFaultMessage) {
              throw (org.immregistries.smm.tester.connectors.co.SecurityFaultMessage) ex;
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
   * Auto generated method signature for Asynchronous Invocations
   *
   * @see org.immregistries.smm.tester.connectors.hi.Client_Service#startsubmitSingleMessage
   * @param submitSingleMessage0
   */
  public void startsubmitSingleMessage(
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessage submitSingleMessage0,
      final org.immregistries.smm.tester.connectors.co.Client_ServiceCallbackHandler callback)
      throws java.rmi.RemoteException {
    org.apache.axis2.client.OperationClient _operationClient =
        _serviceClient.createClient(_operations[0].getName());
    _operationClient.getOptions().setAction("urn:cdc:iisb:2011:submitSingleMessage");
    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

    addPropertyToOperationClient(_operationClient,
        org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

    // create SOAP envelope with that payload
    org.apache.axiom.soap.SOAPEnvelope env = null;
    final org.apache.axis2.context.MessageContext _messageContext =
        new org.apache.axis2.context.MessageContext();

    // Style is Doc.
    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
        submitSingleMessage0,
        optimizeContent(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "submitSingleMessage")),
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "submitSingleMessage"));

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
              org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessageResponse.class,
              getEnvelopeNamespaces(resultEnv));
          callback.receiveResultsubmitSingleMessage(
              (org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessageResponse) object);
        } catch (org.apache.axis2.AxisFault e) {
          callback.receiveErrorsubmitSingleMessage(e);
        }
      }

      public void onError(java.lang.Exception error) {
        if (error instanceof org.apache.axis2.AxisFault) {
          org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
          org.apache.axiom.om.OMElement faultElt = f.getDetail();

          if (faultElt != null) {
            if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(
                faultElt.getQName(), "submitSingleMessage"))) {
              // make the fault by reflection
              try {
                java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
                    .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
                        "submitSingleMessage"));
                java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                java.lang.reflect.Constructor constructor =
                    exceptionClass.getConstructor(java.lang.String.class);
                java.lang.Exception ex =
                    (java.lang.Exception) constructor.newInstance(f.getMessage());

                // message class
                java.lang.String messageClassName =
                    (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
                        faultElt.getQName(), "submitSingleMessage"));
                java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
                java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                    new java.lang.Class[] {messageClass});
                m.invoke(ex, new java.lang.Object[] {messageObject});

                if (ex instanceof org.immregistries.smm.tester.connectors.co.MessageTooLargeFaultMessage) {
                  callback.receiveErrorsubmitSingleMessage(
                      (org.immregistries.smm.tester.connectors.co.MessageTooLargeFaultMessage) ex);

                  return;
                }

                if (ex instanceof org.immregistries.smm.tester.connectors.co.UnknownFaultMessage) {
                  callback.receiveErrorsubmitSingleMessage(
                      (org.immregistries.smm.tester.connectors.co.UnknownFaultMessage) ex);

                  return;
                }

                if (ex instanceof org.immregistries.smm.tester.connectors.co.SecurityFaultMessage) {
                  callback.receiveErrorsubmitSingleMessage(
                      (org.immregistries.smm.tester.connectors.co.SecurityFaultMessage) ex);

                  return;
                }

                callback.receiveErrorsubmitSingleMessage(
                    new java.rmi.RemoteException(ex.getMessage(), ex));
              } catch (java.lang.ClassCastException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorsubmitSingleMessage(f);
              } catch (java.lang.ClassNotFoundException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorsubmitSingleMessage(f);
              } catch (java.lang.NoSuchMethodException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorsubmitSingleMessage(f);
              } catch (java.lang.reflect.InvocationTargetException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorsubmitSingleMessage(f);
              } catch (java.lang.IllegalAccessException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorsubmitSingleMessage(f);
              } catch (java.lang.InstantiationException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorsubmitSingleMessage(f);
              } catch (org.apache.axis2.AxisFault e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorsubmitSingleMessage(f);
              }
            } else {
              callback.receiveErrorsubmitSingleMessage(f);
            }
          } else {
            callback.receiveErrorsubmitSingleMessage(f);
          }
        } else {
          callback.receiveErrorsubmitSingleMessage(error);
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
          callback.receiveErrorsubmitSingleMessage(axisFault);
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
   * Auto generated method signature
   *
   * @see org.immregistries.smm.tester.connectors.hi.Client_Service#connectivityTest
   * @param connectivityTest2
   * @throws org.immregistries.smm.tester.connectors.co.UnsupportedOperationFaultMessage :
   * @throws org.immregistries.smm.tester.connectors.co.UnknownFaultMessage :
   */
  public org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTestResponse connectivityTest(
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTest connectivityTest2)
      throws java.rmi.RemoteException,
      org.immregistries.smm.tester.connectors.co.UnsupportedOperationFaultMessage,
      org.immregistries.smm.tester.connectors.co.UnknownFaultMessage {
    org.apache.axis2.context.MessageContext _messageContext = null;

    try {
      org.apache.axis2.client.OperationClient _operationClient =
          _serviceClient.createClient(_operations[1].getName());
      _operationClient.getOptions().setAction("urn:cdc:iisb:2011:connectivityTest");
      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

      addPropertyToOperationClient(_operationClient,
          org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

      // create a message context
      _messageContext = new org.apache.axis2.context.MessageContext();

      // create SOAP envelope with that payload
      org.apache.axiom.soap.SOAPEnvelope env = null;

      env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
          connectivityTest2,
          optimizeContent(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "connectivityTest")),
          new javax.xml.namespace.QName("urn:cdc:iisb:2011", "connectivityTest"));

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
          org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTestResponse.class,
          getEnvelopeNamespaces(_returnEnv));

      return (org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTestResponse) object;
    } catch (org.apache.axis2.AxisFault f) {
      org.apache.axiom.om.OMElement faultElt = f.getDetail();

      if (faultElt != null) {
        if (faultExceptionNameMap.containsKey(
            new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "connectivityTest"))) {
          // make the fault by reflection
          try {
            java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap.get(
                new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "connectivityTest"));
            java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
            java.lang.reflect.Constructor constructor =
                exceptionClass.getConstructor(java.lang.String.class);
            java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());

            // message class
            java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(
                new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "connectivityTest"));
            java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
            java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
            java.lang.reflect.Method m =
                exceptionClass.getMethod("setFaultMessage", new java.lang.Class[] {messageClass});
            m.invoke(ex, new java.lang.Object[] {messageObject});

            if (ex instanceof org.immregistries.smm.tester.connectors.co.UnsupportedOperationFaultMessage) {
              throw (org.immregistries.smm.tester.connectors.co.UnsupportedOperationFaultMessage) ex;
            }

            if (ex instanceof org.immregistries.smm.tester.connectors.co.UnknownFaultMessage) {
              throw (org.immregistries.smm.tester.connectors.co.UnknownFaultMessage) ex;
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
   * Auto generated method signature for Asynchronous Invocations
   *
   * @see org.immregistries.smm.tester.connectors.hi.Client_Service#startconnectivityTest
   * @param connectivityTest2
   */
  public void startconnectivityTest(
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTest connectivityTest2,
      final org.immregistries.smm.tester.connectors.co.Client_ServiceCallbackHandler callback)
      throws java.rmi.RemoteException {
    org.apache.axis2.client.OperationClient _operationClient =
        _serviceClient.createClient(_operations[1].getName());
    _operationClient.getOptions().setAction("urn:cdc:iisb:2011:connectivityTest");
    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

    addPropertyToOperationClient(_operationClient,
        org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");

    // create SOAP envelope with that payload
    org.apache.axiom.soap.SOAPEnvelope env = null;
    final org.apache.axis2.context.MessageContext _messageContext =
        new org.apache.axis2.context.MessageContext();

    // Style is Doc.
    env =
        toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), connectivityTest2,
            optimizeContent(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "connectivityTest")),
            new javax.xml.namespace.QName("urn:cdc:iisb:2011", "connectivityTest"));

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
              org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTestResponse.class,
              getEnvelopeNamespaces(resultEnv));
          callback.receiveResultconnectivityTest(
              (org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTestResponse) object);
        } catch (org.apache.axis2.AxisFault e) {
          callback.receiveErrorconnectivityTest(e);
        }
      }

      public void onError(java.lang.Exception error) {
        if (error instanceof org.apache.axis2.AxisFault) {
          org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
          org.apache.axiom.om.OMElement faultElt = f.getDetail();

          if (faultElt != null) {
            if (faultExceptionNameMap.containsKey(
                new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "connectivityTest"))) {
              // make the fault by reflection
              try {
                java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
                    .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
                        "connectivityTest"));
                java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                java.lang.reflect.Constructor constructor =
                    exceptionClass.getConstructor(java.lang.String.class);
                java.lang.Exception ex =
                    (java.lang.Exception) constructor.newInstance(f.getMessage());

                // message class
                java.lang.String messageClassName =
                    (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
                        faultElt.getQName(), "connectivityTest"));
                java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                java.lang.Object messageObject = fromOM(faultElt, messageClass, null);
                java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                    new java.lang.Class[] {messageClass});
                m.invoke(ex, new java.lang.Object[] {messageObject});

                if (ex instanceof org.immregistries.smm.tester.connectors.co.UnsupportedOperationFaultMessage) {
                  callback.receiveErrorconnectivityTest(
                      (org.immregistries.smm.tester.connectors.co.UnsupportedOperationFaultMessage) ex);

                  return;
                }

                if (ex instanceof org.immregistries.smm.tester.connectors.co.UnknownFaultMessage) {
                  callback.receiveErrorconnectivityTest(
                      (org.immregistries.smm.tester.connectors.co.UnknownFaultMessage) ex);

                  return;
                }

                callback.receiveErrorconnectivityTest(
                    new java.rmi.RemoteException(ex.getMessage(), ex));
              } catch (java.lang.ClassCastException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorconnectivityTest(f);
              } catch (java.lang.ClassNotFoundException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorconnectivityTest(f);
              } catch (java.lang.NoSuchMethodException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorconnectivityTest(f);
              } catch (java.lang.reflect.InvocationTargetException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorconnectivityTest(f);
              } catch (java.lang.IllegalAccessException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorconnectivityTest(f);
              } catch (java.lang.InstantiationException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorconnectivityTest(f);
              } catch (org.apache.axis2.AxisFault e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorconnectivityTest(f);
              }
            } else {
              callback.receiveErrorconnectivityTest(f);
            }
          } else {
            callback.receiveErrorconnectivityTest(f);
          }
        } else {
          callback.receiveErrorconnectivityTest(error);
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
          callback.receiveErrorconnectivityTest(axisFault);
        }
      }
    });

    org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;

    if ((_operations[1].getMessageReceiver() == null)
        && _operationClient.getOptions().isUseSeparateListener()) {
      _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
      _operations[1].setMessageReceiver(_callbackReceiver);
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
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessage param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {
    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessage.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessageResponse param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {
    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessageResponse.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.MessageTooLargeFault param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {
    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.co.Client_ServiceStub.MessageTooLargeFault.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.Fault param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {
    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.co.Client_ServiceStub.Fault.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SecurityFault param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {
    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SecurityFault.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTest param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {
    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTest.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTestResponse param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {
    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTestResponse.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.UnsupportedOperationFault param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {
    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.co.Client_ServiceStub.UnsupportedOperationFault.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessage param,
      boolean optimizeContent, javax.xml.namespace.QName methodQName)
      throws org.apache.axis2.AxisFault {
    try {
      org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
      emptyEnvelope.getBody()
          .addChild(param.getOMElement(
              org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessage.MY_QNAME,
              factory));

      return emptyEnvelope;
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
  }

  /* methods to provide back word compatibility */
  private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTest param,
      boolean optimizeContent, javax.xml.namespace.QName methodQName)
      throws org.apache.axis2.AxisFault {
    try {
      org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
      emptyEnvelope.getBody()
          .addChild(param.getOMElement(
              org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTest.MY_QNAME,
              factory));

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
      if (org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTest.class
          .equals(type)) {
        return org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTest.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());
      }

      if (org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTestResponse.class
          .equals(type)) {
        return org.immregistries.smm.tester.connectors.co.Client_ServiceStub.ConnectivityTestResponse.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());
      }

      if (org.immregistries.smm.tester.connectors.co.Client_ServiceStub.Fault.class.equals(type)) {
        return org.immregistries.smm.tester.connectors.co.Client_ServiceStub.Fault.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());
      }

      if (org.immregistries.smm.tester.connectors.co.Client_ServiceStub.MessageTooLargeFault.class
          .equals(type)) {
        return org.immregistries.smm.tester.connectors.co.Client_ServiceStub.MessageTooLargeFault.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());
      }

      if (org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SecurityFault.class
          .equals(type)) {
        return org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SecurityFault.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());
      }

      if (org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessage.class
          .equals(type)) {
        return org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessage.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());
      }

      if (org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessageResponse.class
          .equals(type)) {
        return org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SubmitSingleMessageResponse.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());
      }

      if (org.immregistries.smm.tester.connectors.co.Client_ServiceStub.UnsupportedOperationFault.class
          .equals(type)) {
        return org.immregistries.smm.tester.connectors.co.Client_ServiceStub.UnsupportedOperationFault.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());
      }
    } catch (java.lang.Exception e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }

    return null;
  }

  // https://cigtest.state.co.us/CIGservice/ImmunizationService
  public static class SubmitSingleMessage implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME =
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "submitSingleMessage", "ns1");

    /**
     * field for SubmitSingleMessage
     */
    protected SubmitSingleMessageRequestType localSubmitSingleMessage;

    /**
     * Auto generated getter method
     * 
     * @return SubmitSingleMessageRequestType
     */
    public SubmitSingleMessageRequestType getSubmitSingleMessage() {
      return localSubmitSingleMessage;
    }

    /**
     * Auto generated setter method
     * 
     * @param param SubmitSingleMessage
     */
    public void setSubmitSingleMessage(SubmitSingleMessageRequestType param) {
      this.localSubmitSingleMessage = param;
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
      // We can safely assume an element has only one type associated with it
      if (localSubmitSingleMessage == null) {
        throw new org.apache.axis2.databinding.ADBException("submitSingleMessage cannot be null!");
      }

      localSubmitSingleMessage.serialize(MY_QNAME, xmlWriter);
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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
      // We can safely assume an element has only one type associated with it
      return localSubmitSingleMessage.getPullParser(MY_QNAME);
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
      public static SubmitSingleMessage parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        SubmitSingleMessage object = new SubmitSingleMessage();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";

        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          while (!reader.isEndElement()) {
            if (reader.isStartElement()) {
              if (reader.isStartElement()
                  && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "submitSingleMessage")
                      .equals(reader.getName())) {
                object.setSubmitSingleMessage(SubmitSingleMessageRequestType.Factory.parse(reader));
              } // End of if for expected property start element

              else {
                // A start element we are not expecting indicates an invalid parameter was passed
                throw new org.apache.axis2.databinding.ADBException(
                    "Unexpected subelement " + reader.getName());
              }
            } else {
              reader.next();
            }
          } // end of while loop
        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }
    } // end of factory class
  }

  public static class SubmitSingleMessageRequestType
      implements org.apache.axis2.databinding.ADBBean {
    /*
     * This type was generated from the piece of schema that had name =
     * submitSingleMessageRequestType Namespace URI = urn:cdc:iisb:2011 Namespace Prefix = ns1
     */

    /**
     * field for Username
     */
    protected java.lang.String localUsername;

    /**
     * field for Password
     */
    protected java.lang.String localPassword;

    /**
     * field for FacilityID
     */
    protected java.lang.String localFacilityID;

    /**
     * field for Hl7Message
     */
    protected java.lang.String localHl7Message;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getUsername() {
      return localUsername;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Username
     */
    public void setUsername(java.lang.String param) {
      this.localUsername = param;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getPassword() {
      return localPassword;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Password
     */
    public void setPassword(java.lang.String param) {
      this.localPassword = param;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getFacilityID() {
      return localFacilityID;
    }

    /**
     * Auto generated setter method
     * 
     * @param param FacilityID
     */
    public void setFacilityID(java.lang.String param) {
      this.localFacilityID = param;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getHl7Message() {
      return localHl7Message;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Hl7Message
     */
    public void setHl7Message(java.lang.String param) {
      this.localHl7Message = param;
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
          new org.apache.axis2.databinding.ADBDataSource(this, parentQName);

      return factory.createOMElement(dataSource, parentQName);
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
        java.lang.String namespacePrefix = registerPrefix(xmlWriter, "urn:cdc:iisb:2011");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              namespacePrefix + ":submitSingleMessageRequestType", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              "submitSingleMessageRequestType", xmlWriter);
        }
      }

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "username", xmlWriter);

      if (localUsername == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("username cannot be null!!");
      } else {
        xmlWriter.writeCharacters(localUsername);
      }

      xmlWriter.writeEndElement();

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "password", xmlWriter);

      if (localPassword == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("password cannot be null!!");
      } else {
        xmlWriter.writeCharacters(localPassword);
      }

      xmlWriter.writeEndElement();

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "facilityID", xmlWriter);

      if (localFacilityID == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("facilityID cannot be null!!");
      } else {
        xmlWriter.writeCharacters(localFacilityID);
      }

      xmlWriter.writeEndElement();

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "hl7Message", xmlWriter);

      if (localHl7Message == null) {
        // write the nil attribute
        writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);
      } else {
        xmlWriter.writeCharacters(localHl7Message);
      }

      xmlWriter.writeEndElement();

      xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "username"));

      if (localUsername != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUsername));
      } else {
        throw new org.apache.axis2.databinding.ADBException("username cannot be null!!");
      }

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "password"));

      if (localPassword != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassword));
      } else {
        throw new org.apache.axis2.databinding.ADBException("password cannot be null!!");
      }

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "facilityID"));

      if (localFacilityID != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFacilityID));
      } else {
        throw new org.apache.axis2.databinding.ADBException("facilityID cannot be null!!");
      }

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "hl7Message"));

      elementList.add((localHl7Message == null) ? null
          : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHl7Message));

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
      public static SubmitSingleMessageRequestType parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        SubmitSingleMessageRequestType object = new SubmitSingleMessageRequestType();

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

              if (!"submitSingleMessageRequestType".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);

                return (SubmitSingleMessageRequestType) ExtensionMapper.getTypeObject(nsUri, type,
                    reader);
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
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "username")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "username" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setUsername(
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

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "password")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "password" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setPassword(
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

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "facilityID")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "facilityID" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setFacilityID(
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

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "hl7Message")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              java.lang.String content = reader.getElementText();

              object.setHl7Message(
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
            } else {
              reader.getElementText(); // throw away text nodes if any.
            }

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

  public static class SecurityFaultType implements org.apache.axis2.databinding.ADBBean {
    /*
     * This type was generated from the piece of schema that had name = SecurityFaultType Namespace
     * URI = urn:cdc:iisb:2011 Namespace Prefix = ns1
     */

    /**
     * field for Code
     */
    protected java.math.BigInteger localCode;

    /**
     * field for Reason
     */
    protected java.lang.Object localReason;

    /**
     * field for Detail
     */
    protected java.lang.String localDetail;

    /**
     * Auto generated getter method
     * 
     * @return java.math.BigInteger
     */
    public java.math.BigInteger getCode() {
      return localCode;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Code
     */
    public void setCode(java.math.BigInteger param) {
      this.localCode = param;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.Object
     */
    public java.lang.Object getReason() {
      return localReason;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Reason
     */
    public void setReason(java.lang.Object param) {
      this.localReason = param;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getDetail() {
      return localDetail;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Detail
     */
    public void setDetail(java.lang.String param) {
      this.localDetail = param;
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
          new org.apache.axis2.databinding.ADBDataSource(this, parentQName);

      return factory.createOMElement(dataSource, parentQName);
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
        java.lang.String namespacePrefix = registerPrefix(xmlWriter, "urn:cdc:iisb:2011");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              namespacePrefix + ":SecurityFaultType", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              "SecurityFaultType", xmlWriter);
        }
      }

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "Code", xmlWriter);

      if (localCode == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("Code cannot be null!!");
      } else {
        xmlWriter.writeCharacters(
            org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCode));
      }

      xmlWriter.writeEndElement();

      if (localReason != null) {
        if (localReason instanceof org.apache.axis2.databinding.ADBBean) {
          ((org.apache.axis2.databinding.ADBBean) localReason).serialize(
              new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Reason"), xmlWriter, true);
        } else {
          writeStartElement(null, "urn:cdc:iisb:2011", "Reason", xmlWriter);
          org.apache.axis2.databinding.utils.ConverterUtil.serializeAnyType(localReason, xmlWriter);
          xmlWriter.writeEndElement();
        }
      } else {
        throw new org.apache.axis2.databinding.ADBException("Reason cannot be null!!");
      }

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "Detail", xmlWriter);

      if (localDetail == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("Detail cannot be null!!");
      } else {
        xmlWriter.writeCharacters(localDetail);
      }

      xmlWriter.writeEndElement();

      xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Code"));

      if (localCode != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCode));
      } else {
        throw new org.apache.axis2.databinding.ADBException("Code cannot be null!!");
      }

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Reason"));

      if (localReason == null) {
        throw new org.apache.axis2.databinding.ADBException("Reason cannot be null!!");
      }

      elementList.add(localReason);

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Detail"));

      if (localDetail != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDetail));
      } else {
        throw new org.apache.axis2.databinding.ADBException("Detail cannot be null!!");
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
      public static SecurityFaultType parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        SecurityFaultType object = new SecurityFaultType();

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

              if (!"SecurityFaultType".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);

                return (SecurityFaultType) ExtensionMapper.getTypeObject(nsUri, type, reader);
              }
            }
          }

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          reader.next();

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement() && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Code")
              .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "Code" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setCode(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(content));

            reader.next();
          } // End of if for expected property start element

          else {
            // A start element we are not expecting indicates an invalid parameter was passed
            throw new org.apache.axis2.databinding.ADBException(
                "Unexpected subelement " + reader.getName());
          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Reason")
                  .equals(reader.getName())) {
            object.setReason(org.apache.axis2.databinding.utils.ConverterUtil
                .getAnyTypeObject(reader, ExtensionMapper.class));

            reader.next();
          } // End of if for expected property start element

          else {
            // A start element we are not expecting indicates an invalid parameter was passed
            throw new org.apache.axis2.databinding.ADBException(
                "Unexpected subelement " + reader.getName());
          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Detail")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "Detail" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setDetail(
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

  public static class UnsupportedOperationFaultType
      implements org.apache.axis2.databinding.ADBBean {
    /*
     * This type was generated from the piece of schema that had name =
     * UnsupportedOperationFaultType Namespace URI = urn:cdc:iisb:2011 Namespace Prefix = ns1
     */

    /**
     * field for Code
     */
    protected java.math.BigInteger localCode;

    /**
     * field for Reason
     */
    protected java.lang.Object localReason;

    /**
     * field for Detail
     */
    protected java.lang.String localDetail;

    /**
     * Auto generated getter method
     * 
     * @return java.math.BigInteger
     */
    public java.math.BigInteger getCode() {
      return localCode;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Code
     */
    public void setCode(java.math.BigInteger param) {
      this.localCode = param;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.Object
     */
    public java.lang.Object getReason() {
      return localReason;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Reason
     */
    public void setReason(java.lang.Object param) {
      this.localReason = param;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getDetail() {
      return localDetail;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Detail
     */
    public void setDetail(java.lang.String param) {
      this.localDetail = param;
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
          new org.apache.axis2.databinding.ADBDataSource(this, parentQName);

      return factory.createOMElement(dataSource, parentQName);
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
        java.lang.String namespacePrefix = registerPrefix(xmlWriter, "urn:cdc:iisb:2011");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              namespacePrefix + ":UnsupportedOperationFaultType", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              "UnsupportedOperationFaultType", xmlWriter);
        }
      }

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "Code", xmlWriter);

      if (localCode == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("Code cannot be null!!");
      } else {
        xmlWriter.writeCharacters(
            org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCode));
      }

      xmlWriter.writeEndElement();

      if (localReason != null) {
        if (localReason instanceof org.apache.axis2.databinding.ADBBean) {
          ((org.apache.axis2.databinding.ADBBean) localReason).serialize(
              new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Reason"), xmlWriter, true);
        } else {
          writeStartElement(null, "urn:cdc:iisb:2011", "Reason", xmlWriter);
          org.apache.axis2.databinding.utils.ConverterUtil.serializeAnyType(localReason, xmlWriter);
          xmlWriter.writeEndElement();
        }
      } else {
        throw new org.apache.axis2.databinding.ADBException("Reason cannot be null!!");
      }

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "Detail", xmlWriter);

      if (localDetail == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("Detail cannot be null!!");
      } else {
        xmlWriter.writeCharacters(localDetail);
      }

      xmlWriter.writeEndElement();

      xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Code"));

      if (localCode != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCode));
      } else {
        throw new org.apache.axis2.databinding.ADBException("Code cannot be null!!");
      }

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Reason"));

      if (localReason == null) {
        throw new org.apache.axis2.databinding.ADBException("Reason cannot be null!!");
      }

      elementList.add(localReason);

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Detail"));

      if (localDetail != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDetail));
      } else {
        throw new org.apache.axis2.databinding.ADBException("Detail cannot be null!!");
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
      public static UnsupportedOperationFaultType parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        UnsupportedOperationFaultType object = new UnsupportedOperationFaultType();

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

              if (!"UnsupportedOperationFaultType".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);

                return (UnsupportedOperationFaultType) ExtensionMapper.getTypeObject(nsUri, type,
                    reader);
              }
            }
          }

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          reader.next();

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement() && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Code")
              .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "Code" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setCode(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(content));

            reader.next();
          } // End of if for expected property start element

          else {
            // A start element we are not expecting indicates an invalid parameter was passed
            throw new org.apache.axis2.databinding.ADBException(
                "Unexpected subelement " + reader.getName());
          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Reason")
                  .equals(reader.getName())) {
            object.setReason(org.apache.axis2.databinding.utils.ConverterUtil
                .getAnyTypeObject(reader, ExtensionMapper.class));

            reader.next();
          } // End of if for expected property start element

          else {
            // A start element we are not expecting indicates an invalid parameter was passed
            throw new org.apache.axis2.databinding.ADBException(
                "Unexpected subelement " + reader.getName());
          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Detail")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "Detail" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setDetail(
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

  public static class MessageTooLargeFaultType implements org.apache.axis2.databinding.ADBBean {
    /*
     * This type was generated from the piece of schema that had name = MessageTooLargeFaultType
     * Namespace URI = urn:cdc:iisb:2011 Namespace Prefix = ns1
     */

    /**
     * field for Code
     */
    protected java.math.BigInteger localCode;

    /**
     * field for Reason
     */
    protected java.lang.Object localReason;

    /**
     * field for Detail
     */
    protected java.lang.String localDetail;

    /**
     * Auto generated getter method
     * 
     * @return java.math.BigInteger
     */
    public java.math.BigInteger getCode() {
      return localCode;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Code
     */
    public void setCode(java.math.BigInteger param) {
      this.localCode = param;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.Object
     */
    public java.lang.Object getReason() {
      return localReason;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Reason
     */
    public void setReason(java.lang.Object param) {
      this.localReason = param;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getDetail() {
      return localDetail;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Detail
     */
    public void setDetail(java.lang.String param) {
      this.localDetail = param;
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
          new org.apache.axis2.databinding.ADBDataSource(this, parentQName);

      return factory.createOMElement(dataSource, parentQName);
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
        java.lang.String namespacePrefix = registerPrefix(xmlWriter, "urn:cdc:iisb:2011");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              namespacePrefix + ":MessageTooLargeFaultType", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              "MessageTooLargeFaultType", xmlWriter);
        }
      }

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "Code", xmlWriter);

      if (localCode == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("Code cannot be null!!");
      } else {
        xmlWriter.writeCharacters(
            org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCode));
      }

      xmlWriter.writeEndElement();

      if (localReason != null) {
        if (localReason instanceof org.apache.axis2.databinding.ADBBean) {
          ((org.apache.axis2.databinding.ADBBean) localReason).serialize(
              new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Reason"), xmlWriter, true);
        } else {
          writeStartElement(null, "urn:cdc:iisb:2011", "Reason", xmlWriter);
          org.apache.axis2.databinding.utils.ConverterUtil.serializeAnyType(localReason, xmlWriter);
          xmlWriter.writeEndElement();
        }
      } else {
        throw new org.apache.axis2.databinding.ADBException("Reason cannot be null!!");
      }

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "Detail", xmlWriter);

      if (localDetail == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("Detail cannot be null!!");
      } else {
        xmlWriter.writeCharacters(localDetail);
      }

      xmlWriter.writeEndElement();

      xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Code"));

      if (localCode != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCode));
      } else {
        throw new org.apache.axis2.databinding.ADBException("Code cannot be null!!");
      }

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Reason"));

      if (localReason == null) {
        throw new org.apache.axis2.databinding.ADBException("Reason cannot be null!!");
      }

      elementList.add(localReason);

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Detail"));

      if (localDetail != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDetail));
      } else {
        throw new org.apache.axis2.databinding.ADBException("Detail cannot be null!!");
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
      public static MessageTooLargeFaultType parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        MessageTooLargeFaultType object = new MessageTooLargeFaultType();

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

              if (!"MessageTooLargeFaultType".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);

                return (MessageTooLargeFaultType) ExtensionMapper.getTypeObject(nsUri, type,
                    reader);
              }
            }
          }

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          reader.next();

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement() && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Code")
              .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "Code" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setCode(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(content));

            reader.next();
          } // End of if for expected property start element

          else {
            // A start element we are not expecting indicates an invalid parameter was passed
            throw new org.apache.axis2.databinding.ADBException(
                "Unexpected subelement " + reader.getName());
          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Reason")
                  .equals(reader.getName())) {
            object.setReason(org.apache.axis2.databinding.utils.ConverterUtil
                .getAnyTypeObject(reader, ExtensionMapper.class));

            reader.next();
          } // End of if for expected property start element

          else {
            // A start element we are not expecting indicates an invalid parameter was passed
            throw new org.apache.axis2.databinding.ADBException(
                "Unexpected subelement " + reader.getName());
          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Detail")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "Detail" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setDetail(
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

  public static class SubmitSingleMessageResponse implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME =
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "submitSingleMessageResponse", "ns1");

    /**
     * field for SubmitSingleMessageResponse
     */
    protected SubmitSingleMessageResponseType localSubmitSingleMessageResponse;

    /**
     * Auto generated getter method
     * 
     * @return SubmitSingleMessageResponseType
     */
    public SubmitSingleMessageResponseType getSubmitSingleMessageResponse() {
      return localSubmitSingleMessageResponse;
    }

    /**
     * Auto generated setter method
     * 
     * @param param SubmitSingleMessageResponse
     */
    public void setSubmitSingleMessageResponse(SubmitSingleMessageResponseType param) {
      this.localSubmitSingleMessageResponse = param;
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
      // We can safely assume an element has only one type associated with it
      if (localSubmitSingleMessageResponse == null) {
        throw new org.apache.axis2.databinding.ADBException(
            "submitSingleMessageResponse cannot be null!");
      }

      localSubmitSingleMessageResponse.serialize(MY_QNAME, xmlWriter);
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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
      // We can safely assume an element has only one type associated with it
      return localSubmitSingleMessageResponse.getPullParser(MY_QNAME);
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
      public static SubmitSingleMessageResponse parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        SubmitSingleMessageResponse object = new SubmitSingleMessageResponse();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";

        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          while (!reader.isEndElement()) {
            if (reader.isStartElement()) {
              if (reader.isStartElement() && new javax.xml.namespace.QName("urn:cdc:iisb:2011",
                  "submitSingleMessageResponse").equals(reader.getName())) {
                object.setSubmitSingleMessageResponse(
                    SubmitSingleMessageResponseType.Factory.parse(reader));
              } // End of if for expected property start element

              else {
                // A start element we are not expecting indicates an invalid parameter was passed
                throw new org.apache.axis2.databinding.ADBException(
                    "Unexpected subelement " + reader.getName());
              }
            } else {
              reader.next();
            }
          } // end of while loop
        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }
    } // end of factory class
  }

  public static class MessageTooLargeFault implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME =
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "MessageTooLargeFault", "ns1");

    /**
     * field for MessageTooLargeFault
     */
    protected MessageTooLargeFaultType localMessageTooLargeFault;

    /**
     * Auto generated getter method
     * 
     * @return MessageTooLargeFaultType
     */
    public MessageTooLargeFaultType getMessageTooLargeFault() {
      return localMessageTooLargeFault;
    }

    /**
     * Auto generated setter method
     * 
     * @param param MessageTooLargeFault
     */
    public void setMessageTooLargeFault(MessageTooLargeFaultType param) {
      this.localMessageTooLargeFault = param;
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
      // We can safely assume an element has only one type associated with it
      if (localMessageTooLargeFault == null) {
        throw new org.apache.axis2.databinding.ADBException("MessageTooLargeFault cannot be null!");
      }

      localMessageTooLargeFault.serialize(MY_QNAME, xmlWriter);
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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
      // We can safely assume an element has only one type associated with it
      return localMessageTooLargeFault.getPullParser(MY_QNAME);
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
      public static MessageTooLargeFault parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        MessageTooLargeFault object = new MessageTooLargeFault();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";

        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          while (!reader.isEndElement()) {
            if (reader.isStartElement()) {
              if (reader.isStartElement()
                  && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "MessageTooLargeFault")
                      .equals(reader.getName())) {
                object.setMessageTooLargeFault(MessageTooLargeFaultType.Factory.parse(reader));
              } // End of if for expected property start element

              else {
                // A start element we are not expecting indicates an invalid parameter was passed
                throw new org.apache.axis2.databinding.ADBException(
                    "Unexpected subelement " + reader.getName());
              }
            } else {
              reader.next();
            }
          } // end of while loop
        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }
    } // end of factory class
  }

  public static class ConnectivityTestResponseType implements org.apache.axis2.databinding.ADBBean {
    /*
     * This type was generated from the piece of schema that had name = connectivityTestResponseType
     * Namespace URI = urn:cdc:iisb:2011 Namespace Prefix = ns1
     */

    /**
     * field for _return
     */
    protected java.lang.String local_return;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String get_return() {
      return local_return;
    }

    /**
     * Auto generated setter method
     * 
     * @param param _return
     */
    public void set_return(java.lang.String param) {
      this.local_return = param;
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
          new org.apache.axis2.databinding.ADBDataSource(this, parentQName);

      return factory.createOMElement(dataSource, parentQName);
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
        java.lang.String namespacePrefix = registerPrefix(xmlWriter, "urn:cdc:iisb:2011");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              namespacePrefix + ":connectivityTestResponseType", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              "connectivityTestResponseType", xmlWriter);
        }
      }

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "return", xmlWriter);

      if (local_return == null) {
        // write the nil attribute
        writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);
      } else {
        xmlWriter.writeCharacters(local_return);
      }

      xmlWriter.writeEndElement();

      xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "return"));

      elementList.add((local_return == null) ? null
          : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(local_return));

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
      public static ConnectivityTestResponseType parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        ConnectivityTestResponseType object = new ConnectivityTestResponseType();

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

              if (!"connectivityTestResponseType".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);

                return (ConnectivityTestResponseType) ExtensionMapper.getTypeObject(nsUri, type,
                    reader);
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
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "return")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              java.lang.String content = reader.getElementText();

              object.set_return(
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
            } else {
              reader.getElementText(); // throw away text nodes if any.
            }

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

  public static class UnsupportedOperationFault implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME =
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "UnsupportedOperationFault", "ns1");

    /**
     * field for UnsupportedOperationFault
     */
    protected UnsupportedOperationFaultType localUnsupportedOperationFault;

    /**
     * Auto generated getter method
     * 
     * @return UnsupportedOperationFaultType
     */
    public UnsupportedOperationFaultType getUnsupportedOperationFault() {
      return localUnsupportedOperationFault;
    }

    /**
     * Auto generated setter method
     * 
     * @param param UnsupportedOperationFault
     */
    public void setUnsupportedOperationFault(UnsupportedOperationFaultType param) {
      this.localUnsupportedOperationFault = param;
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
      // We can safely assume an element has only one type associated with it
      if (localUnsupportedOperationFault == null) {
        throw new org.apache.axis2.databinding.ADBException(
            "UnsupportedOperationFault cannot be null!");
      }

      localUnsupportedOperationFault.serialize(MY_QNAME, xmlWriter);
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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
      // We can safely assume an element has only one type associated with it
      return localUnsupportedOperationFault.getPullParser(MY_QNAME);
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
      public static UnsupportedOperationFault parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        UnsupportedOperationFault object = new UnsupportedOperationFault();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";

        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          while (!reader.isEndElement()) {
            if (reader.isStartElement()) {
              if (reader.isStartElement()
                  && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "UnsupportedOperationFault")
                      .equals(reader.getName())) {
                object.setUnsupportedOperationFault(
                    UnsupportedOperationFaultType.Factory.parse(reader));
              } // End of if for expected property start element

              else {
                // A start element we are not expecting indicates an invalid parameter was passed
                throw new org.apache.axis2.databinding.ADBException(
                    "Unexpected subelement " + reader.getName());
              }
            } else {
              reader.next();
            }
          } // end of while loop
        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }
    } // end of factory class
  }

  public static class ConnectivityTestRequestType implements org.apache.axis2.databinding.ADBBean {
    /*
     * This type was generated from the piece of schema that had name = connectivityTestRequestType
     * Namespace URI = urn:cdc:iisb:2011 Namespace Prefix = ns1
     */

    /**
     * field for EchoBack
     */
    protected java.lang.String localEchoBack;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getEchoBack() {
      return localEchoBack;
    }

    /**
     * Auto generated setter method
     * 
     * @param param EchoBack
     */
    public void setEchoBack(java.lang.String param) {
      this.localEchoBack = param;
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
          new org.apache.axis2.databinding.ADBDataSource(this, parentQName);

      return factory.createOMElement(dataSource, parentQName);
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
        java.lang.String namespacePrefix = registerPrefix(xmlWriter, "urn:cdc:iisb:2011");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              namespacePrefix + ":connectivityTestRequestType", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              "connectivityTestRequestType", xmlWriter);
        }
      }

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "echoBack", xmlWriter);

      if (localEchoBack == null) {
        // write the nil attribute
        writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);
      } else {
        xmlWriter.writeCharacters(localEchoBack);
      }

      xmlWriter.writeEndElement();

      xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "echoBack"));

      elementList.add((localEchoBack == null) ? null
          : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEchoBack));

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
      public static ConnectivityTestRequestType parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        ConnectivityTestRequestType object = new ConnectivityTestRequestType();

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

              if (!"connectivityTestRequestType".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);

                return (ConnectivityTestRequestType) ExtensionMapper.getTypeObject(nsUri, type,
                    reader);
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
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "echoBack")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              java.lang.String content = reader.getElementText();

              object.setEchoBack(
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
            } else {
              reader.getElementText(); // throw away text nodes if any.
            }

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

  public static class ExtensionMapper {
    public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
        java.lang.String typeName, javax.xml.stream.XMLStreamReader reader)
        throws java.lang.Exception {
      if ("urn:cdc:iisb:2011".equals(namespaceURI) && "soapFaultType".equals(typeName)) {
        return SoapFaultType.Factory.parse(reader);
      }

      if ("urn:cdc:iisb:2011".equals(namespaceURI)
          && "submitSingleMessageResponseType".equals(typeName)) {
        return SubmitSingleMessageResponseType.Factory.parse(reader);
      }

      if ("urn:cdc:iisb:2011".equals(namespaceURI)
          && "connectivityTestRequestType".equals(typeName)) {
        return ConnectivityTestRequestType.Factory.parse(reader);
      }

      if ("urn:cdc:iisb:2011".equals(namespaceURI) && "SecurityFaultType".equals(typeName)) {
        return SecurityFaultType.Factory.parse(reader);
      }

      if ("urn:cdc:iisb:2011".equals(namespaceURI)
          && "submitSingleMessageRequestType".equals(typeName)) {
        return SubmitSingleMessageRequestType.Factory.parse(reader);
      }

      if ("urn:cdc:iisb:2011".equals(namespaceURI) && "MessageTooLargeFaultType".equals(typeName)) {
        return MessageTooLargeFaultType.Factory.parse(reader);
      }

      if ("urn:cdc:iisb:2011".equals(namespaceURI)
          && "connectivityTestResponseType".equals(typeName)) {
        return ConnectivityTestResponseType.Factory.parse(reader);
      }

      if ("urn:cdc:iisb:2011".equals(namespaceURI)
          && "UnsupportedOperationFaultType".equals(typeName)) {
        return UnsupportedOperationFaultType.Factory.parse(reader);
      }

      throw new org.apache.axis2.databinding.ADBException(
          "Unsupported type " + namespaceURI + " " + typeName);
    }
  }

  public static class ConnectivityTestResponse implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME =
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "connectivityTestResponse", "ns1");

    /**
     * field for ConnectivityTestResponse
     */
    protected ConnectivityTestResponseType localConnectivityTestResponse;

    /**
     * Auto generated getter method
     * 
     * @return ConnectivityTestResponseType
     */
    public ConnectivityTestResponseType getConnectivityTestResponse() {
      return localConnectivityTestResponse;
    }

    /**
     * Auto generated setter method
     * 
     * @param param ConnectivityTestResponse
     */
    public void setConnectivityTestResponse(ConnectivityTestResponseType param) {
      this.localConnectivityTestResponse = param;
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
      // We can safely assume an element has only one type associated with it
      if (localConnectivityTestResponse == null) {
        throw new org.apache.axis2.databinding.ADBException(
            "connectivityTestResponse cannot be null!");
      }

      localConnectivityTestResponse.serialize(MY_QNAME, xmlWriter);
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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
      // We can safely assume an element has only one type associated with it
      return localConnectivityTestResponse.getPullParser(MY_QNAME);
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
      public static ConnectivityTestResponse parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        ConnectivityTestResponse object = new ConnectivityTestResponse();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";

        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          while (!reader.isEndElement()) {
            if (reader.isStartElement()) {
              if (reader.isStartElement()
                  && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "connectivityTestResponse")
                      .equals(reader.getName())) {
                object.setConnectivityTestResponse(
                    ConnectivityTestResponseType.Factory.parse(reader));
              } // End of if for expected property start element

              else {
                // A start element we are not expecting indicates an invalid parameter was passed
                throw new org.apache.axis2.databinding.ADBException(
                    "Unexpected subelement " + reader.getName());
              }
            } else {
              reader.next();
            }
          } // end of while loop
        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }
    } // end of factory class
  }

  public static class SecurityFault implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME =
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "SecurityFault", "ns1");

    /**
     * field for SecurityFault
     */
    protected SecurityFaultType localSecurityFault;

    /**
     * Auto generated getter method
     * 
     * @return SecurityFaultType
     */
    public SecurityFaultType getSecurityFault() {
      return localSecurityFault;
    }

    /**
     * Auto generated setter method
     * 
     * @param param SecurityFault
     */
    public void setSecurityFault(SecurityFaultType param) {
      this.localSecurityFault = param;
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
      // We can safely assume an element has only one type associated with it
      if (localSecurityFault == null) {
        throw new org.apache.axis2.databinding.ADBException("SecurityFault cannot be null!");
      }

      localSecurityFault.serialize(MY_QNAME, xmlWriter);
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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
      // We can safely assume an element has only one type associated with it
      return localSecurityFault.getPullParser(MY_QNAME);
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
      public static SecurityFault parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        SecurityFault object = new SecurityFault();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";

        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          while (!reader.isEndElement()) {
            if (reader.isStartElement()) {
              if (reader.isStartElement()
                  && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "SecurityFault")
                      .equals(reader.getName())) {
                object.setSecurityFault(SecurityFaultType.Factory.parse(reader));
              } // End of if for expected property start element

              else {
                // A start element we are not expecting indicates an invalid parameter was passed
                throw new org.apache.axis2.databinding.ADBException(
                    "Unexpected subelement " + reader.getName());
              }
            } else {
              reader.next();
            }
          } // end of while loop
        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }
    } // end of factory class
  }

  public static class SoapFaultType implements org.apache.axis2.databinding.ADBBean {
    /*
     * This type was generated from the piece of schema that had name = soapFaultType Namespace URI
     * = urn:cdc:iisb:2011 Namespace Prefix = ns1
     */

    /**
     * field for Code
     */
    protected java.math.BigInteger localCode;

    /**
     * field for Reason
     */
    protected java.lang.String localReason;

    /**
     * field for Detail
     */
    protected java.lang.String localDetail;

    /**
     * Auto generated getter method
     * 
     * @return java.math.BigInteger
     */
    public java.math.BigInteger getCode() {
      return localCode;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Code
     */
    public void setCode(java.math.BigInteger param) {
      this.localCode = param;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getReason() {
      return localReason;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Reason
     */
    public void setReason(java.lang.String param) {
      this.localReason = param;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getDetail() {
      return localDetail;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Detail
     */
    public void setDetail(java.lang.String param) {
      this.localDetail = param;
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
          new org.apache.axis2.databinding.ADBDataSource(this, parentQName);

      return factory.createOMElement(dataSource, parentQName);
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
        java.lang.String namespacePrefix = registerPrefix(xmlWriter, "urn:cdc:iisb:2011");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              namespacePrefix + ":soapFaultType", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              "soapFaultType", xmlWriter);
        }
      }

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "Code", xmlWriter);

      if (localCode == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("Code cannot be null!!");
      } else {
        xmlWriter.writeCharacters(
            org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCode));
      }

      xmlWriter.writeEndElement();

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "Reason", xmlWriter);

      if (localReason == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("Reason cannot be null!!");
      } else {
        xmlWriter.writeCharacters(localReason);
      }

      xmlWriter.writeEndElement();

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "Detail", xmlWriter);

      if (localDetail == null) {
        // write the nil attribute
        throw new org.apache.axis2.databinding.ADBException("Detail cannot be null!!");
      } else {
        xmlWriter.writeCharacters(localDetail);
      }

      xmlWriter.writeEndElement();

      xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Code"));

      if (localCode != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCode));
      } else {
        throw new org.apache.axis2.databinding.ADBException("Code cannot be null!!");
      }

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Reason"));

      if (localReason != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReason));
      } else {
        throw new org.apache.axis2.databinding.ADBException("Reason cannot be null!!");
      }

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Detail"));

      if (localDetail != null) {
        elementList
            .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDetail));
      } else {
        throw new org.apache.axis2.databinding.ADBException("Detail cannot be null!!");
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
      public static SoapFaultType parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        SoapFaultType object = new SoapFaultType();

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

              if (!"soapFaultType".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);

                return (SoapFaultType) ExtensionMapper.getTypeObject(nsUri, type, reader);
              }
            }
          }

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          reader.next();

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement() && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Code")
              .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "Code" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setCode(
                org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(content));

            reader.next();
          } // End of if for expected property start element

          else {
            // A start element we are not expecting indicates an invalid parameter was passed
            throw new org.apache.axis2.databinding.ADBException(
                "Unexpected subelement " + reader.getName());
          }

          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Reason")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "Reason" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setReason(
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

          if (reader.isStartElement()
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "Detail")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              throw new org.apache.axis2.databinding.ADBException(
                  "The element: " + "Detail" + "  cannot be null");
            }

            java.lang.String content = reader.getElementText();

            object.setDetail(
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

  public static class SubmitSingleMessageResponseType
      implements org.apache.axis2.databinding.ADBBean {
    /*
     * This type was generated from the piece of schema that had name =
     * submitSingleMessageResponseType Namespace URI = urn:cdc:iisb:2011 Namespace Prefix = ns1
     */

    /**
     * field for _return
     */
    protected java.lang.String local_return;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String get_return() {
      return local_return;
    }

    /**
     * Auto generated setter method
     * 
     * @param param _return
     */
    public void set_return(java.lang.String param) {
      this.local_return = param;
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
          new org.apache.axis2.databinding.ADBDataSource(this, parentQName);

      return factory.createOMElement(dataSource, parentQName);
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
        java.lang.String namespacePrefix = registerPrefix(xmlWriter, "urn:cdc:iisb:2011");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              namespacePrefix + ":submitSingleMessageResponseType", xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
              "submitSingleMessageResponseType", xmlWriter);
        }
      }

      namespace = "urn:cdc:iisb:2011";
      writeStartElement(null, namespace, "return", xmlWriter);

      if (local_return == null) {
        // write the nil attribute
        writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);
      } else {
        xmlWriter.writeCharacters(local_return);
      }

      xmlWriter.writeEndElement();

      xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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

      elementList.add(new javax.xml.namespace.QName("urn:cdc:iisb:2011", "return"));

      elementList.add((local_return == null) ? null
          : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(local_return));

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
      public static SubmitSingleMessageResponseType parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        SubmitSingleMessageResponseType object = new SubmitSingleMessageResponseType();

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

              if (!"submitSingleMessageResponseType".equals(type)) {
                // find namespace for the prefix
                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);

                return (SubmitSingleMessageResponseType) ExtensionMapper.getTypeObject(nsUri, type,
                    reader);
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
              && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "return")
                  .equals(reader.getName())) {
            nillableValue =
                reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");

            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              java.lang.String content = reader.getElementText();

              object.set_return(
                  org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
            } else {
              reader.getElementText(); // throw away text nodes if any.
            }

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

  public static class ConnectivityTest implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME =
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "connectivityTest", "ns1");

    /**
     * field for ConnectivityTest
     */
    protected ConnectivityTestRequestType localConnectivityTest;

    /**
     * Auto generated getter method
     * 
     * @return ConnectivityTestRequestType
     */
    public ConnectivityTestRequestType getConnectivityTest() {
      return localConnectivityTest;
    }

    /**
     * Auto generated setter method
     * 
     * @param param ConnectivityTest
     */
    public void setConnectivityTest(ConnectivityTestRequestType param) {
      this.localConnectivityTest = param;
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
      // We can safely assume an element has only one type associated with it
      if (localConnectivityTest == null) {
        throw new org.apache.axis2.databinding.ADBException("connectivityTest cannot be null!");
      }

      localConnectivityTest.serialize(MY_QNAME, xmlWriter);
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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
      // We can safely assume an element has only one type associated with it
      return localConnectivityTest.getPullParser(MY_QNAME);
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
      public static ConnectivityTest parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        ConnectivityTest object = new ConnectivityTest();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";

        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          while (!reader.isEndElement()) {
            if (reader.isStartElement()) {
              if (reader.isStartElement()
                  && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "connectivityTest")
                      .equals(reader.getName())) {
                object.setConnectivityTest(ConnectivityTestRequestType.Factory.parse(reader));
              } // End of if for expected property start element

              else {
                // A start element we are not expecting indicates an invalid parameter was passed
                throw new org.apache.axis2.databinding.ADBException(
                    "Unexpected subelement " + reader.getName());
              }
            } else {
              reader.next();
            }
          } // end of while loop
        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }
    } // end of factory class
  }

  public static class Fault implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME =
        new javax.xml.namespace.QName("urn:cdc:iisb:2011", "fault", "ns1");

    /**
     * field for Fault
     */
    protected SoapFaultType localFault;

    /**
     * Auto generated getter method
     * 
     * @return SoapFaultType
     */
    public SoapFaultType getFault() {
      return localFault;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Fault
     */
    public void setFault(SoapFaultType param) {
      this.localFault = param;
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
      // We can safely assume an element has only one type associated with it
      if (localFault == null) {
        throw new org.apache.axis2.databinding.ADBException("fault cannot be null!");
      }

      localFault.serialize(MY_QNAME, xmlWriter);
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
      if (namespace.equals("urn:cdc:iisb:2011")) {
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
      // We can safely assume an element has only one type associated with it
      return localFault.getPullParser(MY_QNAME);
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
      public static Fault parse(javax.xml.stream.XMLStreamReader reader)
          throws java.lang.Exception {
        Fault object = new Fault();

        int event;
        java.lang.String nillableValue = null;
        java.lang.String prefix = "";
        java.lang.String namespaceuri = "";

        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next();

          // Note all attributes that were handled. Used to differ normal attributes
          // from anyAttributes.
          java.util.Vector handledAttributes = new java.util.Vector();

          while (!reader.isEndElement()) {
            if (reader.isStartElement()) {
              if (reader.isStartElement()
                  && new javax.xml.namespace.QName("urn:cdc:iisb:2011", "fault")
                      .equals(reader.getName())) {
                object.setFault(SoapFaultType.Factory.parse(reader));
              } // End of if for expected property start element

              else {
                // A start element we are not expecting indicates an invalid parameter was passed
                throw new org.apache.axis2.databinding.ADBException(
                    "Unexpected subelement " + reader.getName());
              }
            } else {
              reader.next();
            }
          } // end of while loop
        } catch (javax.xml.stream.XMLStreamException e) {
          throw new java.lang.Exception(e);
        }

        return object;
      }
    } // end of factory class
  }
}
