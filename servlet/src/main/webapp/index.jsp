<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <title>Login</title>
</head>
<style>
    html {
        height: 100%;
    }

    body {
        height: 100%;
    }

    .global-container {
        height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    form {
        padding-top: 10px;
        font-size: 14px;
        margin-top: 30px;
    }

    .card-title {
        font-weight: 300;
    }

    .btn {
        font-size: 14px;
        margin-top: 20px;
    }

    .login-form {
        width: 330px;
        margin: 20px;
    }

</style>
<body>

<div class="global-container">
    <div class="card login-form">
        <div class="card-body">
            <h3 class="card-title text-center"> Login </h3>
            <div class="card-text">
                <form action="LoginServlet" method="post">
                    <div class="form-group">
                        <label for="exampleInputEmail1"> Enter Email address </label>
                        <input type="email" name="email" class="form-control form-control-sm" id="exampleInputEmail1"
                               aria-describedby="emailHelp">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Enter Password </label>
                        <input type="password" name="password" class="form-control form-control-sm" id="exampleInputPassword1">
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Sign in</button>
                </form>
            </div>
        </div>
    </div>
</div>


<div>

</div>
</body>
<script>

    const em = "<%= errorMessage%>";
    console.log(em)
    if (em !== "null") {
        alert("Wrong email or password")
    }
</script>
</html>

