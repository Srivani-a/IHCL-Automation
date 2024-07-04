package com.web.automation.dto.pdfreport;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import org.apache.commons.collections.CollectionUtils;

import com.ihclweb.automation.registry.Registry;
import com.ihclweb.automation.registry.RegistryKey;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class SuitesResult {
	private Set<SuiteResults> suiteResults;

	public SuitesResult() {
		this(null);
	}
	
	public SuitesResult(Set<SuiteResults> suiteResults) {
		super();
		this.suiteResults = CollectionUtils.isNotEmpty(suiteResults) ? new HashSet<>(suiteResults) : new HashSet<>();
	}

	public Set<SuiteResults> getSuiteResults() {
		return suiteResults;
	}

	public void setSuiteResults(Set<SuiteResults> suiteResults) {
		this.suiteResults = suiteResults;
	}

	@Override
	public String toString() {
		return "SuitesResult [suiteResults=" + suiteResults + "]";
	}
	
	public void addSuiteResults(SuiteResults suiteResults){
		this.suiteResults.add(suiteResults);
	}
	
	public int totalTestsExecuted(){
		return suiteResults.stream().filter(Objects::nonNull).mapToInt(SuiteResults::totalTestsExecuted).sum();
	}
	
	public int totalTestsPassed(){
		return suiteResults.stream().filter(Objects::nonNull).mapToInt(SuiteResults::totalTestsPassed).sum();
	}
	
	public int totalTestsFailed(){
		return suiteResults.stream().filter(Objects::nonNull).mapToInt(SuiteResults::totalTestsFailed).sum();
	}
	
	public int totalTestsSkipped(){
		return suiteResults.stream().filter(Objects::nonNull).mapToInt(SuiteResults::totalTestsSkipped).sum();
	}
	
	public Date getStartDate(){
		return Registry.getAttribute(RegistryKey.SUITE_START_TIME, Date.class);
	}
	
	public Date getEndDate(){
		Object obj = Registry.getAttribute(RegistryKey.SUITE_END_TIME);
		if(Objects.isNull(obj))
			Registry.setAttribute(RegistryKey.SUITE_END_TIME, new Date());
		return Registry.getAttribute(RegistryKey.SUITE_END_TIME, Date.class);
	}
	
	public String getSuitesExecuted(){
		StringJoiner joiner = new StringJoiner(", ");
		suiteResults.stream().filter(Objects::nonNull).map(SuiteResults::getSuiteName).forEach(joiner::add);
		return joiner.toString();
	}
}