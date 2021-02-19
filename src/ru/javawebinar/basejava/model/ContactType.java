package ru.javawebinar.basejava.model;

public enum ContactType {
    TELEPHONE("Телефон"),
    SKYPE("Skype") {
        /*@Override
        public String toHtml0(String value){
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }*/
    },
    EMAIL("Почта") {
   /*     @Override
        public String toHtml0(String value){
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }*/
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

/*    protected String toHtml0(String value){
        return title + ": " + value;
    }

    public String toHmtl(String value) {
        return (value== null) ? "" : toHtml0(value);
    }*/
}
