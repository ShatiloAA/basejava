package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.*;

public class HtmlUtil {

    public static String ContactsToHtml(ContactType type, String value) {
        switch (type) {
            case EMAIL:
                return "<a href='mailto:" + value + "'>" + value + "</a>";
            case SKYPE:
                return "<a href='skype:" + value + "'>" + value + "</a>";
            case TELEPHONE:
                return "<a href='tel:" + value + "'>" + value + "</a>";
            case GITHUB:
            case HOMEPAGE:
            case LINKEDIN:
            case STACKOVERFLOW:
                return "<a href=" + value + "'>" + value + "</a>";
            default:
                throw new IllegalArgumentException("Contact " + type + "is illegal");
        }
        //return title + ": " + value;
    }


        public static boolean isEmpty(String str) {
            return str == null || str.trim().length() == 0;
        }
}
