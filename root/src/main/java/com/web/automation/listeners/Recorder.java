package com.web.automation.listeners;

import java.util.Objects;

import org.apache.log4j.Logger;
import org.testng.IClassListener;
import org.testng.ITestClass;

import com.ihclweb.automation.consts.IHCLConstantsWeb;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class Recorder implements IClassListener {

	private static final Logger LOG = Logger.getLogger(Recorder.class);
	private DefaultRecorder recorders;
	
	
	public Recorder() {
		super();
		if(IHCLConstantsWeb.IS_RECORDING_ENABLED) {
			this.recorders = DefaultRecorder.instance();
		}
	}
	
	private boolean isProceedable(){
		boolean isProceedable = false;
		if(IHCLConstantsWeb.IS_RECORDING_ENABLED){
			if(Objects.nonNull(recorders)){
				isProceedable = true;
			}else{
				LOG.warn("Recorder instance found to be null...unable to perform recording.");
			}
		}else{
			LOG.info("Recording is not enabled....");
		}
		return isProceedable;
	}

	@Override
	public void onBeforeClass(ITestClass testClass) {
		if(isProceedable()){
			try {
				recorders.startRecord(testClass.getRealClass().getSimpleName());
			} catch (Exception e) {
				LOG.error("Unknown error occurred while starting record for Test:"+testClass.getRealClass().getSimpleName()+e.getMessage());
			}
		}
	}

	@Override
	public void onAfterClass(ITestClass testClass) {
		if(isProceedable()){
			try {
				recorders.stopRecord();
			} catch (Exception e) {
				LOG.error("Unknown error occurred while starting record for Test:"+testClass.getRealClass().getSimpleName()+e.getMessage());
			}
		}
	}
}