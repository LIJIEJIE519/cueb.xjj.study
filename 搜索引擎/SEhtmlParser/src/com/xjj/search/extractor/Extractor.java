package com.xjj.search.extractor;

import org.htmlparser.*;
import org.htmlparser.util.*;
import org.htmlparser.visitors.*;
import org.htmlparser.nodes.*;
import org.htmlparser.tags.*;
import com.xjj.search.page.*;
import com.xjj.search.util.*;

public class Extractor implements Runnable {
	private String filename;
	private Parser parser;
	private Page page;
	private String encode;
	public void setEncode(String encode){
		this.encode=encode;
	}
	private String combineNodeText(Node[]nodes){
		StringBuffer buffer=new StringBuffer();
		for(int i=0;i<nodes.length;i++){
			Node anode=(Node)nodes[i];
			String line=null;
			if(anode instanceof TextNode){
				TextNode textnode=(TextNode)anode;
				line=textnode.getText();
			}
			else if (anode instanceof LinkTag){
				LinkTag linknode=(LinkTag)anode;
				line=linknode.getLinkText();
			}
			else if (anode instanceof Div){
				if(anode.getChildren()!=null){
					line=combineNodeText(anode.getChildren().toNodeArray());
				}
			}
			else if(anode instanceof ParagraphTag){
				if(anode.getChildren()!=null){
					line=combineNodeText(anode.getChildren().toNodeArray());
				}
			}
			else if (anode instanceof Span){
				if(anode.getChildren()!=null){
					line=combineNodeText(anode.getChildren().toNodeArray());
				}
			}
			else if (anode instanceof TableTag){
				if(anode.getChildren()!=null){
					line=combineNodeText(anode.getChildren().toNodeArray());
				}
			}
			else if(anode instanceof TableRow){
				if(anode.getChildren()!=null){
					line=combineNodeText(anode.getChildren().toNodeArray());
				}
			}
			else if(anode instanceof TableColumn){
				if(anode.getChildren()!=null){
					line=combineNodeText(anode.getChildren().toNodeArray());
				}
			}
			if(line!=null){
				buffer.append(line);
			}
		}
		return buffer.toString();
	}
	private String getUrl(String filename){
		String url=filename;
		url=url.replace(ProperConfig.getValue("mirror.path")+"/mirror", "");
		if(url.lastIndexOf("/")==url.length()-1){
			url=url.substring(0,url.length()-1);
		}
		url=url.substring(1);
		return url;
	}
	private int getScore(String url,int score){
		String[] subStr=url.split("/");
		score=score-(subStr.length-1);
		return score;
	}
	private String getSummary(String context){
		if(context==null){
			context="";
		}
		return MD5.MD5Encode(context);
	}
	public void extract(String filename){
		System.out.println("Messsage:Now extracting"+filename);
		this.filename=filename.replace("\\", "/");
		run();
		if(this.page!=null){
			PageLib.store(this.page);
		}
	}
	public void run(){
		try{
			parser=new Parser(this.filename);			
			parser.setEncoding(encode);			
			HtmlPage visitor=new HtmlPage(parser);			
			parser.visitAllNodesWith(visitor);			
			page=new Page();			
			this.page.setUrl(getUrl(this.filename));			
			this.page.setTitle(visitor.getTitle());
			
			if(visitor.getBody()==null){
				this.page.setContext(null);
			}
			else{
				this.page.setContext(combineNodeText(visitor.getBody().toNodeArray()));
			}
			this.page.setScore(getScore(this.page.getUrl(),this.page.getScore()));
			this.page.setSummary(getSummary(this.page.getContext()));
		}
		catch(ParserException pe){
			pe.printStackTrace();
			System.out.println("Continue...");
		}
		
	}

}