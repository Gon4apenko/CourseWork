package controllers;

import model.entities.*;
import model.services.CatalogService;
import model.services.ProductService;
import model.services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;

@WebServlet(name = "FrontControllerServlet", value = "/app/*")
public class FrontControllerServlet extends HttpServlet {

    UserService userService;
    ProductService productService;
    CatalogService catalogService;

    boolean error = false;
    String errorMessage = null;

    @Override
    public void init() throws ServletException {
        ServletContext sc = getServletContext();

        userService = (UserService) sc.getAttribute("userService");
        productService = (ProductService) sc.getAttribute("productService");
        catalogService = (CatalogService) sc.getAttribute("catalogService");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        if (!path.startsWith("/app"))
        {
            response.sendError(404);
            return;
        }

        if (path.matches("/app/?"))
        {
            chooseRedirection(request, response);
            return;
        }
        else if (path.matches("/app/login/?"))
        {
            if (method.equals("GET"))
                processLoginGet(request, response);
            else if (method.equals("POST"))
                processLoginPost(request, response);
            else
                response.sendError(405);

            return;
        }
        else if (path.matches("/app/logout/?"))
        {
            processLogout(request, response);
            return;
        }
        else if (path.matches("/app/registration/?"))
        {
            if (method.equals("GET"))
                processRegistrationGet(request, response);
            else if (method.equals("POST"))
                processRegistrationPost(request, response);
            else
                response.sendError(405);

            return;
        }
        else if (path.matches("/app/catalog/?.*"))
        {
            if (method.equals("GET"))
                processCatalogGet(path, request, response);
            else if (method.equals("POST"))
            {
                if ("put".equals(request.getParameter("method")))
                    processCatalogPut(path, request, response);
                else if ("delete".equals(request.getParameter("method")))
                    processCatalogDelete(path, request, response);
                else
                    processCatalogPost(path, request, response);
            }
            else
                response.sendError(405);

            return;
        }
        else if (path.matches("/app/product/\\d+"))
        {
            if (method.equals("GET"))
                processProductGet(path, request, response);
            else if (method.equals("POST"))
                if ("put".equals(request.getParameter("method")))
                    processProductPut(path, request, response);
                else if ("delete".equals(request.getParameter("method")))
                    processProductDelete(path, request, response);
                else
                    processProductPost(path, request, response);
            else
                response.sendError(405);

            return;
        }
        else if (path.matches("/app/addproduct/?"))
        {
            if (method.equals("GET"))
                processAddProductGet(request, response);
            else if (method.equals("POST"))
                processAddProductPost(request, response);
            else
                response.sendError(405);

            return;
        }

        response.sendError(404);
    }

    protected void chooseRedirection(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (userService.isLoggedIn(request.getSession()))
        {
            response.sendRedirect("/app/catalog");
        }
        else
        {
            response.sendRedirect("/app/login");
        }
    }

    protected void processLoginGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (userService.isLoggedIn(request.getSession()))
        {
            response.sendRedirect("/app/catalog");
            return;
        }

        if (error)
            createError(request);


        getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

    protected void processLoginPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().invalidate();

        String username = request.getParameter("username");

        User userByUsername = userService.getUserByUsername(username);

        if (userByUsername == null)
        {
            error = true;
            errorMessage = "Such user doesn't exist!";
            response.sendRedirect("/app/login");
            return;
        }

        String password = request.getParameter("password");

        User userByPassword = userService.getUserByPassword(password);

        if (userByPassword == null || !userByPassword.equals(userByUsername))
        {
            error = true;
            errorMessage = "Wrong password!";
            response.sendRedirect("/app/login");
            return;
        }

        request.getSession().setAttribute("user", userByUsername);
        if (userByUsername instanceof Admin) request.getSession().setAttribute("admin", true);
        response.sendRedirect("/app/catalog");
    }

    protected void processLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();

        response.sendRedirect("/app/login");
    }

    protected void processRegistrationGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (error)
            createError(request);

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(request, response);
    }

    protected void processRegistrationPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User newUser;

        try
        {
            newUser = userService.createUserAndInsert(request.getParameter("username"),
                    request.getParameter("password"),
                    request.getParameter("email"),
                    request.getParameter("admin") != null);

        }catch (IllegalArgumentException e)
        {
            error = true;
            errorMessage = e.getMessage();
            response.sendRedirect("/app/registration");
            return;
        }

        request.getSession().invalidate();
        if (newUser instanceof Admin) request.getSession().setAttribute("admin", true);
        request.getSession().setAttribute("user", newUser);
        response.sendRedirect("/app/catalog");
    }

    protected void processCatalogGet(String URI, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (!userService.isLoggedIn(request.getSession()))
        {
            response.sendRedirect("/app/login");
            return;
        }

        String catalogContext = catalogService.getCatalogContext(URI).replace("%20", " ");

        request.setAttribute("catalog", catalogService.getMainSections());
        request.setAttribute("catalogService", catalogService);

        if (catalogContext.equals(""))
        {
            request.setAttribute("products", productService.getAllProducts());
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/catalog.jsp").forward(request, response);
            return;
        }

        String[] categories = catalogService.getCategories(catalogContext);

        Catalog.Section section;

        try
        {
            section = catalogService.followToSection(categories);
        }
        catch (IllegalArgumentException e)
        {
            response.sendError(404);
            return;
        }

        TreeSet<Product> products = new TreeSet<>(Comparator.comparing(Product::getName));

        for (Catalog.Section category : catalogService.collectFromSection(section))
        {
            products.addAll(productService.getProductCollectionBySection(category));
        }

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/products.jsp").forward(request, response);
    }

    protected void processCatalogPut(String URI, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        if (!userService.isLoggedInAsAdmin(session))
        {
            response.sendError(403);
            return;
        }

        String catalogContext = catalogService.getCatalogContext(URI).replace("%20", " ");

        if (catalogContext.equals(""))
        {
            response.sendError(405);
            return;
        }

        String newName = request.getParameter("sectionName");

        if (newName == null || newName.isEmpty() || newName.isBlank())
        {
            response.sendError(400);
            return;
        }

        String[] categories = catalogService.getCategories(catalogContext);

        Catalog.Section section;

        try
        {
            section = catalogService.followToSection(categories);
        }
        catch (IllegalArgumentException e)
        {
            response.sendError(404);
            return;
        }

        section.setName(newName);

        response.sendRedirect(("/app/catalog/" + catalogContext).replaceFirst(categories[categories.length - 1] + "$", newName));
    }

    protected void processCatalogPost(String URI, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        if (!userService.isLoggedInAsAdmin(session))
        {
            response.sendError(403);
            return;
        }

        String catalogContext = catalogService.getCatalogContext(URI).replace("%20", " ");

        String sectionName = request.getParameter("sectionName");

        if (sectionName == null || sectionName.isEmpty() || sectionName.isBlank())
        {
            response.sendError(400);
            return;
        }

        Catalog.Section newSection = new Catalog.Section(sectionName);

        if (catalogContext.equals(""))
        {
            catalogService.insertAfterCatalog(newSection);
        }
        else
        {
            String[] categories = catalogService.getCategories(catalogContext);

            Catalog.Section section;

            try
            {
                section = catalogService.followToSection(categories);
            }
            catch (IllegalArgumentException e)
            {
                response.sendError(404);
                return;
            }

            catalogService.insertAfterSection(section, newSection);
        }

        response.sendRedirect(URI);
    }

    protected void processCatalogDelete(String URI, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        if (!userService.isLoggedInAsAdmin(session))
        {
            response.sendError(403);
            return;
        }

        String catalogContext = catalogService.getCatalogContext(URI).replace("%20", " ");

        if (catalogContext.equals(""))
        {
            response.sendError(405);
            return;
        }

        String[] categories = catalogService.getCategories(catalogContext);

        Catalog.Section section;

        try
        {
            section = catalogService.followToSection(categories);
        }
        catch (IllegalArgumentException e)
        {
            response.sendError(404);
            return;
        }

        catalogService.safeDelete(section, productService);

        response.sendRedirect("/app/catalog");
    }

    protected void processProductGet(String URI, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (!userService.isLoggedIn(request.getSession()))
        {
            response.sendRedirect("/app/login");
            return;
        }

        request.setAttribute("catalog", catalogService.getMainSections());

        int productId;

        try
        {
            productId = productService.getProductIdFromPath(URI);
        }catch (IllegalArgumentException e)
        {
            response.sendError(404);
            return;
        }

        Product product = productService.getProductById(productId);

        if (product == null)
        {
            response.sendError(404);
            return;
        }

        request.setAttribute("product", product);

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/product.jsp").forward(request, response);
    }

    protected void processProductPost(String URI, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!userService.isLoggedInAsClient(request.getSession()))
        {
            response.sendError(403);
            return;
        }

        int productId;

        try
        {
            productId = productService.getProductIdFromPath(URI);
        }catch (IllegalArgumentException e)
        {
            response.sendError(404);
            return;
        }

        Product product = productService.getProductById(productId);

        if (product == null)
        {
            response.sendError(404);
            return;
        }

        if (product.getAmount() < 1)
        {
            response.sendError(400, "Product is out of stock!");
            return;
        }

        product.setAmount(product.getAmount() - 1);

        response.sendRedirect(URI);
    }

    protected void processProductPut(String URI, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (!userService.isLoggedInAsAdmin(request.getSession()))
        {
            response.sendError(403);
            return;
        }

        int productId;

        try
        {
            productId = productService.getProductIdFromPath(URI);
        }catch (IllegalArgumentException e)
        {
            response.sendError(404);
            return;
        }

        Product product = productService.getProductById(productId);

        if (product == null)
        {
            response.sendError(404);
            return;
        }

        String productName = request.getParameter("name");

        if (productName != null)
        {
            if (productName.isEmpty() || productName.isBlank())
            {
                response.sendError(400, "Product name is invalid!");
                return;
            }

            product.setName(productName);
        }

        String productPrice = request.getParameter("price");

        if (productPrice != null)
        {
            try {
                product.setPrice(Double.parseDouble(productPrice));
            }catch (NumberFormatException e)
            {
                response.sendError(400, "Price is invalid");
                return;
            }
        }

        String productDescription = request.getParameter("description");

        if (productDescription != null)
        {
            product.setDescription(productDescription);
        }

        String productAmount = request.getParameter("amount");

        if (productAmount != null)
        {
            try {
                int amount = Integer.parseInt(productAmount);

                if (amount < 0)
                {
                    response.sendError(400, "Invalid amount!");
                    return;
                }

                product.setAmount(amount);
            }catch (NumberFormatException e)
            {
                response.sendError(400);
                return;
            }
        }

        String productCategory = request.getParameter("category");

        if (productCategory != null)
        {
            Catalog.Section category = catalogService.getSectionByName(productCategory);

            if (category == null)
            {
                response.sendError(400);
                return;
            }

            product.setCategory(category);
        }

        response.sendRedirect(URI);
    }

    protected void processProductDelete(String URI, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!userService.isLoggedInAsAdmin(request.getSession()))
        {
            response.sendError(403);
            return;
        }

        int productId;

        try
        {
            productId = productService.getProductIdFromPath(URI);
        }catch (IllegalArgumentException e)
        {
            response.sendError(404);
            return;
        }

        Product product = productService.getProductById(productId);

        if (product == null)
        {
            response.sendError(404);
            return;
        }

        productService.deleteProduct(product);

        response.sendRedirect("/app/catalog");
    }

    protected void processAddProductGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (!userService.isLoggedInAsAdmin(request.getSession()))
        {
            response.sendError(403);
            return;
        }

        request.setAttribute("catalog", catalogService.getMainSections());

        if (error)
            createError(request);

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/addproduct.jsp").forward(request, response);
    }

    protected void processAddProductPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (!userService.isLoggedInAsAdmin(request.getSession()))
        {
            response.sendError(403);
            return;
        }

        int id;

        try {
            id = productService.insertProductWithId(request.getParameterMap(), catalogService);
        }catch (IllegalArgumentException e)
        {
            error = true;
            errorMessage = e.getMessage();
            response.sendRedirect("/app/addproduct");
            return;
        }

        response.sendRedirect("/app/product/" + id);
    }

    protected void createError(HttpServletRequest request) throws IOException, ServletException
    {
        request.setAttribute("error", true);
        request.setAttribute("errorMessage", errorMessage);
        error = false;
    }
}
