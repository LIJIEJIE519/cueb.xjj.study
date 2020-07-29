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
            //��Lucene3.0��ʼ�Ѿ�����ͨ�� tScore.score �������õ�Щ�ĵ��ĵ÷���     
            int docId = tScore.doc;     
            Explanation exp = search.explain(query, docId);     
                 
            Document tDoc = search.doc(docId);     
            String Name = tDoc.get("title");     
          
            float score = exp.getValue();     
            //System.out.println(exp.toString()); //�����Ҫ��ӡ�ĵ��÷ֵ���ϸ��Ϣ�����ͨ���˷���   
                 
            System.out.println("DocId:"+docId+"\tScore:" + score + "\tName:" + Name);     
        }     
        search.close();     
        
        
        /*FSDirectory directory = FSDirectory.open(new File(index_path));  
        IndexReader reader = IndexReader.open(directory);  
        IndexSearcher searcher = new IndexSearcher(reader); // Ϊ��������׼����searcher  
        int numDocs = reader.maxDoc(); // ����ͼ��  
        MoreLikeThis mlt = new MoreLikeThis(reader); // ������������ǳ�  
        mlt.setFieldNames(new String[] { "title", "����" }); // �ҡ����⡱�͡����ߡ����Ƶ�  
        mlt.setMinTermFreq(1); // Ĭ��ֵ��2�������Լ������ƣ�������ܲ鲻�����  
        mlt.setMinDocFreq(1); // Ĭ��ֵ��5�������Լ������ƣ�������ܲ鲻�����  
        for (int docID = 0; docID < numDocs; docID++) {  
            System.out.println();  
            Document doc = reader.document(docID); // ��һ������ͼ��  
            System.out.println("title"+doc.get("title"));  
            Query query = mlt.like(docID); // ׼������������  
            
            System.out.println(" query=" + query);  
            TopDocs similarDocs = searcher.search(query, 10); // ���ѣ�����10�����  
            if (similarDocs.totalHits == 0)  
                System.out.println(" None like this"); // ֻҪ�����Ϊ�գ��Ͱ������ӡ����  
            for (int i = 0; i < similarDocs.scoreDocs.length; i++) {  
                if (similarDocs.scoreDocs[i].doc != docID) { // ���Ű��Լ��ų���Ŷ  
                    doc = reader.document(similarDocs.scoreDocs[i].doc);  
                    System.out.println("title -> " + doc.getField("title").stringValue());  
                }  
            }  
        }  
        
        reader.close();  
        directory.close();  */
    }  
}
