package com.ihclweb.automation.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ihclweb.automation.consts.IHCLConstantsWeb;
import com.ihclweb.automation.enums.Browser;
import com.ihclweb.automation.registry.Registry;
import com.ihclweb.automation.registry.RegistryKey;

/**
 * 
 * @author rajeevsai.t
 *
 */
public final class PathProvider {
	
	private static final Path USER_DIR = Paths.get(System.getProperty("user.dir"));
	private static final String RES_FROM_WORKSPACE = "src/main/resources";

	private static final String CHROME = "chromedriver.exe";
	private static final String FIREFOX = "geckodriver.exe";
	private static final String IE = "IEDriverServer.exe";
	private static final String TESTDATA_DIR_NAME = "testData";
	private static final String LOGS_DIR_NAME = "logs";
	private static final String GEN_DIR_NAME = "gen";
	private static final String REC_DIR_NAME = "recording";
	private static final String MISC_DIR_NAME = "misc";
	private static final String REPORTS_DIR_NAME = "reports";
	private static final String SCREENSHOTS_DIR_NAME = "screenshots";
	private static final String DRIVERS_DIR_NAME = "drivers";
	private static final String DOWNLOADS_NAME = "downloads";
	private static final String DOWNLOADS_FOLDER = "currentFolder";
	
	private static final Logger LOG = Logger.getLogger(PathProvider.class.getName());
	
	private PathProvider() {}
	
	public static Path getForDriver(Browser browser){
		if(Objects.nonNull(browser)) {
			switch (browser) {
			case IE:
				return Paths.get(getForDrivers().toString(),IE);
			case FIREFOX:
				return Paths.get(getForDrivers().toString(),FIREFOX);
			default:
				return Paths.get(getForDrivers().toString(),CHROME);
			}
		}
		return null;
	}
	

	public static Path getForDrivers() {
		return Paths.get(getForResources().toString(), DRIVERS_DIR_NAME);
	}
	
	public static Path getForDownloads() {
		return Paths.get(getForResources().toString(), DOWNLOADS_NAME);
	}
	public static Path getForCurrentFolder() {
		return Paths.get(getForResources().toString(), DOWNLOADS_FOLDER);
	}
	
	public static Path getForResources() {
		return Paths.get(USER_DIR.toString(), RES_FROM_WORKSPACE); // src/main/resources
	}

	public static Path getUserDir() {
		return USER_DIR;
	}
	
	public static Path getForTestData() {
		return Paths.get(getForResources().toString(), TESTDATA_DIR_NAME);
	}
	
	public static Path getForLogs() {
		return Paths.get(getForResources().toString(), LOGS_DIR_NAME);
	}
	
	public static Path getForGenDir() {
		return Paths.get(getForResources().toString(), GEN_DIR_NAME);
	}
	
	public static Path getForRecordingDir() {
		return Paths.get(getForGenDir().toString(), REC_DIR_NAME);
	}
	
	public static Path getForMiscDir() {
		return Paths.get(getForGenDir().toString(), MISC_DIR_NAME);
	}
	
	public static Path getForReportsDir() {
		return Paths.get(getForGenDir().toString(), REPORTS_DIR_NAME);
	}
	
	public static Path getForScreenshotsDir() {
		return Paths.get(getForGenDir().toString(), SCREENSHOTS_DIR_NAME);
	}
	
	public static Path getCurExecScreenshotRootDir(String timestamp) {
		Path path = null;
		if(StringUtils.isNotBlank(timestamp)){
			path = (Path)Registry.getAttribute(RegistryKey.CURRENT_EXE_SNAP_ROOT_PATH);
			if(Objects.isNull(path)){
				path = CommonUtils.genScreenshotDirsForCurrentExe(timestamp);
				Registry.setAttribute(RegistryKey.CURRENT_EXE_SNAP_ROOT_PATH, path);
			}
		}else{
			LOG.error("Program current execution timestamp cannot be empty, it leads to wrong filename generations for the session.");
		}
		return path;
	}
	
	public static Path getTestDataFilePath() {
		return Paths.get(getForTestData().toString(), PropertyUtil.getString(IHCLConstantsWeb.TEST_DATA_WORKBOOK_NAME));
	}
	
	public static String getIhclBaseURL() {
		return PropertyUtil.getString(IHCLConstantsWeb.IHCL_BASE_URL);
	}
	public static String getUatURL() {
		return PropertyUtil.getString(IHCLConstantsWeb.IHCL_UAT_URL);
	}
	
	public static String getDefaultIndividualTestsSheet() {
		return PropertyUtil.getString(IHCLConstantsWeb.REGISTRATION_SHEET);
		}

	public static String getDefaultConfiguredWorkbookName() {
		return PropertyUtil.getString(IHCLConstantsWeb.TEST_DATA_DEFAULT_WORKBOOK_NAME);
	}
	
	public static Path getGenPDFReportFilePath(String timestamp) {
		String reportName = null;
		if(StringUtils.isNotBlank(timestamp)) {
			reportName = PropertyUtil.getString(IHCLConstantsWeb.IHCL_TEST_REPORT_NAME,IHCLConstantsWeb.DEFAULT_IHCL_TEST_REPORT_NAME) + ("-"+ timestamp + IHCLConstantsWeb.DOT_PDF);
		}else {
			LOG.error("Program current execution timestamp cannot be empty, it leads to wrong filename generations for the session.");
		}
		return Paths.get(getForReportsDir().toString(), reportName);
	}
	
	public static String getCSVTestReportFileName(){
		return PropertyUtil.getString(IHCLConstantsWeb.CSV_TEST_REPORT_NAME,"TestReport");
	}
	
	public static String getEncryptionSecretKey() {
		return PropertyUtil.getString(IHCLConstantsWeb.ENCRYPTION_SECRET_KEY);
	}
	
}