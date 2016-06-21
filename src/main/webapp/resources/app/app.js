'use strict';
var app = angular.module('app', ['ngMessages']);

app.service('TypeService', function ($http) {
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

    this.getPrimitiveTypes = ()=> ['Boolean', 'Float', 'Integer'];

    this.create = function (type) {
        type = angular.copy(type);
        setNativeFieldNames(type);
        return $http.post(typeHost, type).then(response => response.data);
    };
    this.loadAll = () => $http.get(typeHost).then((response) => changeTypesVisibility(response.data));
    this.isUnique = (name) => $http({method: "GET", url: typeHost, params: {"isFree": name}})
        .then((response) => response.data);

    var remove = (id) => $http({method: "DELETE", url: typeHost + '/' + id});

    this.clear = function () {
        $http.get(typeHost).then(function (response) {
            for (var type of response.data) {
                remove(type.id);
            }
        })
    };

    this.remove = (id) => remove(id);
});

app.controller('TypeController', function ($scope, $http, TypeService) {
    $scope.primitiveTypes = TypeService.getPrimitiveTypes();
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
        var type = {};
        type.fields = [];
        type.name = "";
        $scope.type = type;
        resetField();
    };
    var loadAll = () => TypeService.loadAll().then((types) => $scope.types = types);

    $scope.addField = function () {
        $scope.type.fields.push(angular.copy($scope.field));
        resetField();
    };
    $scope.create = function () {
        TypeService.create($scope.type).then(function (id) {
            var type = angular.copy($scope.type);
            type.id = id;
            $scope.types.push(type);
            reset();
        })
    };

    $scope.loadAll = loadAll;
    $scope.reset = reset;
    $scope.resetField = resetField;

    $scope.clear = function () {
        TypeService.clear();
        $scope.types = [];
    };
    
    $scope.onChoice = (type) => $scope.type = type;

    $scope.remove = function () {
        TypeService.remove($scope.type.id).
        then(function () {
            reset();
            loadAll();
        });
    };

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