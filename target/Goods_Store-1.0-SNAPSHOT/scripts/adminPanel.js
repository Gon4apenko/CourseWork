document.addEventListener("DOMContentLoaded", () => {
    let addSection = document.querySelector(".add-section-wrapper"),
        updateSection = document.querySelector(".update-section-wrapper"),
        adminAddButton = document.querySelector(".admin-add-btn"),
        adminUpdateButton = document.querySelector(".admin-update-btn"),
        adminPanel = document.querySelector(".admin-panel"),
        adminPanelToggler = document.querySelector(".admin-panel-toggler");

    adminAddButton && adminAddButton.addEventListener("click", () => {
        addSection.classList.remove("d-none");
        addSection.classList.add("d-flex", "justify-content-center", "align-items-center")
    })

    document.querySelectorAll(".close").forEach(item => {
        item.addEventListener("click", (e) => {
            e.currentTarget.parentElement.classList.add("d-none");
        })
    })

    adminUpdateButton && adminUpdateButton.addEventListener("click", () => {
        updateSection.classList.remove("d-none");
        updateSection.classList.add("d-flex", "justify-content-center", "align-items-center")
    })

    adminPanelToggler && adminPanelToggler.addEventListener("click", () => {
        if (!adminPanel.classList.contains("admin-panel-active"))
        {
            adminPanel.classList.add("admin-panel-active");
            adminPanelToggler.innerHTML = ">";
        }
        else
        {
            adminPanel.classList.remove("admin-panel-active");
            adminPanelToggler.innerHTML = "<";
        }
    })
})