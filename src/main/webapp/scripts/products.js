document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".product").forEach((product) => {
        product.addEventListener("click", (e) => {
            e.stopPropagation();

            window.location.href = "/app/product/" + e.currentTarget.dataset.id
        })
    })
})