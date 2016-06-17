<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Welcome page</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<body>
<%--@elvariable id="message" type="java.lang.String"--%>
<p>Today is ${message}</p>

<div ng-app="app" ng-controller="TypeController">

    <p>Types</p>
    <ul>
        <li ng-repeat="type in types">
            <span>{{type.name}}</span>
        </li>
    </ul>

    <p>Fields</p>
    <ul>
        <li ng-repeat="field in type.fields">
            <span>{{field.name}}</span>
            <span>({{field.type.name}}, </span>
            <span>{{field.required && 'required' || 'not required'}})</span>
        </li>
    </ul>
    <br>

    <label>
        Enter field name
        <input type="text" ng-model="field.name" name="field.name" required/>
    </label>
    <br>

    <label>
        Is field required
        <input type="checkbox" ng-model="field.required"/>
    </label>
    <br>

    <label>
        Choose field type
        <select ng-model="field.type" ng-options="type.name for type in primitiveTypes" required></select>
    </label>
    <br>

    <button ng-click="addField()">
        Add field
    </button>
    <br>
    <br>

    <label>
        Enter type name
        <input type="text" ng-model="type.name">
    </label>
    <br>
    <button ng-click="create()">Create</button>
    <button ng-click="reset()">Reset</button>
    <button ng-click="loadAll()">Load All</button>
    <br>
    Is valid type: {{isValid}}
</div>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script src="${contextPath}/resources/api.js"></script>
</body>
</html>