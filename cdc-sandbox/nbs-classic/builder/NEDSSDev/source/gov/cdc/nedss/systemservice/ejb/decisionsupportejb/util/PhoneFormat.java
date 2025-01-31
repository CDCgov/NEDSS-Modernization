package gov.cdc.nedss.systemservice.ejb.decisionsupportejb.util;

public class PhoneFormat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub]]
		
		String temp1 = "123-4567899";
		String temp2 = "1234567899";
		String temp3 = "123-4567899";
		String temp4 = "12345678";
		String temp = "123-4567899";
		
		temp = temp.trim();
		//temp .format("%s-%s-%s", temp .substring(0, 3), temp.substring(4, 7), 
	    //		temp .substring(7, 11));
		System.out.println(temp .substring(0, 7)+"-"+temp.substring(7, 11));
		//System.out.println(temp);

	}

}

