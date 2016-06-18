'use strict';
var app = angular.module("app", []);

app.service('TypeService', function ($http) {
    this.HOME = 'http://' + window.location.host;
    this.URL = this.HOME + '/types';

    var nativeTypeName = function (name) {
        var names = {
            'Boolean': 'BOOLEAN',
            'Float': 'DOUBLE',
            'Integer': 'LONG'
        };
        return names[name];
    };
    var visibleTypeName = function (name) {
        var names = {
            'BOOLEAN': 'Boolean',
            'DOUBLE': 'Float',
            'LONG': 'Integer'
        };
        return names[name];
    };

    this.create = function (type) {
        var order = 0;
        for (var field of type.fields) {
            field.type = nativeTypeName(field.type);
            field.order = order++;
        }
        var config = {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };
        $http.post(this.URL, type, config)
            .success(function (data, status) {
                console.log(status, data);
            })
            .error(function (data, status) {
                console.log(status);
            });
    };
    this.loadAll = function (success) {
        $http.get(this.URL)
            .success(function (data, status) {
                if (status == 200) {
                    for (var type of data) {
                        for (var field of type.fields) {
                            field.type = visibleTypeName(field.type);
                        }
                    }
                }
                console.log(status, data);
                success(data);
            })
            .error(function (data, status) {
                console.log(status, data);
            });
    };
    this.isUnique = function (name, success) {
        $http({
            method: "GET",
            url: this.URL + "?isFree=" + name
        }).then(function onSuccess(response) {
            success(response.data);
        }, function onError(response) {
            console.log("error", response);
        });
    };
    this.loadTypesTemplate = function () {
        var request = new XMLHttpRequest();
        request.open('GET', this.HOME + '/resources/types.htm', false);
        request.send();
        return request.responseText;
    }
});

app.controller('TypeController', function ($scope, $http, TypeService) {
    $scope.primitiveTypes = ['Boolean', 'Float', 'Integer'];
    $scope.type = {
        id: null,
        name: "",
        lastChange: null,
        fields: []
    };
    $scope.types = [];
    $scope.isUnique = false;
    $scope.field = {};

    // $scope.$watch('type.name', function (value) {
    //     console.log('change value', value);
    // });

    $scope.addField = function () {
        $scope.type.fields.push(angular.copy($scope.field));
        $scope.resetField();
    };
    $scope.resetField = function () {
        $scope.field.name = "";
        $scope.field.required = false;
        $scope.field.type = $scope.primitiveTypes[2];
    };
    $scope.create = function () {
        TypeService.create(angular.copy($scope.type));
    };
    $scope.loadAll = function () {
        TypeService.loadAll(function (data) {
            $scope.types = data;
        });
    };
    $scope.reset = function () {
        $scope.type.fields = [];
        $scope.type.name = "";
        $scope.resetField();
    };
    $scope.resetField();
    $scope.loadAll();
});

app.directive('types', function (TypeService) {
    return {
        restrict: 'E',
        replace: false,
        template: TypeService.loadTypesTemplate()
    };
});
app.directive('unique', function (TypeService) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function ($scope, elem, attrs, ctrl) {
            $scope.$watch(attrs.ngModel, function (value) {
                TypeService.isUnique(value,
                    (unique) => ctrl.$setValidity('unique', unique));
            });
        }
    }
});