package gov.cdc.nedss.ldf.vo;

import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.ldf.dt.*;
import java.util.*;

public class LdfBaseVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private Collection<Object> ldfUids;
  public Collection<Object> getTheStateDefinedFieldDataDTCollection() {
    return ldfs;
  }

  /* Read all input ldfs. Descard one with no value entered by user */
  public void setTheStateDefinedFieldDataDTCollection(Collection<Object> newLdfs) {
    if(newLdfs != null && newLdfs.size() > 0){
      ldfs = new ArrayList<Object> ();
      ldfUids = new ArrayList<Object> ();
      Iterator<Object> itr = newLdfs.iterator();
      while (itr.hasNext()) {
        StateDefinedFieldDataDT dt = (StateDefinedFieldDataDT) itr.next();
        ldfUids.add(dt.getLdfUid());
        if (dt != null && dt.getLdfValue() != null
          && dt.getLdfValue().trim().length() != 0) {
        ldfs.add(dt);
      }
    }
  }
  }

  public Collection<Object> getLdfUids() {
    return ldfUids;
  }
      /**
       *
       * @param aItDirty
       */
      public void setItDirty(boolean aItDirty) {
          itDirty = aItDirty;
      }

      /**
       *
       * @return boolean
       */
      public boolean isItDirty() {

          return itDirty;
      }

      /**
       *
       * @param aItNew
       */
      public void setItNew(boolean aItNew) {
          itNew = aItNew;
      }

      /**
       *
       * @return boolean
       */
      public boolean isItNew() {

          return itNew;
      }

      /**
       *
       * @return boolean
       */
      public boolean isItDelete() {

          return itDelete;
      }

      /**
       *
       * @param aItDelete
       */
      public void setItDelete(boolean aItDelete) {
          itDelete = aItDelete;
      }

      /**
 *
 * @param objectname1
 * @param objectname2
 * @param voClass
 * @return boolean
 */
public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {

    return true;
}

}