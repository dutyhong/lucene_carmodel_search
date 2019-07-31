import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SearchIndex {
    // 索引目录地址
    // 查询方法
    public static void testTermQuery() throws IOException, ParseException {
        // 创建查询对象，根据文件名称域搜索匹配文件名称的文档
        //Query query = new TermQuery(new Term("fileName", "aa.txt"));
//            Query query = new TermQuery(new Term("name", "宝马"));
        Query query = new FuzzyQuery(new Term("name", "宝"));
        QueryParser parser = new QueryParser("name", new StandardAnalyzer());
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
        TokenStream result = standardAnalyzer.tokenStream("name", "宝马320li");
        Query query1 = parser.parse("宝马");
        // 指定索引目录
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(IndexUtils.searchSource));
        // 定义IndexReader
        IndexReader reader = DirectoryReader.open(directory);
        // 创建indexSearcher
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        // 执行搜索
        TopDocs topDocs = indexSearcher.search(query, 10);
        // 提取搜索结果
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        System.out.println("共搜索到总记录数：" + topDocs.totalHits);
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 文档id
            int docID = scoreDoc.doc;
            double score = scoreDoc.score;
            // 得到文档
            Document doc = indexSearcher.doc(docID);
            // 输出 文件内容
            IndexUtils.printDocumentOfFile(doc);
            System.out.println(score);
        }
        reader.close();

    }

    public static void main(String[] args) throws IOException, ParseException {
        testTermQuery();
    }
}