package com.ihclweb.automation.exceptions;

import com.ihclweb.automation.util.exceptions.IHCLBusinessException;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class RepositoryException extends IHCLBusinessException {

	private static final long serialVersionUID = 1L;

	public RepositoryException() {}

	public RepositoryException(String message) { super(message);}

	public RepositoryException(Throwable cause) { super(cause); }

	public RepositoryException(String message, Throwable cause) { super(message, cause); }

}
