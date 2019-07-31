

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 一些工具方法
 *
 * @author Lanxiaowei
 *
 */
public class Tools {
    public static int bytes2int(byte[] b) {
        int mask = 0xff;
        int temp = 0;
        int res = 0;
        for (int i = 0; i < 4; i++) {
            res <<= 8;
            temp = b[i] & mask;
            res |= temp;
        }
        return res;
    }

    public static byte[] int2bytes(int num) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (num >>> (24 - i * 8));
        }
        return b;
    }

    public static int nextInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    /**
     * 字节数组转化为输入流
     *
     * @param bytes
     * @return
     */
    public static InputStream bytes2InputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    /**
     * Java反序列化
     *
     * @param is
     * @return
     */
    public static Object deSerialize(InputStream is) {

        try {
            ObjectInputStream oin = new ObjectInputStream(is);
            return oin.readObject();
        } catch (ClassNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static List<String> getPermutationSentence(List<List<String>> termArrays, int start) {
        if (null == termArrays || termArrays.size() <= 0) {
            return Collections.emptyList();
        }

        int size = termArrays.size();
        if (start < 0 || start >= size) {
            return Collections.emptyList();
        }

        if (start == size - 1) {
            return termArrays.get(start);
        }

        List<String> strings = termArrays.get(start);

        List<String> permutationSentences = getPermutationSentence(termArrays,
                start + 1);

        if (null == strings || strings.size() <= 0) {
            return permutationSentences;
        }

        if (null == permutationSentences || permutationSentences.size() <= 0) {
            return strings;
        }

        List<String> result = new ArrayList<String>();
        for (String pre : strings) {
            for (String suffix : permutationSentences) {
                result.add(pre + suffix);
            }
        }
        return result;
    }
    //读取车型车系数据
    public static Set<String> getIndexData(String filePath)
    {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            String line = null;
            Set<String> brandModelName = new HashSet<>();
            while((line=br.readLine())!=null)
            {
                String[] columns = line.split(",");
                if(columns.length!=10)
                    continue;
                String brandName = columns[1];
                String seriesName = columns[3];
                String modelName = columns[5];
                brandModelName.add(brandName);
                brandModelName.add(seriesName);
                brandModelName.add(modelName);
            }
            br.close();
            return brandModelName;
        } catch (IOException e) {
            throw new RuntimeException("没有数据文件。。。。");
        }
    }
    //判断两个字符串除去停用字符之后长短关系, str1是否比str2长
    public static boolean strCompare(String str1, String str2)
    {
        str1 = StringUtils.join(str1.split(" "), "");
        int str1Length = str1.length();
        str2 = StringUtils.join(str2.split(" "), "");
        int str2Length = str2.length();
        if(str1Length>=str2Length)
            return true;
        return false;
    }
    //判断str1中的每个字符是否都在str2中
    public static boolean strContains(String str1, String str2)
    {
        str1 = StringUtils.join(str1.split(" "), "");
        str2 = StringUtils.join(str2.split(" "), "");
        int str1Length = str1.length();
        for(int i=0; i<str1Length; i++)
        {
            String subString = str1.substring(i, i+1);
            if(!str2.contains(subString))
                return false;
        }
        return true;
    }
    //判断一个字符串是否包含中文字符
    // 判断一个字符是否是中文
    public static boolean isChinese(char c) {
        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
    }
    // 判断一个字符串是否含有中文
    public static boolean isContainChinese(String str) {
        if (str == null) return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c)) return true;// 有一个中文字符就返回
        }
        return false;
    }

    public static void main(String[] args)
    {
        String str1 = "奥迪A3L";
        String str2 = "奥迪A3L TDI";
        boolean tag = strCompare(str1, str2);
        System.out.println(tag);
        tag = strContains(str1, str2);
        System.out.println(tag);
    }
}