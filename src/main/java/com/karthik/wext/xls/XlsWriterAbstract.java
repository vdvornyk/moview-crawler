package com.karthik.wext.xls;

import static com.karthik.wext.util.WextUtils.JAR_PATH;
import static com.karthik.wext.util.WextUtils.XLS;

import java.io.File;
import java.io.IOException;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.karthik.wext.configs.SiteName;
import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.pojo.MovieInfo.COLUMN;

public abstract class XlsWriterAbstract {
	protected WritableFont ARIAL;
	protected WritableFont ARIAL_BOLD;
	protected WritableCellFormat ARIAL_FORMAT;
	protected WritableCellFormat ARIAL_BOLD_FORMAT;

	protected String outputXlSPath;
	protected WritableSheet sheet;
	protected WritableWorkbook workbook;

	public XlsWriterAbstract() {
		ARIAL = new WritableFont(WritableFont.ARIAL, 10);
		ARIAL_BOLD = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
		ARIAL_FORMAT = new WritableCellFormat(ARIAL);
		ARIAL_BOLD_FORMAT = new WritableCellFormat(ARIAL_BOLD);

	}

	protected void initWorkbook(SiteName siteName) {
		this.outputXlSPath = JAR_PATH + siteName.toString() + XLS;
		try {
			workbook = Workbook.createWorkbook(new File(outputXlSPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sheet = workbook.createSheet("MovieInfo", 0);
	}

	protected void addInfoToCell(int j, int i, int colN, MovieInfo movieInfo, COLUMN... columnNames) {
		Label labels[] = new Label[colN];
		for (int k = 0; k < colN; k++) {
			if (movieInfo == null) {
				labels[k] = new Label(j + k, i, columnNames[k].toString(), ARIAL_BOLD_FORMAT);
			} else {
				labels[k] = new Label(j + k, i, movieInfo.getDataByColumn(columnNames[k]));
			}
		}
		addCells(labels);

	}

	protected void setAutosizeColumn(int colSize, int colStep) {
		for (int i = 0; i <= colSize; i += colStep) {
			for (int j = 0; j < colSize; j++) {
				CellView cellView = sheet.getColumnView(j);
				cellView.setAutosize(true);
				sheet.setColumnView(j, cellView);
			}
		}
	}

	protected void addCells(Label[] labels) {
		for (int i = 0; i < labels.length; i++) {
			try {
				sheet.addCell(labels[i]);
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
