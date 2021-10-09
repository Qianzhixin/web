package team.ifp.cbirc._enum;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 */
public enum RegulationState {

    //枚举顺序需与数据库中枚举顺序一致
    UNPUBLISHED("未发布"),
    PUBLISHED("已发布");


    private final String name;

    private RegulationState(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }


}
