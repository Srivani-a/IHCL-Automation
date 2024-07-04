package com.web.automation.test.page.actions;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ihclweb.automation.consts.IHCLConstantsWeb;
import com.ihclweb.automation.repo.ExcelRepository;
import com.techouts.ihclweb.webelement.ops.WebElementOperationsWeb;
import com.web.automation.test.handler.GlobalExceptionHandler;
import com.web.automation.test.page.actions.helper.BookAStayHelper;

public class BookAStayAction extends GlobalExceptionHandler {

	private static final Logger LOG = Logger.getLogger(BookAStayAction.class);
	private WebDriver driver = null;
	

	private BookAStayHelper bookAStayHelper;

	public BookAStayAction(WebDriver driver, ExcelRepository repository) {
		this.driver = Objects.requireNonNull(driver,"WebDriver cannot be null to perform actions in Book A Stay Actions class");
		bookAStayHelper = new BookAStayHelper(driver, repository);
	}
	
	public void validateButtonColor() {
		try {
			bookAStayHelper.verifyBookAStayButtonColor(IHCLConstantsWeb.BOOK_A_STAY_COLOR, "background-color");
		} catch (Exception e) {
			handleOnException("Error in Validating Button Color", e);
		}
	}
	
	public void searchForHotel(String testCaseName, String sheetName, int serialNo) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElementOperationsWeb.waitForPageLoad(driver, 20);
			bookAStayHelper.clickOnBookAStayButton();
			bookAStayHelper.sendHotelName(testCaseName, sheetName, serialNo);
			WebElementOperationsWeb.park(2);
			bookAStayHelper.selectHotelFromSearchResults();
			bookAStayHelper.clickonCheckRatesButton();
			WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "NavigateToPlanYourStayPage");
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "NavigateToPlanYourStayPage");
			handleOnException("Error in navigating to Plan Your Stay Page", e);
		}
	}

	public void selectARoom(String testCaseName) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			bookAStayHelper.verifyPlanYourStayPage();
			bookAStayHelper.verifyRoomRatesTab();
			//bookAStayHelper.verifyMemberPackagesTab();
			//bookAStayHelper.verifyOtherPackagesTab();
			bookAStayHelper.clickonStandardRateSelectButton();
			WebElementOperationsWeb.park(2);
			WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "NavigateToReservationsPage");
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "NavigateToReservationsPage");
			handleOnException("Error in navigating to Reservations Page", e);
		}
	}
	
	public void payAtHotel(String testCaseName, String sheetName, int serialNo) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			bookAStayHelper.verifyReservationsPage();
			bookAStayHelper.verifyPayNowButton();
			bookAStayHelper.verifyPayAtHotelButton();
			WebElementOperationsWeb.park(2);
			bookAStayHelper.selectTermsOfUseCheckbox();
			WebElementOperationsWeb.park(5);
			bookAStayHelper.clickOnPayAtHotelButton();
			//bookAStayHelper.clickOnYesButton();
			bookAStayHelper.clickOnConfirmAndProceedButton(testCaseName);
			WebElementOperationsWeb.park(2);
			bookAStayHelper.sendCardNumber(testCaseName, sheetName, serialNo);
			bookAStayHelper.selectExpiryMonth(sheetName, serialNo);
			bookAStayHelper.selectExpiryYear(sheetName, serialNo);
			bookAStayHelper.sendCVVNumber(testCaseName, sheetName, serialNo);
			bookAStayHelper.clickOnMakePaymentButton();
			bookAStayHelper.clickOnSendResponseButton();
			bookAStayHelper.verifyBookingConfirmationPage();
			bookAStayHelper.verifyItineraryNumber();
			bookAStayHelper.verifyBookingStatus();
			LOG.info("Booking Status is confirmed");
			WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "NavigateToBookingConfirmationPage");
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "NavigateToBookingConfirmationPage");
			handleOnException("Error in navigating to Booking Confirmation Page", e);
		}
	}
	
}
