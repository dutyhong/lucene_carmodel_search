import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class SearchUnion {
    public static void main(String[] args) throws IOException
    {
        String queryString = "";
        //拼音索引加载
        Directory directory =  FSDirectory.open(Paths.get("/Users/duty/work/lucene/pinyin"));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        //推荐索引加载
        FSDirectory indexDir = FSDirectory.open(FileSystems.getDefault().getPath("/Users/duty/work/lucene/suggestion"));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        AnalyzingInfixSuggester suggester = new AnalyzingInfixSuggester(indexDir, analyzer);
        HashSet<BytesRef> contexts = new HashSet<>();
        contexts = null;
        String fieldName = "content";

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext())
        {
            queryString = scanner.next();
            boolean isContainChinese = Tools.isContainChinese(queryString);
            if(isContainChinese)
            {
                System.out.println("推荐搜索结果。。。。。。。");

                List<Lookup.LookupResult> results = suggester.lookup(queryString, contexts, 10, true, true);
                for(Lookup.LookupResult result : results)
                {
                    //对检索结果进行筛选
                    if(Tools.strCompare(queryString, (String) result.key))
                        continue;
                    if(!Tools.strContains(queryString, (String) result.key))
                        continue;
                    System.out.println(result.key);
//            System.out.println(result.value);
//            System.out.println(result.contexts);

                }
            }else {
                Query query = new TermQuery(new Term(fieldName, queryString));
                TopDocs topDocs = searcher.search(query, 10);
                ScoreDoc[] docs = topDocs.scoreDocs;
                System.out.println("拼音搜索结果");
                for (ScoreDoc scoreDoc : docs) {
                    int docID = scoreDoc.doc;
                    Document document = searcher.doc(docID);
                    String content = document.get(fieldName);
                    float score = scoreDoc.score;
                    System.out.println(docID + "[" + score + "]\t" + content);
                }
            }
        }

//        contexts.add(new BytesRef(name.getBytes("UTF8")));
    }
}
