package team.ifp.cbirc._enum;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author GuoXinyuan
 * @date 2021/12/9
 */
public enum Predicate {

    TO_ABOLISH("使废止"),
    HIGHER_LEVEL_LAW("上位法"),
    LOWER_LEVEL_LAW("下位法");

    private final String name;

    private Predicate(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @JsonCreator
    public static Predicate getFromCode(String value) {
        for (Predicate e:Predicate.values()) {
            if (e.getName().equals(value)) {
                return e;
            }
        }
        return null;
    }

}
