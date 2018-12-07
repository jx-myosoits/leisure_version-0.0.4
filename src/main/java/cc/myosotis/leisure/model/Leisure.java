package cc.myosotis.leisure.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消闲应用
 */
@Entity
public class Leisure extends Operator {

    // 收藏
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Commodity> collection = new ArrayList<>();

    // 交易（完成/进行中）
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bill> trade = new ArrayList<>();

    // 我的关注
    private String myAttention[] = new String[0];

    // 关注我的人数
    private Integer attentionMy = 0;

    // 映像分
    private Float imageScore = 0.0F;

    // 余额
    private Double balance = 0.0;

    // 收款方式（支付宝）收款码
    private String alipay;

    // 访问量
    private Integer views = 0;

    public List<Commodity> getCollection() {
        return collection;
    }

    public void setCollection(List<Commodity> collection) {
        this.collection = collection;
    }

    public List<Bill> getTrade() {
        return trade;
    }

    public void setTrade(List<Bill> trade) {
        this.trade = trade;
    }

    public String[] getMyAttention() {
        return myAttention;
    }

    public void setMyAttention(String[] myAttention) {
        this.myAttention = myAttention;
    }

    public Integer getAttentionMy() {
        return attentionMy;
    }

    public void setAttentionMy(Integer attentionMy) {
        this.attentionMy = attentionMy;
    }

    public Float getImageScore() {
        return imageScore;
    }

    public void setImageScore(Float imageScore) {
        this.imageScore = imageScore;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public void addBill(Bill bill) {
        this.trade.add(bill);
    }

    public void addCollection(Commodity commodity) {
        this.collection.add(commodity);
    }

}
