/*
 * Created on Jan 27, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.systemservice.util;

/**
 * @author xzheng
 *
 * The validation result.
 */
public class ValidationResult {

	private boolean valid;
	private String message;

	public ValidationResult(boolean valid, String message){
		this.valid = valid;
                String processedMessage = processMessage(message);
		this.message = processedMessage;
	}

	/** The validation message.
	 * @return The validation message.
	 */
	public String getMessage() {
		return message;
	}

	/** The validation status.
	 * @return The validation status.
	 */
	public boolean isValid() {
		return valid;
	}

        /**
         * The method add a space after the separators, such as ",", ";"
         */

        private String processMessage(String message)
        {
          StringBuffer sb = new StringBuffer();
          if(message != null){
            for(int i = 0; i<message.length(); i++)
            {
              char oneChar = message.charAt(i);
              if(oneChar == ',' || oneChar == ';' || oneChar == '.'){
                sb.append(oneChar);
                if(i != (message.length()-1) && message.charAt(i+1)!=' '){
                  sb.append(' ');
                }
              }else{
                sb.append(oneChar);
              }
            }
            return sb.toString();
          }else{
            return null;
          }

        }
/*
        public static void main(String[] args) {
          String testMessage = "ssss,ddd, ddd,ggg,  ff;ff.f;ff;";
          ValidationResult v = new ValidationResult(true, testMessage);
         System.out.println(v.getMessage());

        }
*/
}
