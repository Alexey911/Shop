package com.zhytnik.shop.controller;

import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.zhytnik.shop.controller.ResponseUtil.send;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Alexey Zhytnik
 * @since 09.06.2016
 */
@Controller
@RequestMapping("/types")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<DynamicType> findById(@PathVariable Long id) {
        return send(typeService.findById(id));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> createType(@RequestBody @Valid DynamicType type,
                                           BindingResult result) {
        if (result.hasErrors()) badRequest();
        typeService.create(type);
        return ok(type.getId());
    }
}
