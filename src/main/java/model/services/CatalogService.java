package model.services;

import model.data.CatalogDao;
import model.entities.Catalog;
import model.entities.Product;

import java.util.*;

public class CatalogService {

    CatalogDao catalogDao;

    public CatalogService(CatalogDao catalogDao)
    {
        this.catalogDao = catalogDao;
    }

    public Catalog.Section findSectionInCategory(String name, Collection<Catalog.Section> categoryChildren)
    {
        return catalogDao.findSectionInChildren(name, categoryChildren);
    }

    public Collection<Catalog.Section> collectFromSection(Catalog.Section section)
    {
        if (section == null) return null;

        ArrayList<Catalog.Section> collection = new ArrayList<>();

        collection.add(section);

        Collection<Catalog.Section> children = section.getChildren();

        for (Catalog.Section child : children)
        {
            collection.addAll(collectFromSection(child));
        }

        return collection;
    }

    public String[] getCategories(String path)
    {
        return path.split("/");
    }

    public String getCatalogContext(String URI)
    {
        if (!URI.startsWith("/app/catalog"))
            throw new IllegalArgumentException();

        if (URI.endsWith("/"))
        {
            URI = URI.substring(0, URI.length() - 1);
        }

        return URI.replaceFirst("/app/catalog/?", "");
    }

    public Collection<Catalog.Section> getMainSections()
    {
        return catalogDao.getMainSections();
    }

    public Catalog.Section getSectionByName(String name)
    {
        return catalogDao.getByName(name);
    }

    public Catalog.Section followToSection(String[] chain)
    {
        Collection<Catalog.Section> siblings = getMainSections();

        Catalog.Section section = null;

        for (String category : chain)
        {
            section = findSectionInCategory(category, siblings);

            if (section == null)
            {
                throw new IllegalArgumentException();
            }

            siblings = section.getChildren();
        }

        return section;
    }

    public void insertAfterCatalog(Catalog.Section section)
    {
        catalogDao.insertAfterCatalog(section);
    }

    public void insertAfterSection(Catalog.Section section, Catalog.Section newSection)
    {
        catalogDao.insertAfter(section, newSection);
    }

    public void safeDelete(Catalog.Section category, ProductService productService)
    {
        for (Catalog.Section section : collectFromSection(category))
        {
            for (Product product : productService.getProductCollectionBySection(section))
            {
                product.setCategory(category.getParent());
            }
        }

        catalogDao.delete(category);
    }

    public boolean isSectionNameValid(String sectionName)
    {
        return !sectionName.contains("/") && !sectionName.contains("%20");
    }
}
