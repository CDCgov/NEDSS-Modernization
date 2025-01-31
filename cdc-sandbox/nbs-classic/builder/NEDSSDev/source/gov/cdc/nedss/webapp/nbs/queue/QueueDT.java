package gov.cdc.nedss.webapp.nbs.queue;

import java.lang.reflect.Method;

public class QueueDT {
	private QueueColumnDT column1;
	private QueueColumnDT column2;
	private QueueColumnDT column3;
	private QueueColumnDT column4;
	private QueueColumnDT column5;
	private QueueColumnDT column6;
	private QueueColumnDT column7;
	private QueueColumnDT column8;
	private QueueColumnDT column9;
	private QueueColumnDT columnHidden1;
	private int NUM_COLUMNS_IMPLEMENTED=9;//This number is the total of columns implemented (not hidden) on this class, not the total number of columns with values.
	private int NUM_COLUMNS_IMPLEMENTED_HIDDEN=1;
	
	public QueueColumnDT getColumn1() {
		return column1;
	}
	public void setColumn1(QueueColumnDT column1) {
		this.column1 = column1;
	}
	public QueueColumnDT getColumn2() {
		return column2;
	}
	public void setColumn2(QueueColumnDT column2) {
		this.column2 = column2;
	}
	public QueueColumnDT getColumn3() {
		return column3;
	}
	public void setColumn3(QueueColumnDT column3) {
		this.column3 = column3;
	}
	public QueueColumnDT getColumn4() {
		return column4;
	}
	public void setColumn4(QueueColumnDT column4) {
		this.column4 = column4;
	}
	public QueueColumnDT getColumn5() {
		return column5;
	}
	public void setColumn5(QueueColumnDT column5) {
		this.column5 = column5;
	}
	public QueueColumnDT getColumn6() {
		return column6;
	}
	public void setColumn6(QueueColumnDT column6) {
		this.column6 = column6;
	}
	public QueueColumnDT getColumn7() {
		return column7;
	}
	public void setColumn7(QueueColumnDT column7) {
		this.column7 = column7;
	}
	public QueueColumnDT getColumn8() {
		return column8;
	}
	public void setColumn8(QueueColumnDT column8) {
		this.column8 = column8;
	}
	public QueueColumnDT getColumn9() {
		return column9;
	}
	public void setColumn9(QueueColumnDT column9) {
		this.column9 = column9;
	}
	public QueueColumnDT getColumnHidden1() {
		return columnHidden1;
	}
	public void setColumnHidden1(QueueColumnDT columnHidden1) {
		this.columnHidden1 = columnHidden1;
	}
	
	/**
	 * getColumn(): method for getting the column number numColumn
	 * @param numColumn
	 * @return
	 */
	
	public QueueColumnDT getColumn(int numColumn){
		
		String method ="getColumn";
    	QueueColumnDT column = null;
    	
    	Class tClass = this.getClass();
    	
		try{
			method+=numColumn;
			Method gs1Method = tClass.getMethod(method, new Class[] {});
			column = (QueueColumnDT) gs1Method.invoke(this, new Object[] {});
		}catch(Exception e){
			
		//TODO
		}
		
		return column;
	}
	
	/**
	 * getColumnHidden(): method for getting the hidden column number numColumn
	 * @param numColumn
	 * @return
	 */
	
	public QueueColumnDT getColumnHidden(int numColumn){
			
			String method ="getColumnHidden";
	    	QueueColumnDT column = null;
	    	
	    	Class tClass = this.getClass();
	    	
			try{
				method+=numColumn;
				Method gs1Method = tClass.getMethod(method, new Class[] {});
				column = (QueueColumnDT) gs1Method.invoke(this, new Object[] {});
			}catch(Exception e){
				
			//TODO
			}
			
			return column;
		}
	
	/**
	 * getQueueDTSize(): return the number of visible columns
	 * @return
	 */

	public int getQueueDTSize(){
		
		int size=0;
		
		for(int i=1;i<=NUM_COLUMNS_IMPLEMENTED; i++){
			
			QueueColumnDT column = getColumn(i);
			
			if(column!=null && column.getColumnName()!=null && !column.getColumnId().equalsIgnoreCase(""))
				size++;
		}
		
		return size;
	}
	
	/**
	 * getQueueDTSize(): return the number of hidden columns
	 * @return
	 */
	
	public int getQueueDTHiddenSize(){
		
		int size=0;
		
		for(int i=1;i<=NUM_COLUMNS_IMPLEMENTED_HIDDEN; i++){
			
			QueueColumnDT column = getColumnHidden(i);
			
			if(column!=null && column.getColumnName()!=null && !column.getColumnId().equalsIgnoreCase(""))
				size++;
		}
		
		return size;
	}
	
	
}
