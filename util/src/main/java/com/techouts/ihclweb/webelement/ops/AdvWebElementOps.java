package com.techouts.ihclweb.webelement.ops;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ihclweb.automation.util.PropertyUtil;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class AdvWebElementOps extends WebElementOperationsWeb {

	private static final int ALERT_RETRY = PropertyUtil.getInt("alert-lookup-retry.count", 0);
	private static int retryCount = 0;
	
	private static final Logger LOG = Logger.getLogger(AdvWebElementOps.class);
	
	public static void acceptAlertWithRetry(WebDriver driver){
		try{
			if(retryCount <= ALERT_RETRY){
				acceptAlert(driver);
				retryCount++;
				acceptAlertWithRetry(driver);
			}else{
				LOG.info("Lookups for alert in the page has made with "+retryCount+" count.");
				retryCount = 0;
			}
		}catch (Exception e) {
			LOG.warn("No alert present to handle in the window after "+retryCount+" retries.", e);
			retryCount = 0;
		}
	}
}
