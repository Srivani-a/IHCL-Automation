package com.techouts.ihclweb.testscripts.loginfunctionality;

import java.util.Objects;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ihclweb.automation.dto.credentials.User;
import com.ihclweb.automation.loginfunctionalitycheck.model.LoginFunctionalityCheck;
import com.ihclweb.automation.provider.credential.CredentialProvider;
import com.web.automation.test.AbstractTest;
import com.web.automation.test.page.actions.IHCLLoginFunctionalityAction;
import com.techouts.ihclweb.webelement.ops.WebElementOperationsWeb;

public class IHCLLoginFunctionalityMobileNumberTest extends AbstractTest {

	private IHCLLoginFunctionalityAction ihclLoginActions;
	private static final String TEST_CASE_NAME = "Login Functionality using Mobile Number";

	public IHCLLoginFunctionalityMobileNumberTest() {
		new LoginFunctionalityCheck().init(DRIVER);
		ihclLoginActions = new IHCLLoginFunctionalityAction(DRIVER);
	}

	@BeforeMethod
	public void openIHCLPage() {
		getIhclPage();
	}

	@Test(testName = TEST_CASE_NAME, description = "User Login Functionality ")
	public void verifyLogin() {
		try {

			User mobilelogin = Objects.requireNonNull(CredentialProvider.getUser("E003"),
					"Mobile Login credential should not be null");

			ihclLoginActions.clickOnLogin();
			ihclLoginActions.loginUsingMobileNumber(mobilelogin.getMobileno(), TEST_CASE_NAME);
			ihclLoginActions.clickonContinueButton(TEST_CASE_NAME);
			ihclLoginActions.enterOtp(TEST_CASE_NAME);
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(DRIVER, TEST_CASE_NAME, "VerifyMobileLogin");
			//getIhclPage();
			//WebElementOperationsWeb.waitForPageLoad(DRIVER, 40);
			//LOGIN_ACTIONS.doLogout();
			addRetryTest(new Object() {
			}.getClass().getEnclosingMethod().getName());
			handleOnException("Error occured while Login with MobileNumber", e);
		}
	}
}
