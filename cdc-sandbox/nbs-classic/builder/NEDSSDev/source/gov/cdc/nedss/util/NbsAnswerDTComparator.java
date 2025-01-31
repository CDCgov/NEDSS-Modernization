package gov.cdc.nedss.util;

import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;

import java.util.Comparator;

public class NbsAnswerDTComparator implements Comparator<NbsCaseAnswerDT> {

	@Override
	public int compare(NbsCaseAnswerDT arg0, NbsCaseAnswerDT arg1) {
		if(arg0.getNbsCaseAnswerUid() != null && arg1.getNbsCaseAnswerUid() != null){
			return arg0.getNbsCaseAnswerUid().compareTo(arg1.getNbsCaseAnswerUid());
		}
		return 1;
	}

}
