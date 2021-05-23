package listeners;

import model.data.impl.*;
import model.services.CatalogService;
import model.services.ProductService;
import model.services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class DataListener implements ServletContextListener{

    public DataListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();

        Database database = new Database();

        DataGenerator.putDataToDatabase(database);

        DaoFactory daoFactory = new DaoFactory(new ImplProductDao(database.getProducts()),
                                                new ImplUserDao(database.getUsers()),
                                                new ImplCatalogDao(database.getCatalog()));

        UserService userService = new UserService(daoFactory.getUserDao());
        sc.setAttribute("userService", userService);

        ProductService productService = new ProductService(daoFactory.getProductDao());
        sc.setAttribute("productService", productService);

        CatalogService catalogService = new CatalogService(daoFactory.getCatalogDao());
        sc.setAttribute("catalogService", catalogService);
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
