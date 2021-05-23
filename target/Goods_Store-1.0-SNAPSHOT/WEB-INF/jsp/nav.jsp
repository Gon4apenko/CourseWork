<%@include file="jstl.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/app/catalog">Goods Store</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-5 me-auto mb-2 mb-lg-0 w-100">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Catalog
                    </a>
                    <c:set var="items" value="${requestScope.catalog}" scope="request" />
                    <c:set var="path" value="/app/catalog" scope="request" />
                    <%@include file="catalogDisplay.jsp"%>
                </li>
                <c:if test="${sessionScope.admin}">
                    <li class="nav-item">
                        <a href="/app/addproduct" class="nav-link">Add Product</a>
                    </li>
                </c:if>
                <li class="nav-item ms-0 ms-md-auto">
                    <a href="/app/logout" class="nav-link">Log out</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
