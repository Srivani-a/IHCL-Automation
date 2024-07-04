package com.ihclweb.automation.repo;

import java.util.List;

/**
 * 
 * @author rajeevsai.t
 *
 */
public interface ExcelRepository {

	
	/**
	 * 
	 * This method will write the supplied content to the corresponding cell by supplied cid and rid
	 * 
	 * @param sheet sheet name to lookup
	 * @param cid column index/ID
	 * @param rid row index/ID
	 * @param content value to be written to corresponding cell
	 */
	public abstract void writeTo(String sheet, int cid, int rid, String content);
	
	/**
	 * 
	 * This method will read corresponding cell value by supplied cid and rid within the sheet and returns the same. 
	 * 
	 * @param sheet sheet name to lookup 
	 * @param cid column index/ID
	 * @param serialNo row index/ID
	 * @return cell value on availability or else null
	 */
	public abstract String readStringFrom(String sheet, int cid, int serialNo);
	
	
	/**
	 *
	 * This method will read corresponding cell value by supplied cid and rid within the sheet and 
	 * returns the content by splitting data by ',' token as list.
	 * 
	 * @param sheet sheet name to lookup 
	 * @param cid column index/ID
	 * @param rid row index/ID
	 * @return list of comma separated values with in the cell.
	 */
	public abstract List<String> getCSVData(String sheet, int cid, int rid);
	
	/**
	 * 
	 * @param sheet
	 * @param cid
	 * @param rid
	 * @return
	 */
	public abstract List<String> getSplittedDataWithCustomSeparator(String sheet, int cid, int rid, String separator);
	
	/**
	 * 
	 * This method gets the rowID(rowID would be the same rowID of the supplied value from the pkCid lookup column) for the corresponding cell
	 * 
	 * @param sheet sheet name to lookup
	 * @param value cell value
	 * @param pkCid primary key column index
	 * @return row Id of value occurrence
	 */
	public abstract int getRowIDOnUniqueValue(String sheet, String value, int pkCid);
	
	
	
	/**
	 * 
	 * This method gets the value from the corresponding cell from rowID(rowID would be the same rowID of the supplied uid from the uidLookupColumn) and columnID
	 * 
	 * @param uid unique/primary-key value 
	 * @param uidLookupColumn unique-data/primary-key column-id
	 * @param columnIndex column-ID from where it needs to get the value.
	 * @param sheetName where it needs to lookup for value from the available workbook.
	 * @return
	 */
	public abstract String getCellDataOnUID(String uid, int uidLookupColumn, int columnIndex, String sheetName);


	
	/**
	 * 
	 * This method writes the value to the corresponding cell by 
	 * rowID(rowID would be the same rowID of the supplied uid from the uidLookupColumn) and columnID
	 * 
	 * @param uid unique/primary-key value 
	 * @param uidLookupColumn unique-data/primary-key column-id
	 * @param columnIndex column-ID from where it needs to get the value.
	 * @param sheetName where it needs to lookup for value from the available workbook.
	 * @param value which will be written to the corresponding cell.
	 * @return
	 */
	public void writeToCellOnUID(String uid, int uidLookupColumn, int columnIndex, String sheetName, String value);

	/**
	 * 
	 * This method will check whether the supplied value is unique in the entite columnID supliied with in the sheet
	 * 
	 * @param sheet where it needs to lookup for value from the available workbook.
	 * @param cid column index/ID
	 * @param value to be checked for the unique existence in entire column
	 * @return if there is only one supplied value occurrence then returns true or else false
	 */
	public abstract boolean isUniqueValueInColumn(String sheet, int cid, String value);
	
	/**
	 * 
	 * This method will check whether the supplied value is unique in the entite rowID supliied with in the sheet
	 * 
	 * @param sheet where it needs to lookup for value from the available workbook.
	 * @param rid row index/ID
	 * @param value to be checked for the unique existence in entire column
	 * @return if there is only one supplied value occurrence then returns true or else false
	 */
	public abstract boolean isUniqueValueInRow(String sheet, int rid, String value);
}
