package ru.javawebinar.basejava.model;

import java.util.List;

public class ListSection extends AbstractSection {
    private final List<String> sectionList;

    public ListSection(List<String> infoList) {
        this.sectionList = infoList;
    }

    public List<String> getInfoList() {
        return sectionList;
    }

    public void addInfoToList(String info) {
        sectionList.add(info);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return sectionList.equals(that.sectionList);
    }

    @Override
    public int hashCode() {
        return sectionList.hashCode();
    }

    @Override
    public String toString() {
        return sectionList.toString();
    }
}
