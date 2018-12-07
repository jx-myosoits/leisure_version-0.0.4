package cc.myosotis.leisure.controller;

import cc.myosotis.leisure.model.Commodity;
import cc.myosotis.leisure.service.ActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ActiveController {

//    @Value("${web.upload-path}")
//    private String path;

    private ActiveService service;

    @Autowired
    public ActiveController(ActiveService service) {
        this.service = service;
    }

    // 注销登陆
    @GetMapping("/logout")
    public Object logoutUser() {
        return service.logoutUser();
    }

    // 获取我创建的商品
    @GetMapping("/commodities")
    public Object myCommodities() {
        return service.myCommodities();
    }

    // 获取我的收藏
    @GetMapping("/collection")
    public Object myCollection() {
        return service.myCollection();
    }

    // 获取我的交易
    @GetMapping("/trade")
    public Object myTrade() {
        return service.myTrade();
    }

    // 获取我的关注
    @GetMapping("/attention")
    public Object myAttention() {
        return service.myAttention();
    }

    // 完成交易（卖方/买方）
    @GetMapping("/trade/complete")
    public Object tradeComplete(@RequestParam String billingNumber) {
        return service.tradeComplete(billingNumber);
    }

    // 获取类型
    @GetMapping("/sorts")
    public Object getSorts() {
        return null;
    }

    // 创建商品
    @PostMapping("/create/commodity")
    public Object createCommodity(Double price,
                                  String details,
                                  String label[],
                                  String sortName,
                                  String commodityName,
                                  MultipartFile images[]) {
        Commodity commodity = new Commodity();
        commodity.setPrice(price);
        commodity.setLabel(label);
        commodity.setDetails(details);
        commodity.setCommodityName(commodityName);
        return service.createCommodity(commodity, sortName, images);
    }

    // 修改商品
    @PostMapping("/update/commodity")
    public Object updateCommodity(Commodity commodity, @RequestParam String CN) {
        return service.updateCommodity(commodity, CN);
    }

    // 删除商品
    @DeleteMapping("/delete/commodity")
    public Object deleteCommodity(String commodityNumber) {
        return service.deleteCommodity(commodityNumber);
    }

    // 创建交易（买家）
    @PostMapping("/trade/create")
    public Object createTrade(String commodityNumber, Integer paymentMethod, String payer) {
        return service.createTrade(commodityNumber, paymentMethod, payer);
    }

    // 取消交易（进行中）
    @PostMapping("/trade/delete")
    public Object deleteTrade(String BN) {
        return service.deleteTrade(BN);
    }

    // 删除账单（已完成）
    @PostMapping("/bill/delete")
    public Object deleteBill(String BN) {
        return service.deleteBill(BN);
    }

    // 余额提现
    @PostMapping("/balance/withdrawal")
    public Object withdrawal() {
        return service.withdrawal();
    }

    // 支付
    @PostMapping("/pay")
    public Object pay(String BN) {
        return service.pay(BN);
    }

    // 充值
    @PostMapping("/recharge")
    public Object recharge(@RequestParam Double RMB) {
        return service.recharge(RMB);
    }

    // 添加好友
    @PostMapping("/friend/add")
    public Object addFriend(String friendUsername) {
        return null;
    }


}
