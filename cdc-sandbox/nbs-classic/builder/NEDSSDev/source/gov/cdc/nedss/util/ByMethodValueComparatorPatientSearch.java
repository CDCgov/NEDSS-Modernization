/**
 * Title: ByMethodValueComparator implements Comparator interface.
 * Description: Implementation of the Comparator interface which orders objects
 * by the value returned by invoking the method set in setComparatorMethod on
 * the two object args sent to .
 * Note: this comparator imposes orderings that are inconsistent with equals.
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author sdesai
 * @version 1.0
 */
package gov.cdc.nedss.util;

import java.util.*;
import java.lang.reflect.*;


public class ByMethodValueComparatorPatientSearch implements Comparator{

   //For logging
   static final LogUtils logger = new LogUtils(ByMethodValueComparatorPatientSearch.class.getName());

   Class<?> clazz;
   Method valueGetter;
   boolean ascending = true;

   public ByMethodValueComparatorPatientSearch(Class<?> c){
      clazz = c;
   }
  //sets sorting to descenting
   public void descending(){
      ascending = false;
   }
   //sets sorting to ascending
   public void ascending(){
      ascending = true;
   }
   /*if called ... descending ("true"), then sets sorting to descending*/
   public void descending(String direction){
      if(direction != null && direction.trim().equalsIgnoreCase("true")){
         ascending = false;
         logger.debug("descending = true");
      }
      else{
         ascending = true;
      }
   }
   /*if called ... ascending ("true"), then sets sorting to ascending*/
   public void ascending(String direction){
      if(direction != null && direction.trim().equalsIgnoreCase("true")){
         ascending = true;
      }
      else{
         ascending = false;
      }
   }
   /*if given true, sets to ascending; if false, sets to descending*/
   public void ascending(boolean directionflag){
      ascending = directionflag;
   }



   public void setValueGetterMethod(String getterMethodName) throws NoSuchMethodException{
      try{
         valueGetter = clazz.getMethod(getterMethodName, (Class[])null);
      }
      catch(Exception e){
         logger.fatal("", e);
         throw new NoSuchMethodException(e.toString());
      }
   }

   /**
   * Compares two Objects by comparing the objects returned by the method set in setComparatorMethod().
   * Note: this comparator imposes orderings that are inconsistent with equals.
   */
   @SuppressWarnings("unchecked")
public int compare(Object o1, Object o2){

      if(valueGetter == null){
         return 0;
      }

      try{
         Object v1 = valueGetter.invoke(o1, (Object[])null);
         Object v2 = valueGetter.invoke(o2, (Object[])null);

         if(v1 == null && v2 == null ){
            return 0;
         }
         if(v1 !=null && v1.equals("") && v2 != null && v2.equals("") ){
             return 0;
          }

         if(v1 == null || "".equals(v1)){
                                //return ascending ? 1 : -1;
				//return 1;
            return ascending ? 1 : -1;   //original null is less
         }

         if(v2 == null || "".equals(v2)){
                               // return ascending ? -1 : 1;
				//return -1;
            return ascending ? -1 : 1;  //orig null is less
         }

         if(v1 instanceof Comparable){
            if(ascending){
               return ((Comparable)v1.toString().toUpperCase()).compareTo((Comparable)v2.toString().toUpperCase());
            }
            else{ // descending
               return ((Comparable)v2.toString().toUpperCase()).compareTo((Comparable)v1.toString().toUpperCase());
            }
         }
      }
      catch(Exception e){
         logger.fatal("", e);
      }

      return 0;
   }
   
}//
