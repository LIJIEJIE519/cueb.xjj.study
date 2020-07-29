package cn.edu.cueb.search.util;

import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similar.MoreLikeThis;
import org.apache.lucene.search.similar.MoreLikeThisQuery;



public class MoreLike {
	private static String index_path = "D:/Eclipse/SEhtmlParser/indexes/";
	private static IndexSearcher searcher;
	
	public static void main(String[] args) throws Throwable {
		
		
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_29) ;   
		IndexSearcher search = new IndexSearcher(new NIOFSDirectory(new File(index_path)),true);     
        String kw="Java" ; //
        
        String[] moreLikeFields = {"context"} ; //{ "Name" ,"Info"};  
        MoreLikeThisQuery query = new MoreLikeThisQuery(kw, moreLikeFields, analyzer);  
        query.setMinTermFrequency(1);  
        query.setMaxQueryTerms(5);  
        query.setMinDocFreq(1);  
        TopDocs tDocs = search.search(query,10);     
        ScoreDoc sDocs[] = tDocs.scoreDocs;     
        int len = sDocs.length;     
        System.out.println(len);
        
        for(int i=0;i<len;i++){     
            ScoreDoc tScore = sDocs[i];     
            //从Lucene3.0开始已经不能通过 tScore.score 这样来得到些文档的得分了     
            int docId = tScore.doc;     
            Explanation exp = search.explain(query, docId);     
                 
            Document tDoc = search.doc(docId);     
            String Name = tDoc.get("title");     
          
            float score = exp.getValue();     
            //System.out.println(exp.toString()); //如果需要打印文档得分的详细信息则可以通过此方法   
                 
            System.out.println("DocId:"+docId+"\tScore:" + score + "\tName:" + Name);     
        }     
        search.close();     
        
        
        /*FSDirectory directory = FSDirectory.open(new File(index_path));  
        IndexReader reader = IndexReader.open(directory);  
        IndexSearcher searcher = new IndexSearcher(reader); // 为相似搜索准备的searcher  
        int numDocs = reader.maxDoc(); // 所有图书  
        MoreLikeThis mlt = new MoreLikeThis(reader); // 相似搜索组件登场  
        mlt.setFieldNames(new String[] { "title", "北京" }); // 找“标题”和“作者”相似的  
        mlt.setMinTermFreq(1); // 默认值是2，建议自己做限制，否则可能查不出结果  
        mlt.setMinDocFreq(1); // 默认值是5，建议自己做限制，否则可能查不出结果  
        for (int docID = 0; docID < numDocs; docID++) {  
            System.out.println();  
            Document doc = reader.document(docID); // 逐一过所有图书  
            System.out.println("title"+doc.get("title"));  
            Query query = mlt.like(docID); // 准备相似搜索了  
            
            System.out.println(" query=" + query);  
            TopDocs similarDocs = searcher.search(query, 10); // 开搜，做多10个结果  
            if (similarDocs.totalHits == 0)  
                System.out.println(" None like this"); // 只要结果不为空，就按这个打印出来  
            for (int i = 0; i < similarDocs.scoreDocs.length; i++) {  
                if (similarDocs.scoreDocs[i].doc != docID) { // 记着把自己排除掉哦  
                    doc = reader.document(similarDocs.scoreDocs[i].doc);  
                    System.out.println("title -> " + doc.getField("title").stringValue());  
                }  
            }  
        }  
        
        reader.close();  
        directory.close();  */
    }  
}
