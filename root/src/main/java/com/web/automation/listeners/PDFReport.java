package com.web.automation.listeners;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.ihclweb.automation.consts.IHCLConstantsWeb;
import com.ihclweb.automation.registry.Registry;
import com.ihclweb.automation.registry.RegistryKey;
import com.ihclweb.automation.util.CommonUtils;
import com.ihclweb.automation.util.PathProvider;
import com.ihclweb.automation.util.PropertyUtil;
import com.ihclweb.automation.util.exceptions.IHCLBusinessException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.techouts.ihclweb.webelement.ops.WebElementOperationsWeb;
import com.web.automation.dto.pdfreport.SuiteResults;
import com.web.automation.dto.pdfreport.SuitesResult;
import com.web.automation.dto.pdfreport.TestResults;
import com.web.automation.listeners.JyperionListener.DocumentContent;
import com.web.automation.pdfreport.populator.SuitesResultPopulator;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class PDFReport implements IReporter {

	private static final Logger LOG = Logger.getLogger(PDFReport.class);
	private Path path = null;
	
	private static final Font WELCOME_PAGE_FONT = FontFactory.getFont(FontFactory.TIMES, 30f , Font.BOLD , new Color(0, 26, 77));
	private static final Font STATS_TITLE_FONT = FontFactory.getFont(FontFactory.TIMES, 16f , Font.BOLD , new Color(0, 26, 77));
	private static final boolean CREATE_TAB_ON_NO_RECORDS = PropertyUtil.getBoolean("create.test-results-table.on.no-records", true);

	private static final Font STATS_FONT_VALUE;
	private static final Font STATS_FONT_KEY;
	private static final Font STATS_FONT_DATE;
	
	static{
		Font baseFont = FontFactory.getFont(FontFactory.HELVETICA, 10f );
		STATS_FONT_VALUE = new Font(baseFont);
		baseFont.setSize(11f);
		STATS_FONT_KEY = new Font(baseFont);
		baseFont.setSize(8f);
		STATS_FONT_DATE = new Font(baseFont);
	}
	
	private static final Font SUITE_DECLARATION_FONT = FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, Color.BLACK);
	private static final Font TABLE_INITIAL_TITLE_FONT = FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD, Color.BLACK);
	private static final Font TABLE_TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
	private static final Font TABLE_SUB_TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, Color.BLACK);
	private static final Font TEST_RECORD_FONT = new Font(Font.TIMES_ROMAN, 10);
	
	private static final Color PASSED_COLOR = new Color(20, 229, 104);
	private static final Color SKIPPED_COLOR = new Color(255, 153, 51);
	private static final Color FAILED_COLOR = new Color(255, 63, 63);
	
	private Document document = null;
	
	public PDFReport() {
		document = new Document();
		if(PropertyUtil.getBoolean("report.listener-addition", true)) {
			document.addDocListener(new ReportListener());
		}
		Object obj = Registry.getAttribute(RegistryKey.PDF_REPORT_FILE_PATH);
		path = Objects.nonNull(obj) ? ((Path)obj) : PathProvider.getGenPDFReportFilePath(CommonUtils.getCurExeDirTimestamp());
		LOG.info("=======>>>>>>>>>> PDF Report Generation Path:" +path.toString());
	}

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		try(FileOutputStream outStream = new FileOutputStream(path.toFile())){
			SuitesResult suitesResult = populateSuitesResult(suites);
			if(LOG.isDebugEnabled()) {
			LOG.debug("=====>>>>>> Suites Result: "+suitesResult);
			}
			openDocument(outStream);
			genWelcomePage(suitesResult);
			prepareReportForSuites(suitesResult);
			genStatsPage(suitesResult);
			document.close();
		}catch(Exception e){
			throw new IHCLBusinessException("Unknown error occurred while generating report with StackTrace:",e);
		}
	}
	
	
	private void prepareReportForSuites(SuitesResult suitesResult) {
		suitesResult.getSuiteResults().stream().filter(Objects::nonNull).forEach(this::prepareReportForSuite);
	}
	
	
	private void prepareReportForSuite(SuiteResults suiteResults) {
		document.newPage();
		try{
			if(PropertyUtil.getBoolean("create.suite-entry.on.no-records", true) || suiteResults.totalTestsExecuted() > 0){
				Paragraph suiteDeclParagraph = new Paragraph(suiteResults.getSuiteName() + " Suite Result:", SUITE_DECLARATION_FONT);
				suiteDeclParagraph.setAlignment(Element.ALIGN_LEFT);
				suiteDeclParagraph.setSpacingBefore(10f);
				suiteDeclParagraph.setSpacingAfter(30f);
				document.add(suiteDeclParagraph);
				if(CREATE_TAB_ON_NO_RECORDS || suiteResults.totalTestsPassed() > 0) {
					createTable(suiteResults.getPassedTests(), PASSED_COLOR, false, "PASSED TEST SCRIPTS");
				}
				if(CREATE_TAB_ON_NO_RECORDS || suiteResults.totalTestsSkipped() > 0) {
					createTable(suiteResults.getSkippedTests(), SKIPPED_COLOR, true, "SKIPPED TEST SCRIPTS");
				}
				if(CREATE_TAB_ON_NO_RECORDS || suiteResults.totalTestsFailed() > 0) {
					createTable(suiteResults.getFailedTests(), FAILED_COLOR, true, "FAILED TEST SCRIPTS");
				}
			}
		}catch(Exception e){
			throw new IHCLBusinessException(e);
		}
	}
	
	protected void createTable(Set<TestResults> results, Color headerColor, boolean includeCause, String initialTitle) throws DocumentException{
		PdfPTable table = getReportTableWithHeaders(true, headerColor, initialTitle, getTestColumnHeaders(), getTableCellsWidths(true));
		if(CollectionUtils.isNotEmpty(results)){
			results.forEach(a -> {
				table.addCell(getTableSubHeaderCell(a.getTestName()));
				fillTable(table, a, includeCause);
			});
		}
		if(CollectionUtils.isNotEmpty(results) || PropertyUtil.getBoolean("add.test-results.table.on.no-results", true))
			{
			document.add(table);
			}
	}
	
	protected void fillTable(PdfPTable table, TestResults testResults, boolean includeCause){
		testResults.getResults().stream().forEach(a -> {
			table.addCell(new PdfPCell(new Paragraph(getTestName(a), TEST_RECORD_FONT)));
			table.addCell(new PdfPCell(new Paragraph(StringUtils.EMPTY + WebElementOperationsWeb.convertMillis(a.getEndMillis()-a.getStartMillis()), TEST_RECORD_FONT)));
			table.addCell(new PdfPCell((includeCause && Objects.nonNull(a.getThrowable())) ? getErrMsgCell(a.getThrowable()) : new PdfPCell(new Paragraph("N/A", TEST_RECORD_FONT))));
		});
	}
	
	private PdfPCell getErrMsgCell(Throwable cause){
		PdfPCell cell = null;
		if(Objects.nonNull(cause)){
			Paragraph excep = new Paragraph(Objects.nonNull(cause.getCause()) ? cause.getCause().getMessage() : cause.getMessage(), new Font(Font.TIMES_ROMAN, 8));
			if(PropertyUtil.getBoolean("test-fail.report.screenshot.attachment", false)) {
				excep.add(getChunkForScreenShot());
			}
			cell = new PdfPCell(excep);
		}
		return cell;
	}
	
	private Chunk getChunkForScreenShot(){
		Chunk chunk = new Chunk(DocumentContent.LINKED_SCREENSHOT, new Font(Font.TIMES_ROMAN, 8, Font.UNDERLINE));
		chunk.setAction(new PdfAction(DocumentContent.FILE_PROTOCOL + PathProvider.getForScreenshotsDir().toString()));
		return chunk;
	}
	
	protected String[] getTestColumnHeaders(){
		return new String[]{"TestCase Name", "Duration", "Failure Remarks"};
	}
	
	protected float[] getTableCellsWidths(boolean isReportTable){
		return isReportTable ? new float[]{ .6f, .15f, .5f } : new float[] { .5f, .8f }; 
	}
	
	protected PdfPTable getReportTableWithHeaders(boolean isReportTable, Color cellBgColor, String title, String[] headers, float...cellWidths){
		PdfPTable table = new PdfPTable(cellWidths);
		table.setTotalWidth( isReportTable ? 500f : 200f);
		table.setLockedWidth(true);
		createInitialTableHeader(table, title, cellBgColor);
		createTableColumnHeader(table, headers);
		table.setSpacingAfter(25f);
		return table;
	}
	
	private void createInitialTableHeader(PdfPTable table, String title, Color color) {
		PdfPCell cell = new PdfPCell(new Paragraph(title, TABLE_INITIAL_TITLE_FONT));
		cell.setColspan(getTestColumnHeaders().length +1);
		cell.setBackgroundColor(color);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
	}

	protected void createTableColumnHeader(PdfPTable table, String... headers){
		Arrays.asList(headers).stream().forEach(a -> {
			PdfPCell cell = new PdfPCell(new Paragraph(a, TABLE_TITLE_FONT));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			table.addCell(cell);
		});
	}
	
	protected PdfPCell getTableSubHeaderCell(String title){
		PdfPCell cell = new PdfPCell(new Paragraph(title + " Tests ", TABLE_SUB_TITLE_FONT));
		cell.setColspan(getTestColumnHeaders().length + 1);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		return cell;
	}
	
	
	protected void genWelcomePage(SuitesResult suitesResult) throws DocumentException{
		document.add(new Paragraph(" "));

		Paragraph title = new Paragraph(Objects.requireNonNull(PropertyUtil.getString("report.pdf.welcome-page.title"), "Report Welcome-Page Title Cannot be null."), WELCOME_PAGE_FONT);
		title.setSpacingBefore(document.getPageSize().getHeight()/3);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);
		
		if(PropertyUtil.getBoolean("pdfreport.include.suite-names.executed.in.welcome-page", true)){
			Paragraph subTitle = new Paragraph("Of Suite(s): " + suitesResult.getSuitesExecuted(), FontFactory.getFont("calibri", 8f , Font.BOLD , Color.DARK_GRAY));
			subTitle.setAlignment(Element.ALIGN_CENTER);
			subTitle.setSpacingBefore(5f);
			document.add(subTitle);
		}
		
	}
	
	private static int rowCount = 0;

	protected PdfPCell getStatsCell(String token, Color color, Font font){
		Paragraph paragraph = new Paragraph(token, font);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cell = new PdfPCell(paragraph);
		cell.setBackgroundColor(color);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		return cell;
	}
	
	private static Color getAlternateColor(){
		Color color = null;
		if(PropertyUtil.getBoolean("stats-tab.alternative-color.enabled", false)){
			rowCount++;
			color = ((rowCount % 2) == 0) ? Color.WHITE : new Color(153, 204, 255);
		}else{
			color = Color.WHITE;
		}
		return color;
	}
	
	protected void genStatsPage(SuitesResult suitesResult) throws DocumentException{
		
		document.newPage();
		document.add(new Paragraph(" "));
		
		Paragraph paragraph = new Paragraph(Objects.requireNonNull(PropertyUtil.getString("pdfreport.stats.title"),"PDF Report Stats title cannot be null"), STATS_TITLE_FONT);
		paragraph.setSpacingBefore(50f);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);
		
		PdfPTable table = new PdfPTable(getTableCellsWidths(false));
		table.setSpacingBefore(10f);
		
		Color color = getAlternateColor();
		table.addCell(getStatsCell("Environment", color, STATS_FONT_KEY));
		table.addCell(getStatsCell(PathProvider.getIhclBaseURL(), color, STATS_FONT_VALUE));
		
		color = getAlternateColor();
		Phrase phrase = new  Phrase(new Chunk("Execution Date ", STATS_FONT_KEY));
		phrase.add(new Chunk("["+PropertyUtil.getString(IHCLConstantsWeb.REPORT_DATE_FORMAT).toUpperCase()+"]", STATS_FONT_DATE));
		table.addCell(phrase);
		table.addCell(getStatsCell(getDateAsString(), color, STATS_FONT_VALUE));
		
		color = getAlternateColor();
		table.addCell(getStatsCell("Total Suite(s) Execution Time", color, STATS_FONT_KEY));
		table.addCell(getStatsCell(WebElementOperationsWeb.convertMillis(suitesResult.getEndDate().getTime()-suitesResult.getStartDate().getTime()), color, STATS_FONT_VALUE));

		color = getAlternateColor();
		table.addCell(getStatsCell("Total Test(s) Executed", color, STATS_FONT_KEY));
		table.addCell(getStatsCell(String.valueOf(suitesResult.totalTestsExecuted()), color, STATS_FONT_VALUE));
		
		color = getAlternateColor();
		table.addCell(getStatsCell("Total Test(s) Passed", color, STATS_FONT_KEY));
		table.addCell(getStatsCell(String.valueOf(suitesResult.totalTestsPassed()), color, STATS_FONT_VALUE));

		color = getAlternateColor();
		table.addCell(getStatsCell("Total Test(s) Failed", color, STATS_FONT_KEY));
		table.addCell(getStatsCell(String.valueOf(suitesResult.totalTestsFailed()), color, STATS_FONT_VALUE));

		color = getAlternateColor();
		table.addCell(getStatsCell("Total Test(s) Skipped", color, STATS_FONT_KEY));
		table.addCell(getStatsCell(String.valueOf(suitesResult.totalTestsSkipped()), color, STATS_FONT_VALUE));

		color = getAlternateColor();
		table.addCell(getStatsCell("Total Suite(s) Executed", color, STATS_FONT_KEY));
		table.addCell(getStatsCell(suitesResult.getSuitesExecuted(), color, STATS_FONT_VALUE));

		table.setSpacingAfter(20f);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		document.add(table);
	}
	
	private void openDocument(OutputStream stream) throws DocumentException{
		PdfWriter.getInstance(document, stream);
		document.open();
	}
	
	private String getDateAsString(){
		return new SimpleDateFormat(PropertyUtil.getString(IHCLConstantsWeb.REPORT_DATE_FORMAT), Locale.US).format(new Date());
	}
	
	protected SuitesResult populateSuitesResult(List<ISuite> suites){
		SuitesResult suitesResult = new SuitesResultPopulator().populate(suites);
		removeDuplicates(suitesResult);
		Registry.setAttribute(RegistryKey.TEST_SUITES_RESULT, suitesResult);
		return suitesResult;
	}
	
	private String getTestName(ITestResult result){
		return result.getTestClass().getName().substring(result.getTestClass().getName().lastIndexOf('.')+1);
	}
	
	private void removeDuplicates(SuitesResult suitesResult){
		if(PropertyUtil.getBoolean("is.test-results.manipulation.enabled", true)){
			suitesResult.getSuiteResults().stream().filter(Objects::nonNull).forEach(this::rmDuplicatesOnSuiteResults);
		}
	}
	
	private void rmDuplicatesOnSuiteResults(SuiteResults results){
		/* Removing duplicate results from Skipped Test Results which already available with Failed Test Results*/ 
		results.getFailedTests().stream().filter(Objects::nonNull).forEach(a -> rmDuplicatesOnTests(results.getSkippedTests(), a));
		/* Removing duplicate results from Skipped Test Results which already available with Failed Test Results*/ 
		results.getPassedTests().stream().filter(Objects::nonNull).forEach(a -> rmDuplicatesOnTests(results.getSkippedTests(), a));
	}
	
	private void rmDuplicatesOnTests(Set<TestResults> skippedTests, TestResults failedTest){
		Optional<TestResults> optional = skippedTests.stream().filter(a -> a.getTestName().equals(failedTest.getTestName())).findAny();
		if(optional.isPresent()){
			TestResults skippedTest = optional.get();
			Set<ITestResult> filteredResults = skippedTest.getResults().stream().filter(a -> isRecordNotPresent(a, failedTest.getResults())).collect(Collectors.toSet());
			skippedTest.getResults().clear();
			skippedTest.setResults(new HashSet<>(filteredResults));
		}
	}
	
	private boolean isRecordNotPresent(ITestResult record, Set<ITestResult> results){
		boolean isPresent = false;
		if(CollectionUtils.isNotEmpty(results) && Objects.nonNull(record)){
			isPresent = results.stream().noneMatch(a -> !getTestName(record).equals(getTestName(a)));
		}
		return isPresent;
	}
}