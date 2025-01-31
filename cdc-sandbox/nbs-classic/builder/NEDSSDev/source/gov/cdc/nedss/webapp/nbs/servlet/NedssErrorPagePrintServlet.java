package gov.cdc.nedss.webapp.nbs.servlet;

import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContextSystemInfo;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.justformspdf.pdf.Form;
import com.justformspdf.pdf.FormCheckbox;
import com.justformspdf.pdf.FormElement;
import com.justformspdf.pdf.FormText;
import com.justformspdf.pdf.PDF;
import com.justformspdf.pdf.PDFReader;

public class NedssErrorPagePrintServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final static String pdfFileName = "NBSErrorPage.pdf";
	public static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	public static final String nedssDirectory = new StringBuffer(System.getProperty("nbs.dir")).append(File.separator).toString().intern();
	public static final String propertiesDirectory = new StringBuffer(nedssDirectory).append("Properties").append(File.separator).toString().intern();
	public static final String licKey = "43B78HB6";
	static final LogUtils logger = new LogUtils(NedssErrorPagePrintServlet.class.getName());
	private static final String LINE_BREAK = "\\r";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		printForm(request, response);		
	}
	
	
	private void printForm(HttpServletRequest req, HttpServletResponse res) {
		
		FileInputStream fis = null;
		
		try {			
			fis = new FileInputStream(propertiesDirectory + pdfFileName);
			PDFReader reader = new PDFReader(fis);
			PDF pdf = new PDF(reader);
			pdf.lk(licKey);
			Form pdfForm = pdf.getForm();
			setReadOnly(pdfForm);	
			setErrorPageFields(pdfForm, req, res);
			//render
			pdf.render();
			res.setContentType("application/pdf");
			res.addHeader("Content-disposition", "attachment; filename=NBSErrorPage.pdf");
			pdf.writeTo(res.getOutputStream());	
			
			
		} catch (Exception e) {
			logger.error(e.toString());
			req.getSession().setAttribute("error", "<b>There is an error while printing the Error Page: </b> <br/><br/>");				
			try {
				res.sendRedirect("/nbs/error");
			} catch (IOException e1) {
				logger.error("Error while redirecting Error Print Page: " + e1.toString());
				e1.printStackTrace();
			}
		} finally {		
			try {
				if(fis != null) 
					fis.close();
			} catch (IOException e2) {
				logger.error("Error while closing FileInputStream for Error Print : " + e2.toString());
			}
		}
	}	
	
	private void answerPlainTxt(String id, String value, Form pdfForm) {

		   if(value != null && value.trim().length() > 0) {
				try {
					FormText txtField = (FormText) pdfForm.getElement(id);			
					txtField.setValue(value);
				} catch (Exception e) {
					logger.error("PDF Form Element: " + id + " not found in answerPlainTxt() : ");
				}
		   }
		}	
	private void setReadOnly(Form pForm) throws Exception {
		Vector formElementList = pForm.getAllFormElements();	
		 for (int i=0; i<formElementList.size(); i++) {
		    FormElement element = (FormElement) formElementList.elementAt(i);
		    if (element instanceof FormText) {
		    	element.setValue("");
		    	element.setReadOnly(true);
		    }
		 }
	}	
	
	private void setErrorPageFields(Form pdfForm, HttpServletRequest req, HttpServletResponse res) {
		//URL
		String urlVal = req.getSession().getAttribute("errorUrl") == null ? "" : (String) req.getSession().getAttribute("errorUrl");
		answerPlainTxt("url", urlVal, pdfForm);
		//DateTime
		Date lastAccessed = new Date(req.getSession().getLastAccessedTime());
		answerPlainTxt("dateTime", lastAccessed.toString(), pdfForm);
		//Error
		String errorVal = req.getSession().getAttribute("errorPageException") == null ? "" : (String) req.getSession().getAttribute("errorPageException");
		errorVal = removeCharacters(errorVal);
		answerPlainTxt("error", errorVal, pdfForm);
		//answerTxtArea("error", errorVal, 84, pdfForm);		
	}

	private void answerTxtArea(String id, String value, int charsPerLine, Form pdfForm) {
		   
		   if(value != null && value.trim().length() > 0) {
			   //value = value.replaceAll("\r\n\t"," ").replaceAll("\r\n","");
			   int lines = Math.round( value.length() / (float)charsPerLine);
			   lines = 24;
			   if(lines == 0) lines = 1;
			   StringBuffer sb = new StringBuffer();
			   int start = 0;
			   for(int i=0; i<lines; i++) {
					if(i== (lines-1)) {
						if(value.length() > (start+charsPerLine) )
							sb.append(value.substring(start,(start+charsPerLine)-3)).append("...");
						else
							sb.append(value.substring(start,value.length()));
					} else {
						sb.append(value.substring(start,start+charsPerLine)).append(LINE_BREAK);	
					}
					start = start + charsPerLine;
				}
			   	
				try {
					FormText txtField = (FormText) pdfForm.getElement(id);			
					txtField.setValue(sb.toString());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error while answering Text Areas in Print Error: " + e.toString());
				}
		   }
		   
	   }
	
	private String removeCharacters(String text) {
		text = text.replaceAll("\r\n\t"," ").replaceAll("\r\n","");
		String charsToKeep = ".0123456789abcdefghijklmnopqrstuvwxyz/();: ";
        StringBuffer buffer = new StringBuffer();   
        for(int i = 0; i < text.length(); i++) {   
            char ch = text.charAt(i);
            char chLower = text.toLowerCase().charAt(i);
            if(charsToKeep.indexOf(chLower) > -1) {   
                buffer.append(ch);   
            }   
        }   
        return buffer.toString();   
    }   
	
	
}
