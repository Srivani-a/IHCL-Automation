package com.web.automation.test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.IExecutionListener;
import org.testng.annotations.Listeners;

import com.ihclweb.automation.ApplicationContext;
import com.ihclweb.automation.consts.IHCLConstantsWeb;
import com.ihclweb.automation.login.actions.WebLoginActions;
import com.ihclweb.automation.provider.credential.ResourceProvider;
import com.ihclweb.automation.registry.Registry;
import com.ihclweb.automation.registry.RegistryKey;
import com.ihclweb.automation.repo.ExcelRepository;
import com.ihclweb.automation.repo.impl.DefaultExcelRepository;
import com.ihclweb.automation.util.CommonUtils;
import com.ihclweb.automation.util.PathProvider;
import com.ihclweb.automation.util.PropertyUtil;
import com.ihclweb.automation.util.exceptions.IHCLBusinessException;
import com.web.automation.driver.WebDriverProducer;
import com.web.automation.dto.mail.MailParam;
import com.web.automation.dto.testretry.RetryBook;
import com.web.automation.listeners.DefaultRecorder;
import com.web.automation.listeners.PDFReport;
import com.web.automation.listeners.Recorder;
import com.web.automation.mail.DefaultMailService;
import com.web.automation.test.handler.GlobalExceptionHandler;

@Listeners( value = {PDFReport.class, Recorder.class})
public class ApplicationContextInitializer extends GlobalExceptionHandler implements IExecutionListener {

	private static final Logger LOG = Logger.getLogger(ApplicationContextInitializer.class);
	public static final WebDriver DRIVER;
	protected static final String DEFAULT_WORKBOOK_NAME;
	protected static final ExcelRepository REPOSITORY;
	protected static final WebLoginActions LOGIN_ACTIONS;
	//protected static final ProfileWidgetLoginWebActions PROFILE_WIDGET_LOGIN_ACTIONS;
	public static final String APPLICATION_NAME;
	private static final String TIMESTAMP;
	public static final ApplicationContext CONTEXT;
	public static final Path DOWNLOADS;
	public static final Path CURRENTFOLDER;
	
	
	static{
		TIMESTAMP = CommonUtils.getCurExeDirTimestamp();
		CONTEXT = ApplicationContext.getContext();
		CommonUtils.initCurrentExeDirsInspection(TIMESTAMP);
		APPLICATION_NAME = CommonUtils.getApplicationName();
		//CommonUtils.execCMD(CommonUtils.getConfiguredWebDriverKillCommand());
		DEFAULT_WORKBOOK_NAME = PathProvider.getDefaultConfiguredWorkbookName();
		DRIVER = WebDriverProducer.getWebDriver(CommonUtils.getConfiguredDriverBrowser());
		REPOSITORY = ResourceProvider.instance().getRepository();
		DOWNLOADS = PathProvider.getForDownloads();
		CURRENTFOLDER=PathProvider.getForCurrentFolder();
		//WebDriverManager.chromedriver().setup();
		//DRIVER = new ChromeDriver();
		//new ElementsFactoryWeb().init(DRIVER);
		LOGIN_ACTIONS = WebLoginActions.instance(DRIVER);
		//new ProfileWidgeElementsFactoryWeb().init(DRIVER);
		//PROFILE_WIDGET_LOGIN_ACTIONS = ProfileWidgetLoginWebActions.instance(DRIVER);
	}
	
	
	public void init(){
		Registry.setAttribute(RegistryKey.SUITE_START_TIME, new Date());
		Registry.setAttribute(RegistryKey.DEFAULT_CONFIGURED_BROWSER, CommonUtils.getConfiguredDriverBrowser());
		Registry.setAttribute(RegistryKey.MAIL_PARAM, new MailParam());
		Registry.setAttribute(RegistryKey.CURRENT_EXE_SNAP_ROOT_PATH, PathProvider.getCurExecScreenshotRootDir(TIMESTAMP));
		Registry.setAttribute(RegistryKey.PDF_REPORT_FILE_PATH, PathProvider.getGenPDFReportFilePath(TIMESTAMP));
		Registry.setAttribute(IHCLConstantsWeb.RETRY_BOOK, new RetryBook());
		deleteCookies();
	}
	
	private void deleteCookies(){
		if(PropertyUtil.getBoolean("delete.domain-cookies.before.tests-start", true)){
			try{
				getIhclPage();
				DRIVER.manage().deleteAllCookies();
			}catch(Exception e){
				LOG.error("Unknown error occurred whie deleting all the cookies for the ShoppersStop base Domain.",e);
			}
		}
	}
	
	protected static void getPage(String url) {
		DRIVER.get(url);
	}
	
	protected void getIhclPage() {
		try {
			DRIVER.get(PathProvider.getIhclBaseURL());
		} catch (Exception e) {
			throw new IHCLBusinessException("Unable To Open Application Page", e);
		}
	}
	
	
	public void destroy(){
		LOG.debug("Performing post execution operation and Destroying all the initialized content.");
		Registry.setAttribute(RegistryKey.SUITE_END_TIME, new Date());
		WebDriverProducer.closeAll();
		((DefaultExcelRepository)REPOSITORY).closeBookQuitely();
		new DefaultMailService().build().transport();
		stopCapturer();
		CommonUtils.killProcesses();
		Registry.removeAll();
	}

	protected static WebDriver getDriver(){
		return DRIVER;
	}
	
	private void stopCapturer(){
		try {
			if(IHCLConstantsWeb.IS_RECORDING_ENABLED) {
				DefaultRecorder.instance().stop();
			}
		} catch (IOException e) {
			LOG.error("Error occurred while closing the capturer",e);
		}
	}
	
	public static ExcelRepository getExcelRepository(){
		return REPOSITORY;
	}
	
	protected void addRetryTest(String name){
		((RetryBook)Registry.getAttribute(IHCLConstantsWeb.RETRY_BOOK)).createTest(name);
	}
	
	
	@Override
	public void onExecutionStart() {
		LOG.debug("IHCL execution started.... initializing all the application dependencies.");
		init();
	}

	@Override
	public void onExecutionFinish() {
		LOG.debug("IHCL execution finished.... destroying all the initializations.");
		destroy();
	}

}
