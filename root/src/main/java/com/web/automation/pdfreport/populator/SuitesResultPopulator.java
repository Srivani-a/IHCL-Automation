package com.web.automation.pdfreport.populator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.testng.ISuite;
import org.testng.ISuiteResult;

import com.web.automation.dto.pdfreport.SuiteResults;
import com.web.automation.dto.pdfreport.SuitesResult;
import com.web.automation.dto.pdfreport.TestResults;

/**
 * 
 * @author sairam.p
 *
 */
public class SuitesResultPopulator {

	public SuitesResult populate(List<ISuite> suites){
		return CollectionUtils.isNotEmpty(suites) ? new SuitesResult(suites.stream().filter(Objects::nonNull).map(this::populateSuiteResults).collect(Collectors.toSet())) : null;
	}

	
	private SuiteResults populateSuiteResults(ISuite suite){
		SuiteResults suiteResults = new SuiteResults(suite.getName());
		Map<String, ISuiteResult> results = suite.getResults();
		suiteResults.setPassedTests(results.keySet().stream().filter(Objects::nonNull).map(a -> new TestResults(a, results.get(a).getTestContext().getPassedTests().getAllResults())).collect(Collectors.toSet()));
		suiteResults.setSkippedTests(results.keySet().stream().filter(Objects::nonNull).map(a -> new TestResults(a, results.get(a).getTestContext().getSkippedTests().getAllResults())).collect(Collectors.toSet()));
		suiteResults.setFailedTests(results.keySet().stream().filter(Objects::nonNull).map(a -> new TestResults(a, results.get(a).getTestContext().getFailedTests().getAllResults())).collect(Collectors.toSet()));
		return suiteResults;
	}
}