package com.zhytnik.shop.controller;

import com.zhytnik.shop.exeception.ValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * @author Alexey Zhytnik
 * @since 13.06.2016
 */
@Aspect
@Component
class EntityValidationAspect {

    @Around("execution(public * *(..)) && @target(org.springframework.stereotype.Controller) && @annotation(org.springframework.web.bind.annotation.RequestMapping) && @annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public Object checkValidationErrors(ProceedingJoinPoint pjp) throws Throwable {
        final BindingResult result = getBindingResult(pjp.getArgs());
        if (result != null && result.hasErrors()) throw new ValidationException();
        return pjp.proceed();
    }

    private BindingResult getBindingResult(Object[] args) {
        for (Object arg : args) {
            if (arg != null && arg instanceof BindingResult) {
                return (BindingResult) arg;
            }
        }
        return null;
    }
}
