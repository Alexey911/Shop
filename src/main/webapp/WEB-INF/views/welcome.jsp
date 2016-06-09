<%--@elvariable id="message" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Hello User</title>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
//            $.ajax({
//                url: 'http://localhost:8082/types/1',
//                success: function(data) {
//                    alert(data);
//                },
//                fail: function(){
//                    alert("fail");
//                }
//            });

//            $.ajax({
//                type: 'POST',
//                url: 'http://localhost:8082/types/',
//                data: {
//                    type: {
//                        name: "something_name"
//                    }
//                },
//                dataType: "json",
//                success: alert("haha")
//            });

            var type = {};
            type.name = "something_name";
//            type.fields = [];
            type.fields = [{
                name : "name",
                order : 1,
                primitiveType : "LONG",
                required : false
            }];

                jQuery.ajax({
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    'type': 'POST',
                    'url': 'http://localhost:8082/types/',
                    'data': JSON.stringify(type),
                    'dataType': 'json',
                    'success': alert("lol")
                });


            function onAjaxSuccess(data) {
                // Здесь мы получаем данные, отправленные сервером и выводим их на экран.
                alert(data);
            }


        });
    </script>
</head>
<body>
<p>Today is ${message}</p>
</body>
</html>