package com.ihclweb.automation.registry;

/**
 * 
 * @author rajeevsai.t
 *
 */
public final class RegistryKey {
	
	public static final String TEST_SUITE_RESULT = "test-suite.result".intern();
	public static final String TEST_SUITES_RESULT = "test-suites.result".intern();
	public static final String CURRENT_EXE_SNAP_ROOT_PATH = "current.exe.snap.root.path".intern();
	public static final String CURRENT_EXE_MOVIE_ROOT_PATH = "current.exe.movie.root.path".intern();
	public static final String MAIL_PARAM = "mail.param".intern();

	public static final String PDF_REPORT_OUTPUT_STREAM = "pdf.report.output.stream";
	public static final String PDF_REPORT_FILE_PATH = "pdf.report.file-path";
	public static final String DEFAULT_CONFIGURED_BROWSER = "default.configured.webdriver";
	public static final String DIR_TIMESTAMP_SUFFIX = "dir.timestamp.suffix";
	public static final String APP_NAME = "application.name";
	
	public static final String SUITE_START_TIME = "suite.start.time";
	public static final String SUITE_END_TIME = "suite.end.time";
	
	public static final String ON_START_ITEST_CTX = "on-start.iTestContext";
	public static final String ON_FINISH_ITEST_CTX = "on-finish.iTestContext";
	
	/**
	 * RegistryKey Constructor
	 */
	private RegistryKey() {}
}