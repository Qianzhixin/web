package team.ifp.cbirc._enum;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.LinkedList;
import java.util.List;

/**
 * @author GuoXinyuan
 * @date 2021/11/11
 */
public enum EffectivenessLevel {

    ADMINISTRATIVE_LAWS_REGULATIONS("行政法规"),
    LAWS("法律"),
    NULL("");

    private final String name;

    EffectivenessLevel(String name) { this.name = name; };

    public String getName(){
        return this.name;
    }

    @JsonCreator
    public static EffectivenessLevel getFromCode(String value) {
        for (EffectivenessLevel e:EffectivenessLevel.values()) {
            if (e.getName().equals(value)) {
                return e;
            }
        }
        return null;
    }

    public static List<String> getNameList() {
        List<String> resultList = new LinkedList<>();
        for (EffectivenessLevel e:EffectivenessLevel.values()) {
            if (!e.getName().equals("")) {
                resultList.add(e.getName());
            }
        }
        return resultList;
    }

}
