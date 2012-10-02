package com.edupot.parser;

import static com.karthik.wext.core.LogUtils.logger;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.edubot.SchoolRow;

public class ParseValueAdded {
	public final static String GradeTable1 = "Grades K-8 Value Added - Growth Standard";
	public final static String[] GradesTables2 = new String[] { "Math", "Reading/Language Arts", "Social Studies", "Science" };
	public final static String GradeTable3 = "Grades 9-12 Value Added";

	public final static String[] grades1 = new String[] { "Math", "Reading/Language", "Social Studies", "Science" };
	public final static String[] grades2 = new String[] { "4th", "5th", "6th", "7th", "8th" };
	public final static String[] subject = new String[] { "Algebra I", "Algebra II", "Biology I", "English I", "English II", "English III", "US History" };

	public static void parse(SchoolRow school) {
		File profileFile = new File(school.getShFolder() + "Value-Added.html");
		try {
			Document profileDoc = Jsoup.parse(profileFile, "UTF-8");
			int arrIndex = 0;

			// ===========================
			// ===========================
			// ===========================
			Element tbl1 = profileDoc.select("tbody:contains(" + GradeTable1 + ")").last();

			for (String cat : grades1) {
				Elements elems = tbl1.select("tr:contains(" + cat + ")").last().children();
				for (int elemI = 1; elemI < elems.size(); elemI++) {
					school.valueAdded[arrIndex++] = elems.get(elemI).text().trim();
				}
			}
			// ===========================
			// ===========================
			// ===========================
			for (String table : GradesTables2) {
				Element tbl = profileDoc.select("tbody:contains(" + table + ")").last();
				logger.info("tbl data={}" + table);
				for (String cat : grades2) {

					Elements elems = tbl.select("tr:contains(" + cat + ")").last().children();
					for (int elemI = 1; elemI < elems.size() - 1; elemI++) {
						if (elemI != 4) {
							school.valueAdded[arrIndex++] = elems.get(elemI).text().trim();
						}
					}
				}
			}

			// ===========================
			// ===========================
			// ===========================

			Element tbl2 = profileDoc.select("tbody:contains(" + GradeTable3 + ")").last();

			for (String cat : subject) {
				Elements elems = tbl2.select("tr:contains(" + cat + ")").last().children();
				for (int elemI = 1; elemI < elems.size(); elemI++) {
					school.valueAdded[arrIndex++] = elems.get(elemI).text().trim();
				}
			}
			logger.info("index" + arrIndex);
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error("PROBLEM WITH {} school {} in ValueAdded.html", school.getSystemName(), school.getSchoolName());
		}
	}

	public static String getProfileGeneralInfo(Document profileDoc, String search) {
		return profileDoc.select("td:contains(" + search + ")").last().nextElementSibling().text().trim();
	}

}
