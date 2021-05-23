package model.entities;

import java.util.Comparator;
import java.util.TreeSet;

public class Catalog {

    private String name = "Catalog";
    private final TreeSet<Section> categories = new TreeSet<>(Comparator.comparing(Section::getName));

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeSet<Section> getCategories() {
        return categories;
    }

    public static class Section {

        private String name;
        private final TreeSet<Section> children = new TreeSet<>(Comparator.comparing(Section::getName));
        private Section parent;

        public Section(String name)
        {
            this.name = name;
            parent = null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Section getParent() {
            return parent;
        }

        public void setParent(Section parent) {
            this.parent = parent;
        }

        public TreeSet<Section> getChildren() {
            return children;
        }
    }
}
