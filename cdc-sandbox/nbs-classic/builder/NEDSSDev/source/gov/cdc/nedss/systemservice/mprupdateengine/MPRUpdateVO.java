package gov.cdc.nedss.systemservice.mprupdateengine;

import java.util.Collection;
import gov.cdc.nedss.entity.person.vo.PersonVO;
/**
 * <p>Title: MPRUpdateVO</p>
 * <p>Description: This class defines a value object implementing the
 * MPRUpdateObjectStructure interface. </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender
 * @version 1.0
 */

public class MPRUpdateVO implements MPRUpdateObjectStructure
{
   private PersonVO mpr = null;
   private Collection<Object>  personVOs = null;

   /**
   This is the constructor for the class.
    */
   public MPRUpdateVO(PersonVO mpr, Collection<Object>  personVOs)
   {
    this.mpr = mpr;
    this.personVOs = personVOs;
   }
   /**
    * This method returns an object representing the MPR that an MPR update
    * handler wants to update.
    * @return PersonVO
    */

   public PersonVO getMpr()
   {
	return mpr;
   }

   /**
   This method returns the collection that contains the PersonVO objects that an
   MPR update handler uses to update the MPR with.
    */
   public Collection<Object>  getPersonVOs()
   {
	return personVOs;
   }
}
