package cc.myosotis.leisure.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * 评论实体
 */
@Entity
public class Comment extends Operator {

    // 内容
    private String context;

    // 分数
    private Float score = 10.0F;

    // 商品编号
    private String commodityNumber;

    // 回复
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Msg> reply = new HashSet<>();

    public String getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(String commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Set<Msg> getReply() {
        return reply;
    }

    public void setReply(Set<Msg> reply) {
        this.reply = reply;
    }
}
