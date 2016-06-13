package com.zhytnik.shop.controller;

import com.zhytnik.shop.dto.TypeDto;
import com.zhytnik.shop.service.TypDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Alexey Zhytnik
 * @since 09.06.2016
 */
@Controller
@RequestMapping("/types")
@SuppressWarnings("UnusedParameters")
public class TypeController {

    @Autowired
    private TypDtoService service;

    @ResponseBody
    @RequestMapping(value = "{id}", method = GET)
    public TypeDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ResponseBody
    @RequestMapping(method = POST)
    public Long create(@Valid @RequestBody TypeDto type, BindingResult result) {
        service.create(type);
        return type.getId();
    }

    @ResponseBody
    @RequestMapping(method = GET)
    public List<TypeDto> loadAll() {
        return service.loadAll();
    }

    @ResponseBody
    @RequestMapping(method = GET, params = "isFree")
    public Boolean isFreeName(@RequestParam("isFree") String name) {
        return service.isUniqueName(name);
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = DELETE)
    public HttpStatus remove(@PathVariable Long id) {
        service.remove(id);
        return HttpStatus.OK;
    }

    @ResponseBody
    @RequestMapping(method = PUT)
    public HttpStatus update(@Valid @RequestBody TypeDto type, BindingResult result) {
        if (type.getId() == null) return HttpStatus.BAD_REQUEST;
        service.update(type);
        return HttpStatus.OK;
    }
}
