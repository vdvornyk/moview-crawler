package com.edubot;

import static com.karthik.wext.core.LogUtils.logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.edupot.parser.ParseSchoolSet;
import com.karthik.wext.core.LogUtils;

public class FileParserTest {
	private WritableWorkbook workbookCopy;
	private Workbook workbook;
	private WritableSheet writableSheet;
	private WritableSheet writableSheet2;
	// private Sheet sheet;

	Set<String> originalSystemSet = new LinkedHashSet<String>();
	Map<String, HashSet<SchoolRow>> originalSchoolSet = new HashMap<String, HashSet<SchoolRow>>();

	Set<String> downloadedSystemSet = new HashSet<String>();
	Map<String, HashSet<SchoolRow>> downloadedSchoolSet = new HashMap<String, HashSet<SchoolRow>>();

	@Before
	public void openXls() throws IOException, BiffException {
		workbook = Workbook.getWorkbook(new File("/home/hagen/git/freelance/wext/src/test/java/com/edubot/TN_Data_Template.xls"));
		workbookCopy = Workbook.createWorkbook(new File("/home/hagen/git/freelance/wext/src/test/java/com/edubot/TN_Data_Template_result.xls"), workbook);
		writableSheet = workbookCopy.getSheet("Data_Template");
		writableSheet2 = workbookCopy.createSheet("Data_Template2", 1);
		// sheet = workbook.getSheet("Data_Template");

	}

	@Test
	public void main() throws IOException, BiffException, RowsExceededException, WriteException {
		readXLS();
		readDownloaded();
		setRows();
		parseDownloaded();
		writeXls();
	}

	public void readXLS() throws IOException, BiffException {

		for (int row = 3; row < writableSheet.getRows(); row++) {
			String systemName = writableSheet.getCell(1, row).getContents();
			String schoolName = writableSheet.getCell(2, row).getContents();
			originalSystemSet.add(systemName);
			if (!originalSchoolSet.containsKey(systemName)) {
				originalSchoolSet.put(systemName, new HashSet<SchoolRow>());
			}
			originalSchoolSet.get(systemName).add(new SchoolRow(systemName, schoolName, row));
		}

		logger.info("Oryginal Syste size=" + originalSystemSet.size());

	}

	public void readDownloaded() throws IOException {
		File rootDir = new File(SchoolRow.RESOURCES);
		for (String systemName : rootDir.list(DirectoryFileFilter.INSTANCE)) {

			downloadedSystemSet.add(systemName);
			File systemRoot = new File(SchoolRow.RESOURCES + systemName);
			for (String schoolName : systemRoot.list(DirectoryFileFilter.INSTANCE)) {
				if (!downloadedSchoolSet.containsKey(systemName)) {
					downloadedSchoolSet.put(systemName, new HashSet<SchoolRow>());
				}
				downloadedSchoolSet.get(systemName).add(new SchoolRow(systemName, schoolName));
			}
		}
		logger.info("Downloaded Syste size=" + downloadedSystemSet.size());

	}

	public void contains() {
		logger.info("=====Delete from orig======");
		for (String systemKey : originalSchoolSet.keySet()) {
			Set<SchoolRow> systemSchoolSetOrigBuf = new HashSet<SchoolRow>(originalSchoolSet.get(systemKey));
			Set<SchoolRow> systemSchoolSetOrigBuf1 = new HashSet<SchoolRow>(originalSchoolSet.get(systemKey));
			Set<SchoolRow> systemSchoolSetDownl = new HashSet<SchoolRow>(downloadedSchoolSet.get(systemKey));
			Set<SchoolRow> systemSchoolSetDownl1 = new HashSet<SchoolRow>(downloadedSchoolSet.get(systemKey));

			systemSchoolSetDownl.removeAll(systemSchoolSetOrigBuf);
			systemSchoolSetOrigBuf1.removeAll(systemSchoolSetDownl1);

			if (systemSchoolSetOrigBuf1.size() == 0 || systemSchoolSetDownl.size() == 0) {
				// logger.info("SYSTEM:{} IS OK", systemKey);
			} else {
				logger.info("===========================");
				int findSize = systemSchoolSetOrigBuf.size() - systemSchoolSetOrigBuf1.size();
				int notFindSize = systemSchoolSetOrigBuf1.size();
				logger.info("PROBLEM WITH SYSTEM:" + systemKey + "  Finded = {} ; NotFind = {}", findSize, notFindSize);
				logger.info("ORIGINAL XLS file after delete equals:" + systemSchoolSetOrigBuf1.toString());
				logger.info("Downloaded file after delete equals:" + systemSchoolSetDownl.toString());
				logger.info("===========================");
			}
		}

	}

	public void setRows() {
		for (String systemKey : originalSchoolSet.keySet()) {
			Set<SchoolRow> systemSchoolSetOrigBuf = originalSchoolSet.get(systemKey);
			Set<SchoolRow> systemSchoolSetDownl = downloadedSchoolSet.get(systemKey);
			for (SchoolRow sh : systemSchoolSetOrigBuf) {
				if (systemSchoolSetDownl.contains(sh)) {
					systemSchoolSetDownl.remove(sh);
					SchoolRow newSh = new SchoolRow(sh.getSystemName(), sh.getSchoolName(), sh.getRow());
					systemSchoolSetDownl.add(newSh);
				}
			}
		}
	}

	public void parseDownloaded() {
		ParseSchoolSet.execute(downloadedSchoolSet);
	}

	public void writeXls() throws RowsExceededException, WriteException {
		int system = 0;
		int startColumnToWrite = 4;
		for (String systemKey : downloadedSchoolSet.keySet()) {
			Set<SchoolRow> systemSchoolSetDownl = downloadedSchoolSet.get(systemKey);
			LogUtils.logger.info("writing....{}" + system++);
			for (SchoolRow schoolRow : systemSchoolSetDownl) {
				if (schoolRow.getRow() > 0) {
					Object[] rowToPrint = schoolRow.getFullColumnArray();
					int col2 = 0;
					for (int schCol = 0; schCol < rowToPrint.length; schCol++) {
						int column = startColumnToWrite + schCol;
						int row = schoolRow.getRow();
						if (column < 256) {
							Label label = new Label(column, row, rowToPrint[schCol].toString());
							writableSheet.addCell(label);
						} else {

							Label label = new Label(col2++, row, rowToPrint[schCol].toString());
							writableSheet2.addCell(label);
						}
					}

				}

			}

		}
	}

	@After
	public void closeXls() throws WriteException, IOException {
		workbookCopy.write();
		workbookCopy.close();

	}
}
