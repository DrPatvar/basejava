package basejava.webapp.model;

public enum ContactType {
    PHONE("Тел.:"),
    MOBILE("Молбильный"),
    HOME_PHONE("Домашний тел."),
    SKYPE("Skype"),
    MAIL("Пoчта"),
    LINKEDIN("Профиль в LinkedIn"),
    GITHUB("Профиль в GitHub"),
    STACKOVERFLOW("Профиль StackOverFlow"),
    HOME_PAGE("Домашняя страница");


    private  String title;

    ContactType(String title) {
        this.title = title;
    }

}
