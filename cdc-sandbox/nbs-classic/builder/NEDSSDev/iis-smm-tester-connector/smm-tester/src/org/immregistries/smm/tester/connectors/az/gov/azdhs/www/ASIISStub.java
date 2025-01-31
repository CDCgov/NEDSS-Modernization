
/**
 * ASIISStub.java
 *
 * 
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.7.4 Built on : Oct 21, 2016
 * (10:47:34 BST)
 */

package org.immregistries.smm.tester.connectors.az.gov.azdhs.www;



/*
 * ASIISStub java implementation
 */

@SuppressWarnings({"all"})
public class ASIISStub extends org.apache.axis2.client.Stub implements ASIIS {
  protected org.apache.axis2.description.AxisOperation[] _operations;

  // hashmaps to keep the fault mapping
  private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
  private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
  private java.util.HashMap faultMessageMap = new java.util.HashMap();

  private static int counter = 0;

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
    _service = new org.apache.axis2.description.AxisService("ASIIS" + getUniqueSuffix());
    addAnonymousOperations();

    // creating the operations
    org.apache.axis2.description.AxisOperation __operation;

    _operations = new org.apache.axis2.description.AxisOperation[3];

    __operation = new org.apache.axis2.description.OutInAxisOperation();


    __operation.setName(new javax.xml.namespace.QName("http://www.azdhs.gov", "sync_Submit"));
    _service.addOperation(__operation);



    _operations[0] = __operation;


    __operation = new org.apache.axis2.description.OutInAxisOperation();


    __operation.setName(new javax.xml.namespace.QName("http://www.azdhs.gov", "status_Query"));
    _service.addOperation(__operation);



    _operations[1] = __operation;


    __operation = new org.apache.axis2.description.OutInAxisOperation();


    __operation.setName(new javax.xml.namespace.QName("http://www.azdhs.gov", "async_Submit"));
    _service.addOperation(__operation);



    _operations[2] = __operation;


  }

  // populates the faults
  private void populateFaults() {



  }

  /**
   * Constructor that takes in a configContext
   */

  public ASIISStub(org.apache.axis2.context.ConfigurationContext configurationContext,
      java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
    this(configurationContext, targetEndpoint, false);
  }


  /**
   * Constructor that takes in a configContext and useseperate listner
   */
  public ASIISStub(org.apache.axis2.context.ConfigurationContext configurationContext,
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
  public ASIISStub(org.apache.axis2.context.ConfigurationContext configurationContext)
      throws org.apache.axis2.AxisFault {

    this(configurationContext, "https://appqa.azdhs.gov/asiis/hl7Services/ASIIS.asmx");

  }

  /**
   * Default Constructor
   */
  public ASIISStub() throws org.apache.axis2.AxisFault {

    this("https://appqa.azdhs.gov/asiis/hl7Services/ASIIS.asmx");

  }

  /**
   * Constructor taking the target endpoint
   */
  public ASIISStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
    this(null, targetEndpoint);
  }



  /**
   * Auto generated method signature
   * 
   * @see org.immregistries.smm.tester.connectors.az.gov.azdhs.www.ASIIS#sync_Submit
   * @param sync_Submit6
   * 
   */



  public org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_SubmitResponse sync_Submit(

      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_Submit sync_Submit6)


      throws java.rmi.RemoteException

  {
    org.apache.axis2.context.MessageContext _messageContext = null;
    try {
      org.apache.axis2.client.OperationClient _operationClient =
          _serviceClient.createClient(_operations[0].getName());
      _operationClient.getOptions().setAction("http://www.azdhs.gov/ASIIS/Sync_Submit");
      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



      addPropertyToOperationClient(_operationClient,
          org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");


      // create a message context
      _messageContext = new org.apache.axis2.context.MessageContext();



      // create SOAP envelope with that payload
      org.apache.axiom.soap.SOAPEnvelope env = null;


      env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), sync_Submit6,
          optimizeContent(new javax.xml.namespace.QName("http://www.azdhs.gov", "sync_Submit")),
          new javax.xml.namespace.QName("http://www.azdhs.gov", "Sync_Submit"));

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
          org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_SubmitResponse.class);


      return (org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_SubmitResponse) object;

    } catch (org.apache.axis2.AxisFault f) {

      org.apache.axiom.om.OMElement faultElt = f.getDetail();
      if (faultElt != null) {
        if (faultExceptionNameMap.containsKey(
            new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Sync_Submit"))) {
          // make the fault by reflection
          try {
            java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
                .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Sync_Submit"));
            java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
            java.lang.reflect.Constructor constructor =
                exceptionClass.getConstructor(java.lang.String.class);
            java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
            // message class
            java.lang.String messageClassName = (java.lang.String) faultMessageMap
                .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Sync_Submit"));
            java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
            java.lang.Object messageObject = fromOM(faultElt, messageClass);
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
   * Auto generated method signature for Asynchronous Invocations
   * 
   * @see org.immregistries.smm.tester.connectors.az.gov.azdhs.www.ASIIS#startsync_Submit
   * @param sync_Submit6
   * 
   */
  public void startsync_Submit(

      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_Submit sync_Submit6,

      final org.immregistries.smm.tester.connectors.az.gov.azdhs.www.ASIISCallbackHandler callback)

      throws java.rmi.RemoteException {

    org.apache.axis2.client.OperationClient _operationClient =
        _serviceClient.createClient(_operations[0].getName());
    _operationClient.getOptions().setAction("http://www.azdhs.gov/ASIIS/Sync_Submit");
    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



    addPropertyToOperationClient(_operationClient,
        org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");



    // create SOAP envelope with that payload
    org.apache.axiom.soap.SOAPEnvelope env = null;
    final org.apache.axis2.context.MessageContext _messageContext =
        new org.apache.axis2.context.MessageContext();


    // Style is Doc.


    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), sync_Submit6,
        optimizeContent(new javax.xml.namespace.QName("http://www.azdhs.gov", "sync_Submit")),
        new javax.xml.namespace.QName("http://www.azdhs.gov", "Sync_Submit"));

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
              org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_SubmitResponse.class);
          callback.receiveResultsync_Submit(
              (org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_SubmitResponse) object);

        } catch (org.apache.axis2.AxisFault e) {
          callback.receiveErrorsync_Submit(e);
        }
      }

      public void onError(java.lang.Exception error) {
        if (error instanceof org.apache.axis2.AxisFault) {
          org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
          org.apache.axiom.om.OMElement faultElt = f.getDetail();
          if (faultElt != null) {
            if (faultExceptionNameMap.containsKey(
                new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Sync_Submit"))) {
              // make the fault by reflection
              try {
                java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
                    .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
                        "Sync_Submit"));
                java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                java.lang.reflect.Constructor constructor =
                    exceptionClass.getConstructor(java.lang.String.class);
                java.lang.Exception ex =
                    (java.lang.Exception) constructor.newInstance(f.getMessage());
                // message class
                java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(
                    new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Sync_Submit"));
                java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                java.lang.Object messageObject = fromOM(faultElt, messageClass);
                java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                    new java.lang.Class[] {messageClass});
                m.invoke(ex, new java.lang.Object[] {messageObject});


                callback.receiveErrorsync_Submit(new java.rmi.RemoteException(ex.getMessage(), ex));
              } catch (java.lang.ClassCastException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorsync_Submit(f);
              } catch (java.lang.ClassNotFoundException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorsync_Submit(f);
              } catch (java.lang.NoSuchMethodException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorsync_Submit(f);
              } catch (java.lang.reflect.InvocationTargetException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorsync_Submit(f);
              } catch (java.lang.IllegalAccessException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorsync_Submit(f);
              } catch (java.lang.InstantiationException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorsync_Submit(f);
              } catch (org.apache.axis2.AxisFault e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorsync_Submit(f);
              }
            } else {
              callback.receiveErrorsync_Submit(f);
            }
          } else {
            callback.receiveErrorsync_Submit(f);
          }
        } else {
          callback.receiveErrorsync_Submit(error);
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
          callback.receiveErrorsync_Submit(axisFault);
        }
      }
    });


    org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
    if (_operations[0].getMessageReceiver() == null
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
   * @see org.immregistries.smm.tester.connectors.az.gov.azdhs.www.ASIIS#status_Query
   * @param status_Query8
   * 
   */



  public org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_QueryResponse status_Query(

      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_Query status_Query8)


      throws java.rmi.RemoteException

  {
    org.apache.axis2.context.MessageContext _messageContext = null;
    try {
      org.apache.axis2.client.OperationClient _operationClient =
          _serviceClient.createClient(_operations[1].getName());
      _operationClient.getOptions().setAction("http://www.azdhs.gov/ASIIS/Status_Query");
      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



      addPropertyToOperationClient(_operationClient,
          org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");


      // create a message context
      _messageContext = new org.apache.axis2.context.MessageContext();



      // create SOAP envelope with that payload
      org.apache.axiom.soap.SOAPEnvelope env = null;


      env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), status_Query8,
          optimizeContent(new javax.xml.namespace.QName("http://www.azdhs.gov", "status_Query")),
          new javax.xml.namespace.QName("http://www.azdhs.gov", "Status_Query"));

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
          org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_QueryResponse.class);


      return (org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_QueryResponse) object;

    } catch (org.apache.axis2.AxisFault f) {

      org.apache.axiom.om.OMElement faultElt = f.getDetail();
      if (faultElt != null) {
        if (faultExceptionNameMap.containsKey(
            new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Status_Query"))) {
          // make the fault by reflection
          try {
            java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
                .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Status_Query"));
            java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
            java.lang.reflect.Constructor constructor =
                exceptionClass.getConstructor(java.lang.String.class);
            java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
            // message class
            java.lang.String messageClassName = (java.lang.String) faultMessageMap
                .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Status_Query"));
            java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
            java.lang.Object messageObject = fromOM(faultElt, messageClass);
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
   * Auto generated method signature for Asynchronous Invocations
   * 
   * @see org.immregistries.smm.tester.connectors.az.gov.azdhs.www.ASIIS#startstatus_Query
   * @param status_Query8
   * 
   */
  public void startstatus_Query(

      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_Query status_Query8,

      final org.immregistries.smm.tester.connectors.az.gov.azdhs.www.ASIISCallbackHandler callback)

      throws java.rmi.RemoteException {

    org.apache.axis2.client.OperationClient _operationClient =
        _serviceClient.createClient(_operations[1].getName());
    _operationClient.getOptions().setAction("http://www.azdhs.gov/ASIIS/Status_Query");
    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



    addPropertyToOperationClient(_operationClient,
        org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");



    // create SOAP envelope with that payload
    org.apache.axiom.soap.SOAPEnvelope env = null;
    final org.apache.axis2.context.MessageContext _messageContext =
        new org.apache.axis2.context.MessageContext();


    // Style is Doc.


    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), status_Query8,
        optimizeContent(new javax.xml.namespace.QName("http://www.azdhs.gov", "status_Query")),
        new javax.xml.namespace.QName("http://www.azdhs.gov", "Status_Query"));

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
              org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_QueryResponse.class);
          callback.receiveResultstatus_Query(
              (org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_QueryResponse) object);

        } catch (org.apache.axis2.AxisFault e) {
          callback.receiveErrorstatus_Query(e);
        }
      }

      public void onError(java.lang.Exception error) {
        if (error instanceof org.apache.axis2.AxisFault) {
          org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
          org.apache.axiom.om.OMElement faultElt = f.getDetail();
          if (faultElt != null) {
            if (faultExceptionNameMap.containsKey(
                new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Status_Query"))) {
              // make the fault by reflection
              try {
                java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
                    .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
                        "Status_Query"));
                java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                java.lang.reflect.Constructor constructor =
                    exceptionClass.getConstructor(java.lang.String.class);
                java.lang.Exception ex =
                    (java.lang.Exception) constructor.newInstance(f.getMessage());
                // message class
                java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(
                    new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Status_Query"));
                java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                java.lang.Object messageObject = fromOM(faultElt, messageClass);
                java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                    new java.lang.Class[] {messageClass});
                m.invoke(ex, new java.lang.Object[] {messageObject});


                callback
                    .receiveErrorstatus_Query(new java.rmi.RemoteException(ex.getMessage(), ex));
              } catch (java.lang.ClassCastException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorstatus_Query(f);
              } catch (java.lang.ClassNotFoundException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorstatus_Query(f);
              } catch (java.lang.NoSuchMethodException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorstatus_Query(f);
              } catch (java.lang.reflect.InvocationTargetException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorstatus_Query(f);
              } catch (java.lang.IllegalAccessException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorstatus_Query(f);
              } catch (java.lang.InstantiationException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorstatus_Query(f);
              } catch (org.apache.axis2.AxisFault e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorstatus_Query(f);
              }
            } else {
              callback.receiveErrorstatus_Query(f);
            }
          } else {
            callback.receiveErrorstatus_Query(f);
          }
        } else {
          callback.receiveErrorstatus_Query(error);
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
          callback.receiveErrorstatus_Query(axisFault);
        }
      }
    });


    org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
    if (_operations[1].getMessageReceiver() == null
        && _operationClient.getOptions().isUseSeparateListener()) {
      _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
      _operations[1].setMessageReceiver(_callbackReceiver);
    }

    // execute the operation client
    _operationClient.execute(false);

  }

  /**
   * Auto generated method signature
   * 
   * @see org.immregistries.smm.tester.connectors.az.gov.azdhs.www.ASIIS#async_Submit
   * @param async_Submit10
   * 
   */



  public org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_SubmitResponse async_Submit(

      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_Submit async_Submit10)


      throws java.rmi.RemoteException

  {
    org.apache.axis2.context.MessageContext _messageContext = null;
    try {
      org.apache.axis2.client.OperationClient _operationClient =
          _serviceClient.createClient(_operations[2].getName());
      _operationClient.getOptions().setAction("http://www.azdhs.gov/ASIIS/Async_Submit");
      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



      addPropertyToOperationClient(_operationClient,
          org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");


      // create a message context
      _messageContext = new org.apache.axis2.context.MessageContext();



      // create SOAP envelope with that payload
      org.apache.axiom.soap.SOAPEnvelope env = null;


      env =
          toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), async_Submit10,
              optimizeContent(
                  new javax.xml.namespace.QName("http://www.azdhs.gov", "async_Submit")),
              new javax.xml.namespace.QName("http://www.azdhs.gov", "Async_Submit"));

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
          org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_SubmitResponse.class);


      return (org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_SubmitResponse) object;

    } catch (org.apache.axis2.AxisFault f) {

      org.apache.axiom.om.OMElement faultElt = f.getDetail();
      if (faultElt != null) {
        if (faultExceptionNameMap.containsKey(
            new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Async_Submit"))) {
          // make the fault by reflection
          try {
            java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
                .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Async_Submit"));
            java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
            java.lang.reflect.Constructor constructor =
                exceptionClass.getConstructor(java.lang.String.class);
            java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
            // message class
            java.lang.String messageClassName = (java.lang.String) faultMessageMap
                .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Async_Submit"));
            java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
            java.lang.Object messageObject = fromOM(faultElt, messageClass);
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
   * Auto generated method signature for Asynchronous Invocations
   * 
   * @see org.immregistries.smm.tester.connectors.az.gov.azdhs.www.ASIIS#startasync_Submit
   * @param async_Submit10
   * 
   */
  public void startasync_Submit(

      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_Submit async_Submit10,

      final org.immregistries.smm.tester.connectors.az.gov.azdhs.www.ASIISCallbackHandler callback)

      throws java.rmi.RemoteException {

    org.apache.axis2.client.OperationClient _operationClient =
        _serviceClient.createClient(_operations[2].getName());
    _operationClient.getOptions().setAction("http://www.azdhs.gov/ASIIS/Async_Submit");
    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



    addPropertyToOperationClient(_operationClient,
        org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");



    // create SOAP envelope with that payload
    org.apache.axiom.soap.SOAPEnvelope env = null;
    final org.apache.axis2.context.MessageContext _messageContext =
        new org.apache.axis2.context.MessageContext();


    // Style is Doc.


    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), async_Submit10,
        optimizeContent(new javax.xml.namespace.QName("http://www.azdhs.gov", "async_Submit")),
        new javax.xml.namespace.QName("http://www.azdhs.gov", "Async_Submit"));

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
              org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_SubmitResponse.class);
          callback.receiveResultasync_Submit(
              (org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_SubmitResponse) object);

        } catch (org.apache.axis2.AxisFault e) {
          callback.receiveErrorasync_Submit(e);
        }
      }

      public void onError(java.lang.Exception error) {
        if (error instanceof org.apache.axis2.AxisFault) {
          org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
          org.apache.axiom.om.OMElement faultElt = f.getDetail();
          if (faultElt != null) {
            if (faultExceptionNameMap.containsKey(
                new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Async_Submit"))) {
              // make the fault by reflection
              try {
                java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
                    .get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),
                        "Async_Submit"));
                java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                java.lang.reflect.Constructor constructor =
                    exceptionClass.getConstructor(java.lang.String.class);
                java.lang.Exception ex =
                    (java.lang.Exception) constructor.newInstance(f.getMessage());
                // message class
                java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(
                    new org.apache.axis2.client.FaultMapKey(faultElt.getQName(), "Async_Submit"));
                java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                java.lang.Object messageObject = fromOM(faultElt, messageClass);
                java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                    new java.lang.Class[] {messageClass});
                m.invoke(ex, new java.lang.Object[] {messageObject});


                callback
                    .receiveErrorasync_Submit(new java.rmi.RemoteException(ex.getMessage(), ex));
              } catch (java.lang.ClassCastException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorasync_Submit(f);
              } catch (java.lang.ClassNotFoundException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorasync_Submit(f);
              } catch (java.lang.NoSuchMethodException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorasync_Submit(f);
              } catch (java.lang.reflect.InvocationTargetException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorasync_Submit(f);
              } catch (java.lang.IllegalAccessException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorasync_Submit(f);
              } catch (java.lang.InstantiationException e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorasync_Submit(f);
              } catch (org.apache.axis2.AxisFault e) {
                // we cannot intantiate the class - throw the original Axis fault
                callback.receiveErrorasync_Submit(f);
              }
            } else {
              callback.receiveErrorasync_Submit(f);
            }
          } else {
            callback.receiveErrorasync_Submit(f);
          }
        } else {
          callback.receiveErrorasync_Submit(error);
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
          callback.receiveErrorasync_Submit(axisFault);
        }
      }
    });


    org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
    if (_operations[2].getMessageReceiver() == null
        && _operationClient.getOptions().isUseSeparateListener()) {
      _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
      _operations[2].setMessageReceiver(_callbackReceiver);
    }

    // execute the operation client
    _operationClient.execute(false);

  }


  private javax.xml.namespace.QName[] opNameArray = null;

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

  // https://appqa.azdhs.gov/asiis/hl7Services/ASIIS.asmx
  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_Submit param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {


    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_Submit.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }


  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_SubmitResponse param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {


    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_SubmitResponse.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }


  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_Query param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {


    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_Query.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }


  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_QueryResponse param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {


    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_QueryResponse.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }


  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_Submit param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {


    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_Submit.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }


  }

  private org.apache.axiom.om.OMElement toOM(
      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_SubmitResponse param,
      boolean optimizeContent) throws org.apache.axis2.AxisFault {


    try {
      return param.getOMElement(
          org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_SubmitResponse.MY_QNAME,
          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }


  }


  private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_Submit param,
      boolean optimizeContent, javax.xml.namespace.QName elementQName)
      throws org.apache.axis2.AxisFault {


    try {

      org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
      emptyEnvelope.getBody().addChild(param.getOMElement(
          org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_Submit.MY_QNAME, factory));
      return emptyEnvelope;
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }


  }


  /* methods to provide back word compatibility */



  private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_Query param,
      boolean optimizeContent, javax.xml.namespace.QName elementQName)
      throws org.apache.axis2.AxisFault {


    try {

      org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
      emptyEnvelope.getBody().addChild(param.getOMElement(
          org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_Query.MY_QNAME, factory));
      return emptyEnvelope;
    } catch (org.apache.axis2.databinding.ADBException e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }


  }


  /* methods to provide back word compatibility */



  private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_Submit param,
      boolean optimizeContent, javax.xml.namespace.QName elementQName)
      throws org.apache.axis2.AxisFault {


    try {

      org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
      emptyEnvelope.getBody().addChild(param.getOMElement(
          org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_Submit.MY_QNAME, factory));
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


  private java.lang.Object fromOM(org.apache.axiom.om.OMElement param, java.lang.Class type)
      throws org.apache.axis2.AxisFault {

    try {

      if (org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_Submit.class
          .equals(type)) {

        return org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_Submit.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());


      }

      if (org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_SubmitResponse.class
          .equals(type)) {

        return org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_SubmitResponse.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());


      }

      if (org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_Query.class
          .equals(type)) {

        return org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_Query.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());


      }

      if (org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_QueryResponse.class
          .equals(type)) {

        return org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_QueryResponse.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());


      }

      if (org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_Submit.class.equals(type)) {

        return org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_Submit.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());


      }

      if (org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_SubmitResponse.class
          .equals(type)) {

        return org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_SubmitResponse.Factory
            .parse(param.getXMLStreamReaderWithoutCaching());


      }

    } catch (java.lang.Exception e) {
      throw org.apache.axis2.AxisFault.makeFault(e);
    }
    return null;
  }



}
