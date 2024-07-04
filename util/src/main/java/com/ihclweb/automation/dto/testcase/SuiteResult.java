package com.ihclweb.automation.dto.testcase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class SuiteResult {
	
	private List<TestCaseResult> testCaseResults;
	
	public SuiteResult() {
		testCaseResults = new ArrayList<>();
	}

	public List<TestCaseResult> getTestCaseResults() {
		return testCaseResults;
	}

	public void setTestCaseResults(List<TestCaseResult> testCaseResults) {
		this.testCaseResults = testCaseResults;
	}
	
	public void setTestCaseResult(TestCaseResult testCaseResult) {
		this.testCaseResults.add(testCaseResult);
	}
	
	public long getPassedTestCases() {
		int totalPassed = 0;
		if(isListNotNull()) {
			totalPassed = (int)getTestCaseResults().stream().filter(this::isPassed).count();
		}
		return totalPassed;
	}
	
	public int getFailedTestCases() {
		int totalFailed = 0;
		if(isListNotNull()) {
			totalFailed = (int) getTestCaseResults().stream().filter(this::isNotPassed).count();
		}
		return totalFailed;
	}
	
	public int getTotatlTestCasesExecuted() {
		return isListNotNull() ? testCaseResults.size() : 0;
	}
	
	public int getSkippedTestCases(){
		int totalFailed = 0;
		if(isListNotNull()) {
			totalFailed = (int) getTestCaseResults().stream().filter(Objects::nonNull).filter(TestCaseResult::isSkipped).count();
		}
		return totalFailed;
	}
	
	private boolean isPassed(TestCaseResult result) {
		return result.isPassed();
	}
	
	private boolean isNotPassed(TestCaseResult result) {
		return !isPassed(result);
	}
	
	private boolean isListNotNull() {
		return (Objects.nonNull(testCaseResults) && !testCaseResults.isEmpty()) ? Boolean.TRUE.booleanValue() : Boolean.FALSE.booleanValue();
	}
}