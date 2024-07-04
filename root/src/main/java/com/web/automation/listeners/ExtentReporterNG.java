package com.web.automation.listeners;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

import com.ihclweb.automation.consts.IHCLConstantsWeb;
import com.ihclweb.automation.util.PathProvider;
import com.ihclweb.automation.util.PropertyUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;

public class ExtentReporterNG implements IReporter {
 	
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		if(Objects.nonNull(suites) && !suites.isEmpty()) {
			ExtentReports extentReports = new ExtentReports(getReportGenFilePath(), true, NetworkMode.OFFLINE);
			prepareReportSkeleton(extentReports);
			suites.forEach( a -> {
				Map<String, ISuiteResult> result = a.getResults();
				result.values().forEach(b -> {
					ITestContext context = b.getTestContext();
	                buildTestNodes(extentReports, context.getPassedTests(), LogStatus.PASS);
	                buildTestNodes(extentReports, context.getFailedTests(), LogStatus.FAIL);
	                buildTestNodes(extentReports, context.getSkippedTests(), LogStatus.SKIP);
				});
			});
	    	flushBeforeClose(extentReports);
		}
    }
	
    private void buildTestNodes(ExtentReports extentReports, IResultMap tests, LogStatus status) {
        tests.getAllResults().stream().forEach(result -> {
        	ExtentTest test = extentReports.startTest(result.getMethod().getMethodName());
            test.getTest().startedTime = getTime(result.getStartMillis());
            test.getTest().endedTime = getTime(result.getEndMillis());
            Arrays.asList(result.getMethod().getGroups()).forEach(test::assignCategory);
            test.log(status, (Objects.nonNull(result.getThrowable())) ? result.getThrowable().getMessage() : "Test " + status.toString().toLowerCase() + "ed");
            extentReports.endTest(test);
        });
    }
    
    private static Date getTime(long millis) {
    	return new Date(millis);
    }
    
    private void prepareReportSkeleton(ExtentReports extentReports){
		if(Objects.nonNull(extentReports)) {
			extentReports.config().documentTitle(PropertyUtil.getString(IHCLConstantsWeb.REPORT_DOC_TITLE, IHCLConstantsWeb.DEFAULT_REPORT_DOC_TITLE))
		    .reportName(PropertyUtil.getString(IHCLConstantsWeb.REPORT_NAME, IHCLConstantsWeb.DEFAULT_REPORT_NAME))
		    .reportHeadline(PropertyUtil.getString(IHCLConstantsWeb.REPORT_HEADLINE, IHCLConstantsWeb.DEFAULT_REPORT_HEADLINE))
		    .insertCustomStyles(PropertyUtil.getString(IHCLConstantsWeb.DEFAULT_REPORT_CUSTOM_STYLE, IHCLConstantsWeb.DEFAULT_REPORT_CUSTOM_STYLE));
		}
    }
    
    private void flushBeforeClose(ExtentReports extentReports) {
    	extentReports.flush();
		extentReports.close();
    }
    
    private String getReportGenFilePath() {
    	return Paths.get(PathProvider.getForReportsDir().toString(), PropertyUtil.getString(IHCLConstantsWeb.REPORT_GEN_FILE_NAME, IHCLConstantsWeb.DEFAULT_REPORT_GEN_FILE_NAME)).toString();
    }
}
