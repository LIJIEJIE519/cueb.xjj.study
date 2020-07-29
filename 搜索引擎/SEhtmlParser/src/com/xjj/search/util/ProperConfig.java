package com.xjj.search.util;

import java.util.ResourceBundle;

public class ProperConfig {
	private static String CONFIG_FILE = "config";
	private static ResourceBundle bundle;
	static {
		try {
			bundle = ResourceBundle.getBundle(CONFIG_FILE);
		} catch (Exception e) {
			System.out.println("can not find"+CONFIG_FILE+".properties");
		}
	}
	public static String getValue(String key) {
		return bundle.getString(key);
	}
}
