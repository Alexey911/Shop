package com.zhytnik.shop.controller;

import com.zhytnik.shop.controller.converter.Converter;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.dto.TypeDto;
import com.zhytnik.shop.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.zhytnik.shop.controller.ResponseUtil.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Alexey Zhytnik
 * @since 09.06.2016
 */
@Controller
@RequestMapping("/types")
public class TypeController {

    @Autowired
    private Converter<DynamicType, TypeDto> converter;

    @Autowired
    private TypeService service;

    @ResponseBody
    @RequestMapping(value = "{id}", method = GET)
    public ResponseEntity<TypeDto> findById(@PathVariable Long id) {
        final DynamicType type = service.findById(id);
        return ok(converter.convert(type));
    }

    @ResponseBody
    @RequestMapping(method = POST)
    public ResponseEntity<Long> create(@Valid @RequestBody TypeDto typeDto,
                                       BindingResult result) {
        if (result.hasErrors()) return badRequest();
        final DynamicType type = converter.convert(typeDto);
        service.create(type);
        return ok(type.getId());
    }

    @ResponseBody
    @RequestMapping(method = GET)
    public List<TypeDto> loadAll() {
        final List<TypeDto> types = newArrayList();
        for (DynamicType type : service.loadAll()) types.add(converter.convert(type));
        return types;
    }

    @ResponseBody
    @RequestMapping(method = GET, params = "name")
    public Boolean isFreeName(@RequestParam String name) {
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
    public HttpStatus update(@Valid @RequestBody TypeDto typeDto,
                             BindingResult result) {
        if (result.hasErrors() || typeDto.getId() == null) return HttpStatus.BAD_REQUEST;
        final DynamicType type = converter.convert(typeDto);
        final DynamicType persisted = service.findById(type.getId());
        fill(persisted, type);
        service.update(type);
        return HttpStatus.OK;
    }

    private void fill(DynamicType from, DynamicType to) {
        to.setName(from.getName());
        to.setFields(from.getFields());
    }
}
