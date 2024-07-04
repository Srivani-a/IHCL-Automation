package com.ihclweb.automation.login.actions;

import java.util.Objects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.ihclweb.automation.bookastay.model.BookAStayFunctionality;
import com.ihclweb.automation.util.exceptions.LoginActionException;
import com.techouts.ihclweb.webelement.ops.WebElementOperationsWeb;
import com.web.automation.test.handler.GlobalExceptionHandler;

public class WebLoginActions extends GlobalExceptionHandler {

	private static final Logger LOG = Logger.getLogger(WebLoginActions.class);

	private static final String LOGIN_ACTION = "LoginAction";
	private static final String LOGOUT_SUCCESS = "Logout-Successful";
	private static final String LOGOUT_FAIL = "Logout-Failed";
	private WebDriver driver = null;
	private static WebLoginActions loginActions = null;

	private WebLoginActions(WebDriver driver) {
		this.driver = driver;
	}

	public static WebLoginActions instance(WebDriver driver) {
		if (Objects.isNull(loginActions)) {
			loginActions = new WebLoginActions(
					Objects.requireNonNull(driver, "Webdriver cannot not be null to instantiate LoginActions class"));
		}
		return loginActions;
	}
	/*
	 * public void doLogin(String user, String password) { try{ performLogin(user,
	 * password); WebElementOperationsWeb.waitForPageLoad(driver, 50);
	 * WebElementOperationsWeb.captureScreenShotOnPass(driver, LOGIN_ACTION,
	 * LOGIN_SUCCESS); }catch(Exception e){
	 * WebElementOperationsWeb.captureScreenShotOnFail(driver, LOGIN_ACTION,
	 * LOGIN_FAIL); handleOnException("Unknown error occurred while logging-in.",
	 * e); } }
	 * 
	 * private void performLogin(String user, String password) { expandAccount();
	 * clickSigninLink(); sendUsername(Objects.requireNonNull(user,
	 * "Username cannot be null to perform login operation."));
	 * sendPasscode(Objects.requireNonNull(password,
	 * "Password cannot be null to perform login operation.")); doSubmit(); }
	 */

	public void doLogout() {
		try {
			WebElementOperationsWeb.park(1);
			expandAccount();
			WebElementOperationsWeb.park(2);
			clickLogout();
			WebElementOperationsWeb.captureScreenShotOnPass(driver, LOGIN_ACTION, LOGOUT_SUCCESS);

		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, LOGIN_ACTION, LOGOUT_FAIL);
			handleOnException("Unknown error occurred while logging out.", e);
		}
	}

	private void expandAccount() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getMyAccountIconHomePage())) {
				WebElementOperationsWeb.click(driver, BookAStayFunctionality.getMyAccountIconHomePage());
			} else {
				throw new LoginActionException("Unable to perform logout operation due to Account section WebElement:"
						+ BookAStayFunctionality.getMyAccountIconHomePage() + " visibility issue");
			}
		} catch (Exception e) {

			throw new LoginActionException(
					"Error occurred while clicking on Account Section to perform logout operation on WebElement:"
							+ BookAStayFunctionality.getMyAccountIconHomePage() + e.getMessage());
		}
	}

	private void clickLogout() {
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, BookAStayFunctionality.getLogoutButton())) {
				WebElementOperationsWeb.click(BookAStayFunctionality.getLogoutButton());
			} else {
				throw new LoginActionException("Unable to click on logout button[WebElement:"
						+ BookAStayFunctionality.getLogoutButton() + "] due to visibility issue");
			}
		} catch (Exception e) {
			throw new LoginActionException("Error occurred while performing logout operation on WebElement:"
					+ BookAStayFunctionality.getLogoutButton() + e.getMessage());
		}
	}
}
