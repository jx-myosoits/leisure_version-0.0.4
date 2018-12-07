package cc.myosotis.leisure.service.impl;

import cc.myosotis.leisure.LeisureApplication;
import cc.myosotis.leisure.aspect.HttpAspect;
import cc.myosotis.leisure.model.*;
import cc.myosotis.leisure.repository.CommodityRepository;
import cc.myosotis.leisure.repository.ProfileRepository;
import cc.myosotis.leisure.repository.SortRepository;
import cc.myosotis.leisure.repository.UserRepository;
import cc.myosotis.leisure.service.PublicService;
import cc.myosotis.leisure.util.Callback;
import cc.myosotis.leisure.util.RegisterData;
import cc.myosotis.leisure.util.Register;
import cc.myosotis.leisure.util.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.*;

@Service
public class PublicServiceImpl implements PublicService {

    @Resource
    private UserRepository userR;

    @Resource
    private ProfileRepository profileR;

    @Resource
    private CommodityRepository commodityR;

    @Resource
    private SortRepository sortR;

    /**
     * 获取随机码
     *
     * @param seed 随机种子
     * @param len  长度
     * @return verificationCode
     */
    @Override
    public Result verification(String seed, Integer len) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < len; i++) {
            str.append(seed.charAt(new Random().nextInt(seed.length() - 1)));
        }

        Map<String, Object> results = new HashMap<>();
        results.put("verification", str);

        // 通过匹配注册器实现访问频率控制
        /*return Result.callback(
                LeisureApplication.register,
                new RegisterData(),
                new RegisterData(),
                new Callback() {
                    @Override
                    public Result success() {
                        return null;
                    }

                    @Override
                    public Result error() {
                        return null;
                    }
                }, 19
        );*/
        return Result.success(results);
    }

    /**
     * 创建用户
     *
     * @param user  用户对象
     * @param email 电子邮件
     * @return Boolean
     */
    @Override
    public Result createUser(User user, String email) {
        // 判断格式
        if (false) {
            return Result.error("表单中存在不符合规范的字段格式");
        }
        // 判断用户是否存在
        if (userR.findUserByUsername(user.getUsername()) != null) {
            return Result.error("此用户已经注册");
        }
        // 判断邮件绑定上限
        if (profileR.findProfileByEmail(email).size() >= 3) {
            return Result.error("此邮箱绑定账号数量已达上限");
        }
        // 发送激活电子邮件

        // （测试用例）
        System.out.println("createUser()存在未处理测试用例");

        user.create(user.getUsername(), email);
        userR.save(user);

        return Result.success("创建成功（测试用例数据）");
    }

    /**
     * 激活用户
     *
     * @param token 匹配公钥
     * @return Boolean
     */
    @Override
    public Result activationUser(String token) {
        return Result.success("暂未实现此接口");
    }

    /**
     * 登陆用户
     *
     * @param user         用户对象
     * @param verification 验证码
     * @return Boolean
     */
    @Override
    public Result loginUser(User user, String verification) {
        // 格式判断
        if (false) {
            return Result.error("表单中存在不符合规范的字段格式");
        }
        // 验证码匹配
        if (false) {
            return Result.error("验证码错误或已过期");
        }
        // 判断是否已经登陆用户
        if ((User) HttpAspect.session.getAttribute("active") != null) {
            return Result.error("你已登陆一个用户，请注销后重试");
        }
        // 判断用户是否存在
        User query = userR.findUserByUsername(user.getUsername());
        if (query == null) {
            return Result.error("不存在此用户");
        }
        // 判断密码是否匹配
        if (!query.getPassword().equals(user.getPassword())) {
            return Result.error("密码错误");
        }
        // 记录用户到Session
        HttpAspect.session.setAttribute("active", query);

        return Result.success("登陆成功");

    }

    /**
     * 获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public Result userProfile(String username) {
        Map<String, Object> results = new HashMap<>();
        String test = "http://www.myosotis.cc/test/test.jpg";
        // 判断是否登陆
        User query = userR.findUserByUsername(username);
        if (query == null) {
            return Result.error("用户不存在");
        }
        User active = (User) HttpAspect.session.getAttribute("active");
        if (active != null) {
            // 判断是否自己
            if (query.getUsername().equals(active.getUsername())) {
                results.put("username", username);
                results.put("avatar", test);
                results.put("profile", query.getProfile());
            }
        }
        // 访问其他用户
        results.put("username", username);
        results.put("avatar", test);

        return Result.success(results);

    }

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
    @Override
    public Result getCommodities(String commodityName, String searchMethod, String order, Integer page, Integer size, Double min, Double max) {
        // 页面不能是负数
        page = page == null ? 0 : page < 0 ? 0 : page;

        // 最低价应该取零以上
        min = min == null ? 0 : min < 0 ? 0 : min;

        // 最高价格不超过Double的最大值，而且大于最小价格
        max = max == null ? 0 : max < min + 1 ? Double.MAX_VALUE : max;

        // 页面容量不能超过100
        size = size == null ? 0 : size > 100 ? 100 : size;

        // 商品名可以为空字符串但不能为空
        commodityName = commodityName == null ? "%%" : "%" + commodityName + "%";

        // 只允许 desc 或 asc 两种参数 默认为 desc
        order = order == null ? "" : (!order.equals("desc") && !order.equals("asc")) ? order = "desc" : order;

        User active = (User) HttpAspect.session.getAttribute("active");

        Map<String, Object> attempt = new HashMap<>();
        List<Commodity> commodities = null;

        searchMethod = searchMethod == null ? "" : searchMethod;
        switch (searchMethod) {
            case "default":
                // get by default
                commodities = commodityR.getByDefault(commodityName, page, size);
                break;
            case "intimate":
                // get by intimate(亲密/贴心)
                if (active == null) {
                    return Result.error("未登录");
                }
                // 获取用户的Intimate信息

                // 通过Intimate获取商品
//                commodities = commodityR.getByIntimate();
                break;
            case "latest":
                // get by latest
                commodities = commodityR.getByLatest(commodityName, page, size);
                break;
            case "goods":
                // get by goods
                commodities = commodityR.getByGoods(commodityName, page, size);
                break;
            case "price":
                // get by price
                if (order.equals("desc")) {
                    commodities = commodityR.getByPriceDesc(commodityName, page, size);
                } else {
                    commodities = commodityR.getByPriceAsc(commodityName, page, size);
                }
                break;
            case "area":
                // get by price area
                if (order.equals("desc")) {
                    commodities = commodityR.getByPriceAreaDesc(min, max, commodityName, page, size);
                } else {
                    commodities = commodityR.getByPriceAreaAsc(min, max, commodityName, page, size);
                }
                break;
            default:
                // can not get anything
                return Result.error("searchMethod 参数错误");
        }

        attempt.put("method", searchMethod);
        attempt.put("commodities", commodities);

        return Result.success(attempt);
    }

    /**
     * 获取商品分类
     *
     * @return 分类列表
     */
    @Override
    public Object sorts() {
        // 前端需求将List转换为Array
        List<Sort> sortList = sortR.findAll();
        String[] sortArray = new String[0];
        for (Sort sort : sortList) {
            sortArray = Arrays.copyOf(sortArray, sortArray.length + 1);
            sortArray[sortArray.length - 1] = sort.getSortName();
        }
        return Result.success(sortArray);
    }
}
