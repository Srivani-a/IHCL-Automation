package com.web.automation.dto.pdfreport;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class SuiteResults {
	
	private String suiteName;
	
	private Set<TestResults> passedTests;
	private Set<TestResults> failedTests;
	private Set<TestResults> skippedTests;
	
	public SuiteResults(String suiteName) {
		super();
		this.suiteName = suiteName;
		passedTests = new HashSet<>();
		failedTests = new HashSet<>();
		skippedTests = new HashSet<>();
	}

	public Set<TestResults> getPassedTests() {
		return passedTests;
	}

	public void setPassedTests(Set<TestResults> passedTests) {
		this.passedTests = passedTests;
	}

	public Set<TestResults> getFailedTests() {
		return failedTests;
	}

	public void setFailedTests(Set<TestResults> failedTests) {
		this.failedTests = failedTests;
	}

	public Set<TestResults> getSkippedTests() {
		return skippedTests;
	}

	public void setSkippedTests(Set<TestResults> skippedTests) {
		this.skippedTests = skippedTests;
	}

	public String getSuiteName() {
		return suiteName;
	}
	
	public void addPassedTest(TestResults testResults){
		passedTests.add(testResults);
	}
	
	public void addFailedTest(TestResults testResults){
		failedTests.add(testResults);
	}
	
	public void addSkippedTest(TestResults testResults){
		skippedTests.add(testResults);
	}

	@Override
	public String toString() {
		return "SuiteResults [suiteName=" + suiteName + ", passedTests=" + passedTests + ", failedTests=" + failedTests
				+ ", skippedTests=" + skippedTests + "]";
	}
	
	public int totalTestsExecuted(){
		return totalTestsPassed() + totalTestsSkipped() + totalTestsFailed();
	}
	
	public int totalTestsPassed(){
		return passedTests.stream().filter(Objects::nonNull).filter(a -> CollectionUtils.isNotEmpty(a.getResults())).mapToInt(a -> a.getResults().size()).sum();
	}
	
	public int totalTestsFailed(){
		return failedTests.stream().filter(Objects::nonNull).filter(a -> CollectionUtils.isNotEmpty(a.getResults())).mapToInt(a -> a.getResults().size()).sum();
	}
	
	public int totalTestsSkipped(){
		return skippedTests.stream().filter(Objects::nonNull).filter(a -> CollectionUtils.isNotEmpty(a.getResults())).mapToInt(a -> a.getResults().size()).sum();
	}
}