package cn.edu.cueb.search.util;

import java.io.File;
import java.io.IOException;
/*
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;*/

import org.apache.lucene.document.Document;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class QueryIndex {
	private String index_path = "D:/Eclipse/SEhtmlParser/indexes/";
	//private IndexSearcher searcher;
	//private BooleanQuery query;
	
	public String[] getQueryResult(String keys) throws IOException {
		/*searcher = new IndexSearcher(INDEX_STORE_PATH);
		query = new BooleanQuery();
		if (keys == null) {
			return null;
		}
		int length = keys.length;
		TermQuery[] term = new TermQuery[length];
		for(int i = 0; i < length; i++) {
			term[i] = new TermQuery(new Term("context", keys[i]));
			query.add(term[i], BooleanClause.Occur.MUST);
		}
		Hits hits = searcher.search(query);
		//length = hits.length();
		
		String[] result = new String[length];
		for(int i = 0; i <length; i++) {
			Document doc = hits.doc(i);
			String tmp = doc.getField("title").stringValue();
			tmp = tmp + "|" + doc.getField("url").stringValue();
			tmp = tmp + "|" + doc.getField("context").stringValue();
			result[i] = tmp;
		}
		*/
		
		
        //创建一个Directory对象，指定索引库存放的路径
        Directory directory = FSDirectory.open(new File(index_path));
        //创建IndexReader对象，需要指定Directory对象
        //IndexReader indexReader = DirectoryReader.open(directory);
        //创建Indexsearcher对象，需要指定IndexReader对象
        IndexSearcher searcher = new IndexSearcher(index_path);
        //int length = keys.length;
        //创建一个TermQuery（精准查询）对象，指定查询的域与查询的关键词
        //创建查询
        Query query = new TermQuery(new Term("context", keys));
        //执行查询
        //第一个参数是查询对象，第二个参数是查询结果返回的最大值
        TopDocs topDocs = searcher.search(query, 10);
        
        //System.out.println("查询结果的总条数："+ topDocs.totalHits);
        
	        
        Hits hits = searcher.search(query);
		int length = hits.length();
		
		String[] result = new String[length];
		for(int i = 0; i <length; i++) {
			Document doc = hits.doc(i);
			String tmp = doc.getField("title").stringValue();
			tmp = tmp + "|" + doc.getField("url").stringValue();
			tmp = tmp + "|" + doc.getField("context").stringValue();
			result[i] = tmp;
		}
	      
		//indexReader.close();
		//System.out.println(result.length);
		return result;
	}
}
