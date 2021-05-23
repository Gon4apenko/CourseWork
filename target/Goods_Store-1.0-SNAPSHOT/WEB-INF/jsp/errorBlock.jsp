<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="error rounded border border-danger position-absolute top-0 start-0 ms-3 mt-3 p-3"
                                            style="background-color: rgba(255, 0, 0, 0.5)">
    <h1 class="text-center">Error</h1>
    <hr>
    <p>${requestScope.errorMessage}</p>
</div>