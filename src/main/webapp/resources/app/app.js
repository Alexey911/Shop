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
    var setNativeFieldNames = function (type) {
        var order = 0;
        for (var field of type.fields) {
            field.type = nativeTypeName(field.type);
            field.order = order++;
        }
    };
    var changeTypesVisibility = function (types) {
        for (var type of types) for (var field of type.fields)
            field.type = visibleTypeName(field.type);
        return types;
    };

    this.getTypesTemplate = () => shopHost + '/resources/app/type/types.htm';

    this.create = function (type) {
        type = angular.copy(type);
        setNativeFieldNames(type);
        var config = {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };
        return $http.post(typeHost, type, config);
    };
    this.loadAll = () => $http.get(typeHost).then((response) => changeTypesVisibility(response.data));
    this.isUnique = (name) => $http({method: "GET", url: typeHost, params: {"isFree": name}})
        .then((response) => response.data);
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

    var resetField = function () {
        var field = $scope.field;
        field.name = "";
        field.required = false;
        field.type = $scope.primitiveTypes[2];
    };
    var reset = function () {
        var type = $scope.type;
        type.fields = [];
        type.name = "";
        resetField();
    };
    var loadAll = () => TypeService.loadAll().then((types) => $scope.types = types);

    $scope.addField = function () {
        $scope.type.fields.push(angular.copy($scope.field));
        resetField();
    };
    $scope.create = function () {
        TypeService.create($scope.type).then(function () {
            $scope.types.push(angular.copy($scope.type));
            reset();
        })
    };

    $scope.loadAll = loadAll;
    $scope.reset = reset;
    $scope.resetField = resetField;

    resetField();
    loadAll();
});

app.directive('unique', function (TypeService) {
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