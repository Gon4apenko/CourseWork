<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Filtered goods</title>
    <link rel="shortcut icon" href="/img/favicon.png" type="image/x-icon">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">

    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/nav.css">
    <link rel="stylesheet" href="/styles/products.css">
    <link rel="stylesheet" href="/styles/adminPanel.css">
    <link rel="stylesheet" href="/styles/form.css">
</head>
<body>
    <%@include file="nav.jsp"%>

    <nav class="category-navigation">
        <c:set var="path" scope="request" value="/app/catalog" />

        <a href="${requestScope.path}">Catalog</a> &#8594;
        <c:forEach items="${requestScope.categories}" var="categoryName" varStatus="loop">
            <c:set var="path" value="${requestScope.path}/${categoryName}" scope="request" />

            <a href="${requestScope.path}"><c:out value="${categoryName}" /></a>
            <c:if test="${loop.index != fn:length(requestScope.categories) - 1}">&#8594;</c:if>
        </c:forEach>
    </nav>

    <section class="product-wrapper full-screen">
        <c:forEach items="${requestScope.products}" var="product">
            <div class="product" data-id="${product.id}">
                <h3><c:out value="${product.name}"/></h3>
                <hr>
                <ul>
                    <li>Price: <c:out value="${product.price}" /></li>
                    <li>Description: <c:out value="${product.description}"/></li>
                </ul>
            </div>
        </c:forEach>
    </section>

    <c:if test="${sessionScope.admin}">
        <%@include file="adminPanel.jsp"%>
    </c:if>

    <%@include file="footer.jsp"%>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>

    <script src="/scripts/nav.js"></script>
    <script src="/scripts/adminPanel.js"></script>
    <script src="/scripts/products.js"></script>
    <script> </script>
</body>
</html>
