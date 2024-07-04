package com.web.automation.test;



import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ihclweb.automation.util.Sheet;

/**
 * 
 * @author rajeevsai.t
 *
 */
public abstract class AbstractTest extends ApplicationContextInitializer implements AbstractPerformer {
	
	private static final Logger LOG = Logger.getLogger(AbstractTest.class);

	public static final String HOMEPAGE_SHEET = Sheet.HomePage.class.getSimpleName();
	public static final String MY_ACCOUNT_PAGE = Sheet.MyAccount.class.getSimpleName();
	public static final String BOOKING_SHEET = Sheet.BookingData.class.getSimpleName();
	public static final String CARD_DATA_SHEET = Sheet.CardData.class.getSimpleName();
	public static final String GIFT_CARD_SHEET = Sheet.GiftCard.class.getSimpleName();
	
	@Override
	public void init(){
		LOG.debug("TestNG Suite execution started....!");
	}
	
	@Override
	public void destroy(){
		LOG.debug("TestNG Suite execution finished....!");
	}
	
	@BeforeSuite
	public void beforeSuite() {
		this.init();
	}
	
	@AfterSuite
	public void afterSuite() {
		this.destroy();
	}
}