package team.ifp.cbirc._enum;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.LinkedList;
import java.util.List;

/**
 * @author GuoXinyuan
 * @date 2021/11/11
 */
public enum Department {

    FINANCE_ACCOUNTING("财会部(偿付能力部)"),
    CONSUMER_PROTECTION("消保局"),
    LAWS_REGULATIONS("法规部"),
    INTERNATIONAL("国际部(港澳台办)"),
    CORPORATE_TRUST("信托部"),
    INTERMEDIARY_AGENT("中介部"),
    RISK_DISPOSITION("风险处置局(安全保卫局)"),
    NO_BANK("非银部"),
    NO_BANK_INSPECTION("非银检查局"),
    BANK_INSPECTION("银行检查局"),
    CAPITAL("资金部"),
    PROPERTY_INSURANCE("财险部(再保部)"),
    STATISTICAL_INFORMATION("统信部"),
    INCLUSIVE_FINANCE("普惠金融部"),
    POLICY_RESEARCH("政研局"),
    LARGE_BANK("大型银行部"),
    GENERAL_OFFICE("办公厅"),
    INNOVATE("创新部"),
    CORPORATE_GOVERNANCE("公司治理部"),
    LIFE_INSURANCE("人身险部"),
    NULL("");

    private final String name;

    private Department(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @JsonCreator
    public static Department getFromCode(String value) {
        for (Department e:Department.values()) {
            if (e.getName().equals(value)) {
                return e;
            }
        }
        return null;
    }

    public static List<String> getNameList() {
        List<String> resultList = new LinkedList<>();
        for (Department e:Department.values()) {
            if (!e.getName().equals("")) {
                resultList.add(e.getName());
            }
        }
        return resultList;
    }

}
