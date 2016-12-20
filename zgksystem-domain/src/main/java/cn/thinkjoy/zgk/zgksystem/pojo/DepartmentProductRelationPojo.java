package cn.thinkjoy.zgk.zgksystem.pojo;

import cn.thinkjoy.zgk.zgksystem.domain.DepartmentProductRelation;

/**
 * Created by yangyongping on 2016/10/14.
 */
public class DepartmentProductRelationPojo extends DepartmentProductRelation {
      private  String  intro;
    private Integer cardBusinessType;
    private String cardGrade;
    private String cardOfficial;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getCardBusinessType() {
        return cardBusinessType;
    }

    public void setCardBusinessType(Integer cardBusinessType) {
        this.cardBusinessType = cardBusinessType;
    }

    public String getCardGrade() {
        return cardGrade;
    }

    public void setCardGrade(String cardGrade) {
        this.cardGrade = cardGrade;
    }

    public String getCardOfficial() {
        return cardOfficial;
    }

    public void setCardOfficial(String cardOfficial) {
        this.cardOfficial = cardOfficial;
    }
}
