package com.ihclweb.automation.util.exceptions;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class WebElementOperationException extends IHCLBusinessException {

	private static final long serialVersionUID = 1L;

	public WebElementOperationException() {
		//This is a non parameterized constructor
	}

	public WebElementOperationException(String message) {
		super(message);
	}

	public WebElementOperationException(Throwable cause) {
		super(cause);
	}

	public WebElementOperationException(String message, Throwable cause) {
		super(message, cause);
	}
}