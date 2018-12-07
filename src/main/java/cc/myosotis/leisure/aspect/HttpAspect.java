package cc.myosotis.leisure.aspect;

import cc.myosotis.leisure.model.User;
import cc.myosotis.leisure.util.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Aspect
@Component
public class HttpAspect {

    private ServletRequestAttributes attributes;
    private HttpServletRequest request;
    public static HttpSession session;

    @Around("execution(public * cc.myosotis.leisure.controller.*.*(..))")
    private Object initial(ProceedingJoinPoint pjp) throws Throwable {
        initialAspect();
        return pjp.proceed();
    }

    /**
     * 活动用户
     *
     * @param pjp 切片
     * @return 返回值
     * @throws Throwable 切面异常
     */
    @Around("execution(public * cc.myosotis.leisure.controller.ActiveController.*(..))")
    public Object active(ProceedingJoinPoint pjp) throws Throwable {
        initialAspect();
        // 判断是否登陆
        User query = (User) session.getAttribute("active");
        if (query == null) {
            return Result.error("未登录");
        }
        return pjp.proceed();
    }

    /**
     * 管理员
     *
     * @param pjp 切片
     * @return 返回值
     * @throws Throwable 切面异常
     */
    @Around("execution(public * cc.myosotis.leisure.controller.AdminController.*(..))")
    public Object admin(ProceedingJoinPoint pjp) throws Throwable {
        initialAspect();
        // 判断是否登陆
        User query = (User) session.getAttribute("active");
        if (query == null) {
            return Result.error("未登录");
        }

        // 判断是否管理员
        if (query.getRole().equals(User.ROLE_ADMIN)) {
            return pjp.proceed();
        } else {
            return Result.error("无管理员权限");
        }
    }

    /**
     * 初始化切面参数
     */
    private void initialAspect() {
        attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            request = attributes.getRequest();
        }
        session = request.getSession();
    }

}
