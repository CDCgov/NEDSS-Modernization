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
    private transient Collection<Object> ldfUids;

    public Collection<Object> getTheStateDefinedFieldDataDTCollection() {
        return ldfs;
    }

    /* Read all input ldfs. Descard one with no value entered by user */
    public void setTheStateDefinedFieldDataDTCollection(List<StateDefinedFieldDataDto> newLdfs) {
        if (newLdfs != null && !newLdfs.isEmpty()) {
            ldfs = new ArrayList<>();
            ldfUids = new ArrayList<>();
            Iterator<StateDefinedFieldDataDto> itr = newLdfs.iterator();
            while (itr.hasNext()) {
                StateDefinedFieldDataDto dt = itr.next();
                ldfUids.add(dt.getLdfUid());
                if (dt.getLdfValue() != null && !dt.getLdfValue().trim().isEmpty()) {  // Simplified boolean expression
                    ldfs.add(dt);
                }
            }
        }
    }
}
