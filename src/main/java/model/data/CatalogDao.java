package model.data;

import model.entities.Catalog;

import java.util.Collection;

public interface CatalogDao {

    Collection<Catalog.Section> getMainSections();

    Catalog.Section getByName(String name);

    Catalog.Section findSectionInChildren(String name, Collection<Catalog.Section> children);

    void insertAfter(Catalog.Section section, Catalog.Section newSection);

    void insertAfterCatalog(Catalog.Section section);

    void delete(Catalog.Section section);
}
