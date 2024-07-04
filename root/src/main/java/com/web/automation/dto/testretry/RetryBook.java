package com.web.automation.dto.testretry;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class RetryBook {
	
	private Set<Test> tests = new HashSet<>();

	public Set<Test> getTests() {
		return tests;
	}

	public void setTests(Set<Test> tests) {
		this.tests = tests;
	}
	
	public Optional<Test> getTest(String test){
		return tests.stream().filter(Objects::nonNull).filter(a -> a.getName().equals(test)).findAny();
	}
	
	public void addTest(Test test){
		tests.add(Objects.requireNonNull(test,"Test object cannot be null"));
	}
	
	public boolean isTestPresent(String test){
		return getTest(Objects.requireNonNull(test)).isPresent();
	}
	
	public boolean isTestNotPresent(String test){
		return !isTestPresent(test);
	}
	
	public void createTest(String name){
		if(isTestNotPresent(Objects.requireNonNull(name))){
			addTest(new Test(name));
		}
	}
}