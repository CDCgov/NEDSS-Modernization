package gov.cdc.nedss.act.notification.util;

import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;

import java.lang.reflect.*;

import java.sql.Timestamp;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Title: PopulateNotificationProxyVO
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author nedss project team
 * @version 1.0
 */

public class PopulateNotificationProxyVO {
    public PopulateNotificationProxyVO() {
    }

    /**
     *
     * @param request
     * @return NotificationProxyVO
     */
    public NotificationProxyVO notificationProxyVO(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        NotificationProxyVO npvo = new NotificationProxyVO();

        //NotificationVO nvo = new NotificationVO();
        Collection<Object> actRelationshipCollection = new ArrayList<Object> ();
        ActRelationshipDT actRelationshipDT = new ActRelationshipDT();

        //##!! System.out.println("in the PopulateNotificationPrxyVO");
        if (request.getContentLength() > 0) {

            long tempNotificationUID = -1;
            int seqNumber = 1;

            try {

                String notificationComments = request.getParameter("NTF137");
                String publicHealthCaseUID = request.getParameter("publicHealthCaseUID");

                //String personID = request.getParameter("personID");
                //##!! System.out.println(" comments: " + notificationComments);
                //##!! System.out.println(" publicHealthCaseUID: " + publicHealthCaseUID);
                //npvo.setNotificationUid(new Long(tempNotificationUID));
                //npvo.setTxt(notificationComments);
                npvo.setItNew(true);
                npvo.setItDirty(true);
                actRelationshipDT.setTargetActUid(new Long(publicHealthCaseUID));
                actRelationshipDT.setTargetActUid(new Long(tempNotificationUID));
                actRelationshipDT.setSourceActUid(new Long(publicHealthCaseUID));
                actRelationshipDT.setAddTime(new Timestamp((new Date()).getTime()));

                //this is commenter out because no method getUserId
                //actRelationshipDT.setAddUserId(new Long(nbsSecurityObj.getUserId()));
                actRelationshipDT.setRecordStatusCd("A");
                actRelationshipDT.setStatusTime(new Timestamp((new Date()).getTime()));
                actRelationshipDT.setSequenceNbr(new Integer(seqNumber));
                actRelationshipDT.setStatusCd("A");
                actRelationshipDT.setTypeCd("Notification");
                actRelationshipDT.setItNew(true);
                actRelationshipDT.setItDirty(true);
                actRelationshipCollection.add(actRelationshipDT);

                //npvo.setTheNotificationVO(nvo);
                npvo.setTheActRelationshipDTCollection(actRelationshipCollection);
                npvo.setItNew(true);
                npvo.setItDirty(true);

                return npvo;
            } catch (Exception e) {

                //##!! System.out.println("Error in PopulateNotificationVO = " + e);
                e.printStackTrace();
            }
        } else {

            //##!! System.out.println("Request is empty in PopulateNotificationVO");
        }

        return npvo;
    }
}