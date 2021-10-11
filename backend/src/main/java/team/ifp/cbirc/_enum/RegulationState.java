package team.ifp.cbirc._enum;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 * 法规发布状态枚举
 */
public enum RegulationState {

    //枚举顺序需与数据库中枚举顺序一致
    UNPUBLISHED("UNPUBLISHED"),
    PUBLISHED("PUBLISHED");


    private final String name;

    private RegulationState(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    /**
     * 从String值映射到枚举值
     * "UNPUBLISHED" "PUBLISHED"
     * @param value
     * @return
     */
    @JsonCreator
    public static RegulationState getStateFromCode(String value) {
        for (RegulationState state:RegulationState.values()) {
            if (state.getName().equals(value)) {
                return state;
            }
        }
        return null;
    }
}
