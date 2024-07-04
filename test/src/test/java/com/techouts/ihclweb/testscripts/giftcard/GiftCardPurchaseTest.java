package com.techouts.ihclweb.testscripts.giftcard;

import java.util.Objects;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ihclweb.automation.bookastay.model.BookAStay;
import com.ihclweb.automation.dto.credentials.User;
import com.ihclweb.automation.giftcardjourney.model.GiftCardJourney;
import com.ihclweb.automation.loginfunctionalitycheck.model.LoginFunctionalityCheck;
import com.ihclweb.automation.provider.credential.CredentialProvider;
import com.techouts.ihclweb.testscripts.retry.DefaultRetryAnalyzer;
import com.techouts.ihclweb.webelement.ops.WebElementOperationsWeb;
import com.web.automation.test.AbstractTest;
import com.web.automation.test.page.actions.GiftCardAction;
import com.web.automation.test.page.actions.IHCLLoginFunctionalityAction;

public class GiftCardPurchaseTest extends AbstractTest{
	
	private GiftCardAction giftCardAction;
	private IHCLLoginFunctionalityAction ihclLoginActions;
	
	private static final String TEST_CASE_NAME = "Signature Gift Card Purchase"; 
	private static final int SERIAL_NO = 1;
	private static final Logger LOG = Logger.getLogger(GiftCardPurchaseTest.class.getName());
	
	public GiftCardPurchaseTest() {
		new GiftCardJourney().init(DRIVER);
		new LoginFunctionalityCheck().init(DRIVER);
		new BookAStay().init(DRIVER);
		giftCardAction = new GiftCardAction(DRIVER,REPOSITORY);
		ihclLoginActions = new IHCLLoginFunctionalityAction(DRIVER);
	}
	
	@BeforeMethod
	public void openIHCLPage() {
		getIhclPage();
		WebElementOperationsWeb.captureScreenShotOnPass(DRIVER, TEST_CASE_NAME, "IHCLApplicationOpened");
	}
	
	@Test(testName = TEST_CASE_NAME, description = "Verify Booking Room through Book A Stay Journey", retryAnalyzer = DefaultRetryAnalyzer.class)
	public void verifyGiftCardPurchase() {
		try {			
			
			User mobilelogin = Objects.requireNonNull(CredentialProvider.getUser("E001"),"Mobile Login credential should not be null");

			ihclLoginActions.clickOnLogin();
			ihclLoginActions.loginUsingMobileNumber(mobilelogin.getMobileno(), TEST_CASE_NAME);
			ihclLoginActions.clickonContinueButton(TEST_CASE_NAME);
			ihclLoginActions.enterOtp(TEST_CASE_NAME);
			LOG.info("Login was Successsful");
			
			giftCardAction.navigateToGiftCardPage(TEST_CASE_NAME);
			giftCardAction.enteringUserDetails(TEST_CASE_NAME, GIFT_CARD_SHEET, SERIAL_NO );
			giftCardAction.buyingSignatureGiftCard(TEST_CASE_NAME);
			giftCardAction.enteringPaymentDetails(TEST_CASE_NAME, CARD_DATA_SHEET, 2);
			giftCardAction.verifyingOrderDetails(TEST_CASE_NAME);
			
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(DRIVER, TEST_CASE_NAME, "GiftCardPurchaseJourney");
			getIhclPage();
			WebElementOperationsWeb.waitForPageLoad(DRIVER, 20);
			addRetryTest(new Object() {}.getClass().getEnclosingMethod().getName());
			handleOnException("GiftCardPurchaseJourney", e);
		}
	}
	
	@AfterMethod()
	public void tearDown() {
		getIhclPage();
		LOGIN_ACTIONS.doLogout();
	}
}
