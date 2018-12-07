package cc.myosotis.leisure.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 商品实体
 */
@Entity
public class Commodity extends Operator {

    // 未出售
    public static final Integer UNSOLD = 0;

    // 出售中
    public static final Integer WAITING_SOLD = 1;

    // 已出售
    public static final Integer SOLD = 2;

    // 单价
    private Double price = 0.0;

    // 评分
    private Float score = 0.0F;

    // 浏览数
    private Integer views = 0;

    // 库存
    private Integer sold = UNSOLD;

    // 详细
    private String details;

    // 商品图片
    private String images[] = new String[0];

    // 商品名称
    private String commodityName;

    // 类别
    @OneToOne
    private Sort sort = new Sort();

    // 标签
    private String label[] = new String[0];

    // 商品编号
    private String commodityNumber = "CN_" + new Date().getTime();

    // 商品评论
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<>();

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public String[] getLabel() {
        return label;
    }

    public void setLabel(String[] label) {
        this.label = label;
    }

    public String getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(String commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
