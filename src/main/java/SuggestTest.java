import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;


/**
 * Lucene关键字提示测试 
 *
 * @author Lanxiaowei
 *
 */
public class SuggestTest {
    private static void lookup(AnalyzingInfixSuggester suggester, String name,
                               String region) throws IOException {
        HashSet<BytesRef> contexts = new HashSet<BytesRef>();
        contexts.add(new BytesRef(region.getBytes("UTF8")));
        //先以contexts为过滤条件进行过滤，再以name为关键字进行筛选，根据weight值排序返回前2条  
        //第3个布尔值即是否每个Term都要匹配，第4个参数表示是否需要关键字高亮  
        List<LookupResult> results = suggester.lookup(name, contexts, 5, true, false);
        System.out.println("-- \"" + name + "\" (" + region + "):");
        for (LookupResult result : results) {
            System.out.println(result.key);
            System.out.println(result.value);
            //从payload中反序列化出Product对象  
            BytesRef bytesRef = result.payload;
            InputStream is = Tools.bytes2InputStream(bytesRef.bytes);
            Product product = (Product)Tools.deSerialize(is);
            System.out.println("product-Name:" + product.getName());
//            System.out.println("product-regions:" + product.getRegions());
//            System.out.println("product-image:" + product.getImage());
//            System.out.println("product-numberSold:" + product.getNumberSold());
        }
        System.out.println();
    }
    public static void lookup1(AnalyzingInfixSuggester suggester, String name) throws IOException
    {
        HashSet<BytesRef> contexts = new HashSet<>();
        contexts = null;
//        contexts.add(new BytesRef(name.getBytes("UTF8")));
        List<LookupResult> results = suggester.lookup(name, contexts, 10, true, true);
        for(LookupResult result : results)
        {
            //对检索结果进行筛选
            if(Tools.strCompare(name, (String) result.key))
                continue;
            if(!Tools.strContains(name, (String) result.key))
                continue;
            System.out.println(result.key);
//            System.out.println(result.value);
//            System.out.println(result.contexts);

        }
    }
    public static void main(String[] args) {
        try {
            FSDirectory indexDir = FSDirectory.open(FileSystems.getDefault().getPath("/Users/duty/work/lucene/suggestion"));
            StandardAnalyzer analyzer = new StandardAnalyzer();
            AnalyzingInfixSuggester suggester = new AnalyzingInfixSuggester(indexDir, analyzer);

            //创建CarProduct测试数据
            List<CarProduct> carProducts = new ArrayList<>();
            Set<String> brandModelNames = Tools.getIndexData("/Users/duty/work/lucene/dict_brand_series_model_alpnum.txt");
            for(String brandModelName : brandModelNames)
            {
                carProducts.add(new CarProduct(brandModelName));
            }
//            carProducts.add(new CarProduct("宝马"));
//            carProducts.add(new CarProduct("宝马320"));
//            carProducts.add(new CarProduct("宝马x5"));
//            carProducts.add(new CarProduct("宝沃"));
//            carProducts.add(new CarProduct("奥迪"));
//            carProducts.add(new CarProduct("宝宝"));
//            carProducts.add(new CarProduct("宝马五系"));
//            carProducts.add(new CarProduct("奥迪A4L"));
//            carProducts.add(new CarProduct("奥迪Q5"));
//            carProducts.add(new CarProduct("奥迪4l"));

            // 创建测试索引  
            suggester.build(new CarProductIterator(carProducts.iterator()));
            suggester.commit();
            suggester.close();

            // 开始搜索
//            Scanner scanner = new Scanner(System.in);
//            while (scanner.hasNext())
//            {
//                String input = scanner.next();
//                lookup1(suggester, input);
//            }
//            lookup1(suggester, "宝马 5");
//            lookup(suggester, "Gu", "ZA");
//            lookup(suggester, "Gui", "CA");
//            lookup(suggester, "Electric guit", "US");
        } catch (IOException e) {
            System.err.println("Error!");
        }
    }
}  