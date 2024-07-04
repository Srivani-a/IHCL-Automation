package com.web.automation.dto.pdfreport;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.testng.ITestResult;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class TestResults {
	
	private String testName;
	private Set<ITestResult> results;
	
	public TestResults(String testName) {
		this(testName, null);
	}

	public TestResults(String testName, Set<ITestResult> results) {
		super();
		this.testName = testName;
		this.results = CollectionUtils.isNotEmpty(results) ? new HashSet<>(results) : new HashSet<>();
	}

	public Set<ITestResult> getResults() {
		return results;
	}

	public void setResults(Set<ITestResult> results) {
		this.results = results;
	}

	public String getTestName() {
		return testName;
	}
	
	public void addTestResult(ITestResult result){
		results.add(result);
	}

	public boolean removeTestResult(ITestResult result){
		return results.remove(result);
	}
	
	@Override
	public String toString() {
		return "TestResults [testName=" + testName + ", results=" + results + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((results == null) ? 0 : results.hashCode());
		result = prime * result + ((testName == null) ? 0 : testName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestResults other = (TestResults) obj;
		if (results == null) {
			if (other.results != null)
				return false;
		} else if (!results.equals(other.results)){
			return false;
		}
		if (testName == null) {
			if (other.testName != null)
				return false;
		} else if (!testName.equals(other.testName)){
			return false;
		}
		return true;
	}
}