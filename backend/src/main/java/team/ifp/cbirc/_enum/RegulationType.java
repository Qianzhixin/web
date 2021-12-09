package team.ifp.cbirc._enum;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.LinkedList;
import java.util.List;

/**
 * @author GuoXinyuan
 * @date 2021/11/11
 */
public enum RegulationType {

    POLICIES_REGULATIONS("政策法规"),
    FUNCTIONAL_REGULATION("功能监管"),
    LAWS("法律"),
    NULL("");

    private final String name;

    RegulationType(String name) { this.name = name; };

    public String getName(){
        return this.name;
    }

    @JsonCreator
    public static RegulationType getFromCode(String value) {
        for (RegulationType e:RegulationType.values()) {
            if (e.getName().equals(value)) {
                return e;
            }
        }
        return null;
    }

    public static List<String> getNameList() {
        List<String> resultList = new LinkedList<>();
        for (RegulationType e:RegulationType.values()) {
            if (!e.getName().equals("")) {
                resultList.add(e.getName());
            }
        }
        return resultList;
    }

}
