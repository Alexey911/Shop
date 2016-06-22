'use strict';

var shopHost = 'http://' + window.location.host;
var app = angular.module('app', ['ngMessages']);

app.service('TypeService', function ($http) {
    var typeHost = shopHost + '/types';

    var nativeTypeName = function (name) {
        var names = {
            'Boolean': 'BOOLEAN',
            'Float': 'DOUBLE',
            'Integer': 'LONG',
            'MultiString': 'STRING'
        };
        return names[name];
    };
    var visibleTypeName = function (name) {
        var names = {
            'BOOLEAN': 'Boolean',
            'DOUBLE': 'Float',
            'LONG': 'Integer',
            'STRING': 'MultiString'
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

    this.getPrimitiveTypes = ()=> ['Boolean', 'Float', 'Integer', 'MultiString'];

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
    $scope.languages = ['ru', 'en'];
    $scope.type = {
        id: null,
        name: "",
        lastChange: null,
        fields: []
    };
    $scope.types = [];
    $scope.isUnique = false;
    $scope.field = {};
    $scope.translation = {};

    var resetField = function () {
        var field = $scope.field;
        field.name = "";
        field.required = false;
        field.type = $scope.primitiveTypes[2];
        field.description = {};
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
        var field = angular.copy($scope.field);
        var translation = {};
        translation[field.description.language] = field.description.translate;
        field.description = {'translations': translation};
        $scope.type.fields.push(field);
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
        TypeService.remove($scope.type.id).then(function () {
            reset();
            loadAll();
        });
    };

    $scope.description = function (field) {
        var translations = field.description.translations;
        for (var locale in translations)
            if (translations.hasOwnProperty(locale)) {
                return translations[locale];
            }
        return 'not found';
    };

    resetField();
    loadAll();
});

app.controller('SystemController', function ($scope, $http, $interval) {
    $scope.system = {};
    var update = () => $http.get(shopHost + '/system')
        .then((response) => $scope.system = response.data);
    // $interval(update, 400);
});

app.service('ProductService', function ($http) {
    var productHost = shopHost + '/products';

    this.loadAll = () => $http.get(productHost).then((response) => (response.data));

    this.create = (product) => $http.post(productHost, product).then(response => response.data);
});

app.controller('ProductController', function ($scope, ProductService) {
    $scope.products = [];

    var product = {};

    $scope.product = product;


    product.id = 5;
    product.type = 4025;
    product.shortName = 'Short product name';
    product.title = {"id": 1047, "translations": {"ru": "Заголовок"}};
    product.description = {"id": 1047, "translations": {"ru": "Описание"}};
    product.textField = {"id": 1047, "translations": {"ru": "Описание"}};

    $scope.loadAll = () => ProductService.loadAll().then((products) => $scope.products = products);

    $scope.create = () => ProductService.create($scope.product);

    $scope.create();
    $scope.loadAll();
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