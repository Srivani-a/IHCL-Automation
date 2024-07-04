package com.techouts.ihclweb.testscripts.bookingjourney;

import java.util.Objects;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ihclweb.automation.bookastay.model.BookAStay;
import com.ihclweb.automation.dto.credentials.User;
import com.ihclweb.automation.loginfunctionalitycheck.model.LoginFunctionalityCheck;
import com.ihclweb.automation.provider.credential.CredentialProvider;
import com.techouts.ihclweb.testscripts.retry.DefaultRetryAnalyzer;
import com.techouts.ihclweb.webelement.ops.WebElementOperationsWeb;
import com.web.automation.test.AbstractTest;
import com.web.automation.test.page.actions.BookAStayAction;
import com.web.automation.test.page.actions.IHCLLoginFunctionalityAction;

public class BookAStayJourneyTest extends AbstractTest  {
	
	private BookAStayAction bookAStayAction;
	private IHCLLoginFunctionalityAction ihclLoginActions;
	
	private static final String TEST_CASE_NAME = "Booking Room through Book A Stay"; 
	private static final int SERIAL_NO = 1;
	private static final Logger LOG = Logger.getLogger(BookAStayJourneyTest.class.getName());

	public BookAStayJourneyTest() {
		new BookAStay().init(DRIVER);
		new LoginFunctionalityCheck().init(DRIVER);
		bookAStayAction = new BookAStayAction(DRIVER,REPOSITORY);
		ihclLoginActions = new IHCLLoginFunctionalityAction(DRIVER);
	}

	@BeforeMethod
	public void openIHCLPage() {
		getIhclPage();
		WebElementOperationsWeb.captureScreenShotOnPass(DRIVER, TEST_CASE_NAME, "IHCLApplicationOpened");
	}
	
	@Test(testName = TEST_CASE_NAME, description = "Verify Booking Room through Book A Stay Journey", retryAnalyzer = DefaultRetryAnalyzer.class)
	public void verifyRoomBooking() {
		try {			
			
			User mobilelogin = Objects.requireNonNull(CredentialProvider.getUser("E001"),"Mobile Login credential should not be null");

			ihclLoginActions.clickOnLogin();
			ihclLoginActions.loginUsingMobileNumber(mobilelogin.getMobileno(), TEST_CASE_NAME);
			ihclLoginActions.clickonContinueButton(TEST_CASE_NAME);
			ihclLoginActions.enterOtp(TEST_CASE_NAME);
			WebElementOperationsWeb.waitForPageLoad(DRIVER, 10);
			LOG.info("Login was Successsful");
			
			bookAStayAction.validateButtonColor();
			bookAStayAction.searchForHotel(TEST_CASE_NAME, BOOKING_SHEET,SERIAL_NO);
			bookAStayAction.selectARoom(TEST_CASE_NAME);
			bookAStayAction.payAtHotel(TEST_CASE_NAME, CARD_DATA_SHEET, SERIAL_NO);
			
		} catch (Exception e) {
			//WebElementOperationsWeb.captureScreenShotOnFail(DRIVER, TEST_CASE_NAME, "BookAStayJourney");
			getIhclPage();
			WebElementOperationsWeb.waitForPageLoad(DRIVER, 10);
			addRetryTest(new Object() {}.getClass().getEnclosingMethod().getName());
			handleOnException("BookAStayJourney", e);
		}
	}
	
	@AfterMethod()
	public void tearDown() {
		getIhclPage();
		LOGIN_ACTIONS.doLogout();
	}
}


