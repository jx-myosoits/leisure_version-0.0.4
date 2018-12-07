package cc.myosotis.leisure.service;

import cc.myosotis.leisure.model.Commodity;
import org.springframework.web.multipart.MultipartFile;

public interface ActiveService {

    /**
     * 注销登陆
     *
     * @return Boolean
     */
    Object logoutUser();

    /**
     * 我创建的商品
     *
     * @return CommodityList
     */
    Object myCommodities();

    /**
     * 我收藏的商品
     *
     * @return CommodityList
     */
    Object myCollection();

    /**
     * 我关注的用户
     *
     * @return String[]
     */
    Object myAttention();

    /**
     * 我的交易
     *
     * @return BillList
     */
    Object myTrade();

    /**
     * 交易完成
     *
     * @param billingNumber 账单编号
     * @return Boolean
     */
    Object tradeComplete(String billingNumber);

    /**
     * 创建商品
     *
     * @param commodity 商品对象
     * @param sortName  分类字符串
     * @param files     图片
     * @return Boolean
     */
    Object createCommodity(Commodity commodity, String sortName, MultipartFile[] files);

    /**
     * 修改商品
     *
     * @param updateCommodity 修改后的商品对象
     * @param commodityNumber 待修改的商品编号
     * @return Boolean
     */
    Object updateCommodity(Commodity updateCommodity, String commodityNumber);

    /**
     * 删除商品
     *
     * @param commodityNumber 商品编号
     * @return Boolean
     */
    Object deleteCommodity(String commodityNumber);

    /**
     * 取消交易（进行中） = 删除进行中的Bill
     *
     * @param billingNumber 账单编号
     * @return Boolean
     */
    Object deleteTrade(String billingNumber);

    /**
     * 删除交易（已完成） = 删除已完成的Bill
     *
     * @param billingNumber 账单编号
     * @return Boolean
     */
    Object deleteBill(String billingNumber);

    /**
     * 提现，将账户余额转为支付宝余额
     *
     * @return Boolean
     */
    Object withdrawal();

    /**
     * 支付
     *
     * @param billingNumber 账单编号
     * @return Boolean
     */
    Object pay(String billingNumber);

    /**
     * 充值
     *
     * @param RMB 人名币
     * @return Boolean
     */
    Object recharge(Double RMB);

    /**
     * 创建账单
     *
     * @param commodityNumber 商品编号数组
     * @param paymentMethod   支付方式 4，5，6
     * @param payer           支付者
     * @return Boolean
     */
    Object createTrade(String commodityNumber, Integer paymentMethod, String payer);
}
