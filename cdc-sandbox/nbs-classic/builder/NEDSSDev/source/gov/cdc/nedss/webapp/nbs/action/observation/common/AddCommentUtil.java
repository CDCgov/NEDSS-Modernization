package gov.cdc.nedss.webapp.nbs.action.observation.common;
import java.util.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import javax.servlet.http.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.webapp.nbs.form.observation.*;
import java.text.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class AddCommentUtil {
  public AddCommentUtil() {
  }

  public void  setAddUserComments(AbstractVO proxy, HttpServletRequest request, NBSSecurityObj nbsObj){
   Collection<ObservationVO>  obsColl = null;
    if (proxy instanceof LabResultProxyVO)
  {
    obsColl = ( (LabResultProxyVO) proxy).getTheObservationVOCollection();
  }
  if (proxy instanceof MorbidityProxyVO)
  {
    obsColl = ( (MorbidityProxyVO) proxy).getTheObservationVOCollection();
  }

  Collection<Object>  addCommColl = new ArrayList<Object> ();

  StringBuffer str = new StringBuffer();
 Iterator<ObservationVO>  obsCollIter1 = obsColl.iterator();
      while(obsCollIter1.hasNext()){
        ObservationVO obVO = (ObservationVO)obsCollIter1.next();
        if((obVO.getTheObservationDT().getCd()!= null)&&((obVO.getTheObservationDT().getCd().equals("LAB214"))||(obVO.getTheObservationDT().getCd().equals("MRB180"))) ){
         String comment = "";
         String addUserId = null;
         Long addUserIdTemp = obVO.getTheObservationDT().getAddUserId();
         if(obVO.getTheObservationDT().getAddUserName() == null)
           addUserId = "Unknown User";
         else
           addUserId = obVO.getTheObservationDT().getAddUserName();

         String addUserTime = null;
         if(obVO.getTheObservationDT().getActivityToTime() == null)
           addUserTime = formatDate(new java.sql.Timestamp(new Date().getTime()));
         else
           addUserTime = formatDate(obVO.getTheObservationDT().getActivityToTime());

         String valueTxt = null;
         Collection<Object>  obsValueTxtColl = obVO.getTheObsValueTxtDTCollection();
        Iterator<Object>  obsIter = obsValueTxtColl.iterator();
         while(obsIter.hasNext()){
         ObsValueTxtDT ovtDT = (ObsValueTxtDT) obsIter.next();
         valueTxt = ovtDT.getValueTxt()!=null ? ovtDT.getValueTxt() : "";
         comment = comment+ valueTxt.trim();
         }

         if(comment.trim().length()>0){
         str.append(addUserTime).append("$");
         str.append(addUserId).append("$");
         str.append(comment).append("$");
         str.append("|");
       }


        }
      }
      request.setAttribute("AddUserComments",str.toString());

}

    protected String formatDate(java.sql.Timestamp timestamp) {
       Date date = null;
       SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
       if (timestamp != null) {
         date = new Date(timestamp.getTime());
       }
       if (date == null) {
         return "";
       }
       else {
         return formatter.format(date);
       }
     }

}
