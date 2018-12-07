package cc.myosotis.leisure.service.impl;

import cc.myosotis.leisure.aspect.HttpAspect;
import cc.myosotis.leisure.model.Bill;
import cc.myosotis.leisure.model.Commodity;
import cc.myosotis.leisure.model.User;
import cc.myosotis.leisure.repository.*;
import cc.myosotis.leisure.service.ActiveService;
import cc.myosotis.leisure.util.FileUtils;
import cc.myosotis.leisure.util.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActiveServiceImpl implements ActiveService {

    @Resource
    private CommodityRepository commodityR;
    @Resource
    private LeisureRepository leisureR;
    @Resource
    private BillRepository billR;
    @Resource
    private UserRepository userR;
    @Resource
    private SortRepository sortR;

    /**
     * 注销登陆
     *
     * @return Boolean
     */
    @Override
    public Object logoutUser() {
        // 判断是否登陆
        if ((User) HttpAspect.session.getAttribute("active") == null) {
            return Result.error("注销用户失败，登陆已失效");
        }
        HttpAspect.session.setAttribute("active", null);

        Map<String, Object> results = new HashMap<>();
        results.put("status", "注销成功");

        return Result.success(results);
    }

    /**
     * 我创建的商品
     *
     * @return CommodityList
     */
    @Override
    public Object myCommodities() {
        User active = (User) HttpAspect.session.getAttribute("active");

        Map<String, Object> results = new HashMap<>();
        results.put("nav", "creator");
        results.put("commodities", commodityR.findCommoditiesByCreator(active.getUsername()));

        return results;
    }

    /**
     * 我收藏的商品
     *
     * @return CommodityList
     */
    @Override
    public Object myCollection() {
        User active = (User) HttpAspect.session.getAttribute("active");

        Map<String, Object> results = new HashMap<>();
        results.put("nav", "collection");
        results.put("commodities", active.getLeisure().getCollection());

        return results;
    }

    /**
     * 我关注的用户
     *
     * @return String[]
     */
    @Override
    public Object myAttention() {
        User active = (User) HttpAspect.session.getAttribute("active");

        Map<String, Object> results = new HashMap<>();
        results.put("nav", "attention users");
        results.put("users", active.getLeisure().getMyAttention());

        return results;
    }

    /**
     * 我的交易
     *
     * @return BillList
     */
    @Override
    public Object myTrade() {
        User active = (User) HttpAspect.session.getAttribute("active");

        Map<String, Object> results = new HashMap<>();
        results.put("nav", "trade");
        results.put("Trades", active.getLeisure().getTrade());

        return results;
    }

    /**
     * 交易完成
     *
     * @param billingNumber 账单编号
     * @return Boolean
     */
    @Override
    public Object tradeComplete(String billingNumber) {
        User active = (User) HttpAspect.session.getAttribute("active");
        // 查询账单
        Bill bill = billR.findBillByBillingNumberAndBuyer(billingNumber, active.getUsername());
        if (bill == null) {
            return Result.error("账单丢失或无权访问");
        }
        if (bill.getPaymentMethod().equals(Bill.ONLINE_PAYMENT)) {
            if (bill.getPaymentStatus().equals(Bill.UNPAID)) {
                return Result.error("你的支付方式是线上支付，尚未付款");
            }
        }
        if (bill.getSchedule() >= Bill.SCHEDULE_BC) {
            return Result.error("交易已完成");
        }
        /*
        // 判断是否所有者（卖家）
        if (bill.getSeller().equals(active.getUsername())) {
            if (bill.getSchedule().equals(Bill.SCHEDULE_SC)) {
                return Result.error("你已处理此交易");
            }
            bill.setSchedule(Bill.SCHEDULE_SC);
        }
        */
        // 判断是否（买家）
        if (bill.getBuyer().equals(active.getUsername())) {
            if (bill.getSchedule().equals(Bill.SCHEDULE_BC)) {
                return Result.error("你已处理此交易");
            }
            bill.setSchedule(Bill.SCHEDULE_BC);
        }

        Map<String, Object> results = new HashMap<>();
        results.put("status", bill.getPaymentStatus());

        return Result.success(results);
    }

    /**
     * 创建商品
     *
     * @param commodity 商品对象
     * @param sortName  分类字符串
     * @param files     图片
     * @return Boolean
     */
    @Override
    public Object createCommodity(Commodity commodity, String sortName, MultipartFile[] files) {

        // 字段检测
        System.out.println("createCommodity()存在测试用例");
        if (false) {
            return Result.error("商品字段存在不匹配格式");
        }
        User active = (User) HttpAspect.session.getAttribute("active");
        // 不能重复创建
        List<Commodity> commodities =
                commodityR.findCommoditiesByCreatorAndCommodityName
                        (active.getUsername(), commodity.getCommodityName());

        if (commodities != null) {
            for (Commodity commodity1 : commodities) {
                int sum = 0;// 统计相同标签数量
                for (String label1 : commodity1.getLabel()) {
                    for (String label2 : commodity.getLabel()) {
                        if (label2.equals(label1)) {
                            sum++;
                        }
                    }
                }
                // 判断相同的标签数量是否与创建商品的标签数量一样
                if (commodity.getLabel().length == sum && commodity1.getLabel().length == sum) {
                    return Result.error("相同名称且相同标签的商品已经存在，如果执意创建为何不考虑为此商品添加数量");
                }
            }
        }

        // 要上传的目标文件存放路径
        String localPath = "C:/inetpub/wwwroot/leisure/" + active.getUsername();
        // 上传成功或者失败的提示
        String msg[] = new String[0];
        String path[] = new String[0];
        for (MultipartFile file : files) {
            msg = Arrays.copyOf(msg, msg.length + 1);
            String uuidName = FileUtils.upload(file, localPath, file.getOriginalFilename());
            if (uuidName != null) {
                // 上传成功，给出页面提示
                msg[msg.length - 1] = file.getOriginalFilename() + "--上传成功";
                path = Arrays.copyOf(path, path.length + 1);
                path[path.length - 1] = "http://139.199.2.193/leisure/commodity/" + active.getUsername() + "/" + uuidName;
            } else {
                msg[msg.length - 1] = file.getOriginalFilename() + "--上传失败";
            }
        }

        // 通过 sortName 查询分类
        commodity.setSort(sortR.findSortBySortName(sortName));
        // 保存商品
        commodity.create(active.getUsername());
        commodity.setImages(path);
        Commodity saveCommodity = commodityR.save(commodity);

        Map<String, Object> results = new HashMap<>();
        results.put("status", "创建成功");
        results.put("commodityNumber", saveCommodity.getCommodityNumber());
        results.put("fileUploadStatus", msg);
        return Result.success(results);
    }

    /**
     * 修改商品
     *
     * @param updateCommodity 修改后的商品对象
     * @param commodityNumber 待修改的商品编号
     * @return Boolean
     */
    @Override
    public Object updateCommodity(Commodity updateCommodity, String commodityNumber) {
        return Result.error("暂未实现修改接口");
    }

    /**
     * 删除商品
     *
     * @param commodityNumber 商品编号
     * @return Boolean
     */
    @Override
    public Object deleteCommodity(String commodityNumber) {
        Commodity commodity = commodityR.findCommodityByCommodityNumber(commodityNumber);
        User active = (User) HttpAspect.session.getAttribute("active");
        if (commodity == null) {
            return Result.error("商品不存在");
        }
        if (commodity.getCreator().equals(active.getUsername())) {
            commodityR.delete(commodity);

            Map<String, Object> results = new HashMap<>();
            results.put("status", "删除成功");
            results.put("commodityNumber", commodityNumber);

            return Result.success(results);
        }
        return Result.error("没有删除权限");
    }

    /**
     * 取消交易（进行中） = 删除进行中的Bill
     *
     * @param billingNumber 账单编号
     * @return Boolean
     */
    @Override
    public Object deleteTrade(String billingNumber) {
        User active = (User) HttpAspect.session.getAttribute("active");
        Bill bill = billR.findBillByBillingNumberAndBuyer(billingNumber, active.getUsername());
        if (bill == null) {
            return Result.error("账单不存在或无权访问");
        }
        if (active.getUsername().equals(bill.getBuyer())) {
            if (bill.getSchedule() > 1) {
                return Result.error("交易已完成，不可取消交易");
            }
        }

        for (int i = 0; i < active.getLeisure().getTrade().size(); i++) {
            if (active.getLeisure().getTrade().get(i).getBillingNumber().equals(bill.getBillingNumber())) {
                active.getLeisure().getTrade().remove(i);
            }
        }
        userR.save(active);

        bill.getCommodity().setSold(Bill.UNPAID);
        billR.delete(bill);
        return Result.success("取消成功");
    }

    /**
     * 删除交易（已完成） = 删除已完成的Bill
     *
     * @param billingNumber 账单编号
     * @return Boolean
     */
    @Override
    public Object deleteBill(String billingNumber) {
        User active = (User) HttpAspect.session.getAttribute("active");
        Bill bill = billR.findBillByBillingNumberAndBuyer(billingNumber, active.getUsername());
        if (bill == null) {
            return Result.error("账单不存在或无权访问");
        }
        if (active.getUsername().equals(bill.getBuyer())) {
            if (bill.getSchedule() >= 2) {
                billR.delete(bill);
            } else {
                return Result.error("交易未完成，请先取消交易");
            }
        }
        return Result.error("删除成功");
    }

    /**
     * 提现，将账户余额转为支付宝余额
     *
     * @return Boolean
     */
    @Override
    public Object withdrawal() {
        return null;
    }

    /**
     * 支付
     *
     * @param billingNumber 账单编号
     * @return Boolean
     */
    @Override
    public Object pay(String billingNumber) {
        User active = (User) HttpAspect.session.getAttribute("active");
        // 查询账单
        Bill bill = billR.findBillByBillingNumberAndPayer(billingNumber, active.getUsername());
        if (bill == null) {
            return Result.error("此账单不存在或没有权限访问");
        }
        // 代付
        if (bill.getPaymentMethod().equals(Bill.PRE_PAYMENT)) {
            if (!bill.getPayer().equals(active.getUsername())) {
                return Result.error("您没有权限支付此商品");
            }
        }
        if (!active.getUsername().equals(bill.getBuyer())) {
            return Result.error("您没有权限支付此商品");
        }
        if (bill.getPaymentStatus().equals(Bill.PAID)) {
            return Result.error("你已支付不需重复支付");
        }
        // 扣费
        Double sumRMB = bill.getCommodity().getPrice();
        // 余额
        Double endRMB = active.getLeisure().getBalance();
        if (endRMB >= sumRMB) {
            active.getLeisure().setBalance(endRMB - sumRMB);
            bill.setPaymentStatus(Bill.PAID);
            billR.save(bill);
//            userR.save(active);
            return Result.success("支付完成");
        }

        return Result.error("余额不足，请充值");
    }

    /**
     * 充值
     *
     * @param RMB 人名币
     * @return Boolean
     */
    @Override
    public Object recharge(Double RMB) {
        return Result.error("未申请第三方支付接口，无法充值");
    }

    /**
     * 创建账单
     *
     * @param commodityNumber 商品编号数组
     * @param paymentMethod   支付方式4，5，6
     * @param payer           支付者
     * @return Boolean
     */
    @Override
    public Object createTrade(String commodityNumber, Integer paymentMethod, String payer) {

        User active = (User) HttpAspect.session.getAttribute("active");

        Bill bill = new Bill(active.getUsername());

        paymentMethod = paymentMethod == null ? 4 : paymentMethod;
        if (paymentMethod < 4 || paymentMethod > 6) {
            paymentMethod = 4;
        }

        if (paymentMethod.equals(Bill.PRE_PAYMENT)) {
            if (payer == null) {
                return Result.error("必须指定一个帮你付款的好友");
            }
            User payerU = userR.findUserByUsername(payer);
            // 判断是否好友
            boolean isFriend = false;
            for (User friend : active.getFriends()) {
                if (friend.getUsername().equals(payer)) {
                    isFriend = true;
                }
            }
            if (!isFriend) {
                return Result.error("无法指定该用户，你们不是好友关系");
            }
            bill.setPayer(payer);
        }

        Commodity commodity = commodityR.findCommodityByCommodityNumber(commodityNumber);
        if (commodity == null) {
            return Result.error("不存在商品");
        }
        if (commodity.getCreator().equals(active.getUsername())) {
            return Result.error("自己的商品");
        }
        // 商品状态判断
        if (commodity.getSold().equals(Commodity.WAITING_SOLD)) {
            return Result.error("交易中商品");
        } else if (commodity.getSold().equals(Commodity.SOLD)) {
            return Result.error("已出售商品");
        }
        commodity.setSold(Commodity.WAITING_SOLD);
        commodityR.save(commodity);
        // 添加商品
        bill.setSeller(commodity.getCreator());
        bill.setBuyer(active.getUsername());
        bill.setPayer(active.getUsername());
        bill.setCommodity(commodity);
        bill.setPaymentMethod(paymentMethod);
        /*
            // 添加卖家的账单
            User seller = userR.findUserByUsername(commodity.getCreator());
            seller.getLeisure().addBill(bill);
            userR.save(seller);
        */
        // 创建我的账单

        active.getLeisure().addBill(bill);
        userR.save(active);

        return Result.success("创建成功（测试用例，请查看数据库内容）");
    }

}
