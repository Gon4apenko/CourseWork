<%@include file="jstl.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Catalog</title>
    <link rel="shortcut icon" href="/img/favicon.png" type="image/x-icon">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">

    <link rel="stylesheet" href="/styles/nav.css">
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/products.css">
    <link rel="stylesheet" href="/styles/catalog.css">
</head>
<body>
    <%@include file="nav.jsp"%>

    <main class="container d-flex mt-3 full-screen">
        <aside class="main-categories col-2">
            <ul class="list-unstyled">
                <c:forEach items="${requestScope.catalog}" var="category">
                    <li class="category">
                        <a href="/app/catalog/<c:out value="${category.name}" />" class="text-secondary text-decoration-none d-inline-block py-1 px-2"><c:out value="${category.name}" /></a>
                    </li>
                </c:forEach>
                <c:if test="${sessionScope.admin}">
                    <form action="" method="post" class="p-1">
                        <input type="text" name="sectionName" placeholder="Enter new section...">
                        <label class="add-sign" for="add-catalog-section">+</label>
                        <input id="add-catalog-section" class="d-none" type="submit">
                    </form>
                </c:if>
            </ul>
        </aside>
        <section class="product-wrapper col">
            <c:forEach items="${requestScope.products}" var="product">
                <div class="product" data-id="${product.id}">
                    <h1><c:out value="${product.name}"/></h1>
                    <hr>
                    <ul>
                        <li>Price: <c:out value="${product.price}" /></li>
                        <li>Description: <c:out value="${product.description}"/></li>
                    </ul>
                </div>
            </c:forEach>
        </section>
    </main>

    <%@include file="footer.jsp"%>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>

    <script src="/scripts/nav.js"></script>
    <script src="/scripts/products.js"></script>
</body>
</html>
