package com.karthik.wext.gui;

import lombok.Getter;

import com.karthik.wext.configs.Vendor;

public class CheckableItem {
	private String str;
	@Getter
	private Vendor vendor;

	private boolean isSelected;

	public CheckableItem(Vendor vendor) {
		this.vendor = vendor;
		this.str = vendor.toString();
		isSelected = false;
	}

	public void setSelected(boolean b) {
		isSelected = b;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public String toString() {
		return str;
	}
}