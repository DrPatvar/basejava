package basejava.webapp;

import basejava.webapp.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {

    public Resume resume (String uuid, String fullName){
        Resume resume = new Resume("uuid1","TestResume");

        return resume;
    }

    public static void main(String[] args) {
        Resume resume = new Resume("uuid1", "Ivan Sh");
        Period alcatel = new Period(LocalDate.of(1997, 12, 1),
                LocalDate.of(2005, 01, 15), "Alcatel", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        Period mfti = new Period(LocalDate.of(1984, 9, 23),
                LocalDate.of(1987, 6, 18), "zaochka MFTI", "Закончил с отличием");
        List<Period> alcatelList = new ArrayList<>();
        alcatelList.add(alcatel);
        List<Period> mftiList = new ArrayList<>();
        mftiList.add(mfti);
        Organization orgAlcatel = new Organization("Alcatel", alcatelList,"www.alcatel.ru");
        Organization orgMfti = new Organization("MFTI", mftiList, "www.mipt.ru");
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(orgAlcatel);
        organizationList.add(orgMfti);
        OrganizationSection organizationSection = new OrganizationSection(organizationList);

        String arch = "Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, " +
                "участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет";
        String arch2 = "С 2013 года: разработка проектов Разработка Web приложения,Java Enterprise, Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)." +
                " Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.";
        ArrayList archievement = new ArrayList();
        archievement.add(arch);
        archievement.add(arch2);

        ListSection listSection = new ListSection(archievement);
        TextSection textSection = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры." );

        
    }


}