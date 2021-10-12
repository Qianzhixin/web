package team.ifp.cbirc.userdata;

import java.io.File;

/**
 * @author GuoXinyuan
 * @date 2021/10/12
 */

public class userFileManager {

    /**
     * 项目根路径
     */
    private static final String PROJECT_ROOT_PATH =  new File("").getAbsolutePath();

    /**
     * 数据存放根路径
     */
    private static final String DATA_ROOT_PATH = PROJECT_ROOT_PATH + File.separator + "data";

    static {
        
    }

    public static void main(String[] args) {
        System.out.println(DATA_ROOT_PATH);
        File dir = new File(DATA_ROOT_PATH);
        dir.mkdirs();
    }

}
