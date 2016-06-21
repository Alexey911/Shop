<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Ultimate Shop</title>
    <link rel="stylesheet" type="text/css" href="${context}/resources/css/main.css">
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
        <pre>{{ typeFrom.name.$error | json }}</pre>
        <div ng-messages="typeForm.name.$error" style="color:maroon" role="alert">
            <div ng-message="required">You did not enter a field</div>
            <div ng-message="minlength">Your field is too short</div>
        </div>
        <br>
        <button ng-click="create()" ng-disabled="!typeForm.name.$valid">Create</button>
    </div>
    <button ng-click="reset()">Reset</button>
    <button ng-click="update()">Update</button>
    <button ng-click="remove()">Remove</button>
    <button ng-click="loadAll()">Load All</button>
    <button ng-click="clear()">Clear</button>
</div>

<script src="${context}/resources/libraries/angular/angular.js"></script>
<script src="${context}/resources/libraries/angular-messages/angular-messages.js"></script>
<script src="${context}/resources/app/app.js"></script>
</body>
</html>