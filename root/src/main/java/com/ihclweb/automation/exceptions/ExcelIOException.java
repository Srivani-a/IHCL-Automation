package com.ihclweb.automation.exceptions;


/**
 * 
 * @author sairam.p
 *
 */
public class ExcelIOException extends RepositoryException {

	private static final long serialVersionUID = 1L;

	public ExcelIOException() { }

	public ExcelIOException(String message) { super(message); }

	public ExcelIOException(Throwable cause) { super(cause); }

	public ExcelIOException(String message, Throwable cause) { super(message, cause); }
}