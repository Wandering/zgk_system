package cn.thinkjoy.jx.k12system.util;

import java.io.*;
import java.util.Date;
import java.util.Map;

/**
 * Created by yhwang on 15/8/24.
 */
public class HTMLGenerator {
    private static String modelPath = "";
    private static String targetPath = "";
    /**
     * 根据本地模板生成静态页面
     * @param paramsMap html路经
     * @return
     */
    public static boolean JspToHtmlFile(Map<String,Object> paramsMap) {
        String str = "";
        long beginDate = (new Date()).getTime();
        try {
            String tempStr = "";
            FileInputStream is = new FileInputStream(modelPath);//读取模块文件
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((tempStr = br.readLine()) != null)
                str = str + tempStr ;
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {

            str = str.replaceAll("###title###",
                    paramsMap.get("title").toString());
            str = str.replaceAll("###content###",
                    paramsMap.get("content").toString());
            str = str.replaceAll("###author###",
                    paramsMap.get("editor").toString());//替换掉模块中相应的地方

            File f = new File(targetPath);
            BufferedWriter o = new BufferedWriter(new FileWriter(f));
            o.write(str);
            o.close();
            System.out.println("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
