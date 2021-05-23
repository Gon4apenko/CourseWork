<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product</title>
    <link rel="shortcut icon" href="/img/favicon.png" type="image/x-icon">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">

    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/nav.css">
</head>
<body>
    <%@include file="nav.jsp"%>

    <section class="d-flex justify-content-center full-screen">
        <form action="" method="post" class="content shadow mt-5">
            <h1>Add New Product</h1>
            <hr>
            <ul class="list-unstyled w-25">
                <li class="mb-3">
                    <input class="form-control" required type="text" name="name" placeholder="name...">
                </li>
                <li class="mb-3">
                    <input class="form-control" required type="number" step=".01" name="price" placeholder="price...">
                </li>
                <li class="mb-3">
                    <input class="form-control" required type="number" name="amount" placeholder="amount...">
                </li>
                <li class="mb-3">
                    <input class="form-control" required type="text" name="category" placeholder="category...">
                </li>
                <li class="mb-3">
                    <textarea class="form-control" name="description" placeholder="description..."></textarea>
                </li>
                <li>
                    <input type="submit" value="Add Product" class="btn btn-primary">
                </li>
            </ul>
        </form>
    </section>

    <c:if test="${requestScope.error}">
        <%@include file="errorBlock.jsp"%>
    </c:if>

    <%@include file="footer.jsp"%>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>

    <script src="/scripts/nav.js"></script>
</body>
</html>
