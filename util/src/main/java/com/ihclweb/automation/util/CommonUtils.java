package com.ihclweb.automation.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ihclweb.automation.consts.IHCLConstantsWeb;
import com.ihclweb.automation.enums.Browser;
import com.ihclweb.automation.registry.Registry;
import com.ihclweb.automation.registry.RegistryKey;
import com.ihclweb.automation.util.exceptions.IHCLBusinessException;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class CommonUtils {
	
	private static final String RUN = "RUN_";
	private static final Logger LOG = Logger.getLogger(CommonUtils.class.getName());
	private static final String SAMPLE_PNG = "sample.png";
	private static final String USER_HOME = "user.home";
	private static final String ADD_FILES_UPLOAD = Paths.get(PathProvider.getForTestData() .toString(), "AddFiles.exe").toString();
	
	private CommonUtils() {}
	
	public static Path genScreenshotDirsForCurrentExe(String timestamp) {
		if(StringUtils.isNotBlank(timestamp)){
			try {
				Path screenshotDir = PathProvider.getForScreenshotsDir();
				Path curExeDir = Paths.get(screenshotDir.toString(),RUN + timestamp);
				LOG.info("=====>>>>> Current Execution Screenshot Directory:"+curExeDir.toString());
				FileUtils.forceMkdir(curExeDir.toFile());
				FileUtils.forceMkdir(Paths.get(curExeDir.toString(), IHCLConstantsWeb.PASSED).toFile());
				FileUtils.forceMkdir(Paths.get(curExeDir.toString(), IHCLConstantsWeb.FAILED).toFile());
				Registry.setAttribute(RegistryKey.CURRENT_EXE_SNAP_ROOT_PATH,curExeDir);
				return curExeDir;
			}catch(IOException e) {
				throw new IHCLBusinessException("Error occurred while creating the Directories for current execution",e);
			}
		}else {
			LOG.error("Program current execution timestamp cannot be empty, it leads to wrong filename generations for the session.");
		}
		return null;
	}
	
	public static Path getRecordingDirForCurrentExe(String timestamp){
		Object obj = Registry.getAttribute(RegistryKey.CURRENT_EXE_MOVIE_ROOT_PATH);
		Path path = null;
		if(Objects.isNull(obj)){
			path = Paths.get(PathProvider.getForRecordingDir().toString(), RUN + timestamp);
			try {
				if(!path.toFile().exists() && IHCLConstantsWeb.IS_RECORDING_ENABLED) {
					FileUtils.forceMkdir(path.toFile());
				}
			} catch (IOException e) {
				LOG.error("Unable to create Movie creation directory...", e);
			}
			Registry.setAttribute(RegistryKey.CURRENT_EXE_MOVIE_ROOT_PATH, path);
		}
		return path;
	}
	
	public static String getTimestamp(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}
	
	public static Browser getConfiguredDriverBrowser() {
		Object obj = Registry.getAttribute(RegistryKey.DEFAULT_CONFIGURED_BROWSER);
		return Objects.nonNull(obj) ? ((Browser)obj) : Browser.valueOf(PropertyUtil.getString(IHCLConstantsWeb.DRIVER_BROWSER));
	}
	
	public static void checkAndCreateProjStructure(){
		Path gen = PathProvider.getForGenDir();
		Path recording = PathProvider.getForRecordingDir();
		Path screenshots = PathProvider.getForScreenshotsDir();
		Path misc = PathProvider.getForMiscDir();
		Path reports = PathProvider.getForReportsDir();
		Path logs = PathProvider.getForLogs();
		Path downloads = PathProvider.getForDownloads();
		Path currentFolder = PathProvider.getForCurrentFolder();
		try {
			if(!gen.toFile().exists()) {
				FileUtils.forceMkdir(gen.toFile());
			}
			if(!recording.toFile().exists()) {
				FileUtils.forceMkdir(recording.toFile());
			}
			if(!screenshots.toFile().exists()) {
				FileUtils.forceMkdir(screenshots.toFile());
			}
			if(!misc.toFile().exists()) {
				FileUtils.forceMkdir(misc.toFile());
			}
			if(!reports.toFile().exists()) {
				FileUtils.forceMkdir(reports.toFile());	
			}
			if(!logs.toFile().exists()) {
				FileUtils.forceMkdir(logs.toFile());
			}
			if(!downloads.toFile().exists()) {
				FileUtils.forceMkdir(downloads.toFile());
			}
			if(!currentFolder.toFile().exists()) {
				FileUtils.forceMkdir(currentFolder.toFile());
			}
			copyUploadableFile();
		} catch (IOException e) {
			throw new IHCLBusinessException("Error occurred while creating project structure.",e);
		}
	}
	
	public static void copyUploadableFile(){
		if(PropertyUtil.getBoolean("copy.uploadable-file.toUserHome", false)) {
			copyFile(Paths.get(PathProvider.getForTestData().toString(), SAMPLE_PNG).toFile(), new File(System.getProperty(USER_HOME)));
			}
		}
	
	public static void copyFile(File srcFile, File destDir){
		if((Objects.nonNull(srcFile) && Objects.nonNull(destDir)) && (isValidFile(srcFile) && isValidDir(destDir))){
			try {
				FileUtils.copyFileToDirectory(srcFile, destDir);
			} catch (IOException e) {
				LOG.error("Unknown error occurred while copying the File:"+srcFile+" to Destination:"+destDir,e);
			}
		}
	}
	
	private static boolean isValidFile(File file){
		return file.exists() && file.isFile();
	}
	
	private static boolean isValidDir(File dir){
		return dir.exists() && dir.isDirectory();
	}
	
	public static String getConfiguredWebDriverKillCommand(){
		try{
			Browser browser = getConfiguredDriverBrowser();
			if(Objects.nonNull(browser)){
				switch (browser) {
					case IE: return PropertyUtil.getString(IHCLConstantsWeb.IE_PROCESS_KILL);
					case FIREFOX: return PropertyUtil.getString(IHCLConstantsWeb.FIREFOX_PROCESS_KILL);
					default: return PropertyUtil.getString(IHCLConstantsWeb.CHROME_PROCESS_KILL);
				}
			}
		}catch(Exception e){
			LOG.error("Unknown error occurred while killing WebDriver process.", e);
		}
		return PropertyUtil.getString(IHCLConstantsWeb.CHROME_PROCESS_KILL);
	}
	
	public static void killProcesses(){
		execCMD(CommonUtils.getConfiguredWebDriverKillCommand());
		execCMD(PropertyUtil.getString(IHCLConstantsWeb.ADDFILES_PROCESS_KILL));
	}
	
	public static String getCurExeDirTimestamp() {
		Object object = Registry.getAttribute(RegistryKey.DIR_TIMESTAMP_SUFFIX);
		if(Objects.nonNull(object)) {
			return (String)object;
		}else {
			String timestamp = new SimpleDateFormat(IHCLConstantsWeb.DDMMYYHHMMSSSSS).format(new Date());
			Registry.setAttribute(RegistryKey.DIR_TIMESTAMP_SUFFIX, timestamp);
			return timestamp;
		}
	}
	
	public static boolean writeToPropertiesFile(Path path, Properties properties){
		boolean isWritten = false;
		if(Objects.nonNull(properties) && !properties.isEmpty() && Objects.nonNull(path)){
			try(OutputStream stream = new FileOutputStream(path.toFile())){
				properties.store(stream, "This file is auto-generated by project.");
				isWritten = true;
			}catch(IOException|RuntimeException e){
				LOG.error("Error occurred while writing the file to "+path+" location with content ["+properties+"]. with StackTrace["+e+"]");
			}
		}
		return isWritten;
	}
	
	public static Properties readProperties(Path path){
		Properties properties = null;
		if(Objects.nonNull(path)){
			try(BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))){
				properties = new Properties();
				properties.load(reader);
			}catch(IOException|RuntimeException e){
				LOG.error("Error occurred while reading file from "+path+". with StackTrace["+e+"]");
			}
		}
		return properties;	
	}
	
	public static void killExcelProcess(){
		if(PropertyUtil.getBoolean("execl-process.kill.enabled", true)) {
			execCMD("cmd /c start taskkill /F /IM excel.exe");
		}
	}
	
	public static void uploadSamplFile(){
		execCMD(ADD_FILES_UPLOAD);
	}
	
	public static void execCMD(String cmd) {
		try {
			Runtime.getRuntime().exec(cmd);
		}catch(IOException|RuntimeException e) {
			LOG.error("Error occured while executing the command :"+cmd, e);
		}
	}
	
	public static int getDefaultImplicitTime(){
		return PropertyUtil.getInt(IHCLConstantsWeb.DEFAULT_IMPLICIT_TIME, 30);
	}
	
	public static int getDefaultExplicitTime(){
		return PropertyUtil.getInt(IHCLConstantsWeb.DEFAULT_EXPLICIT_TIME, 30);
	}
	
	public static String getApplicationName(){
		Object object = Registry.getAttribute(RegistryKey.APP_NAME);
		if(Objects.nonNull(object)) {
			return (String)object;
		}else {
			String appName = PropertyUtil.getString(IHCLConstantsWeb.APPLICATION_NAME);
			Registry.setAttribute(RegistryKey.APP_NAME, appName);
			return appName;
		}
	}
	
	public static void initCurrentExeDirsInspection(String timestamp){
		checkAndCreateProjStructure();
		genScreenshotDirsForCurrentExe(timestamp);
		getRecordingDirForCurrentExe(timestamp);
	}
}