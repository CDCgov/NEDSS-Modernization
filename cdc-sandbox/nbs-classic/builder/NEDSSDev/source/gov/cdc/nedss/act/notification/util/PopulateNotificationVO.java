package gov.cdc.nedss.act.notification.util;

import gov.cdc.nedss.act.notification.dt.*;
import gov.cdc.nedss.act.notification.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.util.*;

import java.lang.reflect.*;

import java.sql.Timestamp;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Title: PopulateNotificationVO
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author nedss project team
 * @version 1.0
 */

public class PopulateNotificationVO {
    public PopulateNotificationVO() {
    }

    /**
     *
     * @param request
     * @param oldPVO
     * @return NotificationVO
     */
    public NotificationVO notificationVO(HttpServletRequest request, NotificationVO oldPVO) {

        NotificationVO nvo = new NotificationVO();
        NotificationDT ndt = new NotificationDT();
        Collection<Object> actRelationshipCollection  = new ArrayList<Object> ();
        ActRelationshipDT actRelationshipDT = new ActRelationshipDT();

        //##!! System.out.println("in the PopulateNotificationVO");
        if (request.getContentLength() > 0) {

            long tempNotificationUID = -1;
            int seqNumber = 1;

            try {

                String notificationComments = request.getParameter("NTF137");
                String publicHealthCaseUID = request.getParameter("publicHealthCaseUID");

                //String personID = request.getParameter("personID");
                //##!! System.out.println(" comments: " + notificationComments);
                //##!! System.out.println(" publicHealthCaseUID: " + publicHealthCaseUID);
                ndt.setNotificationUid(new Long(tempNotificationUID));
                ndt.setTxt(notificationComments);
                ndt.setItNew(true);
                ndt.setItDirty(false);

                //actRelationshipDT.setActUid(new Long(publicHealthCaseUID));
                actRelationshipDT.setTargetActUid(new Long(tempNotificationUID));
                actRelationshipDT.setSourceActUid(new Long(publicHealthCaseUID));
                actRelationshipDT.setAddTime(new Timestamp((new Date()).getTime()));

                //actRelationshipDT.setAddUserId(new Long(personID));
                actRelationshipDT.setRecordStatusCd("A");
                actRelationshipDT.setStatusTime(new Timestamp((new Date()).getTime()));
                actRelationshipDT.setSequenceNbr(new Integer(seqNumber));
                actRelationshipDT.setStatusCd("A");
                actRelationshipDT.setTypeCd("Notification");
                actRelationshipDT.setItNew(true);
                actRelationshipDT.setItDirty(true);
                actRelationshipCollection.add(actRelationshipDT);
                nvo.setTheNotificationDT(ndt);
                nvo.setTheActRelationshipDTCollection(actRelationshipCollection);
                nvo.setItNew(true);
                nvo.setItDirty(true);

                return nvo;
            } catch (Exception e) {

                //##!! System.out.println("Error in PopulateNotificationVO = " + e);
                e.printStackTrace();
            }
        } else {

            //##!! System.out.println("Request is empty in PopulateNotificationVO");
        }

        return nvo;
    }
}