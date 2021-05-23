document.addEventListener("DOMContentLoaded", () => {
    let updateProduct = document.querySelector(".update-product-wrapper"),
        updateProductIcon = document.querySelector(".update-product svg"),
        orderButton = document.querySelector(".order-btn"),
        orderSection = document.querySelector(".order-section");


    updateProductIcon && updateProductIcon.addEventListener("click", () => {
        updateProduct.classList.remove("d-none");
        updateProduct.classList.add("d-flex", "justify-content-center", "align-items-center");
    })

    document.querySelectorAll(".close").forEach(close => {
        close.addEventListener("click", (e) => {
            e.currentTarget.parentElement.classList.add("d-none");
        })
    })

    orderButton && orderButton.addEventListener("click", () => {
        orderSection.classList.remove("d-none");
        orderSection.classList.add("d-flex", "justify-content-center", "align-items-center");
    })
})