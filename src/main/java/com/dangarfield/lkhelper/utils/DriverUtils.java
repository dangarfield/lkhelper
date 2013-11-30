package com.dangarfield.lkhelper.utils;

import java.net.MalformedURLException;

import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.ProxyServer;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;

public interface DriverUtils {

	public WebDriver startSeleniumWebDriver();
	public WebDriver startSeleniumWebDriver(final Proxy proxy);

	public ProxyServer startBrowserMobProxyServer(final int port) throws Exception;
	
	public HarEntry getBaseMapJsonRequestUrl(final ProxyServer proxyServer) throws MalformedURLException;
	
}
