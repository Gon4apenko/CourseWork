<%@include file="jstl.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="shortcut icon" href="/img/favicon.png" type="image/x-icon">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">

    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/form.css">
</head>
<body>
    <main class="d-flex justify-content-center align-items-center full-screen">
        <form action="" method="post" class="form">
            <h3>Log in</h3>
            <div class="form-group">
                <input type="text" required class="form-control" name="username" placeholder="username">
            </div>
            <div class="form-group">
                <input type="password" required class="form-control" name="password" placeholder="password">
            </div>
            <div class="form-group">
                <input type="submit" class="btnSubmit" value="Log in">
            </div>
            <div class="form-group">
                <a href="/app/registration" class="redirect">Sign up!</a>
            </div>
        </form>
    </main>


    <c:if test="${requestScope.error}">
        <%@include file="errorBlock.jsp"%>
    </c:if>

    <%@include file="footer.jsp"%>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>
</body>
</html>
