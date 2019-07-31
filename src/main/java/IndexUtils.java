import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

public class IndexUtils {
    // 索引源，即源数据目录
    public static String searchSource = "/Users/duty/work/lucene/source_file";
    // 索引目标地址
    public static String indexFolder = "/Users/duty/work/lucene/indexdata";

    // 从文件创建Document
    public static List<Document> file2Document() throws IOException {
        List<Document> list = new ArrayList<Document>();
        Document document1 = new Document();
        document1.add(new TextField("name", "宝马320li", Store.YES));
        list.add(document1);
        Document document2 = new Document();
        document2.add(new TextField("name", "奥迪A 4L", Store.YES));
        list.add(document2);
        Document document3 = new Document();
        document3.add(new TextField("name", "奥迪 A3L", Store.YES));
        list.add(document3);
        Document document4 = new Document();
        document4.add(new TextField("name", "奥迪A5L", Store.YES));
        list.add(document4);
        Document document5 = new Document();
        document5.add(new TextField("name", "奥迪Q5", Store.YES));
        list.add(document5);
        Document document6 = new Document();
        document6.add(new TextField("name", "宝沃A3L", Store.YES));
        list.add(document6);

//        document1.add(new TextField("name", "奥迪A4L", Store.YES));
//        document1.add(new TextField("name", "奥迪A3L", Store.YES));
//        document1.add(new TextField("name", "奥迪Q5", Store.YES));
//        document1.add(new TextField("name", "宝沃A3L", Store.YES));
//        document1.add(new TextField("name", "奥拓A3L", Store.YES));
//        document1.add(new TextField("name", "奔驰C200", Store.YES));
//        document1.add(new TextField("name", "奔驰c200", Store.YES));
//        document1.add(new TextField("name", "c200", Store.YES));

        Document document7 = new Document();
        document7.add(new TextField("name", "奔驰", Store.YES));
        list.add(document7);
        return list;

    }

    public static void printDocumentOfFile(Document doc) {
        System.out.println("name =" + doc.get("name"));

    }

}
