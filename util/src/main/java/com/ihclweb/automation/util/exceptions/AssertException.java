package com.ihclweb.automation.util.exceptions;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class AssertException extends IHCLBusinessException {

	private static final long serialVersionUID = 1L;

	public AssertException() {
		//This is a non parameterized constructor
	}

	public AssertException(String message) {
		super(message);
	}

	public AssertException(Throwable cause) {
		super(cause);
	}

	public AssertException(String message, Throwable cause) {
		super(message, cause);
	}
}
