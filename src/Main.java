import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            String s = Parser.convertCSV2String(".\\resource\\t9.csv");
            System.out.println("found file");
            Map<String, List<String>> map = Parser.parseString2Map(s);
            System.out.println("parse date complete");
            //Parser.writeMap2File(map);
            Parser.writer2SingleFile(map);
        } catch (java.io.IOException e) {
            System.out.println(e);
        }
    }
}
