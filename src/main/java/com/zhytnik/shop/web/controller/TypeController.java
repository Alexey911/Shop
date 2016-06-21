package com.zhytnik.shop.web.controller;

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
class TypeController {

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
        return service.create(type);
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

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = DELETE)
    public void remove(@PathVariable Long id) {
        service.remove(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = PUT)
    public void update(@PathVariable Long id, @Valid @RequestBody TypeDto type, BindingResult result) {
        type.setId(id);
        service.update(type);
    }
}
