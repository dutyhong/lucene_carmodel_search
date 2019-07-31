import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;



/**
 * 拼音分词器测试
 * @author Lanxiaowei
 *
 */
public class PinyinAnalyzerTest {
    public static void main(String[] args) throws IOException {
        String text = "孙艳子";
        Analyzer analyzer = new PinyinAnalyzer(20);
        AnalyzerUtils.displayTokens(analyzer, text);
    }
}