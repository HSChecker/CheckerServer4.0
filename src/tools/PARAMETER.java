package tools;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * PARAMETER¿‡
 *
 * @author HanShuo
 * @Date 2020/4/24 19:28
 */
public class PARAMETER {

    private static PARAMETER This;

    private PARAMETER() throws IOException {
        FILE.createFile(PATH+"/setting.properties");
        Properties s = new Properties();
        s.load(new FileReader(PATH+"/setting.properties"));
        if(s.get("threadNum") == null){
            FileWriter fw = new FileWriter(PATH+"/setting.properties",true);
            fw.write("prot = 11313\n");
            fw.write("threadNum = 6\n");
            fw.flush();
            fw.close();
        }
        prot = Integer.parseInt((String)s.get("prot"));
        threadNum = Integer.parseInt((String)s.get("threadNum"));
    }

    public static void run() throws IOException {
        This = new PARAMETER();
    }


    private static String path_te = new NULL().getClass().getProtectionDomain().getCodeSource().getLocation().getFile();

    public static String PATH = path_te.substring(1,path_te.length()-(path_te.substring(path_te.length()-4,path_te.length()).equals(".jar")?25:15));

    public static int prot;

    public static int threadNum;

    public static String version = "BETE4.0.0S";

    static class NULL{}

}