package com.zhytnik.shop.exeception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alexey Zhytnik
 * @since 02.06.2016
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not unique")
public class NotUniqueException extends RuntimeException {
}
