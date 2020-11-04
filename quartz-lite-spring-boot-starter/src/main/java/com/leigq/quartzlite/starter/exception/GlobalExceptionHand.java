package com.leigq.quartzlite.starter.exception;

import com.leigq.quartzlite.autoconfigure.bean.common.Response;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.management.ServiceNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.lang.reflect.Field;
import java.util.*;
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
     * 400 - 缺少请求参数：前端少传了参数给后端
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Response handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String msg = "缺少请求参数";
        log.warn(msg, e);
        return Response.fail(msg);
    }


    /**
     * 400 - 参数类型不匹配：前端页面传过来的参数与你controller接收的参数类型不匹配
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Response handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String msg = "参数类型不匹配";
        log.warn(msg, e);
        return Response.fail(msg);
    }

    /**
     * 400 - Http消息不可读：参数类型与页面的contentType类型不匹配
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String msg = "Http消息不可读";
        log.warn(msg, e);
        return Response.fail(msg);
    }


    /**
     * 405 - 不支持当前请求方法
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String msg = "不支持当前请求方法";
        log.error(msg, e);
        return Response.fail(msg);
    }

    /**
     * 415 - Http媒体类型不支持
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Response handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        String msg = "不支持当前媒体类型";
        log.error(msg, e);
        return Response.fail(msg);
    }

    /**
     * 422 - UNPROCESSABLE_ENTITY
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Response handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        String msg = "所上传文件大小超过最大限制，上传失败";
        log.warn(msg, e);
        return Response.fail(msg);
    }


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
     * 方法参数无效，当对用@Valid注释的参数进行验证失败时，将引发异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = handleBindingResult(e.getBindingResult());
        log.warn("方法参数无效: ", e);
        return Response.fail(msg);
    }

    /**
     * 参数绑定失败
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public Response handleBindException(BindException e) {
        String msg = handleBindingResult(e.getBindingResult());
        log.warn("参数绑定失败:", e);
        return Response.fail(msg);
    }

    /**
     * 参数验证失败
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public Response handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String msg = violations.iterator().next().getMessage();
        log.warn("参数验证失败:", e);
        return Response.fail(msg);
    }

    /**
     * 参数验证失败
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ValidationException.class)
    public Response handleValidationException(ValidationException e) {
        String msg = e.getMessage();
        log.warn("参数验证失败：", e);
        return Response.fail(msg);
    }


    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceNotFoundException.class)
    public Response handleServiceNotFoundException(ServiceNotFoundException e) {
        String msg = "服务内部异常";
        log.error(msg, e);
        return Response.fail(msg);
    }

    /**
     * 500 - 娄底异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        String msg = "服务内部异常";
        log.error(msg, e);
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
    private String handleBindingResult(BindingResult result) {
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
    private Comparator<FieldError> getFieldOrderComparator(List<String> fieldsWithOrder) {
        return (fe1, fe2) -> NumberUtils.compare(fieldsWithOrder.indexOf(fe1.getField()), fieldsWithOrder.indexOf(fe2.getField()));
    }
}
