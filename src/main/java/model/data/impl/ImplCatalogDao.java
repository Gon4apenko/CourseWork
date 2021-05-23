package model.data.impl;

import model.data.CatalogDao;
import model.entities.Catalog;

import java.util.Collection;

public class ImplCatalogDao implements CatalogDao {

    Catalog catalog;

    public ImplCatalogDao(Catalog catalog) {
        this.catalog = catalog;
    }


    @Override
    public Collection<Catalog.Section> getMainSections() {
        return catalog.getCategories();
    }

    @Override
    public Catalog.Section getByName(String name) {
        for (Catalog.Section category : catalog.getCategories())
        {
            Catalog.Section result = get(name, category);

            if (result != null)
                return result;
        }

        return null;
    }

    private Catalog.Section get(String name, Catalog.Section section)
    {
        if (section.getName().equals(name))
            return section;

        for (Catalog.Section category : section.getChildren())
        {
            Catalog.Section result = get(name, category);

            if (result != null)
                return result;
        }

        return null;
    }

    @Override
    public Catalog.Section findSectionInChildren(String name, Collection<Catalog.Section> children)
    {
        return children.stream().filter(section -> section.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public void insertAfter(Catalog.Section section, Catalog.Section newSection) {
        newSection.setParent(section);
        section.getChildren().add(newSection);
    }

    @Override
    public void insertAfterCatalog(Catalog.Section section) {
        catalog.getCategories().add(section);
    }

    @Override
    public void delete(Catalog.Section section) {
        Catalog.Section parent = section.getParent();

        if (parent != null)
            parent.getChildren().remove(section);
        else
            catalog.getCategories().remove(section);
    }
}
