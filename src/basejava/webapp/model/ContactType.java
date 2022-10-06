package basejava.webapp.model;

public enum ContactType {
    phoneNumber("Тел.:"),
    Skype("Skype"),
    eMail("Пoчта"),
    LinkedIn("Профиль в LinkedIn"),
    GitHub("Профиль в GitHub"),
    StackOverFlow("Профиль StackOverFlow"),
    HomePage("Домашняя страница");


    private  String title;

    ContactType(String title) {
        this.title = title;
    }

}
