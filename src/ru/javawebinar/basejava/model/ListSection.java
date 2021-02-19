package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)

public class ListSection extends Section {
    private static final long serialVersionUID = 1L;
    private List<String> sectionList;
    public static final ListSection EMPTY = new ListSection("");

    public ListSection() {
    }

    public ListSection(String... items) {
        this.sectionList = Arrays.asList(items);
    }

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
