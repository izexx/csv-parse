import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class Parser {

    private static final String PORTS_PATTERN = "Goto\\d+";
    private static final String NAME_PATTERN = "UnknownPort-%d";
    private static final String NUM_PATTERN = ",(-?\\d+)(\\.\\d+)?(e-?\\d+)?";
    private static final String TRACE_VALUES = "trace_values";
    private static final Map<String, String> PORT_NAME = new HashMap<>();
    private static String path = "";
    private static int cnt;

    public static String convertCSV2String(String path) throws java.io.IOException {
        Parser.path = path;
        File file = new File(path);
        return FileUtils.readFileToString(file, "UTF-8");
    }

    public static Map<String, List<String>> parseString2Map(String data) {
        LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
        // 默认的时间变量
        map.put("time", new ArrayList<>());
        Matcher nameMatcher = Pattern.compile(PORTS_PATTERN).matcher(data);
        while (nameMatcher.find()) {
            String port = nameMatcher.group();
            String name = parsePort(port);
            if (name == null) {
                name = String.format(NAME_PATTERN, cnt++);
            }
            map.put(name, new ArrayList<>());
        }
        Matcher startPlace = Pattern.compile(TRACE_VALUES).matcher(data);
        startPlace.find();
        int i = startPlace.start();
        // 忽略非数据部分的数字
        Matcher numMatcher = Pattern.compile(NUM_PATTERN).matcher(data.substring(i));
        List[] lists = new List[map.size()];
        int t = 0;
        for (List list : map.values()) {
            lists[t++] = list;
        }
        t = 0;
        while (numMatcher.find()) {
            String s = numMatcher.group();
            lists[t].add(s.substring(1));
            t++;
            if (t == lists.length) {
                t = 0;
            }
        }
        System.out.println(map.size() + " data names in total");
        return map;
    }

    public static void writeMap2File(Map<String, List<String>> map) throws IOException {
        String dirName = ".\\resource\\" + java.time.LocalDateTime.now().toString().replace('.', '-').replace(':', '-');
        FileUtils.forceMkdir(new File(dirName));
        for (Map.Entry<String, List<String>> e : map.entrySet()) {
            StringBuilder sb = new StringBuilder("[");
            String fileName = dirName + "\\" + e.getKey() + ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
            System.out.println(e.getKey() + " has " + e.getValue().size());
            for (String s : e.getValue()) {
                sb.append(s + " ");
            }
            sb.append("]");
            writer.write(sb.toString());
            writer.close();
        }
    }

    public static String parsePort(String port) {
        if (PORT_NAME.isEmpty()) {
            for (Port p : Port.values()) {
                PORT_NAME.put(p.getPort(), p.getName());
            }
        }
        return PORT_NAME.get(port);
    }
}
