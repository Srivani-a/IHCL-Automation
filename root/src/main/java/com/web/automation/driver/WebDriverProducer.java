package com.web.automation.driver;

import java.nio.file.Path;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.MapUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import com.ihclweb.automation.consts.IHCLConstantsWeb;
import com.ihclweb.automation.enums.Browser;
import com.ihclweb.automation.registry.Registry;
import com.ihclweb.automation.util.CommonUtils;
import com.ihclweb.automation.util.PathProvider;
import com.ihclweb.automation.util.PropertyUtil;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class WebDriverProducer {
	
	private WebDriverProducer() {}
	
	private static Map<Browser, WebDriver> drivers = new EnumMap<>(Browser.class);
	private static final Logger LOG = Logger.getLogger(WebDriverProducer.class.getName());
	private static final Path DOWNLOADS;

	static{
		LOG.debug("Setting up system properties for Selenium WebDriver.");
		System.setProperty(IHCLConstantsWeb.CHROME_SYS_PROP_KEY, PathProvider.getForDriver(Browser.CHROME).toString());
		System.setProperty(IHCLConstantsWeb.FIREFOX_SYS_PROP_KEY, PathProvider.getForDriver(Browser.FIREFOX).toString());
		System.setProperty(IHCLConstantsWeb.IE_SYS_PROP_KEY, PathProvider.getForDriver(Browser.IE).toString());
		 DOWNLOADS = PathProvider.getForDownloads();
	}
	
	public static WebDriver getWebDriver(Browser browser) {
		WebDriver webdriver = null;
		if(Objects.nonNull(browser)) {
				if(drivers.containsKey(browser)) {
					LOG.info("Returning already available WebDriver["+drivers.get(browser)+"] from WebDriverRegistry.");
					webdriver = drivers.get(browser);
				}
				else {
					webdriver = initBrowser(browser);
					LOG.info("No WebDriver found in the WebDriverRegistry for ["+browser.toString()+"], Creating a new WebDriver");
					drivers.put(browser, webdriver);
					registryUpdationOnAbsence(browser, webdriver);
				}
		}
		return webdriver;	
	}
	
	private static void registryUpdationOnAbsence(Browser browser, WebDriver driver){
		if(Objects.isNull(Registry.getAttribute(browser.toString()))){
			Registry.setAttribute(browser.toString(), driver);
		}
	}
	
	private static WebDriver initBrowser(Browser browser) {
		if(Objects.nonNull(browser)) {
				switch (browser) {
				case IE: return getIEDriver(null);
				case FIREFOX: return getFireFoxDriver(null);
				case CHROME: return getChromeDriver(getChromeOptions());
				default: throw new UnsupportedOperationException(browser+" is not configured for the execution.");	
			}
		}
		else {
			LOG.warn("Browser input type received null, returning the same.");
			return null;
		}
	}
	
	private static ChromeOptions getChromeOptions(){
		ChromeOptions options = new ChromeOptions();
		if(PropertyUtil.getBoolean("webdriver.chrome.capabilities.enable", true)){
			  HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		      chromePrefs.put("profile.default_content_settings.popups", 0);
		      chromePrefs.put("download.default_directory", DOWNLOADS+"\\");
		      options = new ChromeOptions().addArguments("start-maximized", "--disable-notifications");
		      options.setExperimentalOption("prefs", chromePrefs);
		}
		return options;
	}
	
	
	private static InternetExplorerDriver getIEDriver(Capabilities capabilities) {
		if(Objects.nonNull(capabilities) && capabilities instanceof InternetExplorerOptions) {
			LOG.info("instantiating InternetExplorerDriver with supplied InternetExplorerOptions["+((InternetExplorerOptions) capabilities)+"]");
			return new InternetExplorerDriver((InternetExplorerOptions) capabilities);
		}else {
			LOG.info("instantiating InternetExplorerDriver without InternetExplorerOptions[null]");
			return new InternetExplorerDriver();
		}
	}
	
	private static FirefoxDriver getFireFoxDriver(Capabilities capabilities) {
		if(Objects.nonNull(capabilities) && capabilities instanceof FirefoxOptions) {
			LOG.info("instantiating FirefoxDriver with supplied FirefoxOptions["+((FirefoxOptions) capabilities)+"]");
			return new FirefoxDriver((FirefoxOptions) capabilities);
		}else {
			LOG.info("instantiating FirefoxDriver without FirefoxOptions[null]");
			return new FirefoxDriver();
		}
	}
	
	private static ChromeDriver getChromeDriver(Capabilities capabilities) {
		if(Objects.nonNull(capabilities) && capabilities instanceof ChromeOptions) {
			LOG.info("instantiating ChromeDriver with supplied ChromeOptions["+((ChromeOptions) capabilities)+"]");
			return new ChromeDriver((ChromeOptions) capabilities);
		}else {
			LOG.info("instantiating ChromeDriver without ChromeOptions[null]");
			return new ChromeDriver();
		}
	}
	
	public static void closeDriver(Browser browser) {
		if(Objects.nonNull(browser) && isDriversNotNull() && drivers.containsKey(browser)) 
			drivers.remove(browser).close();
	}
	
	public static void closeAll() {
		if(isDriversNotNull())
			drivers.keySet().stream().filter(Objects::nonNull).forEach(a -> drivers.remove(a).quit());
		CommonUtils.execCMD(CommonUtils.getConfiguredWebDriverKillCommand());
	}
	
	private static boolean isDriversNotNull() {
		return (MapUtils.isNotEmpty(drivers)) ? Boolean.TRUE.booleanValue() : Boolean.FALSE.booleanValue();
	}
}
