package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt;
/**
 *
 * <p>Title: SRTTreatmentDrugDT</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Mark Hankey
 * @version 1.0
 */

import java.io.*;


public class SRTTreatmentDrugDT implements SRTCode,Comparable, Serializable{

	private String drugCd;
	private String drugDescTxt;



        public int compareTo(java.lang.Object o){
          if(!(o instanceof SRTTreatmentDrugDT))
            throw new ClassCastException();
          return this.drugDescTxt.compareTo(((SRTTreatmentDrugDT)o).drugDescTxt);
        }//end compareTo

	/**
	 * @return
	 */
	public String getDrugCd() {
		return drugCd;
	}

	/**
	 * @return
	 */
	public String getDrugDescTxt() {
		return drugDescTxt;
	}

        public Long getIndentLevel()
        {
          return null;
        }


	/**
	 * @param string
	 */
	public void setDrugCd(String string) {
		drugCd = string;
	}

	/**
	 * @param string
	 */
	public void setDrugDescTxt(String string) {
		drugDescTxt = string;
	}

        public String getCode(){
          return drugCd;
        }

        public String getDesc(){
          return drugDescTxt;
        }



}
