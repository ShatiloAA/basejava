package ru.javawebinar.basejava.model;

import java.util.List;

public class ListAbstractSection extends AbstractSection {
    private final List<String> sectionList;

    public ListAbstractSection(List<String> infoList) {
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
