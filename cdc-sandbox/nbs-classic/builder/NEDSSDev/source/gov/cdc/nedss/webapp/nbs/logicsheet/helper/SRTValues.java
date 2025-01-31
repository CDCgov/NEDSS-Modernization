package gov.cdc.nedss.webapp.nbs.logicsheet.helper;

/**
 * Title:
 * Description: gets the coded values from the srt table and converts it into an XML fragment for use in XSP page
 * Copyright:    Copyright (c) 2001
 * Company: CSC
 * @author: Nedss Development Team
 * @version 1.0
 */

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.rmi.*;

//import gov.cdc.nedss.systemservice.ejb.resultsetejb.bean.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.*;



public class SRTValues {

   private NEDSSConstants Constants = new NEDSSConstants();
   private PropertyUtil propertyUtil = PropertyUtil.getInstance();
   SRTMap srtm = null;

  public SRTValues() {
}
    private SRTMap getSRTMapEJBRef()
    {

        if(srtm == null)
        {
          NedssUtils nu = new NedssUtils();
	try
	{

	    Object objref = nu.lookupBean(JNDINames.SRT_CACHE_EJB);

	    //logger.debug("objref " + objref);
	    SRTMapHome home = (SRTMapHome)PortableRemoteObject.narrow(objref,SRTMapHome.class);
	    srtm = home.create();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
        }
        return srtm;
    }


  public  TreeMap<Object,Object> getCodedValues(String type) {
    TreeMap<Object,Object>  treeMap = null;
            StringBuffer sHTML = new StringBuffer();
            try{
                  treeMap = getSRTMapEJBRef().getCodedValues(type);
            }
            catch (Exception e) {
              e.printStackTrace();
            }
	    return treeMap;
    }

    public TreeMap<Object,Object> getStateCodes1(String type) {
            TreeMap<Object,Object>  treeMap = null;
            StringBuffer sHTML = new StringBuffer();
            //sHTML += "<option><name></name><value></value></option>";
            try{
                  treeMap = getSRTMapEJBRef().getStateCodes1(type);
            }
            catch (Exception e) {
              e.printStackTrace();
            }
	    return treeMap;
    }

     //method to return County codes
     public TreeMap<Object,Object> getCountyCodes(String StateCode) { //only single state in beta release
            TreeMap<Object,Object>  treeMap = null;
            StringBuffer sHTML = new StringBuffer();
            //sHTML += "<option><name></name><value></value></option>";
            try{
                  treeMap = getSRTMapEJBRef().getCountyCodes(StateCode);
            }
            catch (Exception e) {
              e.printStackTrace();
            }
	    return treeMap;
    }
     //method to return all County codes for all states
     public TreeMap<Object,Object> getCountyCodes() {
            TreeMap<Object,Object>  treeMap = null;
            StringBuffer sHTML = new StringBuffer();
            //sHTML += "<option><name></name><value></value></option>";
            try{
                  treeMap = getSRTMapEJBRef().getCountyCodes();
            }
            catch (Exception e) {
              e.printStackTrace();
            }
	    return treeMap;
    }

    //method to get Region List
     public TreeMap<Object,Object> getRegionCodes() {
            TreeMap<Object,Object>  treeMap = null;
            StringBuffer sHTML = new StringBuffer();
            try{
                  treeMap = getSRTMapEJBRef().getRegionCodes();
            } catch (Exception e) {
              e.printStackTrace();
            }
	    return treeMap;
    }

  private void closeContext(Context initContext)  {
  try {
  if (initContext != null)

       initContext.close();
  }

  catch(NamingException ne) {

   ne.printStackTrace();


  }

  }

  public TreeMap<Object,Object> getProgramAreaConditions(String programAreas)
  {
     TreeMap<Object,Object> programAreaTreeMap = null;
     try
     {
         programAreaTreeMap = getSRTMapEJBRef().getProgramAreaConditions(programAreas);
      }
      catch(Exception e)
      {

         e.printStackTrace();
      }
      return   programAreaTreeMap;
  }

  public TreeMap<Object,Object> getProgramAreaConditions(String programAreas, int indentLevelNbr)
  {
     TreeMap<Object,Object> programAreaTreeMap = null;
     try
     {
         programAreaTreeMap = getSRTMapEJBRef().getProgramAreaConditions(programAreas, indentLevelNbr);
      }
      catch(Exception e)
      {

         e.printStackTrace();
      }
      return   programAreaTreeMap;
  }
  public static void main(String[] args) {
  }
}