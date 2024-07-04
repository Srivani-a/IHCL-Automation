package com.web.automation.test.page.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ihclweb.automation.util.exceptions.IHCLBusinessException;
import com.techouts.ihclweb.webelement.ops.WebElementOperationsWeb;
import com.web.automation.test.handler.GlobalExceptionHandler;
import com.web.automation.test.page.actions.helper.IHCLLoginFunctionalityHelper;

public class IHCLLoginFunctionalityAction extends GlobalExceptionHandler {

	private WebDriver driver = null;
	private IHCLLoginFunctionalityHelper ihclLoginFunctionalityHelper;

	public IHCLLoginFunctionalityAction(WebDriver driver) {
		this.driver = Objects.requireNonNull(driver,"WebDriver cannot be null to perform actions in Login Actions class");
		ihclLoginFunctionalityHelper = new IHCLLoginFunctionalityHelper(driver);  
	}

	public void clickOnLogin() {
		try {
			WebElementOperationsWeb.park(4);
			ihclLoginFunctionalityHelper.clickOnLoginButton();
			WebElementOperationsWeb.park(4);
		} catch (Exception e) {
			handleOnException("Unable to click on Login/Join button", e);
		}
	}
	
	public void loginUsingMobileNumber(String mobileNumber, String testCaseName) {
		try {
			WebElementOperationsWeb.park(1);
			ihclLoginFunctionalityHelper.enterMobileNumber(mobileNumber);
			ihclLoginFunctionalityHelper.selectTermsOfUseCheckbox();	
			WebElementOperationsWeb.park(3);
			WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "MobileNumber");
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "MobileNumber");
			handleOnException("Error occured while entering mobile number", e);
		}
	}

	/*
	 * public void LoginFunctionalityEnterOTP(String testCaseName) { try {
	 * 
	 * WebElementOperationsWeb.park(15);
	 * ssbLoginFunctionalityHelper.enterOtp(testCaseName);
	 * WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName,
	 * "Logged in Successfully"); } catch (Exception e) {
	 * handleOnException("Error occured while entering otp", e); } }
	 */

	public void clickonContinueButton(String testCaseName) {
		try {
			ihclLoginFunctionalityHelper.clickOnContinueButton();
			WebElementOperationsWeb.park(2);
			WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "OTPPage");
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "OTPPage");
			handleOnException("Error occured while clicking continue buton", e);
		}
	}

	public void enterOtp(String testCaseName) {
		try {
			WebElementOperationsWeb.park(2);
			ihclLoginFunctionalityHelper.enterOtp();
			WebElementOperationsWeb.park(6);
			WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "EnteringOTP");
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "EnteringOTP");
			handleOnException("Error occured while entering otp", e);
		}
	}
	
}
