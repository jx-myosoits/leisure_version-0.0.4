package cc.myosotis.leisure.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 交易实体
 */
@Entity
public class Bill extends Operator {

    // No progress 无进度
    public static final Integer SCHEDULE_NP = 0;

    // Seller confirmation 卖家确认
    public static final Integer SCHEDULE_SC = 1;

    // Buyer confirmation 买家确认
    public static final Integer SCHEDULE_BC = 2;

    // Express confirmation 快递确认
    public static final Integer SCHEDULE_EC = 3;

    // Online payment 线上支付
    public static final Integer ONLINE_PAYMENT = 4;

    // cash payment 现金支付
    public static final Integer CASH_PAYMENT = 5;

    // pre-payment 好友代付
    public static final Integer PRE_PAYMENT = 6;

    // 已支付
    public static final Integer PAID = 7;

    // 未支付
    public static final Integer UNPAID = 8;

    // 邮寄
    public static final Integer TM_MAILING = 9;

    // 见面交易
    public static final Integer TM_FACE = 10;

    // 买方 = this.creator
    private String buyer;

    // 卖方 = Commodity.creator // 如果商品列表有多个卖家就无法确认具体的收款方
    private String seller;

    // 运单编号
    private String waybillNumber;

    // 进度
    private Integer schedule = SCHEDULE_NP;

    // 支付方式
    private Integer paymentMethod = ONLINE_PAYMENT;

    // 支付状态
    private Integer paymentStatus = UNPAID;

    // 代付人
    private String payer;

    // 账单编号
    private String billingNumber = "BN_" + new Date().getTime();

    // 状态 默认null
    private boolean valid;

    // 交易方式
    private Integer tradingMethod = TM_MAILING;

    // 商品
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Commodity commodity;

    public Bill() {
    }

    public Bill(String creator) {
        super(creator);
    }

    public String getBillingNumber() {
        return billingNumber;
    }

    public void setBillingNumber(String billingNumber) {
        this.billingNumber = billingNumber;
    }

    public String getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(String waybillNumber) {
        this.waybillNumber = waybillNumber;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public boolean isValid() {
        return valid;
    }

    public Integer getTradingMethod() {
        return tradingMethod;
    }

    public void setTradingMethod(Integer tradingMethod) {
        this.tradingMethod = tradingMethod;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void create(String who) {
        super.create(who);
        this.setBuyer(who);
    }

}
