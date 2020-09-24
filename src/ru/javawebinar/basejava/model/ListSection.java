package ru.javawebinar.basejava.model;

import java.util.List;

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
    public String toString() {
        return sectionList.toString();
    }
}
