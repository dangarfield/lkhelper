package com.dangarfield.lkhelper.utils;

import java.util.Properties;

public interface ConfigUtils {
	
	public void setBaseConfig();
	
	public void setLoggingLevels();
	
	public void setTimeZone();
	
	Properties getDefaultUserConfigProperties();
}
