//Source file: C:\\Development\\Source\\gov\\cdc\\nedss\\helpers\\AbstractVO.java

package gov.cdc.nedss.util;
import gov.cdc.nedss.ldf.dt.*;

import java.io.Serializable;
import java.lang.Cloneable;
import java.util.*;

public abstract class AbstractVO implements DirtyMarkerInterface, Serializable, Cloneable
{

   /**
   @roseuid 3BB8D63F0365
    */
   public AbstractVO()
   {

   }
   private static final long serialVersionUID = 1L;
   protected boolean itNew;
   protected boolean itOld;
   protected boolean itDirty;
   protected boolean itDelete;
   protected Collection<Object> ldfs;
   /**
   @param objectname1
   @param objectname2
   @param voClass
   @return boolean
   @roseuid 3BB8B67D021A
    */
   public abstract boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, java.lang.Class<?> voClass);

}
