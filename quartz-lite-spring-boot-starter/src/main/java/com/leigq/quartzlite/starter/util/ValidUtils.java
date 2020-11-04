package com.leigq.quartzlite.starter.util;

import com.leigq.quartzlite.starter.exception.ServiceException;
import lombok.NonNull;

import java.util.Optional;

/**
 * 验证工具类
 * <br/>
 *
 * @author ：leigq
 * @date ：2019/7/31 22:11
 */
public final class ValidUtils {

    private ValidUtils() {

    }

    /*
     * 针对验证不通过抛 {@link ServiceException} 异常处理，适用于 Service 层业务流程执行、判断需要事务回滚的方法
     */

    /**
     * 验证参数是否符合表达式
     * <br/>
     *
     * @param expression 表达式， 如果 expression == true 抛 {@link ServiceException} 异常，
     *                   ServiceException 异常在全局异常处理中处理，以 json 格式返回消息给用户
     * @param errorMsg   错误提示信息
     */
    public static void checkArg(boolean expression, @NonNull Object errorMsg) {
        if (expression) {
            throwException(String.valueOf(errorMsg));
        }
    }

    /**
     * 验证对象是否为空
     * <br/>
     * Optional的使用参考：<a href='http://www.ibloger.net/article/3209.html'>JAVA8之妙用Optional解决判断Null为空的问题</a>
     * <br/>
     *
     * @param o        待验证的对象
     * @param errorMsg 错误信息
     */
    public static void isNull(Object o, @NonNull Object errorMsg) {
        Optional.ofNullable(o).orElseThrow(() -> new ServiceException(String.valueOf(errorMsg)));
    }


    /**
     * 抛自定义 Service 异常
     *
     * @param errorMsg 错误信息
     */
    public static void throwException(String errorMsg) {
        throw new ServiceException(errorMsg);
    }

}
