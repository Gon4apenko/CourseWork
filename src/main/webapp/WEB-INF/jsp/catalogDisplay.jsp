<%@include file="jstl.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${requestScope.items.size() > 0}">
    <ul class="dropdown-menu <c:out value='${submenu}' default=''/> " aria-labelledby="navbarDropdown">
        <c:set var="submenu" value="submenu" scope="request"/>

        <c:forEach items="${requestScope.items}" var="category">
            <c:set var="items" value="${category.children}" scope="request"/>
            <c:set var="path" value="${requestScope.path}/${category.name}" scope="request" />

            <li><a href="${requestScope.path}" class="dropdown-item"><c:out value="${category.name}" /></a>
                <jsp:include page="catalogDisplay.jsp"/>
            </li>

            <c:set var="toReplace" value="/${category.name}" />
            <c:set var="path" value="${fn:replace(requestScope.path, toReplace, '')}" scope="request" />
        </c:forEach>
    </ul>
</c:if>


