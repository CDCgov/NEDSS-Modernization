

package gov.cdc.nedss.webapp.nbs.form.nbssecurity;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import org.apache.struts.action.ActionForm;

import gov.cdc.nedss.systemservice.nbssecurity.*;

public class PermissionSetForm extends ActionForm
{
    private Boolean loadedFromDB = new Boolean(false);

    private PermissionSet perSet = null;
    private PermissionSet oldSet = null;

    private byte[] users = new byte[BusinessObjOperationUtil.getBusinessObjOperationStoreSize()];
    private byte[] guests = new byte[BusinessObjOperationUtil.getBusinessObjOperationStoreSize()];


    public byte getUsers(int index)
    {


	return users[index];
    }

    public byte getGuests(int index)
    {
        return guests[index];
    }

    public void reset()
    {
      perSet = null;
    }

    public boolean hasPermissionSet()
    {
      if (perSet == null)
	return false;
      else
	return true;
    }

    public PermissionSet getPermissionSet()
    {
      if (perSet == null)
	perSet = new PermissionSet();

	return this.perSet;
    }
    public void setPermissionSet(PermissionSet newPerSet)
    {
	this.perSet = newPerSet;
    }

    public PermissionSet getOldPermissionSet()
    {
      if (oldSet == null)
	oldSet = new PermissionSet();

	return this.oldSet;
    }

    public void setOldPermissionSet(PermissionSet oldPerSet)
    {
	this.oldSet = oldPerSet;
    }

    public boolean isLoadedFromDB()
    {
      return (this.loadedFromDB.booleanValue());
    }

    public void setLoadedFromDB(boolean newBool)
    {
      this.loadedFromDB = new Boolean(newBool);
    }


}