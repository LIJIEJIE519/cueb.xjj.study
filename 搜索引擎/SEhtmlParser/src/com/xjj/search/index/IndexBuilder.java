package com.xjj.search.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

import jeasy.analysis.MMAnalyzer;

public class IndexBuilder {
	IndexWriter writer;
	public IndexBuilder(String path) throws IOException {
		writer = new IndexWriter(path, new MMAnalyzer());
	}
	public void build(String path) throws IOException {
		BufferedReader reader = null;
		File[] files = new File(path).listFiles();
		for(int i = 0; i <files.length; i++) {
			System.out.println(i);
			reader = new BufferedReader(new FileReader(files[i]));
			Document doc = new Document();
			Field[] fields = new Field[5];
			fields[0] = new Field("id", String.valueOf(i), Field.Store.YES,Field.Index.NO);
			fields[1] = new Field("url", reader.readLine(), Field.Store.YES,Field.Index.NO);
			fields[2] = new Field("title", reader.readLine(), Field.Store.YES,Field.Index.TOKENIZED);
			fields[3] = new Field("score", reader.readLine(), Field.Store.YES,Field.Index.NO);
			fields[4] = new Field("context", getBodyFile(files[i].getAbsolutePath(), reader), Field.Store.YES,Field.Index.NO);
			// 创建
			for(int j = 0; j < fields.length; j++) {
				doc.add(fields[j]);
			}
			writer.addDocument(doc);
		}
		writer.optimize();
		writer.close();
		reader.close();
	}
	private String getBodyFile(String absolutePath, BufferedReader reader) throws IOException{
		StringBuffer buffer = new StringBuffer();
		String line = reader.readLine();
		while (line != null) {
			buffer.append(line);
			line = reader.readLine();
		}
		return buffer.toString();
	}
	
}
