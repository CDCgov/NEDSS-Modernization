package gov.cdc.nedss.act.ctcontact.dt;

import java.lang.reflect.Field;

/**
 * Title:CTContactHistDT 
 * Description: Copyright: Copyright (c) 2009
 * Company:CSC
 * @author: NBS project team
 * @version 3.1 ContactTracing
 */
public class CTContactHistDT extends CTContactDT {
	private static final long serialVersionUID = 1L;
	private Long ctContactHistUid;
	
	public void setCtContactHistUid(Long ctContactHistUid) {
		this.ctContactHistUid = ctContactHistUid;
	}

	public Long getCtContactHistUid() {
		return ctContactHistUid;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = CTContactHistDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}



}