package com.web.automation.test.page.actions;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ihclweb.automation.repo.ExcelRepository;
import com.techouts.ihclweb.webelement.ops.WebElementOperationsWeb;
import com.web.automation.test.handler.GlobalExceptionHandler;
import com.web.automation.test.page.actions.helper.GiftCardHelper;

public class GiftCardAction extends GlobalExceptionHandler{
	
	private static final Logger LOG = Logger.getLogger(BookAStayAction.class);
	private WebDriver driver = null;
	
	private GiftCardHelper giftCardHelper;

	public GiftCardAction(WebDriver driver, ExcelRepository repository) {
		this.driver = Objects.requireNonNull(driver,"WebDriver cannot be null to perform actions in Gift Card Actions class");
		giftCardHelper = new GiftCardHelper(driver, repository);
	}
	
	/*
	 * Navigating to Gift Card Page
	 */
	public void navigateToGiftCardPage(String testCaseName) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElementOperationsWeb.waitForPageLoad(driver, 20);
			giftCardHelper.clickOnMoreMenu();
			giftCardHelper.clickOnGifting();
			giftCardHelper.mouseHoverOnGiftCards();
			giftCardHelper.clickOnGiftCardsMore();
			giftCardHelper.verifyEGiftCardTab();
			giftCardHelper.verifyPhysicalGiftCardsTab();
			giftCardHelper.verifyWeddingGiftCardsTab();
			giftCardHelper.verifyCardBalanceReloadTab();
			WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "NavigateToGiftCardPage");
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "NavigateToGiftCardPage");
			handleOnException("Error in navigating to Gift Card Page", e);
		}
	}
	
	/*
	 * Entering User Details
	 */
	public void enteringUserDetails(String testCaseName, String sheetName, int serialNo) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElementOperationsWeb.waitForPageLoad(driver, 20);
			giftCardHelper.clickonBuyNowButton();
			giftCardHelper.sendAmount(testCaseName, sheetName, serialNo);
			giftCardHelper.clickonQuantityButton();
			giftCardHelper.selectQuantity();
			giftCardHelper.sendAddress(testCaseName, sheetName, serialNo);
			giftCardHelper.sendPincode(testCaseName, sheetName, serialNo);
			giftCardHelper.clickonMyselfButton();
			WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "UserDetailsPage");
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "UserDetailsPage");
			handleOnException("Error in navigating to User Details Page", e);
		}
	}
	
	/*
	 * Buying Signature Gift Card
	 */
	public void buyingSignatureGiftCard(String testCaseName) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElementOperationsWeb.waitForPageLoad(driver, 20);
			giftCardHelper.clickonPreviewGiftCardButton();
			giftCardHelper.selectTermsOfUseCheckbox();
			giftCardHelper.clickonProceedToPaymentButton();
			giftCardHelper.clickonPayNowButton();
			WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "CardDetails");
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "CardDetails");
			handleOnException("Error in navigating to Payment Details Page", e);
		}
	}
	
	/*
	 * Entering Payment Details
	 */
	public void enteringPaymentDetails(String testCaseName, String sheetName, int serialNo) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElementOperationsWeb.waitForPageLoad(driver, 20);
			WebElementOperationsWeb.switchToFrame(driver, 0);
			giftCardHelper.sendCardNumber(testCaseName, sheetName, serialNo);
			giftCardHelper.sendExpiryDate(testCaseName, sheetName, serialNo);
			giftCardHelper.sendCVVNumber(testCaseName, sheetName, serialNo);
			giftCardHelper.clickonProceedToPayButton();
			giftCardHelper.clickonOptOutForNowButton();
			WebElementOperationsWeb.waitForPageLoad(driver, 10);
			WebElementOperationsWeb.switchToDefaultContentFrame(driver);
			giftCardHelper.clickonSendResponseButton();
			WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "PaymentPage");
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "PaymentPage");
			handleOnException("Error in Entering Payment Details", e);
		}
	}
	
	/*
	 * Verifying Order Confirmation
	 */
	public void verifyingOrderDetails(String testCaseName) {
		try {
			WebElementOperationsWeb.waitForPageLoad(driver, 20);
			giftCardHelper.verifyGiftCardConfirmationPage();
			giftCardHelper.verifyOrderNumber();
			WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "OrderConfirmation");
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "OrderConfirmation");
			handleOnException("Error occured in Order Confirmation Page", e);
		}
	}
	
	
}
