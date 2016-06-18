<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Ultimate Shop</title>
    <style>
        input.ng-invalid {
            background-color: pink;
        }

        input.ng-valid {
            background-color: lightgreen;
        }
    </style>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
    <script src="${contextPath}/resources/api.js"></script>
</head>
<body ng-app="app">
<div ng-controller="TypeController">
    <h2>Types</h2>
    <types></types>

    <h3>Fields</h3>
    <ul>
        <li ng-repeat="field in type.fields">
            <span>{{field.name}}</span>
            <span>({{field.type}}, </span>
            <span>{{field.required && 'required' || 'not required'}})</span>
        </li>
    </ul>
    <br>

    <div ng-form name="fieldForm">
        <label>
            Enter field name
            <input type="text" ng-model="field.name" name="name" required ng-minlength="3"/>
        </label>
        <br>

        <label>
            Is field required
            <input type="checkbox" ng-model="field.required"/>
        </label>
        <br>

        <label>
            Choose field type
            <select ng-model="field.type" ng-options="type for type in primitiveTypes" required></select>
        </label>
        <br>

        <button ng-click="addField()" ng-disabled="!fieldForm.name.$valid">
            Add field
        </button>
    </div>
    <br>
    <br>

    <div ng-form name="typeForm">
        <label>
            Enter type name
            <input type="text" name="name" ng-model="type.name" required ng-minlength="5" unique>
        </label>
        <br>
        <button ng-click="create()" ng-disabled="!typeForm.name.$valid">Create</button>
    </div>
    <button ng-click="reset()">Reset</button>
    <button ng-click="loadAll()">Load All</button>
</div>
</body>
</html>