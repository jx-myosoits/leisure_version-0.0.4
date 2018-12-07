package cc.myosotis.leisure.service;

import cc.myosotis.leisure.model.User;
import cc.myosotis.leisure.util.Result;


public interface PublicService {

    /**
     * 获取随机码
     *
     * @param seed 随机种子
     * @param len  长度
     * @return verificationCode
     */
    Result verification(String seed, Integer len);

    /**
     * 创建用户
     *
     * @param user  用户对象
     * @param email 电子邮件
     * @return Boolean
     */
    Result createUser(User user, String email);

    /**
     * 激活用户
     *
     * @param token 匹配公钥
     * @return Boolean
     */
    Result activationUser(String token);

    /**
     * 登陆用户
     *
     * @param user         用户对象
     * @param verification 验证码
     * @return Boolean
     */
    Result loginUser(User user, String verification);

    /**
     * 获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    Result userProfile(String username);

    /**
     * 搜索商品
     *
     * @param commodityName 商品名
     * @param searchMethod  搜索方式
     * @param order         排序
     * @param page          页面
     * @param size          数量
     * @param min           最低价格
     * @param max           最高价格
     * @return CommodityList
     */
    Result getCommodities(String commodityName,
                          String searchMethod,
                          String order,
                          Integer page,
                          Integer size,
                          Double min,
                          Double max);

    /**
     * 获取商品分类
     *
     * @return 分类列表
     */
    Object sorts();
}
