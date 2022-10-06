package basejava.webapp;

import basejava.webapp.model.ContactType;
import basejava.webapp.model.Period;
import basejava.webapp.model.Resume;

import java.time.LocalDate;
import java.time.Month;

public class ResumeTestData {

    public static void main(String[] args) {
    Resume resume = new Resume("uuid1", "Ivan Sh");
        Period alcatel = new Period(LocalDate.of(1997, Month.DECEMBER, 1),
                LocalDate.of(2005,Month.JANUARY, 15), "Alcatel", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        Period mfti = new Period(LocalDate.of(1984, Month.SEPTEMBER, 23),
                LocalDate.of(1987, Month.JUNE, 18), "zaochka MFTI", "Закончил с отличием");
        
    }


}