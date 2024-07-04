package com.web.automation.listeners;

import org.apache.log4j.Logger;

import com.lowagie.text.DocListener;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Rectangle;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class ReportListener implements DocListener {

	private static final Logger LOG = Logger.getLogger(ReportListener.class);
	
	@Override
	public boolean add(Element paramElement) throws DocumentException {
		return false;
	}

	@Override
	public void open() {
		LOG.debug("Document Opened...!");
	}

	@Override
	public void close() {
		LOG.debug("Document Closed...!");
	}

	@Override
	public boolean newPage() {
		return false;
	}

	@Override
	public boolean setPageSize(Rectangle paramRectangle) {
		return false;
	}

	@Override
	public boolean setMargins(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
		return false;
	}

	@Override
	public boolean setMarginMirroring(boolean paramBoolean) {
		return false;
	}

	@Override
	public boolean setMarginMirroringTopBottom(boolean paramBoolean) {
		return false;
	}

	@Override
	public void setPageCount(int paramInt) {
	
	}

	@Override
	public void resetPageCount() {
		
	}

	@Override
	public void setHeader(HeaderFooter paramHeaderFooter) {
		
	}

	@Override
	public void resetHeader() {
		
	}

	@Override
	public void setFooter(HeaderFooter paramHeaderFooter) {
	
	}

	@Override
	public void resetFooter() {
	
	}
}