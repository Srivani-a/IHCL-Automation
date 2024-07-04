package com.web.automation.test.handler;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.ihclweb.automation.util.Assert;
import com.ihclweb.automation.util.exceptions.IHCLBusinessException;

/**
 * 
 * @author rajeevsai.t
 *
 */
public abstract class GlobalExceptionHandler {

	private static final Set<String> PACKAGES ;
	
	static{
		PACKAGES = Arrays.asList(IHCLBusinessException.class.getPackage().getName(), Assert.class.getPackage().getName()).stream().collect(Collectors.toSet());
	}
	
	private boolean isIHCLExcep(Throwable throwable){
		return (Objects.nonNull(throwable) && isUsable(throwable));
	}
	
	protected final void handleOnException(String message, Exception exception) {
		throw new IHCLBusinessException(isIHCLExcep(exception) ? exception.getMessage() : message);
	}
	
	private boolean isUsable(Throwable cause){
		return PACKAGES.contains(cause.getClass().getPackage().getName());
	} 
}
