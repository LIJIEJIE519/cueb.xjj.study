package cn.edu.cueb.search.util;

import java.util.HashMap;

public class CharacterHandle {
	private static HashMap<String, String> map=new HashMap<>();
	public static String trans(String line){
		map.put(" ", "");
		map.put(", ", ",");
		map.put("¡£ ", ".");
		map.put(" ¡¶ ", "<");
		map.put("¡· ", ">");
		map.put(" ¡¶", "<");
		map.put("¡· ", ">");
		map.put(" ¡¾", "[");
		map.put("¡¿ ", "]");
		map.put("£¿ ", "?");
		map.put("¡° ", "\"");
		map.put("¡± ", "\"");
		map.put("£º ", ":");
		map.put("£» ", ";");
		map.put("¡¢ ", ",");
		map.put("{", "{");
		map.put("} ", "}");
		map.put(" £¨", "(");
		map.put("£© ", ")");
		map.put("+", "+");
		map.put("-", "-");
		map.put("£¡", "!");
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		map.put("4", "4");
		map.put("5", "5");
		map.put("6", "6");
		map.put("7", "7");
		map.put("8", "8");
		map.put("9", "9");
		map.put("0", "0");
		for(int i = 0; i < line.length(); i++) {
			String charat = line.substring(i, i+1);
			if (map.get(charat) != null) {
				line = line.replace(charat, (String)map.get(charat));
			}
		}
		line = line.toLowerCase();
		return line;
	}

}
