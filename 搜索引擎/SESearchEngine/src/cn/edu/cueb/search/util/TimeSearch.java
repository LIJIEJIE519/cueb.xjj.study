package cn.edu.cueb.search.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TimeSearch {
	
	public static List<Map.Entry<String,Info>> sort(Map<String, Info> map) {
		
		List<Map.Entry<String, Info>> list = new ArrayList<Map.Entry<String, Info>>(map.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Info>>() {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			public int compare(Entry<String, Info> o1, Entry<String, Info> o2) {
				try {
					Date dt1 = format.parse(o1.getValue().getTime());
					Date dt2 = format.parse(o2.getValue().getTime());
					if (dt1.getTime() > dt2.getTime()) {
						return -1;
					} else if (dt1.getTime() < dt2.getTime()) {
						return 1;
					} else {
						return 0;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			};
		});
		return list;
	}
}
