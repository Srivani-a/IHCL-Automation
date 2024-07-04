package com.ihclweb.automation.repo.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ihclweb.automation.exceptions.DuplicateUIDException;
import com.ihclweb.automation.exceptions.ExcelIOException;
import com.ihclweb.automation.exceptions.ExcelRepositoryException;
import com.ihclweb.automation.exceptions.ValueNotFoundException;
import com.ihclweb.automation.repo.ExcelRepository;
import com.ihclweb.automation.util.CommonUtils;

/**
 * 
 * @author sairam.p
 *
 */
public class DefaultExcelRepository implements ExcelRepository {

	private static final Logger LOG = Logger.getLogger(DefaultExcelRepository.class);
	
	private boolean isClosed;
	private XSSFWorkbook workbook = null;
	private String path = null;
	private boolean isOnDemand;
	private static int initCount = 0;
	private boolean isDirty;
	
	private InputStream inputStream = null;
	
	public DefaultExcelRepository(String path, boolean isOnDemand){
		CommonUtils.killExcelProcess();
		this.path = Objects.requireNonNull(path);
		this.isOnDemand = isOnDemand;
		initWorkbook();
	}
	
	private void initWorkbook(InputStream inputStream){
		try {
			this.inputStream = Objects.requireNonNull(inputStream);
			this.workbook = new XSSFWorkbook(inputStream);
			isClosed = false;
			incrementCount();
			LOG.debug("Workbook initialization completed with Count: "+getInitCount());
		} catch (IOException e) {
			LOG.error("Error occured while initializing the workbook.",e);
			throw new ExcelRepositoryException("Error occured while initializing the workbook.",e);
		}
	}
	
	public static int getInitCount() {
		return initCount;
	}

	private static void incrementCount() {
		DefaultExcelRepository.initCount++;
	}

	private void initWorkbook(){
		File file = new File(path);
		if(file.exists() && file.isFile()){
			try {
				initWorkbook(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				throw new ExcelIOException("Error occured while initializing the workbook with give Path: "+path+".",e);
			}
		}
	}
	
	protected XSSFWorkbook getWorkbook() {
		return workbook;
	}

	protected boolean isClosed() {
		return isClosed;
	}

	protected void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public String getPath() {
		return path;
	}
	
	protected void flush(){
		if(isOnDemand) {
			forceFlush(); 
	} 
	}
	private void forceFlush(){
		try {
			if(LOG.isDebugEnabled()) {
				LOG.debug("Force flushing the file on on write detected for the Strategy ===>>> OnDemandIO.");
			}
				closeStreams();
			reInitWorkbook();
		} catch (IOException e) {
			throw new ExcelRepositoryException("Error occured while performing force flush.",e);
		}
	}
	
	private void closeStreams() throws IOException{
		closeInStream();
		closeOutStream();
		workbook.close();
		isClosed = true;
	}
	
	private void reInitWorkbook() {
		makeStreamsNull();
		initWorkbook();
	}
	
	private void closeOutStream() throws IOException{
		if(isDirty){
			try(OutputStream stream = new FileOutputStream(path)){
				workbook.write(stream);
				isDirty = false;
			}catch(Exception e){
				LOG.error("Error occurred while writing to File:"+path);
				throw new ExcelIOException(e);
			}
		}
	}
	
	private void closeInStream() throws IOException{
		if(inputStream != null) {
			inputStream.close();
	}
	}
	private void makeStreamsNull(){
		if(Objects.nonNull(inputStream)) {
			inputStream = null;}
		if(Objects.nonNull(workbook)) {
			workbook = null;
	}
	
	}
	@Override
	public final boolean isUniqueValueInColumn(String sheet, int pkCid, String value){
		boolean isUnique = false;
		if(StringUtils.isNotBlank(sheet) && StringUtils.isNotBlank(value) && pkCid >= 0){
			XSSFSheet xssfSheet = getSheet(sheet);
			if(Objects.nonNull(xssfSheet)){
				List<XSSFCell> cells = getColumnCells(xssfSheet, pkCid);
				if(CollectionUtils.isNotEmpty(cells)){
					isUnique = cells.stream().filter(Objects::nonNull).filter(a -> {
						a.setCellType(CellType.STRING);
						return value.equals(a.getStringCellValue());
					}).count() == 1;
				}
			}
		}else{
			LOG.warn("Mandatory inputs found null/empty/incorrect to find unique value in column with values Sheet: "+sheet+", ColumnID: "+pkCid+", Value: "+value);
		}
		return isUnique;
	}
	
	@Override
	public boolean isUniqueValueInRow(String sheet, int rid, String value){
		boolean isUnique = false;
		if(StringUtils.isNotBlank(sheet) && StringUtils.isNotBlank(value) && rid >= 0){
			List<XSSFCell> cells = getRowCells(sheet, rid);
			if(CollectionUtils.isNotEmpty(cells)){
				isUnique = cells.stream().filter(Objects::nonNull).filter(a -> {
					a.setCellType(CellType.STRING);
					return value.equals(a.getStringCellValue());
				}).count() == 1;
			}
		}else{
			LOG.warn("Mandatory inputs found null/empty/incorrect to find unique value in row with values Sheet: "+sheet+", RowID: "+rid+", Value: "+value);
		}
		return isUnique;
	}
	
	public List<XSSFRow> getAllRows(XSSFSheet sheet){
		List<XSSFRow> rows = null;
		if(Objects.nonNull(sheet)){
			rows = new ArrayList<>(sheet.getLastRowNum()+1);
			for(int i=0; i<=sheet.getLastRowNum(); i++){
				if(Objects.nonNull(sheet.getRow(i))) {
					rows.add(sheet.getRow(i));
			}
			}
		}else{
			LOG.warn("Mandatory data, Sheet:"+sheet+" cannot be null to get all the rows.");
		}
		return CollectionUtils.isEmpty(rows) ? Collections.emptyList() : rows;
	}
	
	public List<XSSFCell> getRowCells(String sheet, int rid){
		List<XSSFCell> cells = null;
		if(StringUtils.isNotBlank(sheet) && rid>=0){
			XSSFSheet xssfSheet = getSheet(sheet);
			if(Objects.nonNull(xssfSheet) && Objects.nonNull(xssfSheet.getRow(rid))){
				XSSFRow row = xssfSheet.getRow(rid);
				cells = new ArrayList<>(row.getLastCellNum()+1);
				for(int i = row.getFirstCellNum(); i <= row.getLastCellNum() ; i++){
					if(Objects.nonNull(row.getCell(i))) {
						cells.add(row.getCell(i));
					}
					}
			}
		}else{
			LOG.warn("Mandatory info Sheet:"+sheet+" and Row["+rid+"] cannot be null to get all the cells within the row.");
		}
		return CollectionUtils.isEmpty(cells) ? Collections.emptyList() : cells;
	}
	
	
	public List<XSSFCell> getColumnCells(XSSFSheet sheet, int cid){
		List<XSSFCell> cells = null;
		if(Objects.nonNull(sheet) && cid >= 0){
			List<XSSFRow> rows = getAllRows(sheet);
			if(CollectionUtils.isNotEmpty(rows)){
				cells = new ArrayList<>(rows.size());
				for(int i=0; i<rows.size(); i++){
					if(Objects.nonNull(rows.get(i)) && Objects.nonNull(rows.get(i).getCell(cid))){
						cells.add(rows.get(i).getCell(cid));
					}
				}
			}
		}else{
			LOG.warn("Mandatory input Sheet:"+sheet+" and Column["+cid+"] cannot be null to get all the cells within the column.");
		}
		return CollectionUtils.isEmpty(cells) ? Collections.emptyList() : cells;
	}
	
	public XSSFSheet getSheet(String sheet){
		return StringUtils.isNotBlank(sheet) ? getWorkbook().getSheet(sheet) : null;
	}
	
	public XSSFRow getRow(String sheet, int rid){
		XSSFSheet xssfSheet = getSheet(sheet);
		return getRow(xssfSheet, rid);
	}
	
	public XSSFRow getRow(XSSFSheet sheet, int rid){
		return Objects.nonNull(sheet) && rid >= 0 ? sheet.getRow(rid) : null;
	}
	
	public XSSFCell getCell(XSSFRow row, int cid){
		XSSFCell cell = null;
		if(Objects.nonNull(row) && cid >=0){
			cell = row.getCell(cid);
		}
		return cell;
	}
	
	public XSSFCell getCell(String sheet, int rid, int cid){
		XSSFCell cell = null;
		if(StringUtils.isNotBlank(sheet) && rid >= 0 && cid >= 0){
			XSSFRow row = getRow(sheet, rid);
			if(Objects.nonNull(row)) {
				cell = row.getCell(cid);
			}
		}
		return cell;
	}
	
	public void writeTo(String sheet, int cid, int rid, String content) {
		if(LOG.isDebugEnabled()) {
			LOG.debug("Writing Value:"+content+" in Cell[RowID: "+rid+" and ColumnID: "+cid+"] in the Sheet: "+sheet);
		}
			XSSFSheet xssfSheet = getWorkbook().getSheet(sheet);
		XSSFRow row = getOrCreateRow(xssfSheet, rid);
		XSSFCell cell = getOrCreateCell(row, cid);
		cell.setCellValue(content);
		isDirty = true;
		flush();
	}

	public XSSFSheet getOrCreateSheet(String sheet) {
		XSSFSheet xssfSheet = null;
		if(StringUtils.isNotBlank(sheet)){
			return Objects.isNull(getWorkbook().getSheet(sheet)) ? getWorkbook().createSheet(sheet) : getWorkbook().getSheet(sheet); 
		}
		return xssfSheet;
	}

	@Override
	public String readStringFrom(String sheet, int cid, int rid) {
		String value = null;
		if(StringUtils.isNotBlank(sheet) && cid >= 0&& rid >= 0){
			XSSFSheet xssfSheet = getSheet(sheet);
			if(Objects.nonNull(xssfSheet) && Objects.nonNull(xssfSheet.getRow(rid))){
				XSSFCell cell = xssfSheet.getRow(rid).getCell(cid);
				if(Objects.nonNull(cell)){
					cell.setCellType(CellType.STRING);
					value = cell.getStringCellValue();
				}
			}
		}else{
			LOG.warn("Mandatory inputs found null/empty/incorrect to read value in from cell with values, Sheet: "+sheet+", ColumnID: "+cid+", RowID: "+rid);
		}
		return value;
	}

	@Override
	public int getRowIDOnUniqueValue(String sheet, String value, int pkCid) {
		if(StringUtils.isNotBlank(sheet) && StringUtils.isNotBlank(value) && pkCid >= 0){
			XSSFSheet xssfSheet = getSheet(sheet);
			if(Objects.nonNull(xssfSheet)){
				if(isUniqueValueInColumn(sheet, pkCid, value)){
					List<XSSFCell> cells = getColumnCells(xssfSheet, pkCid);
					if(CollectionUtils.isNotEmpty(cells)){
						Optional<XSSFCell> optional = cells.stream().filter(Objects::nonNull).filter(a -> value.equals(a.getStringCellValue())).findAny();
						return getIdx(optional, sheet, value);
					}
				} else {
					throw new DuplicateUIDException("Duplicate/No UID found with Value: "+value+" from Sheet: "+sheet);
				}
			}
		}else{
			LOG.warn("Mandatory input Sheet:"+sheet+" or Lookup Column:"+pkCid+" or Value:"+value+" cannot be null to find the row id.");
		}
		throw new ValueNotFoundException("No value found in the supplied Sheet:"+sheet+" with uid/value:"+value);
	}

	
	private int getIdx(Optional<XSSFCell> optional, String sheet, String value) {
		if(optional.isPresent()) {
			return optional.get().getRowIndex();
		}
		else {
			throw new ValueNotFoundException("No value found in the supplied Sheet:"+sheet+" with uid/value:"+value);
	}
	}
	@Override
	public String getCellDataOnUID(String uid, int uidLookupColumn, int columnIndex, String sheetName) {
		int rowIdx = getRowIDOnUniqueValue(sheetName, uid, uidLookupColumn);
		return readStringFrom(sheetName, columnIndex, rowIdx);
	}

	@Override
	public void writeToCellOnUID(String uid, int uidLookupColumn, int columnIndex, String sheetName, String value) {
		int rowIdx = getRowIDOnUniqueValue(sheetName, uid, uidLookupColumn);
		writeTo(sheetName, columnIndex, rowIdx, value);
	}

	public XSSFRow getOrCreateRow(XSSFSheet sheet, int rid) {
		XSSFSheet xssfSheet = Objects.requireNonNull(sheet);
		if(rid >= 0){
			XSSFRow row = xssfSheet.getRow(rid);
			return Objects.isNull(row) ? xssfSheet.createRow(rid) : row;
		}
		return null;
	}

	public XSSFCell getOrCreateCell(XSSFRow row, int cid) {
		return Objects.nonNull(row.getCell(cid)) ? row.getCell(cid) : row.createCell(cid);
	}
	
	public void closeBook() throws IOException{
		if(Objects.nonNull(getWorkbook()) && !isClosed) {
			closeStreams();
		}
	}
	
	public void closeBookQuitely(){
		try {
			closeBook();
		} catch (Exception e) {
			LOG.error("Unknown error occurred while closing workbook"+e.getMessage());
		}
	}

	@Override
	public List<String> getCSVData(String sheet, int cid, int rid) {
		return getSplittedDataWithCustomSeparator(sheet, cid, rid, ",");
	}

	@Override
	public List<String> getSplittedDataWithCustomSeparator(String sheet, int cid, int rid, String separator) {
		String data = readStringFrom(sheet, cid, rid);
		return StringUtils.isNotBlank(data) ? Arrays.asList(data.split(separator)) : Collections.emptyList();
	}
}