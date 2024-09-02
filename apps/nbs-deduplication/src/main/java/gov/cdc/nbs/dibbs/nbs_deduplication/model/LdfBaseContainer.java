package gov.cdc.nbs.dibbs.nbs_deduplication.model;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
public class LdfBaseContainer extends BaseContainer {
    private static final long serialVersionUID = 1L;
    private Collection<Object> ldfUids;

    public Collection<Object> getTheStateDefinedFieldDataDTCollection() {
        return ldfs;
    }

    /* Read all input ldfs. Descard one with no value entered by user */
    public void setTheStateDefinedFieldDataDTCollection(List<StateDefinedFieldDataDto> newLdfs) {
        if(newLdfs != null && newLdfs.size() > 0){
            ldfs = new ArrayList<Object> ();
            ldfUids = new ArrayList<Object>();
            Iterator<StateDefinedFieldDataDto> itr = newLdfs.iterator();
            while (itr.hasNext()) {
                StateDefinedFieldDataDto dt = (StateDefinedFieldDataDto) itr.next();
                ldfUids.add(dt.getLdfUid());
                if (dt != null && dt.getLdfValue() != null
                        && dt.getLdfValue().trim().length() != 0) {
                    ldfs.add(dt);
                }
            }
        }
    }
}
