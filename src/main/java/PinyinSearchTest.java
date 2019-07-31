
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;


/**
 * 拼音搜索测试
 * @author Lanxiaowei
 *
 */
public class PinyinSearchTest {
    public static void main(String[] args) throws Exception {
        String fieldName = "content";
        String queryString = "aodi";

        Directory directory =  FSDirectory.open(Paths.get("/Users/duty/work/lucene/pinyin"));
//        Analyzer analyzer = new PinyinAnalyzer();
//        IndexWriterConfig config = new IndexWriterConfig(analyzer);
//        IndexWriter writer = new IndexWriter(directory, config);

        /****************创建测试索引begin********************/
//        Document doc1 = new Document();
//        doc1.add(new TextField(fieldName, "宝马", Store.YES));
//        writer.addDocument(doc1);
//
//        Document doc2 = new Document();
//        doc2.add(new TextField(fieldName, "宝沃", Store.YES));
//        writer.addDocument(doc2);
//
//        Document doc3 = new Document();
//        doc3.add(new TextField(fieldName, "奥迪", Store.YES));
//        writer.addDocument(doc3);
//
//        Document doc4 = new Document();
//        doc4.add(new TextField(fieldName, "奔驰", Store.YES));
//        writer.addDocument(doc4);
//
//        Document doc5 = new Document();
//        doc5.add(new TextField(fieldName, "宝马五系", Store.YES));
//        writer.addDocument(doc5);
//
//        Document doc6 = new Document();
//        doc6.add(new TextField(fieldName, "宝马320", Store.YES));
//        writer.addDocument(doc6);
//        Set<String> brandModelNames = Tools.getIndexData("/Users/duty/work/lucene/dict_brand_series_model_alpnum.txt");
//        for(String brandModelName: brandModelNames)
//        {
//            Document document = new Document();
//            document.add(new TextField(fieldName, brandModelName, Store.YES));
//            writer.addDocument(document);
//        }
//
//        //强制合并为1个段
//        writer.forceMerge(1);
//        writer.close();
        /****************创建测试索引end********************/

        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext())
        {
            queryString = scanner.next();
            Query query = new TermQuery(new Term(fieldName,queryString));
            TopDocs topDocs = searcher.search(query,Integer.MAX_VALUE);
            ScoreDoc[] docs = topDocs.scoreDocs;
            if(null == docs || docs.length <= 0) {
                System.out.println("No results.");
                return;
            }

            //打印查询结果
            System.out.println("ID[Score]\tcontent");
            for (ScoreDoc scoreDoc : docs) {
                int docID = scoreDoc.doc;
                Document document = searcher.doc(docID);
                String content = document.get(fieldName);
                float score = scoreDoc.score;
                System.out.println(docID + "[" + score + "]\t" + content);
            }
        }

    }
}