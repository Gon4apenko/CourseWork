<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<aside class="admin-panel border p-3 rounded">
    <div class="admin-panel-toggler"><</div>
    <div>
        <button class="admin-add-btn btn btn-secondary d-inline-block w-100">Add Section</button>
    </div>
    <form action="" class="admin-remove-section" method="post">
        <input type="hidden" name="method" value="delete">
        <input type="submit" class="btn btn-secondary d-inline-block w-100" value="Remove This Section">
    </form>
    <div>
        <button class="admin-update-btn btn btn-secondary d-inline-block w-100">Update This Section</button>
    </div>
</aside>

<section class="admin-section add-section-wrapper d-none position-absolute top-0 bottom-0 start-0 end-0">
    <div class="close close-add">&times;</div>
    <form action="" method="post" class="form bg-light">
        <h3>Enter name of section</h3>
        <hr>
        <div class="form-group">
            <input class="form-control" type="text" name="sectionName">
        </div>
        <div class="form-group">
            <input class="btnSubmit" type="submit" value="Add Section">
        </div>
    </form>
</section>

<section class="admin-section update-section-wrapper d-none position-absolute top-0 bottom-0 start-0 end-0">
    <div class="close close-update">&times;</div>
    <form action="" method="post" class="form bg-light">
        <input type="hidden" name="method" value="put">
        <h3>Rename this section</h3>
        <hr>
        <div class="form-group">
            <input class="form-control" type="text" name="sectionName">
        </div>
        <div class="form-group">
            <input class="btnSubmit" type="submit" value="Rename Section">
        </div>
    </form>
</section>