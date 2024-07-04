package com.web.automation.test.page.actions.helper;

import java.util.Objects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ihclweb.automation.bookastay.model.BookAStayFunctionality;
import com.ihclweb.automation.loginfunctionalitycheck.model.IHCLLoginFunctionality;
import com.ihclweb.automation.repo.ExcelRepository;
import com.ihclweb.automation.util.Assert;
import com.ihclweb.automation.util.Sheet;
import com.ihclweb.automation.util.exceptions.IHCLBusinessException;
import com.techouts.ihclweb.webelement.ops.WebElementOperationsWeb;
import com.web.automation.test.handler.GlobalExceptionHandler;

public class BookAStayHelper extends GlobalExceptionHandler {

	private WebDriver driver = null;
	private ExcelRepository repository;

	private static final Logger LOG = Logger.getLogger(BookAStayHelper.class);
	private static final String IS_NOT_DISPLAYED = "] is not displayed";

	public BookAStayHelper(WebDriver driver,ExcelRepository repository) {
		this.driver = Objects.requireNonNull(driver,"WebDriver cannot be null to perform actions in Book A Stay Helper class");
		this.repository = Objects.requireNonNull(repository, "Repository cannot be null ");

	}
	// Booking Room using Book A Stay Option

	// Search Box
	public void sendHotelName(String testCaseName, String sheetName, int serialNo) {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getSearchBar())) {
				WebElementOperationsWeb.sendKeys(driver, BookAStayFunctionality.getSearchBar(),repository.readStringFrom(sheetName, Sheet.BookingData.HOTEL_NAME, serialNo) );

			} else {
				throw new IHCLBusinessException("Unable to find Search Bar ["+ BookAStayFunctionality.getSearchBar() + "]");
			}
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "FindAHotel");
			handleOnException("Error occured while sending Hotel Name [" + BookAStayFunctionality.getSearchBar() + "]",e);
		}
	}
	
	//Validating Book A Stay Button Color
	public void verifyBookAStayButtonColor(String expectedColor, String attributeVal) {
		try {
			if(WebElementOperationsWeb.isColorIdentical(driver, BookAStayFunctionality.getBookAStayButton(), expectedColor, attributeVal)) {
				LOG.info("Button Color is identical with expected color");
			}
		} catch (Exception e) {
			handleOnException("Color is not identical for [" + BookAStayFunctionality.getBookAStayButton() + "]",e);
		}
	}

	// Clicking on Book A Stay Button
	public void clickOnBookAStayButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getBookAStayButton())) {
				WebElementOperationsWeb.jsClick(driver, BookAStayFunctionality.getBookAStayButton());

			} else {
				throw new IHCLBusinessException("Unable to find Book A Stay Button["+ BookAStayFunctionality.getBookAStayButton() + "]");
			}
			
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking Book A Stay Button: " + BookAStayFunctionality.getBookAStayButton(),e);
		}
	}

	// Clicking on Change Button
	public void selectHotelFromSearchResults() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getSearchResult())) {
				WebElementOperationsWeb.click(driver, BookAStayFunctionality.getSearchResult());

			} else {
				throw new IHCLBusinessException("Unable to find Search result["+ BookAStayFunctionality.getSearchResult() + "]");
			}

		} catch (Exception e) {
			handleOnException("Unknown error occured while selecting Hotel from search results: " + BookAStayFunctionality.getSearchResult(), e);
		}
	}
	public void clickonCheckRatesButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getCheckRatesButton())) {
			WebElementOperationsWeb.click(driver, BookAStayFunctionality.getCheckRatesButton());
			}
			
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on Check Rates Button: " +BookAStayFunctionality.getCheckRatesButton(), e);
		}
	}
	// Verifying User is landed on Plan Your Stay Page
	public void verifyPlanYourStayPage() {
		if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getPlanYourStayLabel())) {
			LOG.info("User is landed on Plan Your Stay Page");
		} else {
			throw new IHCLBusinessException("Plan Your Stay Page [" + BookAStayFunctionality.getPlanYourStayLabel() + IS_NOT_DISPLAYED);
		}
	}

	// Verifying Rooms Rates Tab is displayed
	public void verifyRoomRatesTab() {
		if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getRoomRatesTab())) {
			LOG.info("Room Rates Tab is Displayed");
		} else {
			throw new IHCLBusinessException("Room Rates tab [" + BookAStayFunctionality.getRoomRatesTab() + IS_NOT_DISPLAYED);
		}
	}
	
	// Verifying Member Packages Tab is displayed
	public void verifyMemberPackagesTab() {
		if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getMemberPackagesTab())) {
			LOG.info("Member Packages Tab is Displayed");
		} else {
			throw new IHCLBusinessException("Member Packages tab [" + BookAStayFunctionality.getMemberPackagesTab() + IS_NOT_DISPLAYED);
		}
	}
		
	// Verifying Other Packages Tab is displayed
	public void verifyOtherPackagesTab() {
		if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getOtherPackagesTab())) {
			LOG.info("Other Packages Tab is Displayed");
		} else {
			throw new IHCLBusinessException("Other Packages tab [" + BookAStayFunctionality.getOtherPackagesTab() + IS_NOT_DISPLAYED);
		}
	}
	
	//Select Standard Rate
	public void clickonStandardRateSelectButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getStandardRateSelectButton())) {
				WebElementOperationsWeb.scrollTo(driver, BookAStayFunctionality.getStandardRateSelectButton());
				WebElementOperationsWeb.jsClick(driver, BookAStayFunctionality.getStandardRateSelectButton());
			} else {
				throw new IHCLBusinessException("Standard Rates Select Button ["+ BookAStayFunctionality.getStandardRateSelectButton() + IS_NOT_DISPLAYED);
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on Standard Rate Button: "+ BookAStayFunctionality.getStandardRateSelectButton(), e);
		}
	}
	
	// Verifying User is landed on Reservations Page
	public void verifyReservationsPage() {
		if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getReservationLabel())) {
			LOG.info("User is landed on Reservations Page");
		} else {
			throw new IHCLBusinessException("Reservations Page [" + BookAStayFunctionality.getReservationLabel() + IS_NOT_DISPLAYED);
		}
	}
		
	//Select Terms of Use Check box
	public void selectTermsOfUseCheckbox() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getTermsAndConditionsText())) {
				WebElementOperationsWeb.park(2);
				WebElementOperationsWeb.scrollTo(driver, BookAStayFunctionality.getTermsAndConditionsText());
				WebElementOperationsWeb.executeJSWithRetry(driver, "arguments[0].click()",BookAStayFunctionality.getPrivacyPolicyCheckbox(), 2);
				WebElementOperationsWeb.park(2);
			} else {
				LOG.error("Privacy Policy and Terms of Use Checkbox is not Found");
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking Terms of Use Checkbox: "+ IHCLLoginFunctionality.getTermsCheckBox(), e);
		}
	}
	
	// Verifying Pay Now Button is displayed
	public void verifyPayNowButton() {
		if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getPayNowButton())) {
			LOG.info("Pay Now Button is Displayed");
		} else {
			throw new IHCLBusinessException("Pay Now Button [" + BookAStayFunctionality.getPayNowButton() + IS_NOT_DISPLAYED);
		}
	}
		
	// Verifying Pay At Hotel Button is displayed
	public void verifyPayAtHotelButton() {
		if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getPayAtHotelButton())) {
			LOG.info("Pay At Hotel Button is Displayed");
		} else {
			throw new IHCLBusinessException("Pay At Hotel Button [" + BookAStayFunctionality.getPayAtHotelButton() + IS_NOT_DISPLAYED);
		}
	}
	
	//Clicking on Pay At Hotel Button
	public void clickOnPayAtHotelButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getPayAtHotelButton())) {
				WebElementOperationsWeb.click(driver, BookAStayFunctionality.getPayAtHotelButton());
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on Pay At Hotel Button: "+ BookAStayFunctionality.getPayAtHotelButton(), e);
		}
	}
	
	//Changing Payment option to Pay At Hotel Button
	public void clickOnYesButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getYesButton())) {
				WebElementOperationsWeb.click(driver, BookAStayFunctionality.getYesButton());
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on yes Button: "+ BookAStayFunctionality.getYesButton(), e);
		}
	}
	
	//Clicking on Confirm And Proceed Button
	public void clickOnConfirmAndProceedButton(String testCaseName) {
		try {
			if(WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getConfirmAndProceedButton())) {
				WebElementOperationsWeb.park(2);
				WebElementOperationsWeb.scrollTo(driver, BookAStayFunctionality.getConfirmAndProceedButton());
				WebElementOperationsWeb.jsClick(driver, BookAStayFunctionality.getConfirmAndProceedButton());
			}
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "ConfirmButton");
			handleOnException("Unknown error occured while clicking on Confirm And Proceed Button: "+ BookAStayFunctionality.getConfirmAndProceedButton(), e);
		}
	}
	
	public void sendCardNumber(String testCaseName, String sheetName, int serialNo) {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getCardNumber())) {
				WebElementOperationsWeb.sendKeys(driver, BookAStayFunctionality.getCardNumber(),repository.readStringFrom(sheetName, Sheet.CardData.CARD_NUMBER, serialNo) );

			} else {
				throw new IHCLBusinessException("Unable to find Card Number Field ["+ BookAStayFunctionality.getCardNumber() + "]");
			}
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "PaymentsPage");
			handleOnException("Error occured while sending Card Number [" + BookAStayFunctionality.getCardNumber() + "]",e);
		}
	}
	
	public void selectExpiryMonth(String sheetName, int serialNo) {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getExpiryMonth())) {
			WebElementOperationsWeb.dropDownByTextVisibility(BookAStayFunctionality.getExpiryMonth(), repository.readStringFrom(sheetName, Sheet.CardData.EXPIRY_MONTH, serialNo));
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while selecting Expiry Month: " +BookAStayFunctionality.getCheckRatesButton(), e);
		}
	}
	
	public void selectExpiryYear(String sheetName, int serialNo) {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getExpiryYear())) {
			WebElementOperationsWeb.dropDownByTextVisibility(BookAStayFunctionality.getExpiryYear(), repository.readStringFrom(sheetName, Sheet.CardData.EXPIRY_YEAR, serialNo));
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while while selecting Expiry Year: " +BookAStayFunctionality.getExpiryYear(), e);
		}
	}
	
	public void sendCVVNumber(String testCaseName, String sheetName, int serialNo) {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getCVVNumber())) {
				WebElementOperationsWeb.sendKeys(driver, BookAStayFunctionality.getCVVNumber(),repository.readStringFrom(sheetName, Sheet.CardData.CVV, serialNo) );

			} else {
				throw new IHCLBusinessException("Unable to find CVV Number Field ["+ BookAStayFunctionality.getCVVNumber() + "]");
			}
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "CVV");
			handleOnException("Error occured while sending CVV Number [" + BookAStayFunctionality.getCVVNumber() + "]",e);
		}
	}
	
	//Click on Make Payment Button
	public void clickOnMakePaymentButton() {
		try {
			WebElementOperationsWeb.click(driver, BookAStayFunctionality.getMakePaymentButton());
			WebElementOperationsWeb.park(2);
		} catch (Exception e) {
			LOG.error("Make Payment Button is not clickable");
		}
	}
	
	//Click on Send Response Button
	public void clickOnSendResponseButton() {
		try {
			WebElementOperationsWeb.click(driver, BookAStayFunctionality.getSendResponseButton());
			WebElementOperationsWeb.park(2);
		} catch (Exception e) {
			LOG.error("Send Response Button is not clickable");
		}
	}
	
	// Verifying User is landed on Booking Confirmation Page
	public void verifyBookingConfirmationPage() {
		if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getBookingConfirmedLabel())) {
			LOG.info("User is landed on Booking Confirmation Page");
		} else {
			throw new IHCLBusinessException("Booking Confirmation Page [" + BookAStayFunctionality.getBookingConfirmedLabel() + IS_NOT_DISPLAYED);
		}
	}
	
	// Verifying Itinerary Number
	public void verifyItineraryNumber() {
		if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getItineraryNumber())) {
			LOG.info("Itinerary Number is displayed");
		} else {
			throw new IHCLBusinessException("Itinerary Number ["+ BookAStayFunctionality.getItineraryNumber() + IS_NOT_DISPLAYED);
		}
	}
	
	// Verifying Booking Status
	public void verifyBookingStatus() {
		if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getBookingStatus())) {
			Assert.assertEquals(WebElementOperationsWeb.getText(driver, BookAStayFunctionality.getBookingStatus()), "CONFIRMED", "Booking Status is not confirmed");
		} else {
			throw new IHCLBusinessException("Booking Status [" + BookAStayFunctionality.getBookingStatus() + IS_NOT_DISPLAYED);
		}
	}
}
