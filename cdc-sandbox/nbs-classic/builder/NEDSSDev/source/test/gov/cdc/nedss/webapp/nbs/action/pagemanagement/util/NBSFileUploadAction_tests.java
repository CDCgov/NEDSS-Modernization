package test.gov.cdc.nedss.webapp.nbs.action.pagemanagement.util;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import static org.mockito.Matchers.any;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.act.observation.helper.ObservationProcessor;
import gov.cdc.nedss.entity.person.vo.PersonSearchResultTmp;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSAttachmentDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.NBSFileUploadAction;
import gov.cdc.nedss.webapp.nbs.form.util.FileUploadForm;

/**
 * Title:  NBSFileUploadAction_tests.java
 * Description: NBSFileUploadAction_tests is an Test Class, which validates methods in the corresponding java class NBSFileUploadAction.java. 
 * 				These Methods are being used in the attachments section of the View Investigation Page and while adding new contact Records from
 * 				the contact Tracing tab of the View Investigation. 
 * Company: GDIT
 * @since:October 9th, 2023.
 * @author Prabhakar.
 * @version 1.0
 * 
 */
@SuppressWarnings("unused")
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.util.FileUploadForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.NBSFileUploadAction","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(Enclosed.class)
@PrepareForTest({NBSFileUploadAction.class,PropertyUtil.class})
@PowerMockIgnore("javax.management.*")
public class NBSFileUploadAction_tests {
	
	 /**
	  * Title:  DoUpload_test.java
	  * Description: The DoUpload_test Inner Test Class validates doUpload method in the NBSFileUploadAction.java file.  
	  *              The Original Method(doUpload in NBSFileUploadAction.java) Uploads the selected attachment in the attachments section of the View Investigation Page (Supplemental Info Tab) to NBS
	  *              and display back to as a record in the attachment form.
	  *              The Method(doUpload_test) in this class, go through original method and validates each line with success and failure cases.
	  * @return Nothing.
	  * @exception NumberFormatException/IOException On input error.
	  * @see NumberFormatException/IOException
	  */
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.util.FileUploadForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.NBSFileUploadAction","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({NBSFileUploadAction.class,PropertyUtil.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	public static class DoUpload_test{
		private int maxFileSize;
		private int actualFileSize;
		private String healthCaseId;
		private String userName;
		private String fileName;
		private String fileDescription;
		private String attachment;
		private String forwardValue;
		private String expectedValue;
		private int iteration;
		
		
		public DoUpload_test(int maxFileSize, int actualFileSize, String healthCaseId, String userName, String fileName,
				String fileDescription, String attachment, String forwardValue, String expectedValue, int iteration) {
			super();
			this.maxFileSize = maxFileSize;
			this.actualFileSize = actualFileSize;
			this.healthCaseId = healthCaseId;
			this.userName = userName;
			this.fileName = fileName;
			this.fileDescription = fileDescription;
			this.attachment = attachment;
			this.forwardValue = forwardValue;
			this.expectedValue = expectedValue;
			this.iteration = iteration;
		}

		@Mock
		LogUtils logger;
		
		@Mock
		PropertyUtil propertyUtil;
		
		@Mock
		ActionForm form;
	
		@Mock
		FormFile file;
		
		@Mock
		HttpSession session;
		
		@Mock
		HttpServletResponse response;
		
		@Mock
		HttpServletRequest request;
		
		@Mock
		ActionMapping mapping;
		
	
		@Mock
		NBSSecurityObj secObj;
		
		@Mock
		UserProfile userProfile;
	
		@Mock
		User user;
		
		@Mock
		NBSAttachmentDT attachmetDt;
		
		@InjectMocks
		NBSFileUploadAction fileUploadAction;

		
		 @Before
		 public void initMocks() throws Exception {
			 MockitoAnnotations.initMocks(this);
			 form=Mockito.mock(FileUploadForm.class);
			 Whitebox.setInternalState(NBSFileUploadAction.class, "logger", logger);
		 }
		 
		@Test
		public void  doUpload_test() throws Exception{
			PowerMockito.mockStatic(PropertyUtil.class);
			PowerMockito.mockStatic(NBSFileUploadAction.class);
			fileUploadAction = PowerMockito.spy(new NBSFileUploadAction());
			//PowerMockito.doNothing().when(fileUploadAction,"_makeNewAttachmentRowSecurity",any(Object.class),any(Object.class));
			when(((FileUploadForm) form).getCtFile()).thenReturn(file);
			when(PropertyUtil.getInstance()).thenReturn(propertyUtil);
			when(propertyUtil.getMaxFileAttachmentSizeInMB()).thenReturn(this.maxFileSize);
			when(file.getFileSize()).thenReturn(this.actualFileSize);
			when(request.getSession()).thenReturn(session);
			when(session.getAttribute(any(String.class))).thenReturn(this.healthCaseId).thenReturn(secObj);
			when(secObj.getTheUserProfile()).thenReturn(userProfile);
			when(userProfile.getTheUser()).thenReturn(user);
			when(user.getEntryID()).thenReturn(this.userName);
			when(((FileUploadForm) form).getFileName()).thenReturn(this.fileName);
			when(((FileUploadForm) form).getFileDescription()).thenReturn(this.fileDescription);
			PowerMockito.doReturn(23456L).when(NBSFileUploadAction.class, "processRequest", any(NBSAttachmentDT.class),any(HttpSession.class));
			PowerMockito.doNothing().when(NBSFileUploadAction.class,"_makeNewAttachmentRowSecurity",any(NBSAttachmentDT.class),any(HttpServletRequest.class));
			when(mapping.findForward(forwardValue)).thenReturn(getActionForwardPath(forwardValue));
			Object[] oParams = new Object[] {mapping, form, request,response};
			ActionForward actionForward=Whitebox.invokeMethod(new NBSFileUploadAction(), "doUpload", oParams);
			String actualResult="Failure To Redirect";
			if(null != actionForward) actualResult = actionForward.getPath();
			Assert.assertEquals(this.expectedValue,actualResult);
			System.out.println("Method Name: doUpload, forwardValue::"+this.forwardValue+", Iteration: "+this.iteration+", Expected value:"+this.expectedValue+" Actual value: "+actualResult);
			System.out.println("RESULTS::::::PASSED");
		}
		
		
		/**
		 * These are input parameters(Multiple Records With Success and Failure cases) to the class DoUpload_test since we did use
		 * Parameterized as PowerMockRunnerDelegate
		 */
		 @SuppressWarnings("rawtypes")
			@Parameterized.Parameters
			   public static Collection input() {
			      return Arrays.asList(new Object[][]{
			    		  //maxFileSize, actualFileSize,publicHealthCaseUid,username,filename",fileDescription, attachmentToFrontend, expected path, actual path, iteration
			    		  {10,9,"0987654365","9999999","upload1.txt","File Upload Descroption With out Special Characters", "attachement1","success","/pagemanagement/supplemental/attachments/InvestigationAttachment.jsp",1},
			    		  {10,9,"0987654365","9999999","upload123.txt","File Upload Descroption With Special Characters &&&^%$%", "attachement1223","success","/pagemanagement/supplemental/attachments/InvestigationAttachment.jsp",2},  //Descroption with SpecialCharacters.
			    		  {10,104857890,"0987654450","9999999","upload2.txt","File Upload Descroption With out Special Characters", "attachement2","success","/pagemanagement/supplemental/attachments/InvestigationAttachment.jsp",3},   // Else Loop Validation
			    		  {10,7,"0987654450IUYT","9999999","upload3.txt","File Upload Descroption With out Special Characters", "attachement3","error","Failure To Redirect",4}, //this is for failure validation, please comment if don't want to see stack trace on console.
			    	  });
			   }
		 
		 
		/**
		 * This Method sets the redirect path based on success or failure of the test.
		 * @return ActionForward Object.
		 */
		 private ActionForward getActionForwardPath(String forwardAction) {
			 ActionForward actionForward = new ActionForward();
			 String path="";
			 if("success".equals(forwardAction) ) {
				path="/pagemanagement/supplemental/attachments/InvestigationAttachment.jsp";
			 }else {
				path="Failure To Redirect";
			 }
			 actionForward.setPath(path);
			 return actionForward;
		 }
		
	}
	
}
