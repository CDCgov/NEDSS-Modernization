package gov.cdc.nedss.webapp.nbs.action.summary.util;

import java.util.Collection;

/**
 * CategoryTable is a representation of the Header, Row & Metadata information about the Summary relevant Tables.
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * CategoryTable.java
 * Aug 6, 2009
 * @version
 */
public class CategoryTable {

	private Long nbsTableUid;
	private String tableName;//can be used to display subSection
	private Collection<Object>  headers;
	private Collection<Object>  records;
	
	public Long getNbsTableUid() {
		return nbsTableUid;
	}
	public void setNbsTableUid(Long nbsTableUid) {
		this.nbsTableUid = nbsTableUid;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Collection<Object>  getHeaders() {
		return headers;
	}
	public void setHeaders(Collection<Object>  headers) {
		this.headers = headers;
	}
	public Collection<Object>  getRecords() {
		return records;
	}
	public void setRecords(Collection<Object>  records) {
		this.records = records;
	}
	
}
