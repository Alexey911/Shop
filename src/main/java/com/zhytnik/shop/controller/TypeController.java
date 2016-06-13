package com.zhytnik.shop.controller;

import com.zhytnik.shop.dto.TypeDto;
import com.zhytnik.shop.service.TypDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    private TypDtoService service;

    @ResponseBody
    @RequestMapping(value = "{id}", method = GET)
    public TypeDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ResponseBody
    @RequestMapping(method = POST)
    public ResponseEntity<Long> create(@Valid @RequestBody TypeDto type,
                                       BindingResult result) {
        if (result.hasErrors()) return badRequest();
        service.create(type);
        return ok(type.getId());
    }

    @ResponseBody
    @RequestMapping(method = GET)
    public List<TypeDto> loadAll() {
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
    public HttpStatus update(@Valid @RequestBody TypeDto type,
                             BindingResult result) {
        if (result.hasErrors() || type.getId() == null) return HttpStatus.BAD_REQUEST;
        service.update(type);
        return HttpStatus.OK;
    }
}
