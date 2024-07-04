package com.ihclweb.automation.exceptions;


/**
 * 
 * @author sairam.p
 *
 */
public class ValueNotFoundException extends RepositoryException {

	private static final long serialVersionUID = 1L;

	public ValueNotFoundException() { }

	public ValueNotFoundException(String message) { super(message); }

	public ValueNotFoundException(Throwable cause) { super(cause); }

	public ValueNotFoundException(String message, Throwable cause) { super(message, cause); }
}
