package com.ihclweb.automation.util.exceptions;

/**
 * 
 * @author sairam.p
 *
 */
public class AccountNotFoundException extends AccountException {

	private static final long serialVersionUID = 1L;

	public AccountNotFoundException() {
		super();
	}

	public AccountNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountNotFoundException(String message) {
		super(message);
	}

	public AccountNotFoundException(Throwable cause) {
		super(cause);
	}
}
