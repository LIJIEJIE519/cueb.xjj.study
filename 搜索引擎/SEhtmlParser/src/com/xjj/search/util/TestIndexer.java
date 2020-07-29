package com.xjj.search.util;

import java.io.IOException;

import com.xjj.search.index.IndexBuilder;

public class TestIndexer {
	public static void main(String[] args) {
		try{
			IndexBuilder index = new IndexBuilder(ProperConfig.getValue("index.path"));
			index.build(ProperConfig.getValue("files.path"));
		}catch (IOException e) {
			System.out.println("Index create failed !");
			e.printStackTrace();
		}
		System.out.println("Index create finished!");
	}
}
