package edu.neu.coe.info6205;

import edu.neu.coe.info6205.SortBenchmark;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SortBenchmarkTest {
    private static String[] result = new String[] {"艾宏", "白维维", "蔡晓微", "曹玉德", "陈安芬", "陈大柱", "陈定宇", "陈旭楠", "褚胜男", "党权", "狄剑", "董华丽", "董俊华", "董慕云",
            "段宏俊", "樊翠霞", "樊辉辉", "高晨", "高民政", "高伟凯", "耿奕", "龚文胜", "顾芳芳", "顾方方", "郭健华", "郭建俊", "郭雪风", "韩太平", "何文元", "洪文胜", "黄斯琪", "黄锡鸿", "贾玉卓",
            "姜贝", "蒋开文", "焦森", "金景花", "康朝锋", "柯茗", "柯铭峰", "雷琼珍", "李开彬", "李来寿", "李天初", "林嘉慧", "林淑平", "刘持平", "刘辉兵", "刘青荣", "刘思言", "吕延杰", "陆子艺", "罗练",
            "罗庆富", "麻寅生", "孟会", "孟祥江", "穆琳琳", "曲东阳", "沙洪涛", "尚学辉", "尚永锋", "史宏达", "舒冬梅", "宋士奎", "宋雪光", "苏会敏", "孙保营", "汤惠休", "唐少林", "田贞见", "王广风", "王琴波",
            "王诗卉", "王树来", "王卫光", "王仙芳", "魏雯霞", "温泉铭", "武彬", "吴春容", "吴达海", "吴艳妮", "巫玉华", "吴子章", "夏尚", "肖桂杰", "许凤山", "徐志森", "雅迅", "鄢福生", "杨春娇", "杨腊香",
            "杨心", "叶敏洁", "叶巧蕊", "余海兵", "俞燕华", "俞长峰", "苑彬", "袁继鹏"};
    @BeforeClass
    public static void runPreprocess() {
        SortBenchmark.preProcess("Trimmed");
    }

    @Test
    public void testMSDSort() {
        String[] pinyinList = SortBenchmark.getShuffledChinesePinyin().clone();
        SortBenchmark.MSDSortBenchmark(pinyinList);
        assertTrue(pinyinList[10].equals(result[10]));
        assertTrue(pinyinList[15].equals(result[15]));
        assertTrue(pinyinList[20].equals(result[20]));
        assertTrue(pinyinList[25].equals(result[25]));
        assertTrue(pinyinList[30].equals(result[30]));
    }
    @Test
    public void testCustomMSDSort() {
        String[] pinyinList1 = SortBenchmark.getShuffledChinesePinyin().clone();
        SortBenchmark.MSDSortCE1Benchmark(pinyinList1);
        assertTrue(pinyinList1[11].equals(result[11]));
        assertTrue(pinyinList1[16].equals(result[16]));
        assertTrue(pinyinList1[21].equals(result[21]));
        assertTrue(pinyinList1[26].equals(result[26]));
        assertTrue(pinyinList1[31].equals(result[31]));

        String[] pinyinList2 = SortBenchmark.getShuffledChinesePinyin().clone();
        SortBenchmark.MSDSortCE2Benchmark(pinyinList2);
        assertTrue(pinyinList2[12].equals(result[12]));
        assertTrue(pinyinList2[17].equals(result[17]));
        assertTrue(pinyinList2[22].equals(result[22]));
        assertTrue(pinyinList2[27].equals(result[27]));
        assertTrue(pinyinList2[32].equals(result[32]));
    }
    @Test
    public void testLSDSort() {
        String[] pinyinList = SortBenchmark.getShuffledChinesePinyin().clone();
        SortBenchmark.LSDSortBenchmark(pinyinList);
        for(int i = 0; i < result.length; i++) {
            assertTrue(pinyinList[i].equals(result[i]));
        }
    }
    @Test
    public void testHuskySort() {
        String[] pinyinList = SortBenchmark.getShuffledChinesePinyin().clone();
        SortBenchmark.HuskySortBenchmark(pinyinList);
        for(int i = 0; i < result.length; i++) {
            assertTrue(pinyinList[i].equals(result[i]));
        }
    }
    @Test
    public void testTimsortSort() {
        String[] pinyinList = SortBenchmark.getShuffledChinesePinyin().clone();
        SortBenchmark.TimsortBenchmark(pinyinList);
        for(int i = 0; i < result.length; i++) {
            assertTrue(pinyinList[i].equals(result[i]));
        }
    }
    @Test
    public void testDPQuickSort() {
        String[] pinyinList = SortBenchmark.getShuffledChinesePinyin().clone();
        SortBenchmark.DualPivotQuickSortBenchmark(pinyinList);
        for(int i = 0; i < result.length; i++) {
            assertTrue(pinyinList[i].equals(result[i]));
        }
    }
}
