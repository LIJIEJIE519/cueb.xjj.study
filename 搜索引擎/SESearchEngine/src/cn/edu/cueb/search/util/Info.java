package cn.edu.cueb.search.util;

public class Info {
	private String title;
	private String url;
	private String summary;
	private String time;
	
	public Info(String url, String summary, String timeT) {
		this.url = url;
		this.summary = summary;
		if (timeT.contains("Äê")) {
			timeT = timeT.replace("Äê","-");
			timeT = timeT.replace("ÔÂ","-");
		}
		if (timeT.length() < 10) {
			timeT = timeT+01;
		}
		
		this.time = timeT;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summery) {
		this.summary = summery;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	

}
