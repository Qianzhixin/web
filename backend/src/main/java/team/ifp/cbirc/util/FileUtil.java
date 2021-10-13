package team.ifp.cbirc.util;

import java.io.*;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 */

public class FileUtil {

    /**
     * 将content根据append模式写入path所指文件
     * @param content
     * @param path
     * @param append
     * @return
     */
    public static boolean writeFile(String content,String path,boolean append) {
        File file = checkAndMakeFile(path);
        if (file == null) return false;

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,append))) {

            bufferedWriter.write(content);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 读取文件内容
     * @param path
     * @return
     */
    public static String readFile(String path) {
        File file = new File(path);

        if(!file.exists() || file.isDirectory()) return null;

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }

            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检查文件是否存在
     * 如果存在并且是File,返回File
     * 如果存在并且是Dir,返回Null
     * 如果不存在,递归创建路径上的Dir并最终创建File并返回
     * @param path
     * @return
     */
    private static File checkAndMakeFile(String path) {
        File file = new File(path);

        //检查是否存在且是File
        if(file.exists()) {
            if(file.isFile()) return file;
            return null;
        }

        //分离File名与其前面的Dir名
        int i;
        for (i = path.length()-1; i >= 0 ; i--) {
            if(path.charAt(i) == File.separatorChar) break;
        }

        //创建Dir
        File dir = new File(path.substring(0,i));
        if(dir.exists()) {
            if(dir.isFile()) return null;
        }
        else {
            if(!dir.mkdirs()) return null;
        }

        //创建File
        try {
            if(!file.createNewFile()) return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return file;
    }

    /**
     * 获取文件前缀名
     * @param name
     * @return
     */
    public static String getFileNamePrefix(String name) {
        String[] ss = name.split("\\.");
        StringBuilder stringBuilder = new StringBuilder(ss[0]);
        for (int i = 1; i < ss.length - 1; i++) {
            stringBuilder.append(".");
            stringBuilder.append(ss[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取文件后缀名
     * @param name
     * @return
     */
    public static String getFileNameSuffix(String name) {
        String[] ss = name.split("\\.");
        if(ss.length == 1) return "";
        return ss[ss.length-1];
    }

}
