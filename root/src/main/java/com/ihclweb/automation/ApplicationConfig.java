package com.ihclweb.automation;

import com.ihclweb.automation.util.CommonUtils;
import com.ihclweb.automation.util.PropertyUtil;

/**
 * 
 * @author rajeevsai.t
 *
 */
public final class ApplicationConfig {

	private final boolean isRecordingEnabled;
	private final String appName;
	
	ApplicationConfig() {
		isRecordingEnabled = PropertyUtil.getBoolean("is.scripts-recording.enabled", false);
		appName = CommonUtils.getApplicationName();
	}

	public boolean isRecordingEnabled() {
		return isRecordingEnabled;
	}

	public String getAppName() {
		return appName;
	}
}