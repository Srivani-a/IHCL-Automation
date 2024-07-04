package com.ihclweb.automation.dto.testcase;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.ihclweb.automation.consts.IHCLConstantsWeb;

/**
 * 
 * @author rajeevsai.t
 *
 */
public final class TestCaseResult {
	
	private String testCase;
	private boolean isPassed;
	private String id;
	private String comment;
	private boolean isSkipped;
	private String description;
	
	public TestCaseResult(String testCase, boolean isPassed, String description) {
		super();
		this.testCase = testCase;
		this.isPassed = isPassed;
		if(StringUtils.isNotBlank(description)) {
			this.description = description;
		}
		this.id = UUID.randomUUID().toString();
	}
	
	public TestCaseResult(String testCase, boolean isPassed, String comment, String description) {
		this(testCase, isPassed, description);
		this.comment = comment;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTestCase() {
		return testCase;
	}
	
	public boolean isPassed() {
		return isPassed;
	}
	
	public String isPassedAsString() {
		return isPassed ? IHCLConstantsWeb.PASS : IHCLConstantsWeb.FAIL;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "TestCaseResult [testCase=" + testCase + ", isPassed=" + isPassed + ", id=" + id + ", comment=" + comment
				+ ", isSkipped=" + isSkipped + ", description=" + description + "]";
	}

	public boolean isSkipped() {
		return isSkipped;
	}

	public void setSkipped(boolean isSkipped) {
		this.isSkipped = isSkipped;
	}
}
