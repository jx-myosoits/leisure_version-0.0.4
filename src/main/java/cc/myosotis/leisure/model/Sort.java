package cc.myosotis.leisure.model;

import javax.persistence.Entity;

/**
 * 类别实体
 */
@Entity
public class Sort extends Operator {

    // 类别名称
    private String sortName;

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
}
