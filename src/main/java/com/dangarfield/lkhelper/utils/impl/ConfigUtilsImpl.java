package com.dangarfield.lkhelper.utils.impl;

import java.util.Properties;
import java.util.TimeZone;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.dangarfield.lkhelper.utils.ConfigUtils;

public class ConfigUtilsImpl implements ConfigUtils {
	private static Logger LOG = LogManager.getLogger("ConfigUtils");
	
	@Override
	public void setBaseConfig() {
		setLoggingLevels();
		setTimeZone();
	}
	
	@Override
	public void setLoggingLevels() {	
		java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(java.util.logging.Level.FINEST);
		java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(java.util.logging.Level.FINEST);
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "ERROR");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "ERROR");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "ERROR");
	}
	@Override
	public void setTimeZone() {
		TimeZone originalDefault = TimeZone.getDefault();
		TimeZone gmtTimezone = TimeZone.getTimeZone("GMT");
		if (!originalDefault.equals(gmtTimezone)) {
			TimeZone.setDefault(gmtTimezone);
		}
	}
	
	@Override
	public Properties getDefaultUserConfigProperties() {
		
		LOG.info("Loading config properties");
				
		Properties prop = new Properties();
		//TODO - This needs to be from a file or db entry somewhere
		prop.setProperty("login.email", "dangarfielduk@hotmail.com");
		prop.setProperty("login.password", "perfection");
		prop.setProperty("login.world", "UK 3");
		
//    	try {
//           prop.load(new FileInputStream("src/main/resources/config.properties"));
//           
//           LOG.debug("  login.email=" + prop.getProperty("login.email"));
//           
//           String password = prop.getProperty("login.password");
//           StringBuilder maskedPassword = new StringBuilder();
//           for (int i = 0; i < password.length(); i++) {
//        	   if (i==0 || i == password.length()-1) {
//        		   maskedPassword.append(password.charAt(i));
//        	   } else {
//        		   maskedPassword.append("x");
//        	   }
//           }
//           LOG.debug("  login.password=" + maskedPassword + " (masked)");
//           LOG.debug("  login.world=" + prop.getProperty("login.world"));
// 
//    	} catch (IOException ex) {
//    		ex.printStackTrace();
//        }
    	
    	return prop;
	}
}
