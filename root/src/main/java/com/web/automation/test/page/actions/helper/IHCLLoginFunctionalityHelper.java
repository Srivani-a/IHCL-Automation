package com.web.automation.test.page.actions.helper;

import java.util.List;
import java.util.Objects;

import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ihclweb.automation.loginfunctionalitycheck.model.IHCLLoginFunctionality;
import com.ihclweb.automation.util.exceptions.LoginActionException;
import com.techouts.ihclweb.webelement.ops.WebElementOperationsWeb;
import com.web.automation.test.handler.GlobalExceptionHandler;

public class IHCLLoginFunctionalityHelper extends GlobalExceptionHandler {
	// In this class we are performing operations on the web element

	private WebDriver driver = null;
	private static final Logger LOG = Logger.getLogger(IHCLLoginFunctionalityHelper.class);
	
	public IHCLLoginFunctionalityHelper(WebDriver driver) {
		this.driver = Objects.requireNonNull(driver, "WebDriver cannot be null to perform actions in Login Functionality Actions Helper class");
	}

	// Clicking on the Login / Join button
	public void clickOnLoginButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, IHCLLoginFunctionality.getSignIn())) {
				WebElementOperationsWeb.click(driver, IHCLLoginFunctionality.getSignIn());
			} else {
				LOG.error("Login Element not Found");
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking on Login Button: " + IHCLLoginFunctionality.getSignIn(), e);
		}
	}
	
	// Enter mobile number
	public void enterMobileNumber(String token) {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, IHCLLoginFunctionality.getMobileNumber())) {
				WebElementOperationsWeb.sendKeys(IHCLLoginFunctionality.getMobileNumber(), token);
				if (WebElementOperationsWeb.isDisplayed(driver, IHCLLoginFunctionality.getIncorrectMobileNumber())) {
					throw new LoginActionException("Unable to perform login operation due to Incorrect Mobile Number:"+ IHCLLoginFunctionality.getIncorrectMobileNumber() + " Error");
				}
			} else {
				LOG.error("Mobile number element not found");
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while entering Mobile number: " + IHCLLoginFunctionality.getMobileNumber(),e);
		}
	}
	
	//Select Terms of Use Check box
	public void selectTermsOfUseCheckbox() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, IHCLLoginFunctionality.getTermsCheckBox())) {
				WebElementOperationsWeb.click(driver, IHCLLoginFunctionality.getTermsCheckBox());
			} else {
				LOG.error("Terms Checkbox is not Found");
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking Terms of Use Checkbox: "+ IHCLLoginFunctionality.getTermsCheckBox(), e);
		}
	}
	
	// Click on Proceed
	public void clickOnContinueButton() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, IHCLLoginFunctionality.getContinueButton())) {
				WebElementOperationsWeb.park(2);
				WebElementOperationsWeb.jsClick(driver, IHCLLoginFunctionality.getContinueButton());
			} else {
				LOG.error("Continue button is not found");
			}
		} catch (Exception e) {
			handleOnException("Unknown error occured while clicking Password Block: "+ IHCLLoginFunctionality.getContinueButton(), e);
		}
	}

	String otpNum = "214263";
	
	public void enterOtp() {
		try {
			WebElementOperationsWeb.park(5);
			List<WebElement> otp = driver.findElements(By.xpath("//form[contains(@class,'MuiBox-root css-l484w7')]/div/div/div/input"));
			WebElementOperationsWeb.park(2);
			for (int i = 0; i < otp.size(); i++) {
				WebElementOperationsWeb.sendKeys(otp.get(i), String.valueOf(otpNum.charAt(i)));
			}
		} catch (Exception e) {
			handleOnException("Error occured while entering otp", e);
		}
	}	
}
