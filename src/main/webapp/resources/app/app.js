'use strict';
var app = angular.module('app', []);

app.service('TypeService', function ($http, $q) {
    var shopHost = 'http://' + window.location.host;
    var typeHost = shopHost + '/types';

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

    this.getTypesTemplate = () => shopHost + '/resources/app/type/types.htm';

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
        $http.post(typeHost, type, config)
            .success(function (data, status) {
                console.log(status, data);
            })
            .error(function (data, status) {
                console.log(status);
            });
    };
    this.loadAll = function (success) {
        var deferred = $q.defer();
        $http.get(typeHost)
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

    this.isUnique = (name) => $http({method: "GET", url: typeHost, params: {"isFree": name}});
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

    $scope.addField = function () {
        $scope.type.fields.push(angular.copy($scope.field));
        $scope.resetField();
    };
    $scope.create = () => TypeService.create(angular.copy($scope.type));
    $scope.loadAll = () => TypeService.loadAll((data) => $scope.types = data);
    $scope.reset = function () {
        $scope.type.fields = [];
        $scope.type.name = "";
        $scope.resetField();
    };
    $scope.resetField = function () {
        $scope.field.name = "";
        $scope.field.required = false;
        $scope.field.type = $scope.primitiveTypes[2];
    };
    $scope.resetField();
    $scope.loadAll();
});

app.directive('unique', function ($q, TypeService) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function ($scope, elem, attrs, ctrl) {
            $scope.$watch(attrs.ngModel, function (name) {
                TypeService.isUnique(name)
                    .then((unique) => ctrl.$setValidity('unique', unique));
            });
        }
    }
});
app.directive('types', function (TypeService) {
    return {
        restrict: 'E',
        replace: false,
        templateUrl: TypeService.getTypesTemplate()
    }
});