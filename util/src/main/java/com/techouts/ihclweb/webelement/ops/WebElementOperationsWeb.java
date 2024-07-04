package com.techouts.ihclweb.webelement.ops;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ihclweb.automation.consts.IHCLConstantsWeb;
import com.ihclweb.automation.registry.Registry;
import com.ihclweb.automation.registry.RegistryKey;
import com.ihclweb.automation.util.Assert;
import com.ihclweb.automation.util.CommonUtils;
import com.ihclweb.automation.util.PathProvider;
import com.ihclweb.automation.util.PropertyUtil;
import com.ihclweb.automation.util.exceptions.IHCLBusinessException;
import com.ihclweb.automation.util.exceptions.WebElementOperationException;
import com.techouts.ihclweb.webelement.functional.TriConsumer;



/**
 * 
 * @author rajeevsai.t
 *
 */
public class WebElementOperationsWeb {

	protected static final int UNHANDLED_ALERT_RETRY;
	protected static int unHandledRetryCount = 0;
	private static final String SCREENSHOT_TIME_FORMAT = "ddMMYYYYHHmmssSSS"; 
	private static int clickRetry = 0;
	private static int clickRetryCount = 0;
	private static final Logger LOG = Logger.getLogger(WebElementOperationsWeb.class.getName());

	protected WebElementOperationsWeb() {}

	static {
		UNHANDLED_ALERT_RETRY = PropertyUtil.getInt("page.default.unhandle-alert.retry",1);
	}
	
	
	/**
	 * This method will enter/send the value to WebElement of TesxtField given
	 */
	public static  void sendKeys(WebElement element, String token) {
		try {
			park(0.3);
			element.clear();
			element.sendKeys(token);
		} catch (Exception e) {
			throwWebElementOperationException("Unable to sendKeys["+token+"] on Web Element["+element+"].", e);
		}
	}
	
	/**
	 * This method will act as per the key value
	 * @param element - Web element
	 * @param key - pressable Key from keyboard
	 */
	public static  void sendKeys(WebElement element, Keys key) {
		try {
			park(0.7);
			element.sendKeys(key);
		} catch (Exception e) {
			throwWebElementOperationException("Unable to sendKeys["+key+"] on Web Element["+element+"].", e);
		}
	}
	
	/**
	 * This method will enter/send the value to WebElement of TesxtField given
	 */
	public static  void sendKeys(WebDriver driver, WebElement element, String token) {
		try {
			park(0.3);
			element.clear();
			element.sendKeys(token);
		} catch(UnhandledAlertException e){
			handleOnAlert(driver, element, token, e, WebElementOperationsWeb::sendKeys);
		} catch (Exception e) {
			throwWebElementOperationException("Unable to sendKeys["+token+"] on Web Element["+element+"].", e);
		}
	}
	
	/**
	 * This method wait until the given wait time to get alert visible
	 */
	public static boolean waitForAlertPresent(WebDriver driver, int seconds) {
		try {
			new WebDriverWait(driver, seconds).until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (Exception e) {
			throwWebElementOperationException("Couldn't find alert on the page, post "+seconds+" seconds WebDriverWait ", e);
		}
		return false;
	}
	
	/**
	 * This method accepts the alert while waiting for the alert to be present with given wait time. 
	 */
	public static boolean acceptAlertIfPresent(WebDriver driver, int seconds){
		try {
			waitForAlertPresent(driver, seconds);
			acceptAlert(driver);
			return true;
		} catch (Exception e) {
			throwWebElementOperationException("Error occurred while performing accepting alert by waiting with wait of "+seconds+" second(s) time", e);
		}
		return false;
	}
	
	/**
	 * This method click on the first argument as "arguments[0].click()" 
	 */
	public static void jsClick(WebDriver driver,WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}
	
	/**
	 * This method scrolls to the position with the points/axis given 
	 */
	public static  void scrollToPOsition(WebDriver driver, int xAxis, int yAxis) {
		executeJS(driver, new StringBuilder("\"window.scrollBy(").append(xAxis).append(',').append(yAxis).append(")").toString());
	}
	
	/**
	 * This method verifies the title of the page
	 */
	public static  boolean verifyPageTitle(WebDriver driver, String expectedTitle){
		String pageTitle = null;
		try {
			pageTitle = driver.getTitle().trim();
			Assert.assertEquals(pageTitle, expectedTitle, "Page title is not as expected.");
			return true;
		}catch (Exception e) {
			LOG.info("Logged in to Application",e);
		}
		return false;
	}
	
	public static String getPageTitle(WebDriver driver) {
		String pageTitle;
		pageTitle = driver.getTitle().trim();
		return pageTitle;
	}

	public static void driverNavigation(String action, WebDriver driver) {
		LOG.info("Performing navigation operations");
		switch (action) {
		case "forward":
			driver.navigate().forward();
			break;
		case "backward":
			driver.navigate().back();
			break;
		default:
			break;
		}
	}
	
	public static  boolean silentPageTitleValidation(WebDriver driver, String expectedTitle){
		boolean isIdentical = false;
		try{
			isIdentical = verifyPageTitle(driver, expectedTitle);
		}catch (Exception e){
			LOG.error(e);
		}
		return isIdentical;
	}
	
	/**
	 * This method wait until current page load
	 */
	public static void waitForPageLoad(WebDriver driver, int seconds) {
		try{
			new WebDriverWait(driver, seconds);
//			new WebDriverWait(driver, seconds).until(a -> executeJS(driver, IHCLConstantsWeb.JS_EXPECTED_CONDITION_RESULT_SCRIPT).equals(IHCLConstantsWeb.COMPLETE));
		}catch(Exception e){
			throwWebElementOperationException("Error occurred while waiting for the page to load with "+seconds+" second(s) wait.", e);
		}
	}
	
	/**
	 * This method waits until element is visible
	 */
	public static void waitForElementVisible(WebDriver driver, int seconds) {
		try{
			new WebDriverWait(driver, seconds);
//			new WebDriverWait(driver, seconds).until(ExpectedConditions.visibilityOf(el));
		}catch(Exception e){
			throwWebElementOperationException("Error occured while waiting for the WebElement"+seconds+" seconds wait.", e);
		}
	}
	
	/**
	 * This method wait until element is click-able
	 */
	public static void waitForElementClickable(WebDriver driver, int seconds, WebElement el) {
		try{
			new WebDriverWait(driver, seconds).until(ExpectedConditions.elementToBeClickable(el));
		}catch(Exception e){
			throwWebElementOperationException("Error occurred while waiting for the WebElement["+el+"] to be clickable with "+seconds+" seconds wait.", e);
		}
	}
	
	/**
	 * This method select the value based on index
	 */
	public static  void dropDownByIndex(WebElement element, int index) {
		try {
			new Select(element).selectByIndex(index);
		} catch (Exception e) {
			throwWebElementOperationException("Error occurred while performing drop-down click operation by Index["+index+"] on WebElement:"+element,e);
		}
	}
	
	/**
	 * This method select the value based on inner text
	 */
	public static  void dropDownByTextVisibility(WebElement element, String visibleTxt) {
		try {
			new Select(element).selectByVisibleText(visibleTxt);
		} catch (Exception e) {
			throwWebElementOperationException("Error occurred while performing drop-down select operation by TextVisibility["+visibleTxt+"] on WebElement:"+element,e);
		}
	}
	
	/**
	 * This method select the value based on inner text
	 */
	/*public static  void dropDownByIndex(WebElement element) {
		try {
			new Select(element).selectByIndex(0);
		} catch (Exception e) {
			throwWebElementOperationException("Error occurred while performing drop-down select operation by Index on WebElement:"+element,e);
		}
	}*/
	/**
	 * This method switch to present/default frame
	 */
	public static void switchToDefaultContentFrame(WebDriver driver) {
		try {
			driver.switchTo().defaultContent();
		}catch(Exception e) {
			throwWebElementOperationException("Error occurred while switching to default ContentFrame.",e);
		}
	}
	
	/**
	 * This method switch to frame based on the index
	 */
	public static void switchToFrame(WebDriver driver, int index) {
		try {
			driver.switchTo().frame(index);
		}catch(Exception e) {
			throwWebElementOperationException("Error occurred while switching to frame with index["+index+"].",e);
		}
	}
	
	/**
	 * This method clicks on element
	 */
	public static  void click(WebElement element) {
		try {
			element.click();
		}catch (Exception e) {
			throwWebElementOperationException("Error occurred while performing click operation on WebElemet:"+element,e);
		}
	}
	
	public static void click(WebDriver driver, WebElement element) {
		try {
			element.click();
		} catch(UnhandledAlertException e){
			handleOnAlert(driver, element, e, WebElementOperationsWeb::click);
		} catch (Exception e) {
			throwWebElementOperationException("Error occurred while performing click operation on WebElemet:"+element,e);
		}
	}
	public static void clear(WebDriver driver, WebElement element) {
		try {
			element.clear();
		} catch(UnhandledAlertException e){
			handleOnAlert(driver, element, e, WebElementOperationsWeb::clear);
		} catch (Exception e) {
			throwWebElementOperationException("Error occurred while clearing on WebElemet:"+element,e);
		}
	}
	public static void handleOnAlert(WebDriver driver, WebElement element, Throwable e, BiConsumer<WebDriver, WebElement> bi){
		if(unHandledRetryCount <= UNHANDLED_ALERT_RETRY){
			LOG.info("Alert-Action: Retrying operation on WebElement["+element+"] for "+(unHandledRetryCount+1)+" time.");
			acceptAlertQuitely(driver);
			unHandledRetryCount++;
			bi.accept(driver, element);
		}else{
			elseImpl(element, e);
		}
	}
	
	public static void handleOnAlert(WebDriver driver, WebElement element, String token, Throwable e, TriConsumer<WebDriver, WebElement, String> tri){
		if(unHandledRetryCount <= UNHANDLED_ALERT_RETRY){
			LOG.info("Alert-Action: Retrying operation on WebElement["+element+"] for "+(unHandledRetryCount+1)+" time.");
			acceptAlertQuitely(driver);
			unHandledRetryCount++;
			tri.consume(driver, element, token);
		}else{
			elseImpl(element, e);
		}
	}
	
	private static void elseImpl(WebElement element, Throwable cause){
		LOG.warn("Handling unexpected alert limit has reached, ignoring retry on WebElement["+element+"].");
		unHandledRetryCount =0;
		throwWebElementOperationException("Error occurred while performing operation for AlertRetry on WebElemet:"+element +" with retryies:"+UNHANDLED_ALERT_RETRY,cause);
	}
	
	private static void acceptAlertQuitely(WebDriver driver){
		try{
			acceptAlert(driver);
		}catch(Exception e){
			LOG.error("Error occurred while accepting alert quitely with StackTrace:"+e);
		}
	}
	
	/**
	 * This method Highlights the element
	 */
	public static  void webElementHighlighter(WebDriver driver, WebElement element) {
		executeJS(driver, IHCLConstantsWeb.HIGHLIGHTER_TOKEN, element);
	}
	
	/**
	 * This method Highlights the element
	 */
	public static  void webElementsHighlighter(WebDriver driver, List<WebElement> elements) {
		if(CollectionUtils.isNotEmpty(elements)) {
			elements.stream().filter(Objects::nonNull).forEach(a -> webElementHighlighter(driver, a));
		}else {
			LOG.warn("List of WebElements found empty to perform highlight operation");
		}
	}
	
	/**
	 * This method click on element mentioned in script(js)
	 */
	public static Object executeJS(WebDriver driver, String script) {
		return executeJS(driver, script, null);
	}
	
	/**
	 * This method executes the given Javascript on the WebElement(if the WebElement is passed) or directly on the driver/current page.
	 */
	public static Object executeJS(WebDriver driver, String script, WebElement element) {
		Object result = null;
		try {
			if (Objects.nonNull(driver) && (driver instanceof JavascriptExecutor) && StringUtils.isNotBlank(script)) {
				if (Objects.nonNull(element)) {
					result = ((JavascriptExecutor) driver).executeScript(script, element);
				} else {
					result = ((JavascriptExecutor) driver).executeScript(script);
				}
			}
		}catch(Exception e) {
			throwWebElementOperationException("Error occurred while executing Javascript["+script+"] on WebElement["+element+"]", e);
		}
		return result;
	}
	
	public static void executeJSWithRetry(WebDriver driver, String script, WebElement element, int retry) {
		try {
			if (clickRetry == 0 && retry > 0) {
				clickRetry = retry;
			}
			executeJS(driver, script, element);
		} catch (Exception e) {
			if (clickRetry > 0 && clickRetryCount <= clickRetry) {
				clickRetryCount++;
				LOG.info("Retrying executeJS for " + clickRetryCount + " time. with JSScript:" + script);
				executeJSWithRetry(driver, script, element, clickRetry);
			} else {
				if(clickRetry != 0) {
					clickRetry = 0;
				}
				throwWebElementOperationException("Error occurred while executing Javascript[" + script + "] on WebElement[" + element + "] With " + retry + " retries",e);
			}
		}
	}
	
	
	/**
	 * This method is to throw an exception with the given values.
	 */
	private static  void throwWebElementOperationException(String msg, Throwable throwable){
		String errMsg = "An unknown error occurred";
		if(StringUtils.isNotBlank(msg) && Objects.nonNull(throwable)) {
			LOG.error(msg,throwable);
		}
		else if(StringUtils.isNotBlank(msg)) {
			LOG.error(msg);
		}
		else if(Objects.nonNull(throwable)) {
			LOG.error(errMsg,throwable);
		}
		else {
			LOG.error(errMsg);
		}
		throw new WebElementOperationException(StringUtils.isNotBlank(msg) ? msg : errMsg);
	}

	/**
	 * this method captures the screenshot
	 */
	public static Path captureScreenShot(WebDriver driver, String testCaseName, boolean isPassed, String filename) {
		Path path = null;
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			path = prepareScreenshotPath( isPassed, testCaseName, filename); 
			FileUtils.copyFile(scrFile, path.toFile());
			if(LOG.isDebugEnabled()) {
				LOG.debug("Captured screen on Test "+(isPassed ? IHCLConstantsWeb.PASS : (IHCLConstantsWeb.FAIL+" at Location:"+path.toString())));
			}
		} catch (Exception e) {
			throwWebElementOperationException("Error occurred while taking "+(isPassed ? IHCLConstantsWeb.PASSED : IHCLConstantsWeb.FAILED)+" screenshot at Path["+path+"] for TestCaseName:"+testCaseName,e);
		}
		return path;
	}
	
	/**
	 * This method capture ScreenShot with file name contains Pass as label 
	 * and stores the image in Passed directory
	 */
	public static  Path captureScreenShotOnPass(WebDriver driver, String testCaseName, String filename) {
		return captureScreenShot(driver, testCaseName, true, filename);
	}
	
	/**
	 * This method capture ScreenShot with file name contains Fail as label 
	 * and stores the image in Failed directory
	 */
	public static  Path captureScreenShotOnFail(WebDriver driver, String testCaseName, String filename) {
		return captureScreenShot(driver, testCaseName, false, filename);
	}
	
	/**
	 * This method prepares the screenshot path based on the test case result(i.e., pass or fail)
	 */
	private static Path prepareScreenshotPath(boolean isPassed, String testName, String msg){
		return prepareFile(Paths.get(PathProvider.getCurExecScreenshotRootDir((String)Registry.getAttribute(RegistryKey.DIR_TIMESTAMP_SUFFIX)).toString(), isPassed ? IHCLConstantsWeb.PASSED : IHCLConstantsWeb.FAILED), testName, msg);
	}
	
	public static Path prepareFile(Path resultDir, String testName, String filename){
		Path testDir = Paths.get(resultDir.toString(), PropertyUtil.getBoolean("screenshot.test.dir-name.force-uppercase", false) ? testName.trim().toUpperCase() : testName.trim());
		try{
			FileUtils.forceMkdir(testDir.toFile());
			return Paths.get(testDir.toString(), prepareFileName(filename));
		}catch(Exception e){
			LOG.error("Error occurred while creating specific screenshot folder for individual test at: "+testDir,e);
			return Paths.get(resultDir.toString(), prepareFileName(testName.trim().toUpperCase(), filename.trim().toUpperCase()));
		}
	}
	
	private static String prepareFileName(String... filename){
		StringJoiner joiner = new StringJoiner("-");
		Arrays.stream(filename).filter(StringUtils::isNotBlank).forEach(joiner::add);
		return joiner.add(CommonUtils.getTimestamp(SCREENSHOT_TIME_FORMAT)+IHCLConstantsWeb.DOT_JPG).toString();
	}
	
	/**
	 * This method accepts the alert on the page
	 */
	public static void acceptAlert(WebDriver driver){
		try{
			driver.switchTo().alert().accept();
		} catch(Exception e){
			throwWebElementOperationException("Error occurred while handling alert.", e);
		}
	}
	
	/**
	* This method will refresh the current page
	* @param driver
	*/
	public static void refreshPage(WebDriver driver) {
		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			throwWebElementOperationException("Unable to refresh the current page:", e);
		}
	}

	/**
	 * this method parse/reads the text from web element by waiting 30 seconds Webdriver wait.
	 */
	public static String getText(WebDriver driver, WebElement element) {
		String text = null;
		int wait = 30;
		try {
			waitForElementVisible(driver, wait);
			text = element.getText();
		} catch (Exception ex) {
			throwWebElementOperationException("Error occurred while parsing text from WebElement["+element+"] with "+wait+" second(s) wait for text visibility.", ex);
		}
		return text;
	}
	
	/**
	 * This method parse/reads the text from alert
	 */
	public static String alertText(WebDriver driver) {
		try {
			return driver.switchTo().alert().getText();
		} catch (Exception e) {
			throwWebElementOperationException("Error occurred while parsing/reading the text from alert", e);
		}
		return null;
	}
	
	/**
	 * This method to checks whether the attribute value has greater than zero.
	 */
	public static boolean verifyAttributeValueForGTZero(WebElement element) {
		float val = Float.parseFloat(element.getAttribute("value"));
		return (val > 0f) ? Boolean.TRUE.booleanValue() : Boolean.FALSE.booleanValue();
	}
	
	/**
	 * This method checks whether element attribute value is empty
	 */
	public static boolean containsEmptyAttributeValue(WebElement element) {
		String val = getAttributeValue(element);
		return StringUtils.isBlank(val);
	}
	
	/**
	 * This method gets the attribute value on element
	 */
	public static  String getAttributeValue(WebElement element) {
		return element.getAttribute("value");
	}
	
	//This method converts the milliseconds to Duration String like 00:00:00 format.
	public static String convertMillis(Long millis){
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis =millis-TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        return String.format("%02d:%02d:%02d",hours, minutes,seconds);
	}
	
	/**
	 * This method makes current thread to sleep till the given seconds.
	 */
	public static void park(double seconds) {
		try{
			Thread.sleep((int)(seconds * 1000));
		}catch(Exception e) {
			throwWebElementOperationException("Unknown error occurred while waiting for "+seconds +" seconds.", e);
		}
	}
	
	/**
	 * This method checks for the element visibility, if it is visible it highlights the element
	 */
	public static boolean isDisplayed(WebDriver driver,WebElement element) {
		boolean isDisplayed = false;
		try{
			park(0.3);
			if(PropertyUtil.getBoolean("explicit-wait.before.displaycheck", true)) {
				waitForElementVisible(driver, 3);
			}
			isDisplayed = element.isDisplayed();
			if(isDisplayed) {
				webElementHighlighter(driver, element);
			}
		}catch(Exception e) {
			LOG.error("Error occurred while checking for the WebElement:["+element+"] visibility.",e);
		}
		return isDisplayed;
	}
	
	/**
	 * This method is used for mouse scrolling by action
	 */
	public static void scrollingByAction(WebDriver driver,WebElement element) {
		try{
			new Actions(driver).moveToElement(element).build().perform();
		}catch(Exception e){
			throwWebElementOperationException("Error occuurred while scrollingByAction on WebElemenet["+element+"].", e);
		}
	}
	
	/**
	 * this method to select the suffixIncrements
	 */
	public static String incrementSuffixBy(String name, int increment){
		String result = null;
		try {
			result =  name.replaceAll("[^A-Za-z]", "") + Integer.toString(Integer.parseInt(name.replaceAll("[^0-9]", "")) + increment);
		} catch (Exception e) {
			throwWebElementOperationException("Error occurred while increamenting the given string suffix by "+increment, e);
		}
		return result;
	}
	
	/**
	 * This method performs enter operation by Robot class
	 */
	public static boolean enterByRobot() {
		boolean isEntered = false;
		try {
			park(0.5);
			new Robot().keyPress(KeyEvent.VK_ENTER);
			isEntered = true;
		} catch (Exception e) {
			throwWebElementOperationException("Error occurred while performing enter operation by Robot", e);
		}
		return isEntered;
	}
	
	public static void enterKeysWithEnter(WebDriver driver,WebElement element,String token) {
		try {
			park(0.3);
			element.clear();
			element.sendKeys(token,Keys.ENTER);
		} catch (Exception e) {
			throwWebElementOperationException("Error occurred while performing enter operation by Robot", e);
		}
	}
	//this method for MouseActions	
	public static void mouseaction(WebDriver driver,WebElement element)
	{
		try {
			new Actions(driver).moveToElement(element).build().perform();
		} catch (Exception e) {
			throwWebElementOperationException("Unable to perform mouse hover operation on WebElemet:"+element, e);	 
		}
	}
	
	public static void mouseRightClick(WebDriver driver,WebElement element)
	{
		try {
			new Actions(driver).contextClick(element).perform();
		} catch (Exception e) {
			throwWebElementOperationException("Unable to perform right click operation on WebElemet:"+element, e);	 
		}
	}
	
	/**
	 * This method performs Mouse Hover action on supplied WebElement
	 */
	public static boolean mouseOver(WebDriver driver, WebElement element) {
		boolean isHovered = false;
		try {
			new Actions(driver).moveToElement(element).build().perform();
			isHovered = true;
		} catch (Exception e) {
			throwWebElementOperationException("Unable to mouse over on WebElement:"+element, e);
		}
		return isHovered;
	}
	
	public static void mouseHoverAction(WebDriver driver,WebElement mainElement, WebElement subElement) {
		try {
			Thread.sleep(2000);
			new Actions(driver).moveToElement(mainElement).build().perform();
			Thread.sleep(5000);
			jsClick(driver,subElement);

		} catch (Exception e) {
			throwWebElementOperationException("Unable to mouse over on WebElement:"+subElement, e);
		}

	}
	/**
	 * this method performs Doube-Click on the given WebElement
	 */
	public static void doubleClick(WebDriver driver, WebElement element) {
		try {
			webElementHighlighter(driver, element);
			park(0.3);
			new Actions(driver).doubleClick(element).perform();
		} catch (Exception e) {
			throwWebElementOperationException("Unable to perform click operation on WebElemet:"+element, e);
		}
	}
	
	
	/**
	 * this method performs Scrolling till the given WebElement displayed 
	 */
	public static void scrollTo(WebDriver driver, WebElement element) {
		try {
			executeJS(driver, "arguments[0].scrollIntoView();", element);
		} catch (Exception e) {
			throwWebElementOperationException("Unable to perform Scrool down operation on WebElemet:"+element, e);
		}
	}
	
	public static void scrollingToPageBottom(WebDriver driver,WebElement element) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight || document.documentElement.scrollHeight)", element);
		Thread.sleep(5000);
	}
	/**
	 * this method checks whether the given WebElement is visible or not 
	 */
	public static boolean isElementVisible(WebElement webElement) {
		boolean isElementFound = false;
		try {
			isElementFound = webElement.isDisplayed();
		} catch (Exception e) {
			throwWebElementOperationException("Error occurred while checking for the WebElement["+webElement+"] visibility",e);
		}
		return isElementFound;
	}
	
	public static boolean btnEnableVerification(WebElement webElement) {
		boolean isElementEnable = false;
		try {
			isElementEnable = webElement.isEnabled();
		} catch (Exception e) {
			throwWebElementOperationException("Error occurred while checking for the WebElement["+webElement+"] visibility",e);
		}
		return isElementEnable;
	}
	//This method used to remove the attribute
	public static void jsSendkeys1(WebDriver driver,WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].removeAttribute('autocomplete')", element);
	}
	
	/**
	 *This method switches to the different windows within the available handles. 
	 */
	public static void windowHandle(WebDriver driver) {
		Set<String> handles = driver.getWindowHandles();
		if(CollectionUtils.isNotEmpty(handles)) {
			handles.stream().filter(Objects::nonNull).forEach(a -> driver.switchTo().window(a));
		}
	}
	
	public static void openingNewTab(WebDriver driver) {
	JavascriptExecutor executor = (JavascriptExecutor) driver;
	executor.executeScript("window.open()");
	}
	
	/**
	 *This method switches to the child to parent window
	 */
	public static void handleParentTab(WebDriver driver,int arrayNum){
		  try {
			  Set<String> allWindowHandles = driver.getWindowHandles();
			  String parent = (String) allWindowHandles.toArray()[arrayNum];
			  driver.switchTo().window(parent);
		} catch (Exception e) {
			throwWebElementOperationException("Could not able to navigate to parent window", e);
		}
	}
	public static void handleSecondTab(WebDriver driver){
		  try {
			  Set<String> allWindowHandles = driver.getWindowHandles();
			  String parent = (String) allWindowHandles.toArray()[1];
			  driver.switchTo().window(parent);
		} catch (Exception e) {
			throwWebElementOperationException("Could not able to navigate to parent window", e);
		}
	}
	
	/**
	 *This method performs the color-validation 
	 */
	public static boolean isColorIdentical(WebDriver driver, WebElement element, String expectedColor, String attributeVal) {
		boolean isColorIdentical = false;
		try {
			String actualcolor = element.getCssValue(attributeVal);
			if (actualcolor.equals(expectedColor)) {
				isColorIdentical = true;
				WebElementOperationsWeb.isDisplayed(driver, element);
			}
		} catch (Exception e) {
			throwWebElementOperationException("Error occurred while performing color vaidation with WebElement:"+element+" , ExpectedColor:"+expectedColor+" on CSSAttribute:"+attributeVal, e);
		}
		return isColorIdentical;
	}
	
	public static List<String> commaSaperatedValuesDataReader(String data) {
		LOG.info(data);
		return Arrays.asList(data.split("\\s*,\\s*"));
	}
	
	public static void newWindowActionURLValidateWithURL(WebDriver driver, String url) {
		try {
			String newWindowURL = null;
			String handle = driver.getWindowHandle();
			Set<String> handles = driver.getWindowHandles();
			for (String handle1 : handles) {
				LOG.info("handling " + handle1);
				driver.switchTo().window(handle1);
			}
			waitForPageLoad(driver, 10);
			newWindowURL = driver.getCurrentUrl();
			LOG.info("Actual url " + newWindowURL);
			if (!(newWindowURL.contains(url) || url.contains(newWindowURL))) {
				LOG.info("Url mismatched. Current URL is : " + newWindowURL);
			}
			driver.close();
			park(2);
			driver.switchTo().window(handle);
			waitForPageLoad(driver, 20);
		} catch (Exception e) {
			LOG.error("Page Header not found on the page");
			throw new IHCLBusinessException();
		}

	}
	
	public static void openWebPage(WebDriver driver, String url) {
		
		driver.get(url);
	}

	
}