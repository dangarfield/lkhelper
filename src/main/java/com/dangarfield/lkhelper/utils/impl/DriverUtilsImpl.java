package com.dangarfield.lkhelper.utils.impl;

import java.net.MalformedURLException;
import java.util.List;

import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.ProxyServer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.dangarfield.lkhelper.utils.DriverUtils;

public class DriverUtilsImpl implements DriverUtils{
	private static Logger LOG = LogManager.getLogger("DriverUtils");
	
	@Override
	public WebDriver startSeleniumWebDriver() {
		return startSeleniumWebDriver(null);
	}
	@Override
	public WebDriver startSeleniumWebDriver(final Proxy proxy) {
		LOG.info("Starting selenium web driver");
		if(proxy != null) {
			final DesiredCapabilities capabilities = new DesiredCapabilities();
			LOG.info("Using proxy server for selenium: port: " + proxy.getHttpProxy());
			capabilities.setCapability(CapabilityType.PROXY, proxy);
			return new FirefoxDriver(capabilities);
		} else {
			LOG.info("Bypass proxy for the time being whilst at work");
			FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("network.proxy.type", 0);               
            return new FirefoxDriver(profile);
		}
	}
	
	@Override
	public ProxyServer startBrowserMobProxyServer(final int port) throws Exception {
		final ProxyServer proxyServer = new ProxyServer(port);
		proxyServer.start();
		return proxyServer;
	}
	
	@Override
	public HarEntry getBaseMapJsonRequestUrl(final ProxyServer proxyServer) throws MalformedURLException {
		final Har har = proxyServer.getHar();
		
		HarEntry mapCallEntry = null;
		List<HarEntry> entries = har.getLog().getEntries();
		for(HarEntry entry : entries) {
			LOG.debug(entry.getRequest().getUrl().toString());
			if (entry.getRequest().getUrl().contains("woa/wa/MapAction/map")) {
				LOG.info("Captured MapCallEntry from Har file - " + entry.getRequest().getUrl().toString());
				mapCallEntry = entry;
				break;
			}
		}
		
		if(mapCallEntry == null) {
			throw new RuntimeException("Map has not been loaded");
		}
		
		return mapCallEntry;
	}
	
}
