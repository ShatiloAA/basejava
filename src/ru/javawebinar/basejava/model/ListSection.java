package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
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
        return Objects.equals(sectionList, that.sectionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionList);
    }

    @Override
    public String toString() {
        return sectionList.toString();
    }
}
