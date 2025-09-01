<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>JSON 로그인</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    $(document).ready(function(){
        $("#loginBtn").click(function(){
            $.ajax({
                url: "/api/login.do",
                type: "POST",
                data: JSON.stringify({
                    email: $("#email").val(),
                    password: $("#password").val()
                }),
                contentType: "application/json; charset=UTF-8",
                dataType: "json",
                success: function(result){
                    if(result.success){
                        alert("로그인 성공: " + result.email);
                    }else{
                        alert("로그인 실패");
                    }
                },
                error: function(){
                    alert("서버 오류");
                }
            });
        });
    });
    </script>
</head>
<body>
    <h2>로그인</h2>
    아이디: <input type="text" id="email"><br>
    비밀번호: <input type="password" id="password"><br>
    <button id="loginBtn">로그인</button>
</body>
</html>
