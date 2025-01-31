/*
 * Created on Dec 11, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt;

import java.io.*;

/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SRTTreatmentDT implements SRTCode,Comparable, Serializable{
	private String treatmentCd;
	private String treatmentDescTxt;


        public int compareTo(java.lang.Object o){
          if(!(o instanceof SRTTreatmentDT))
            throw new ClassCastException();
          return this.treatmentDescTxt.compareTo(((SRTTreatmentDT)o).treatmentDescTxt);
        }//end compareTo

	/**
	 * @return
	 */
	public String getTreatmentCd() {
		return treatmentCd;
	}

        public Long getIndentLevel()
        {
          return null;
        }

	/**
	 * @return
	 */
	public String getTreatmentDescTxt() {
		return treatmentDescTxt;
	}

	/**
	 * @param string
	 */
	public void setTreatmentCd(String string) {
		treatmentCd = string;
	}

	/**
	 * @param string
	 */
	public void setTreatmentDescTxt(String string) {
		treatmentDescTxt = string;
	}
        public String getCode(){
          return treatmentCd;
        }
        public String getDesc(){
        return treatmentDescTxt;
        }

}
