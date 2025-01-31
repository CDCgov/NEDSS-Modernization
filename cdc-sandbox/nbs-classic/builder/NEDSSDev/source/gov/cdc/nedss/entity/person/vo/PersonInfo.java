/**
* Name:		PersonInfo.java
* Description:	This abstract class provides a list of objects returned to the web tier
*               in response to a search request.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Shailender Rachamalla & NEDSS Development Team
* @version	1.0
*/


package gov.cdc.nedss.entity.person.vo;


import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;


public class PersonInfo implements Serializable
{

    private Long personUID;
    //private String sPersonUID;
    private String localID;
    private String personName;
    private String firstName;
    private String lastName;
    private Timestamp DOB;
    private String currentSex;

    public PersonInfo()
    {
    }

    public PersonInfo(Long personUID, String localID, String lastName, String firstName,  Timestamp DOB, String currentSex)
    {
        this.personUID = personUID;
        this.localID = localID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.DOB = DOB;
        this.currentSex = currentSex;
    }

    public Long getPersonUID()
    {
        return personUID;
    }

    public String getStrPersonUID()
    {
        if(personUID != null)
            return personUID.toString();
        else
            return null;
    }

    public String getLocalID()
    {
        return localID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getDOB()
    {
        return formatDate(DOB);
    }

    public String getCurrentSex()
    {
        return currentSex;
    }

    public String getPersonName()
    {
        return lastName + " " + firstName;
    }

    private String formatDate(java.sql.Timestamp timestamp)
    {
	Date date = null;
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	if(timestamp != null) date = new Date(timestamp.getTime());
	if(date == null)
	return "";
	else
	return 	formatter.format(date);
    }
}//end of ProgramAreaVO class
