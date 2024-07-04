package com.web.automation.listeners;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.ihclweb.automation.testcase.service.SuiteResultCaptureService;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class SuiteResultCaptureListener implements ITestListener {

	private SuiteResultCaptureService capture;
	private static final Logger LOG = Logger.getLogger(SuiteResultCaptureListener.class);
	
	private static long start;
	private static long end;
	
	public SuiteResultCaptureListener() {
		capture = Objects.requireNonNull(SuiteResultCaptureService.instance());
		if(LOG.isDebugEnabled())
			LOG.debug("SuiteResultCaptureListener has beeb initialized.....");
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		LOG.debug("TestCase: "+(StringUtils.isNotBlank(result.getTestName()) ? result.getTestName() : result.getName())+" is started...");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		capture.addPassedTestResult(StringUtils.isNotBlank(result.getTestName()) ? result.getTestName() : result.getName(), result.getMethod().getDescription());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		capture.addFailedTestResult(StringUtils.isNotBlank(result.getTestName()) ? result.getTestName() : result.getName(), getErrMessage(result.getThrowable()), result.getMethod().getDescription());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		capture.addSkippedTestResult(StringUtils.isNotBlank(result.getTestName()) ? result.getTestName() : result.getName(), result.getMethod().getDescription());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		//no-op
	}

	private String getErrMessage(Throwable cause){
		String errMsg = null;
		if(Objects.nonNull(cause)){
			errMsg = (Objects.nonNull(cause.getCause()) ? cause.getCause().getMessage() : cause.getMessage()); 
		}
		return errMsg;
	}
	
	@Override
	public void onStart(ITestContext context) {
		LOG.debug("Test: "+context.getName()+" is started form Suite:"+context.getSuite().getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		LOG.debug("Test: "+context.getName()+" is finished form Suite:"+context.getSuite());
	}

	public static long getStart() {
		return start;
	}

	public static void setStart(long start) {
		SuiteResultCaptureListener.start = start;
	}

	public static long getEnd() {
		return end;
	}

	public static void setEnd(long end) {
		SuiteResultCaptureListener.end = end;
	}
}
