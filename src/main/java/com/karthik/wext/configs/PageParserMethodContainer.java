package com.karthik.wext.configs;

import jxl.Cell;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PageParserMethodContainer {

	private Cell[] methodsName;
	private Cell[] methodsBody;

	public int size() {
		return methodsBody.length;
	}

	public String getName(int i) {
		return methodsName[i].getContents();
	}

	public String getBody(int i) {
		return methodsBody[i].getContents();
	}

}
