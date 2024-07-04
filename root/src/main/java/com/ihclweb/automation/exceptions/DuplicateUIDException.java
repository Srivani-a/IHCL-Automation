package com.ihclweb.automation.exceptions;


/**
 * 
 * @author rajeevsai.t
 *
 */
public class DuplicateUIDException extends RepositoryException {

	private static final long serialVersionUID = 1L;

	public DuplicateUIDException() { }

	public DuplicateUIDException(String message) { super(message); }

	public DuplicateUIDException(Throwable cause) { super(cause); }

	public DuplicateUIDException(String message, Throwable cause) { super(message, cause); }
}