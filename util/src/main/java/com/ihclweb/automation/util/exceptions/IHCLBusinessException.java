package com.ihclweb.automation.util.exceptions;

import org.apache.log4j.Logger;

import com.ihclweb.automation.util.PropertyUtil;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class IHCLBusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(IHCLBusinessException.class);
	protected static final boolean LOG_ENABLED = PropertyUtil.getBoolean("exception-context.logging.enabled", true);
	
	public IHCLBusinessException() {
		//This is a non parameterized constructor
	}

	public IHCLBusinessException(String message) {
		super(message);
		if(LOG_ENABLED) {
			LOG.error(message);
		}
	}

	public IHCLBusinessException(Throwable cause) {
		this(cause.getMessage());
		if(LOG_ENABLED) {
			LOG.error(cause);
		}
	}

	public IHCLBusinessException(String message, Throwable cause) {
		this(message);
		if(LOG_ENABLED) {
			LOG.error(message,cause);
		}
	}
}
