package com.edupot.parser;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.edubot.SchoolRow;
import com.karthik.wext.core.LogUtils;

public class ParseSchoolSet {

	public static void execute(Map<String, HashSet<SchoolRow>> downloadedSchoolSet) {
		for (String systemKey : downloadedSchoolSet.keySet()) {
			Set<SchoolRow> systemSchoolSetDownl = downloadedSchoolSet.get(systemKey);
			for (SchoolRow schoolRow : systemSchoolSetDownl) {
				if (schoolRow.getRow() > 0) {
					ParseProfile.parse(schoolRow);
					ParseAchievements.parse(schoolRow);
					ParseValueAdded.parse(schoolRow);
					ParseAttendance.parse(schoolRow);
					ParseDiscipline.parse(schoolRow);
					ParseTeacher.parse(schoolRow);
					LogUtils.logger.info("Parsing system{} school{}", schoolRow.getSystemName(), schoolRow.getSchoolName());

				}
			}

		}
	}

}
