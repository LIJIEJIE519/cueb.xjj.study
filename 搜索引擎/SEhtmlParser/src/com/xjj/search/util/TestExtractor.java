package com.xjj.search.util;

import java.io.*;
import com.xjj.search.extractor.*;
import com.xjj.search.util.ProperConfig;
public class TestExtractor {
	public static void main(String[] args) {
		System.out.println("Start extracing pages...");
		processExtract(ProperConfig.getValue("mirror.path"));
		System.out.println("Extraction is end");
		System.out.println("=======================");
	}
	public static void processExtract(String path){
		File[] files=new File(path).listFiles();
		
		for(int i=0;i<files.length;i++){
			if(files[i].isDirectory()==true){
				processExtract(files[i].getAbsolutePath());
			}
			else{
				String encode="UTF-8";
				try{
					BufferedReader reader=new BufferedReader(new FileReader(files[i].getAbsoluteFile()));
					String line=reader.readLine();
					while(line!=null){
						if(line.indexOf("charset=")!=-1){
							int start=line.indexOf("charset=");
							start=start+8;
							String tmp=line.substring(start,start+3);
							if("ISO".equals(tmp)||"iso".equals(tmp)){
								encode="ISO-8859-1";
							}
							else if ("UTF".equals(tmp)||"utf".equals(tmp)){
								encode="UTF-8";
							}
							else if ("GBK".equals(tmp)||"gbk".equals(tmp)){
								encode="GBK";
							}
							else {
								encode="GB2312";
							}
							reader.close();
							break;
						}
						else{
							line=reader.readLine();
						}
					}
				}
				catch(IOException ioe){
					ioe.printStackTrace();
				}
				Extractor extractor=new Extractor();
				extractor.setEncode(encode);
				extractor.extract(files[i].getAbsolutePath());
				
			}
		}
	}

}