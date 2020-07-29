package com.xjj.search.page;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.Buffer;

import com.xjj.search.util.ProperConfig;

public class PageLib {
	public static void store(Page page) {
		String storepath = ProperConfig.getValue("files.path")+"/"+page.getSummary();
		if (new File(storepath).exists() == true) {
			System.out.println("Message:" + storepath + "is exists!");
			return;
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(storepath));
			writer.append(page.getUrl());
			writer.newLine();
			writer.append(page.getTitle());
			writer.newLine();
			writer.append(String.valueOf(page.getScore()));
			writer.newLine();
			writer.append(page.getContext());
			writer.close();
		} catch (Exception e) {
			System.out.println("Error:Processing"+page.getUrl()+" accurs error");
			e.printStackTrace();
		}
	}
}
