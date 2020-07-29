package cn.edu.cueb.search.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.lang.Math;

/*
 * 同意词林检测程序
 * */

public class SimilarityWord {
	
	//读取该类下的.txt文档，为jar打包做准备
	InputStream is=this.getClass().getResourceAsStream("WordTree.txt");
	ArrayList TYCCL = new ArrayList();
	InputStreamReader read1 = new InputStreamReader(is, "utf-8");
	BufferedReader TYCCL_br = new BufferedReader((read1));
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		
		String ConceptA = "命中";
		SimilarityWord tyccl = new SimilarityWord();
		tyccl.findSimilarity(ConceptA);

		long during = System.currentTimeMillis() - start;
		System.out.println("During: " + during + "ms");
		//139ms
	}
	
	public SimilarityWord() throws IOException {		
		String TYCCL_Line = null;
		for (; (TYCCL_Line = TYCCL_br.readLine()) != null; ) {	
			String currentLineWords[] = TYCCL_Line.split(" ");
			TYCCL.add(currentLineWords);		
		}
		TYCCL_br.close();
	}

	// 找同义词
	public ArrayList<String> findSimilarity(String ConceptA) throws IOException {
		ArrayList<String> result  = new ArrayList<>();

		String CurrentTongyicicilinLine = null;
		Map map_ConceptA = new HashMap();

		Iterator itA = TYCCL.iterator();
		for(int j=0;j<17817;j++){		
			
			String currentLineWords[] = (String []) itA.next();
			for (int i = 1; i < currentLineWords.length; i++) {
				if (currentLineWords[i].equals(ConceptA)) {
					if (currentLineWords.length > 6) {
						for (i = 1; i < 6; i++) {
							result.add(currentLineWords[i]);
						}
					}else {
						for(i = 1; i < currentLineWords.length; i++){
							result.add(currentLineWords[i]);
						}
					}
					if (result.contains(ConceptA)) {
						result.remove(ConceptA);
					}
					break;
				}
			}
		}
		/*for (String string : result) {
			System.out.println(string);
		}*/
		return result;
	}		
}

