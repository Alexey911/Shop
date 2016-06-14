package com.zhytnik.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alexey Zhytnik
 * @since 02.06.2016
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class NotUniqueException extends TranslatableException {
}
