/* ---------------------------------------------------------------------------------------------------
 * Emergint
 * 501 East Broadway
 * Suite 250, Louisville, KY 40202
 * Copyright © 2002
 *  -----------------------------------------------------------------------------------------------------
 *  Author:
 *  Description :
 *-------------------------------------------------------------------------------------------------------
 */

package nndm;

import java.io.*;
import java.util.*;

/**
 * NEDSS Notify Disease Message Manger general Exception class to report/log errors
 * while performing the task of transforming messages for disease notification.
 */
public class nndmException extends Exception{


   private static String logFile = nndmConfig.DEFAULT_ERROR_LOG;
   /**
    * Static block to set up the log file
    */
   {
      String logFileName =nndmConfig.getNndmConfigurations(nndmConfig.ERROR_LOG_PROPERTY_NAME);
      if (!(logFileName.trim().length() == 0)){
         logFile = logFileName;
      }
   }

   /**
    * This is the specific Exception instance that occurred and 'caused' the
    * instantiation of this nndmException.  Set this if wanting to keep
    * the stack trace of the specific Exception.
    */
   private Throwable cause = null;

   /**
    * Default constructor to construct this nndmException object.
    */
   public nndmException() {
      super();
   }

    /**
    * Constructs this nndmException with message describing the exception.
    * In addition, this Exception will be written to the error log as
    * denoted by the nndmConfig.ERROR_LOG_PROPERTY_NAME entry.
    * @param message extra detail describing the exception condition.
    * @see nndm.nndmConfig#ERROR_LOG_PROPERTY_NAME
    */
   public nndmException(String message){
      super(message);
      logErrorTOFile(nndmException.logFile,this,true);
   }

    /**
    * Constructs this nndmException with message describing the exception and
    * the specific Excpetion instance that was the 'root cause' for constructing
    * this nndmException.
    * In addition, this Exception will be written to the error log as
    * denoted by the nndmConfig.ERROR_LOG_PROPERTY_NAME entry.
    * @param message extra detail describing the exception condition.
    * @param cause the specific Excpetion instance that was the <i> cause </i> for
    * constructing this nndmException.  This <i> cause </i> stack trace will be
    * appended to the nndmException stack trace.
    */
   public nndmException(String message, Throwable cause){
      super(message);
      setCause(cause);
      logErrorTOFile(nndmException.logFile,this,true);
   }


   /**
    * Writes an error exception message to a error log file.  If the given error file
	 * exists in current working directory, then error message will get appended to file,
    * else a new file will be created.
	 * @param nameOfFile the error file to write exception message to.
    * @param exception the Exception object.
    * @param verbose flag to write the exception's stack trace to file, thus
    * <code> true </code> to print stack trace to file, otherwise <code> false </code>
    * to write only the exception getMessage() string.
    * Security Exception.
    */
   protected static void logErrorTOFile(String fileName, Exception exception, boolean verbose) {
      PrintWriter pw = null;
      Date timeStamp = new Date();
      try{
         // open fileName in append mode
         FileOutputStream toFile = new FileOutputStream(fileName, true);
         OutputStreamWriter osw = new OutputStreamWriter(toFile, "UTF8");
         BufferedWriter bw = new BufferedWriter(osw, 16384);
         pw = new PrintWriter(bw);
         if (verbose){
            pw.print(timeStamp + " : ");
            // now print the stack trace to the file
            exception.printStackTrace(pw);
         } else {
            pw.println(timeStamp + " : " + exception.getMessage());
         }
      } catch (Exception e){
         System.err.println("Write error: while appending to " + fileName);
         e.printStackTrace();
      } finally {
         try{pw.close();} catch (Exception e){e.printStackTrace();}
         timeStamp = null;
      }
   }

   /**
    * Gets the cause of this nndmException - usually some specific instance
    * of an Exception object.
    * @return cause of this nndmException
    */
   public Throwable getCause(){
   return this.cause;
   }

   /**
    * Sets the cause of this nndmException - usually some specific instance
    * of an Exception object.
    * @param cause an Exception object that is to be added to this
    * nndmException's stack trace.
    */
   public void setCause(Throwable cause){
   this.cause = cause;
   }

   /**
    * Prints the stack trace for this nndmException, and appends the
    * <i> cause's </i> stack trace if not null.
    */
   public void printStackTrace() {
    super.printStackTrace();
    if (cause != null) {
      System.err.println("**** Caused By: ");
      cause.printStackTrace();
    }
   }

   /**
    * Prints the stack trace for this nndmException, and appends the
    * <i> cause's </i> stack trace if not null.
    * @param ps the printstream in which to send the stack trace text.
    */
   public void printStackTrace(java.io.PrintStream ps)
   {
    super.printStackTrace(ps);
    if (cause != null) {
      ps.println("**** Caused By: ");
      cause.printStackTrace(ps);
    }
   }

   /**
    * Prints the stack trace for this nndmException, and appends the
    * <i> cause's </i> stack trace if not null.
    * @param pw the print writer in which to send the stack trace text.
    */
   public void printStackTrace(java.io.PrintWriter pw)
   {
    super.printStackTrace(pw);
    if (cause != null) {
      pw.println("**** Caused By: ");
      cause.printStackTrace(pw);
    }
   }


}