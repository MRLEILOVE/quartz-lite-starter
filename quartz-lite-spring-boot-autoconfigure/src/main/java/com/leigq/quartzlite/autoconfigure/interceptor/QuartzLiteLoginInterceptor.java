package com.leigq.quartzlite.autoconfigure.interceptor;

import com.leigq.quartzlite.autoconfigure.bean.common.Response;
import com.leigq.quartzlite.autoconfigure.constant.SysUserConstant;
import com.leigq.quartzlite.autoconfigure.util.JacksonUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * 登录拦截器
 * <br/>
 * 参考:<a href='http://note.youdao.com/noteshare?id=5ca2c10916c3286df034b78f938b201e'>SpringMVC Interceptor</a>
 * 创建人：leigq <br>
 * 创建时间：2018-11-05 11:26 <br>
 *
 * @author leigq
 */
public class QuartzLiteLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        // 获取 session
        HttpSession session = request.getSession();
        Object userKey = session.getAttribute(SysUserConstant.USER_SESSION_KEY);

        if (Objects.nonNull(userKey)) {
            return true;
        }

        // 判断是否为ajax请求，默认不是
        boolean isAjaxRequest = false;
        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {
            isAjaxRequest = true;
        }

        if (isAjaxRequest) {
            Response fail = Response.fail(1000, "登录超时，请重新登录");
            response.setContentType("application/json;charset=UTF-8");
            try (PrintWriter printWriter = response.getWriter()) {
                printWriter.write(JacksonUtils.objToJson(fail));
            }
        } else {
            response.sendRedirect("/quartz-lite/quartz-lite-login.html");
        }
        return false;
    }
}
