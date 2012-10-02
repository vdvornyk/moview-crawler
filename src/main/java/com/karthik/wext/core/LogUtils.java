package com.karthik.wext.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
	public static final Logger logger = LoggerFactory.getLogger("MTF");

	public void info(String msg) {
		logger.info(msg);
	}

	public void info(String format, Object arg) {
		logger.info(format, arg);
	}

	public void info(String format, Object arg1, Object arg2) {
		logger.info(format, arg1, arg2);
	}

	public void info(String format, Object[] argArray) {
		logger.info(format, argArray);
	}

	public void error(String msg) {
		logger.error(msg);
	}

	public void error(String format, Object arg) {
		logger.error(format, arg);
	}

	public void error(String format, Object arg1, Object arg2) {
		logger.error(format, arg1, arg2);
	}

	public void error(String format, Object[] argArray) {
		logger.error(format, argArray);
	}

}
