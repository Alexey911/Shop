'use strict';
var App = {};
App.host = window.location.host;
App.api = {};
App.api.types = {};
App.api.types.path = 'http://' + App.host + '/types';

App.api.types.createType = function () {
    return {
        name: null,
        fields: []
    };
};

App.api.types.createField = function () {
    return {
        name: null,
        order: null,
        primitiveType: null,
        required: false
    };
};

App.api.types.load = function (id, success, fail, async = false) {
    let result = undefined;

    function withDateTransform(type) {
        type.changeDate = new Date(type.changeDate);
        return type;
    }

    const url = this.path + '/' + id;
    let onSuccess;
    if (success) {
        onSuccess = (data) => success(withDateTransform(data));
    } else {
        onSuccess = (data) => result = withDateTransform(data);
    }
    const onFail = fail || (() => console.log("fail load Type with id=" + id));
    App.utils.get(url, onSuccess, onFail, async);
    return result;
};

App.api.types.create = function (type, success, fail, async = true) {
    let result = undefined;
    const onFail = fail || (() => console.log("fail create Type " + type));
    const onSuccess = success || ((data) => result = data);
    App.utils.post(this.path, JSON.stringify(type), onSuccess, onFail, async);
    return result;
};

App.api.types.remove = function (id, success, fail, async = true) {
    const url = this.path + '/' + id;
    const onFail = fail || (() => console.log("fail remove Type " + type));
    App.utils.delete(url, id, success, onFail, async);
    if (!async) return true;
};


App.utils = {};
App.utils.get = function (url, success, fail, async) {
    $.ajax({
        async: async,
        type: 'GET',
        url: url,
        success: success,
        fail: fail
    });
};

App.utils.post = function (url, data, success, fail, async) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        async: async,
        type: 'POST',
        url: url,
        data: data,
        dataType: 'json',
        success: success,
        fail: fail
    });
};

App.utils.delete = function (url, data, success, fail, async) {
    $.ajax({
        async: async,
        type: 'DELETE',
        url: url,
        data: data,
        dataType: 'json',
        success: success,
        fail: fail
    });
};