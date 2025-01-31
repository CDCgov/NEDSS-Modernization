//Source file: C:\\BLDC_Dev_Development\\development\\source\\gov\\cdc\\nedss\\nbssecurity\\helpers\\BusinessObjOperationUtil.java

package gov.cdc.nedss.systemservice.nbssecurity;

import java.util.*;

/**
 * The businessObjOperationUtil provides the support methods necessary for storing
 * business object operation information in a compressed fashion.  There should
 * only be one of these created at system startup and used by all.  All the
 * methods of this class are static.  There should never be a need to create an
 * instance of this class, but it is important that the initialize method be
 * called at system startup.
 */
public class BusinessObjOperationUtil
{

   /**
    * Indicates that the operation is available to the user
    */
   public static final byte OPERATION_AVAILABLE = 1;

   /**
    * Indicates that the operation is not available to the user.
    */
   public static final byte OPERATION_UNAVAILABLE = 0;

   /**
    * Indicates that the operation is available to a user who is a guest.
    */
   public static final byte OPERATION_GUEST = 2;

   /**
    * Indicates that the operation is available to the user and guest
    */
   public static final byte OPERATION_AVAILABLE_ALL = 3;

   /**
    * Returns the number of business object types available based on the list of
    * business object lookup names.
    *
    * @return int
    * @roseuid 3CDB39520207
    */
   public static int getBusinessObjectCount()
   {
    return NBSBOLookup.getBusinessObjCount();
   }

   /**
    * Returns the number of business object operations available based on the list of
    * operation names.
    *
    * @return int
    * @roseuid 3CDB3B880337
    */
   public static int getOperationCount()
   {
    return NBSOperationLookup.getOperationCount();
   }

   /**
    * Returns the coded index for the given business object operation.
    *
    * @param businessObjectLookupName
    * @param operation
    * @return int
    * @roseuid 3CDB3BE6017A
    */
   public static int getBusinesObjOperationIndex(String businessObjectLookupName, String operation)
   {
      int index = 0;

      index = (NBSBOLookup.getBOIndex(businessObjectLookupName) * NBSOperationLookup.getOperationCount()) + NBSOperationLookup.getOperationIndex(operation);

      return index;
   }

   /**
    * Returns the corresponding business object name.  This can be obtained by using
    * the result of an integer division operation of the index with the operation
    * count ( index / operationCount).  The resulting value is used to look up the
    * name in the businessObjectMap.
    *
    * @param index
    * @return String
    * @roseuid 3CDB3DFA011A
    */
   public static String getBusinessObjNameFromIndex(int index)
   {
    return NBSBOLookup.getBOLookupName(index / NBSOperationLookup.getOperationCount());
   }

   /**
    * Returns the corresponding operation name.  This can be obtained by using
    * the result of a modulus operation of the index with
    * the operation count ( index % operationCount).  The resulting value is used to
    * look up the name in the
    * operationMap.
    *
    * @param index
    * @return String
    * @roseuid 3CDB41980311
    */
   public static String getOperationNameFromIndex(int index)
   {
    return NBSOperationLookup.getOperationLookupName(index % NBSOperationLookup.getOperationCount());
   }

   /**
    * Returns the number of array elements necessary to store a complete
    * array of business object operations.
    *
    * @return int
    * @roseuid 3CDC0C71037F
    */
   public static int getBusinessObjOperationStoreSize()
   {
    return (getBusinessObjectCount())*(getOperationCount());
   }

}
