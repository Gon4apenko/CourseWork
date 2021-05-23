<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Review</title>
    <link rel="shortcut icon" href="/img/favicon.png" type="image/x-icon">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">

    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/nav.css">
    <link rel="stylesheet" href="/styles/adminPanel.css">
    <link rel="stylesheet" href="/styles/product.css">
    <link rel="stylesheet" href="/styles/form.css">
</head>
<body>
    <%@include file="nav.jsp"%>

    <main class="full-screen">
        <section class="product-wrapper d-flex justify-content-center">
            <div class="product-content mt-5 content shadow position-relative">
                <c:if test="${sessionScope.admin}">
                    <div class="toolbar d-flex">
                        <div class="update-product">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
                                <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                            </svg>
                        </div>
                        <form action="" class="delete-product" method="post">
                            <input type="hidden" name="method" value="delete">
                            <label for="delete-product">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                                    <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                    <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                                </svg>
                            </label>
                            <input id="delete-product" type="submit" class="d-none">
                        </form>
                    </div>
                </c:if>
                <div class="product-fields">
                    <div class="product-field row">
                        <span class="col-2">Name:</span><span class="col"><c:out value="${requestScope.product.name}" /></span>
                    </div>
                    <div class="product-field row">
                        <span class="col-2">Price:</span><span class="col"><c:out value="${requestScope.product.price}" /></span>
                    </div>
                    <div class="product-field row">
                        <span class="col-2">Description:</span><span class="col"><c:out value="${requestScope.product.description}" /></span>
                    </div>
                </div>
                <c:if test="${!sessionScope.admin}">
                    <div class="d-flex justify-content-end">
                        <c:choose>
                            <c:when test="${requestScope.product.amount > 0}">
                                <button class="btn order-btn btn-primary">Order</button>
                            </c:when>
                            <c:otherwise>
                                <button tabindex="-1" class="btn disabled btn-primary">Out of stock</button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </div>
        </section>

        <section class="admin-section update-product-wrapper position-absolute top-0 bottom-0 start-0 end-0 d-none">
            <div class="close close-update">&times;</div>
            <form action="" method="post" class="form bg-light">
                <input type="hidden" name="method" value="put">
                <h3>Update product</h3>
                <hr>
                <div class="form-group">
                    <input class="form-control" type="text" name="name" value="<c:out value="${requestScope.product.name}" />">
                </div>
                <div class="form-group">
                    <input class="form-control" type="number" step=".01" name="price" value="<c:out value="${requestScope.product.price}" />">
                </div>
                <div class="form-group">
                    <input class="form-control" type="number" name="amount" value="<c:out value="${requestScope.product.amount}" />">
                </div>
                <div class="form-group">
                    <input class="form-control" type="text" name="category" value="<c:out value="${requestScope.product.category.name}" />">
                </div>
                <div class="form-group">
                    <textarea class="form-control" name="description"><c:out value="${requestScope.product.description}" /></textarea>
                </div>
                <div class="form-group">
                    <input class="btnSubmit" type="submit" value="Apply">
                </div>
            </form>
        </section>

        <section class="order-section position-absolute top-0 bottom-0 start-0 end-0 d-none">
            <div class="close close-update">&times;</div>
            <form action="" method="post" class="form bg-light rounded shadow">
                <h3>Confirm your order</h3>
                <hr>
                <p>You can confirm your order by clicking on the button below</p>
                <p>We'll connect with you via email(${sessionScope.user.email})</p>
                <div class="text-end">
                    <input type="submit" class="btn btn-primary" value="Confirm">
                </div>
            </form>
        </section>
    </main>

    <%@include file="footer.jsp"%>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>

    <script src="/scripts/nav.js"></script>
    <script src="/scripts/product.js"></script>
</body>
</html>
