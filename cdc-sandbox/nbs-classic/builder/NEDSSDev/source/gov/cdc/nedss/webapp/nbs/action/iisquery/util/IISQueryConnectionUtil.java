package gov.cdc.nedss.webapp.nbs.action.iisquery.util;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.util.LogUtils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.immregistries.smm.tester.connectors.Connector;

public class IISQueryConnectionUtil {
	static final LogUtils logger = new LogUtils(IISQueryConnectionUtil.class.getName());
	static String propertyFilePath="";
	static{
		String nedssDir = new StringBuffer(System.getProperty("nbs.dir")).append(File.separator).toString().intern();
		String propertiesDir = new StringBuffer(nedssDir).append("Properties").append(File.separator).toString().intern();
		propertyFilePath= propertiesDir + "iisconnection.txt";
		System.out.println("propertyFilePath:"+propertyFilePath);
	}
	private static String configFile = null;
	
	private static String readIISConnection() throws NEDSSAppException {
		try {
			System.out.println("propertyFilePath from readIISConnection : "+propertyFilePath);
			if(configFile == null){
				configFile = FileUtils.readFileToString(new File(propertyFilePath));
				System.out.println("ConfigFile: "+configFile);
			}
			return configFile;
		} catch (IOException ex) {
			logger.fatal("IO Exception :"+ex.getMessage(),ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}catch (Exception ex) {
			logger.fatal("Exception :"+ex.getMessage(),ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	public static Connector getIISConnection() throws NEDSSAppException{
		try{
			Connector connector = Connector.makeConnectors(readIISConnection()).get(0);
			return connector;
		}catch(Exception ex){
			logger.fatal("Exception :"+ex.getMessage(),ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
}
