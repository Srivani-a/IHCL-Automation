/**
 * 
 */
package com.web.automation.test.page.actions.helper;

import java.util.Objects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.ihclweb.automation.bookastay.model.BookAStayFunctionality;
import com.ihclweb.automation.giftcardjourney.model.GiftCardJourneyElements;
import com.ihclweb.automation.loginfunctionalitycheck.model.IHCLLoginFunctionality;
import com.ihclweb.automation.repo.ExcelRepository;
import com.ihclweb.automation.util.Sheet;
import com.ihclweb.automation.util.exceptions.IHCLBusinessException;
import com.techouts.ihclweb.webelement.ops.WebElementOperationsWeb;
import com.web.automation.test.handler.GlobalExceptionHandler;

/**
 * @author Rajeev Sai
 *
 */
public class GiftCardHelper extends GlobalExceptionHandler{
	private WebDriver driver = null;
	private ExcelRepository repository;

	private static final Logger LOG = Logger.getLogger(BookAStayHelper.class);
	private static final String IS_NOT_DISPLAYED = "] is not displayed";
	public GiftCardHelper(WebDriver driver, ExcelRepository repository) {
		this.driver = Objects.requireNonNull(driver,"WebDriver cannot be null to perform actions in Gift Card Helper class");
		this.repository = Objects.requireNonNull(repository, "Repository cannot be null ");
	}
	
	// Clicking on More Menu
	public void clickOnMoreMenu() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getMoreLink())) {
				WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getMoreLink());

			} else {
				throw new IHCLBusinessException("Unable to find More Menu[" + GiftCardJourneyElements.getMoreLink() + "]");
			}

		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking More Menu: " + GiftCardJourneyElements.getMoreLink(), e);
		}
	}
	
	//Clicking on Gifting
	public void clickOnGifting() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getGifting())) {
				WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getGifting());

			} else {
				throw new IHCLBusinessException("Unable to find Gifting Button[" + GiftCardJourneyElements.getGifting() + "]");
			}

		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking Gifting Button: " + GiftCardJourneyElements.getGifting(), e);
		}
	}
	
	//Mouse Hover On Gift Cards
	public void mouseHoverOnGiftCards() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getGiftCards())) {
				WebElementOperationsWeb.mouseOver(driver, GiftCardJourneyElements.getGiftCards());

			} else {
				throw new IHCLBusinessException("Unable to find Gift Cards[" + GiftCardJourneyElements.getGiftCards() + "]");
			}

		} catch (Exception e) {
			handleOnException("Unknown error occured while mouse hover on Gift Cards: " + GiftCardJourneyElements.getGiftCards(), e);
		}
	}
	
	// Clicking on More Menu
	public void clickOnGiftCardsMore() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getGiftCardsMore())) {
				WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getGiftCardsMore());

			} else {
				throw new IHCLBusinessException("Unable to find More link[" + GiftCardJourneyElements.getGiftCardsMore() + "]");
			}

		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking More link: " + GiftCardJourneyElements.getGiftCardsMore(), e);
		}
	}
	
		
	// Verifying E-Gift Card Tab is displayed
	public void verifyEGiftCardTab() {
		if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getEGiftCards())) {
			LOG.info("E-Gift Card Tab is Displayed");
		} else {
			throw new IHCLBusinessException("E-Gift Card tab [" + GiftCardJourneyElements.getEGiftCards() + IS_NOT_DISPLAYED);
		}
	}
	
	// Verifying Physical Gift Cards Tab is displayed
	public void verifyPhysicalGiftCardsTab() {
		if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getPhysicalGiftCards())) {
			LOG.info("Physical Gift Cards Tab is Displayed");
		} else {
			throw new IHCLBusinessException("E-Gift Card tab [" + GiftCardJourneyElements.getPhysicalGiftCards() + IS_NOT_DISPLAYED);
		}
	}
	
	// Verifying Wedding Gift Cards Tab is displayed
	public void verifyWeddingGiftCardsTab() {
		if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getWeddingGiftCards())) {
			LOG.info("Wedding Gift Cards Tab is Displayed");
		} else {
			throw new IHCLBusinessException("Wedding Gift Card tab [" + GiftCardJourneyElements.getWeddingGiftCards() + IS_NOT_DISPLAYED);
		}
	}
	
	// Verifying Card Balance Reload Tab is displayed
	public void verifyCardBalanceReloadTab() {
		if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getCardBalanceReload())) {
			LOG.info("Card Balance Reload Tab is Displayed");
		} else {
			throw new IHCLBusinessException("Card Balance Reload tab [" + GiftCardJourneyElements.getCardBalanceReload() + IS_NOT_DISPLAYED);
		}
	}
	
	//Click on Buy Now Button
	public void clickonBuyNowButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getBuyNowButton())) {
				WebElementOperationsWeb.scrollTo(driver, GiftCardJourneyElements.getBuyNowButton());
				WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getBuyNowButton());
			} else {
				throw new IHCLBusinessException("Buy Now Button ["+ GiftCardJourneyElements.getBuyNowButton() + IS_NOT_DISPLAYED);
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on Buy Now Button: "+ GiftCardJourneyElements.getBuyNowButton(), e);
		}
	}
	
	//Enter Amount
	public void sendAmount(String testCaseName, String sheetName, int serialNo) {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getEnterAmount())) {
				WebElementOperationsWeb.sendKeys(driver, GiftCardJourneyElements.getEnterAmount(),repository.readStringFrom(sheetName, Sheet.GiftCard.AMOUNT, serialNo));

			} else {
				throw new IHCLBusinessException("Unable to find Enter Amount input box[" + GiftCardJourneyElements.getEnterAmount() + "]");
			}
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "EnteringAmount");
			handleOnException("Error occured while sending Amount [" + GiftCardJourneyElements.getEnterAmount() + "]",e);
		}
	}
	
	//Click on Quantity Button
	public void clickonQuantityButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getQuantity())) {
				WebElementOperationsWeb.park(2);
				WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getEnterDetailsLabel());
				new Actions(driver).click(GiftCardJourneyElements.getQuantityClick()).build().perform();
			} else {
				throw new IHCLBusinessException("Quantity Input Box [" + GiftCardJourneyElements.getQuantity() + IS_NOT_DISPLAYED);
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on Quantity Input Box: "+ GiftCardJourneyElements.getQuantityClick(), e);
		}
	}
	
	//Select Quantity
	public void selectQuantity() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getQuantitySelection())) {
				WebElementOperationsWeb.park(2);
				WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getQuantitySelection());
			} else {
				throw new IHCLBusinessException("Quantity Options are [" + GiftCardJourneyElements.getQuantitySelection() + IS_NOT_DISPLAYED);
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on Quantity : "+ GiftCardJourneyElements.getQuantitySelection(), e);
		}
	}
	
	//Enter Address
	public void sendAddress(String testCaseName, String sheetName, int serialNo) {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getAddress())) {
				WebElementOperationsWeb.sendKeys(driver, GiftCardJourneyElements.getAddress(),repository.readStringFrom(sheetName, Sheet.GiftCard.ADDRESS, serialNo));

			} else {
				throw new IHCLBusinessException("Unable to find Address input box[" + GiftCardJourneyElements.getAddress() + "]");
			}
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "EnteringAddress");
			handleOnException("Error occured while sending Address [" + GiftCardJourneyElements.getAddress() + "]",e);
		}
	}
	
	//Enter Pincode
	public void sendPincode(String testCaseName, String sheetName, int serialNo) {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getPincode())) {
				WebElementOperationsWeb.sendKeys(driver, GiftCardJourneyElements.getPincode(),repository.readStringFrom(sheetName, Sheet.GiftCard.PINCODE, serialNo));

			} else {
				throw new IHCLBusinessException("Unable to find Pincode input box[" + GiftCardJourneyElements.getPincode() + "]");
			}
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "EnteringPincode");
			handleOnException("Error occured while sending Pincode [" + GiftCardJourneyElements.getPincode() + "]", e);
		}
	}
	
	//Click on Myself Button
	public void clickonMyselfButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getMyselfButton())) {
				WebElementOperationsWeb.scrollTo(driver, GiftCardJourneyElements.getMyselfButton());
				WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getMyselfButton());
			} else {
				throw new IHCLBusinessException("Myself Button[" + GiftCardJourneyElements.getMyselfButton() + IS_NOT_DISPLAYED);
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on Myself Button: "+ GiftCardJourneyElements.getQuantity(), e);
		}
	}
	
	//Click on Preview Gift Card Button
	public void clickonPreviewGiftCardButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getPreviewGiftCardButton())) {
				WebElementOperationsWeb.scrollTo(driver, GiftCardJourneyElements.getPreviewGiftCardButton());
				WebElementOperationsWeb.park(2);
				WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getPreviewGiftCardButton());
			} else {
				throw new IHCLBusinessException("Preview GiftCard Button["+ GiftCardJourneyElements.getPreviewGiftCardButton() + IS_NOT_DISPLAYED);
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on Preview GiftCard Button: "+ GiftCardJourneyElements.getPreviewGiftCardButton(), e);
		}
	}
	
	//Select Terms of Use Check box
	public void selectTermsOfUseCheckbox() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getPrivacyPolicyText())) {
				WebElementOperationsWeb.park(2);
				WebElementOperationsWeb.scrollTo(driver, GiftCardJourneyElements.getPrivacyPolicyText());
				WebElementOperationsWeb.executeJSWithRetry(driver, "arguments[0].click()",BookAStayFunctionality.getPrivacyPolicyCheckbox(), 2);
				WebElementOperationsWeb.park(2);
			} else {
				LOG.error("Privacy Policy and Terms of Use Checkbox is not Found");
			}
		} catch (Exception e) {
			throw new IHCLBusinessException("Unknown error occured while clicking on Terms of Use Checkbox: "+ BookAStayFunctionality.getPrivacyPolicyCheckbox(), e);
		}
	}
	
	//Click on Proceed to Payment Button
	public void clickonProceedToPaymentButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getProceedToPaymentButton())) {
				WebElementOperationsWeb.scrollTo(driver, GiftCardJourneyElements.getProceedToPaymentButton());
				WebElementOperationsWeb.park(2);
				WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getProceedToPaymentButton());
			} else {
				throw new IHCLBusinessException("Proceed to Payment Button["+ GiftCardJourneyElements.getProceedToPaymentButton() + IS_NOT_DISPLAYED);
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on Proceed to Payment Button: "+ GiftCardJourneyElements.getProceedToPaymentButton(), e);
		}
	}
	
	//Click on Pay Now Button
	public void clickonPayNowButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getPayNowButton())) {
				WebElementOperationsWeb.scrollTo(driver, GiftCardJourneyElements.getPayNowButton());
				WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getPayNowButton());
				WebElementOperationsWeb.park(5);
			} else {
				throw new IHCLBusinessException("Pay Now Button[" + GiftCardJourneyElements.getPayNowButton() + IS_NOT_DISPLAYED);
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on Pay Now Button: "+ GiftCardJourneyElements.getPayNowButton(), e);
		}
	}
	
	//Click on Select Credit Card
		public void selectCreditCard() {
			try {
				if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getSelectCreditCard())) {
					WebElementOperationsWeb.scrollTo(driver, GiftCardJourneyElements.getSelectCreditCard());
					WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getSelectCreditCard());
					WebElementOperationsWeb.park(5);
				} else {
					throw new IHCLBusinessException("Credit Card option [" + GiftCardJourneyElements.getSelectCreditCard() + IS_NOT_DISPLAYED);
				}
			} catch (Exception e) {
				handleOnException("Unknown error occured while clicking on Credit Card option: "+ GiftCardJourneyElements.getSelectCreditCard(), e);
			}
		}
		
		//Click on Select Credit Card checkbox
				public void selectCreditCardCheckBox() {
					try {
						if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getSecureCardCheckBox())) {
							WebElementOperationsWeb.scrollTo(driver, GiftCardJourneyElements.getSecureCardCheckBox());
							WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getSecureCardCheckBox());
							WebElementOperationsWeb.park(5);
						} else {
							throw new IHCLBusinessException("Credit Card option [" + GiftCardJourneyElements.getSecureCardCheckBox() + IS_NOT_DISPLAYED);
						}
					} catch (Exception e) {
						handleOnException("Unknown error occured while clicking on Credit Card option: "+ GiftCardJourneyElements.getSecureCardCheckBox(), e);
					}
				}
		
	//Enter Card Number
	public void sendCardNumber(String testCaseName, String sheetName, int serialNo) {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getCardNumber())) {
				WebElementOperationsWeb.sendKeys(driver, GiftCardJourneyElements.getCardNumber(),repository.readStringFrom(sheetName, Sheet.CardData.CARD_NUMBER, serialNo) );

			} else {
				throw new IHCLBusinessException("Unable to find Card Number Field ["+ GiftCardJourneyElements.getCardNumber() + "]");
			}
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "PaymentsPage");
			handleOnException("Error occured while sending Card Number [" + GiftCardJourneyElements.getCardNumber() + "]",e);
		}
	}
	
	//Enter expiry month and year
	public void sendExpiryDate(String testCaseName, String sheetName, int serialNo) {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getExpiryMonthYear())) {
				WebElementOperationsWeb.sendKeys(driver, GiftCardJourneyElements.getExpiryMonthYear(),repository.readStringFrom(sheetName, Sheet.CardData.EXPIRY_MONTH, serialNo) );

			} else {
				throw new IHCLBusinessException("Unable to find Expiry Date Field ["+ GiftCardJourneyElements.getExpiryMonthYear() + "]");
			}
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "CVV");
			handleOnException("Error occured while sending Expiry Date [" + GiftCardJourneyElements.getExpiryMonthYear() + "]",e);
		}
	}
	
	//Enter CVV number
	public void sendCVVNumber(String testCaseName, String sheetName, int serialNo) {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getCVV())) {
				WebElementOperationsWeb.sendKeys(driver, GiftCardJourneyElements.getCVV(),repository.readStringFrom(sheetName, Sheet.CardData.CVV, serialNo) );

			} else {
				throw new IHCLBusinessException("Unable to find CVV Number Field ["+ GiftCardJourneyElements.getCVV() + "]");
			}
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "CVV");
			handleOnException("Error occured while sending CVV Number [" + GiftCardJourneyElements.getCVV() + "]",e);
		}
	}
	
	//Click on Proceed to Pay Button
	public void clickonProceedToPayButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getProceedToPayButton())) {
				WebElementOperationsWeb.scrollTo(driver, GiftCardJourneyElements.getProceedToPayButton());
				WebElementOperationsWeb.park(1);
				WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getProceedToPayButton());
			} else {
				throw new IHCLBusinessException("Proceed to Pay Button["+ GiftCardJourneyElements.getProceedToPayButton() + IS_NOT_DISPLAYED);
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on Proceed to Pay Button: "+ GiftCardJourneyElements.getProceedToPayButton(), e);
		}
	}
	
	//Click on Opt Out For Now Button
	public void clickonOptOutForNowButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getOptOutForNowButton())) {
				WebElementOperationsWeb.park(1);
				WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getOptOutForNowButton());
			} else {
				throw new IHCLBusinessException("Opt Out For Now Button[" + GiftCardJourneyElements.getOptOutForNowButton() + IS_NOT_DISPLAYED);
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on Opt Out For Now Button: "+ GiftCardJourneyElements.getOptOutForNowButton(), e);
		}
	}
	
	//Click on Send Response Button
	public void clickonSendResponseButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getSendResponseButton())) {
				WebElementOperationsWeb.scrollTo(driver, GiftCardJourneyElements.getSendResponseButton());
				WebElementOperationsWeb.jsClick(driver, GiftCardJourneyElements.getSendResponseButton());
			} else {
				throw new IHCLBusinessException("Send Response Button[" + GiftCardJourneyElements.getSendResponseButton() + IS_NOT_DISPLAYED);
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on Send Response Button: "+ GiftCardJourneyElements.getSendResponseButton(), e);
		}
	}
	
	// Verifying GiftCard Confirmation Page is displayed
	public void verifyGiftCardConfirmationPage() {
		if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getCongratulationsLabel())) {
			LOG.info("GiftCard Confirmation Page is Displayed");
		} else {
			throw new IHCLBusinessException("GiftCard Confirmation Page [" + GiftCardJourneyElements.getCongratulationsLabel() + IS_NOT_DISPLAYED);
		}
	}
	
	// Verifying Order Number is displayed
	public void verifyOrderNumber() {
		if (WebElementOperationsWeb.isDisplayed(driver, GiftCardJourneyElements.getOrderNumber())) {
			LOG.info("Order Id number is Displayed");
		} else {
			throw new IHCLBusinessException("Order ID ["+ GiftCardJourneyElements.getOrderNumber() + IS_NOT_DISPLAYED);
		}
	}
}
