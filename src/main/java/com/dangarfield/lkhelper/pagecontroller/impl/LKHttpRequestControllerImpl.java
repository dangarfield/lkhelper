package com.dangarfield.lkhelper.pagecontroller.impl;

import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangarfield.lkhelper.exceptions.GatherDataException;
import com.dangarfield.lkhelper.pagecontroller.LKHttpRequestController;
import com.dangarfield.lkhelper.pagecontroller.LKMainPageController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class LKHttpRequestControllerImpl implements LKHttpRequestController {

	private static Logger LOG = LogManager.getLogger("LKHttpRequestController");

	public static String BASE_MAP_URL_PARAM_CALLBACK_DEFAULT_VALUE = "dgCallback";
	
	@Autowired
	protected LKMainPageController lkMainPageController;


	@Override
	public JsonNode getMap(final WebDriver driver, final URL mapUrlString) throws GatherDataException {
	try {
			final String httpResponse = readUrl(driver, mapUrlString);
			LOG.debug(httpResponse);
			
			LOG.debug("removing json callback and wrapping");
			final int callbackMethodIndex = httpResponse.indexOf( BASE_MAP_URL_PARAM_CALLBACK_DEFAULT_VALUE );
			String trimmeredHttpResponseIntoJsonFormat = httpResponse.substring(callbackMethodIndex + BASE_MAP_URL_PARAM_CALLBACK_DEFAULT_VALUE.length() + 1,httpResponse.lastIndexOf(")"));
			LOG.debug(trimmeredHttpResponseIntoJsonFormat);
			
			ObjectMapper mapper = new ObjectMapper();
			final JsonNode root = mapper.readTree(trimmeredHttpResponseIntoJsonFormat);
			
//			JSONParser jsonParser = new JSONParser();
//			org.json.simple.JSONObject obj = (org.json.simple.JSONObject) jsonParser.parse(trimmeredHttpResponseIntoJsonFormat);
//			String jsonString = obj.toJSONString();
//			final JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonString);
			
			//final JSONObject json = (JSONObject) JSONSerializer.toJSON( trimmeredHttpResponseIntoJsonFormat );
			LOG.debug(root);
		    return root;
		    
		} catch (Exception e) {
			throw new GatherDataException(e.getMessage());
		}
	}
	
	private String readUrl(final WebDriver driver, final URL url) {

		//selectWindow(driver);
        driver.get(url.toString());
        final String response = driver.getPageSource();
        //closeTab(driver, url.toString());
        
        return response;
	}
	
	/**
	 * @return the lkMainPageController
	 */
	public LKMainPageController getLkMainPageController() {
		return lkMainPageController;
	}

	/**
	 * @param lkMainPageController the lkMainPageController to set
	 */
	public void setLkMainPageController(LKMainPageController lkMainPageController) {
		this.lkMainPageController = lkMainPageController;
	}


}
