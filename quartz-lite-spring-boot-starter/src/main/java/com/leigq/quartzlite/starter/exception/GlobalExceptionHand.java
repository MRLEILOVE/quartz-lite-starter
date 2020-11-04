package com.leigq.quartzlite.starter.exception;

import com.leigq.quartzlite.autoconfigure.bean.common.Response;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 * <p>
 *
 * @author leigq <br>
 * @date 2019-05-14 17:09 <br>
 */
@RestControllerAdvice
public class GlobalExceptionHand {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHand.class);

    /**
     * 自定义业务异常处理
     * <br>
     * 处理 BusinessException 及其 子类异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServiceException.class)
    public Response handleServiceException(ServiceException e) {
        String msg = e.getMessage();
        log.warn(msg, e);
        return Response.fail(msg);
    }


    /**
     * 处理参数绑定异常，并拼接出错的参数异常信息。
     * <p>
     * 创建人：leigq <br>
     * 创建时间：2017年10月16日 下午9:09:22 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param result 错误提示信息
     */
    public static String handleBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            // 获取目标对象类（验证的对象）
            final Class<?> targetClass = Objects.requireNonNull(result.getTarget()).getClass();
            // 获取所有属性
            final Field[] declaredFields = targetClass.getDeclaredFields();

            // 排序字段，按照其在表单中的显示方式进行排序
            List<String> fieldsWithOrder = Arrays.stream(declaredFields).map(Field::getName).collect(Collectors.toList());

            // 获取与字段相关的所有错误
            final List<FieldError> fieldErrors = result.getFieldErrors();

            AtomicReference<String> errorMessage = new AtomicReference<>();
            fieldErrors.stream().min(getFieldOrderComparator(fieldsWithOrder)).ifPresent(fieldError -> errorMessage.set(fieldError.getDefaultMessage()));
            return errorMessage.get();
        }
        return null;
    }


    /**
     * Gets field order comparator.
     * <br/>
     * 参考：https://www.icode9.com/content-1-588364.html
     *
     * @param fieldsWithOrder 排序字段，按照其在表单中的显示方式进行排序
     * @return the field order comparator
     */
    private static Comparator<FieldError> getFieldOrderComparator(List<String> fieldsWithOrder) {
        return (fe1, fe2) -> NumberUtils.compare(fieldsWithOrder.indexOf(fe1.getField()), fieldsWithOrder.indexOf(fe2.getField()));
    }
}
