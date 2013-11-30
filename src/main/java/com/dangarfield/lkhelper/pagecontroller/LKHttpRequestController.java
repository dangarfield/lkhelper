package com.dangarfield.lkhelper.pagecontroller;

import java.net.URL;
import org.openqa.selenium.WebDriver;
import com.dangarfield.lkhelper.exceptions.GatherDataException;
import com.fasterxml.jackson.databind.JsonNode;

public interface LKHttpRequestController {
	
	public JsonNode getMap(final WebDriver driver, final URL mapUrlString) throws GatherDataException;
	
}
