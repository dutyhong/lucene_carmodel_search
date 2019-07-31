
import java.io.BufferedReader;
import  java.io.File;
import java.io.FileInputStream;
import  java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import  java.util.Date;
import java.util.List;

import  org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public   class TextFileIndexer  {
    public   static   void  testCreateIndex()  throws Exception {
        List<Document> docs = IndexUtils.file2Document();
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(IndexUtils.searchSource));
        Analyzer luceneAnalyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(luceneAnalyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        for(Document document: docs)
        {
            indexWriter.addDocument(document);
        }
        indexWriter.commit();
        indexWriter.close();
        System.out.println("索引创建完成");

    }
    public static void main(String[] args) throws Exception {
        testCreateIndex();
    }

}