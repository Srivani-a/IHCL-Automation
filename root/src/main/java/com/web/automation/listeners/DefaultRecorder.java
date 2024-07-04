package com.web.automation.listeners;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_MJPG;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import com.ihclweb.automation.registry.RegistryKey;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class DefaultRecorder extends ScreenRecorder {

	private static final Logger LOG = Logger.getLogger(DefaultRecorder.class);
	private static final Path RECORD_CURR_EXE_DIR = (Path)com.ihclweb.automation.registry.Registry.getAttribute(RegistryKey.CURRENT_EXE_MOVIE_ROOT_PATH);
	private String filename;
	private static DefaultRecorder recorder = null;


	private boolean isRecording;
	
	public static DefaultRecorder instance(){
		if(Objects.isNull(recorder)){
			try{
				recorder = getInstance();
			} catch(Exception e){
				LOG.error("Unknown error occurred while while initializing Recorder..."+e.getMessage());
			}
		}
		return recorder;
	} 
	
	
	private static DefaultRecorder getInstance() throws IOException, AWTException{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle captureSize = new Rectangle(0, 0, screenSize.width, screenSize.height);
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		return new DefaultRecorder(gc, captureSize,
				new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_MJPG, CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
						Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
				null, RECORD_CURR_EXE_DIR.toFile());
	}
	
	private DefaultRecorder(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat,
			Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder)
			throws IOException, AWTException {
		super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
		LOG.debug("Screen Recorder Initialized....!");
	}
	
	
	@Override
	protected File createMovieFile(Format fileFormat) throws IOException {
		return new File(movieFolder, filename + "-" + new SimpleDateFormat("HHmmss").format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
	}
	
	
	void startRecord(String filename) throws IOException {
		if(isRecording) { 
			stopRecord();
		}
		this.filename = filename;
		recorder.start();
		isRecording = true;
		LOG.debug("Started Recording with Name:"+filename);
	}

	void stopRecord() throws IOException {
		if(isRecording){
			recorder.stop();
			isRecording = false;
			LOG.debug("Stopped Recording...");
		}
	}
	
	public boolean isRecordingInProgress(){
		return isRecording;
	}
}
