package com.zhytnik.shop.controller;

import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.zhytnik.shop.controller.ResponseUtil.badRequest;
import static com.zhytnik.shop.controller.ResponseUtil.send;
import static com.zhytnik.shop.domain.DomainObjectUtil.isUnknown;
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
    private TypeService service;

    @ResponseBody
    @RequestMapping(value = "{id}", method = GET)
    public ResponseEntity<DynamicType> findById(@PathVariable Long id) {
        return send(service.findById(id));
    }

    @ResponseBody
    @RequestMapping(method = POST)
    public ResponseEntity<Long> create(@Valid @RequestBody DynamicType type,
                                       BindingResult result) {
        if (result.hasErrors()) return badRequest();
        service.create(type);
        return ok(type.getId());
    }

    @ResponseBody
    @RequestMapping(method = GET)
    public List<DynamicType> loadAll() {
        return service.loadAll();
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
    public HttpStatus update(@Valid @RequestBody DynamicType type,
                             BindingResult result) {
        if (result.hasErrors() || isUnknown(type)) return HttpStatus.BAD_REQUEST;
        final DynamicType persisted = service.findById(type.getId());
        if (persisted == null) return HttpStatus.NOT_FOUND;
        fill(persisted, type);
        service.update(type);
        return HttpStatus.OK;
    }

    private void fill(DynamicType from, DynamicType to) {
        to.setName(from.getName());
        to.setFields(from.getFields());
    }
}
