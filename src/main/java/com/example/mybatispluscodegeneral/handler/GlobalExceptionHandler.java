package com.example.mybatispluscodegeneral.handler;

import com.example.mybatispluscodegeneral.utils.resultbean.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 用来处理bean validation异常
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResultBean<String> resolveConstraintViolationException(ConstraintViolationException ex){
        ResultBean<String> stringResultBean = new ResultBean<>();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if(!CollectionUtils.isEmpty(constraintViolations)){
            StringBuilder msgBuilder = new StringBuilder();
            for(ConstraintViolation constraintViolation :constraintViolations){
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if(errorMessage.length()>1){
                errorMessage = errorMessage.substring(0,errorMessage.length()-1);
            }
            // 非法参数code
            stringResultBean.setCode(100);
            stringResultBean.setMsg(errorMessage);
            return stringResultBean;
        }
        // 非法参数code
        stringResultBean.setCode(100);
        stringResultBean.setMsg(ex.getMessage());
        return stringResultBean;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultBean<String> resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ResultBean<String> stringResultBean = new ResultBean<>();
        List<ObjectError>  objectErrors = ex.getBindingResult().getAllErrors();
        if(!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            // 非法参数code
            stringResultBean.setCode(100);
            stringResultBean.setMsg(errorMessage);
            return stringResultBean;
        }
        // 非法参数code
        stringResultBean.setCode(100);
        stringResultBean.setMsg(ex.getMessage());
        return stringResultBean;
    }
}