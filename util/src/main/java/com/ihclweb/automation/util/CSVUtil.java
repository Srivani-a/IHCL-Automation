package com.ihclweb.automation.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * 
 * @author sairam.p
 *
 */
public class CSVUtil {

	private static final Logger LOG=Logger.getLogger(CSVUtil.class.getName());
	
	private CSVUtil() {}
	
	public static boolean writeToCSV(List<String[]> data, Path path){
		boolean isWritten=false;
		if(CollectionUtils.isNotEmpty(data)) {
			try (CSVWriter writer = new CSVWriter(new FileWriter(path.toFile()));){
				writer.writeAll(data);
				writer.flush();
				isWritten=true;
			}catch(IOException e) {
				LOG.error(e.getMessage(),e);
			}
		}
		return isWritten;
	}
	
	public static boolean writeToCSV(ResultSet rs, Path path, boolean includeHeaders){
		boolean isWritten=false;
		if(rs!=null) {
			try (CSVWriter writer = new CSVWriter(new FileWriter(path.toFile()));){
				writer.writeAll(rs, includeHeaders);
				writer.flush();
				isWritten=true;
			}catch(IOException | SQLException e) {
				LOG.error(e.getMessage(),e);
			}
		}
		return isWritten;
	}
	
	public static List<String[]> loadCSV(Path path){
		List<String[]> list=null;
		try (CSVReader reader = new CSVReader(new FileReader(path.toFile()));){
				list=reader.readAll();
			}catch(IOException e) {
				LOG.error(e.getMessage(),e);
			}
		return list;
	}
}