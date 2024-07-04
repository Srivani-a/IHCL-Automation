package com.ihclweb.automation.util.exceptions;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class LoginActionException extends IHCLBusinessException {

	private static final long serialVersionUID = 1L;

	public LoginActionException() {
		super();
	}

	public LoginActionException(String message, Throwable cause) { super(message, cause); }

	public LoginActionException(String message) { super(message); }

	public LoginActionException(Throwable cause) { super(cause); }

}
