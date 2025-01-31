package gov.cdc.nedss.util;



  import javax.servlet.http.*;

  /**
   * Title        :   Command is a interface.
   * Description  :   This class is used for bean utils
   * Copyright    :   Copyright (c) 2001
   * Company      :   Computer Sciences Corporation
   * @author      :   CSC
   * @version     :   1.0
   */
  public interface Command   {

     /**
      * Access method for the execute servlet request.
      * @param RequestHelper the helper
      * @return   as a String
      */
    public String execute(RequestHelper helper) throws javax.servlet.ServletException,
                                                        java.io.IOException, java.lang.Exception;
  }