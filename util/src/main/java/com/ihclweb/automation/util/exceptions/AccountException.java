package com.ihclweb.automation.util.exceptions;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class AccountException extends IHCLBusinessException {
	
	private static final long serialVersionUID = 1L;

	public AccountException() {
		super();
	}

	public AccountException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountException(String message) {
		super(message);
	}

	public AccountException(Throwable cause) {
		super(cause);
	}
}
