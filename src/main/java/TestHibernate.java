import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;

import java.util.*;

public class TestHibernate {

    private static SessionFactory factory;

    public static void main(String[] args) {

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        System.out.println("Wyświetlenie wszystkich produkcji:");
        showQueryResults("FROM Filmy");

        System.out.println();
        System.out.println("a) Wyświetl średnią ocenę filmów wydanych po 2016 roku. Pomiń wyniki -1.");
        showQueryResults("SELECT AVG(score) FROM Filmy WHERE release_d >= 2016 and score > -1");

        System.out.println();
        System.out.println("b) Oblicz, ile procent filmów nie ma oceny PEGI. ");
        showQueryResults("SELECT ROUND(COUNT(pegi)/(SELECT COUNT(pegi) FROM Filmy)*100,2) FROM Filmy WHERE pegi = 'NR'");

        System.out.println();
        System.out.println("c) Wyświetl posortowane alfabetycznie nazwy filmów z ‘the’ w nazwie. Ogranicz wynik do\n" +
                "pięciu tytułów.");
        String hql1 = "SELECT movie_name FROM Filmy WHERE movie_name LIKE :movie_name";

        Query qLIKE = session.createSQLQuery(hql1).addScalar("movie_name", StandardBasicTypes.STRING).setParameter("movie_name", "%the%");
        List listLIKE = qLIKE.list();
        for (int i = 0; i < listLIKE.size(); i++)
            System.out.println(listLIKE.get(i));




        System.out.println();
        System.out.println("d) Wybierz dwa seriale z listy najlepszych seriali Neflix wg serwisu IMDB2 i oblicz wartość\n" +
                "bezwzględną różnicy ocen. Przykład „The Crown” - „Stranger Things”:");
        showQueryResults("SELECT ABS(score - (SELECT score FROM Filmy WHERE movie_name = 'The Crown')) FROM Filmy WHERE movie_name = 'Stranger Things'");


        System.out.println();
        System.out.println("e) Wyświetl zestawienie liczby filmów w zadanym roku w formacie rok: liczba filmów.\n" +
                "Wyniku uszereguj zaczynając od najstarszych filmów.");
        showQueryResults("SELECT CONCAT(release_d, ': ', COUNT(movie_name)) FROM Filmy GROUP BY release_d ORDER BY release_d");


        System.out.println();
        System.out.println("f) Wyświetl tytuł, rok i ocenę filmu o najdłuższej nazwie.");
        Query longestString = session.createQuery("SELECT new LIST(movie_name, release_d, score) FROM Filmy ORDER BY LENGTH(movie_name) DESC ");
        System.out.println(longestString.list().get(0));




        System.out.println();
        System.out.println("g) Wyświetl liczbę lat, które upłynęły od premiery filmu o zadanej nazwie");
        showQueryResults("SELECT year(current_date) - release_d FROM Filmy WHERE movie_name = 'Prison Break'");


        System.out.println();
        System.out.println("h) Wyświetl malejąco lata premiery filmów, które miały ocenę powyżej 90.");
        showQueryResults("SELECT new list(movie_name, release_d) FROM Filmy WHERE score > 90 ORDER BY release_d");
//        System.out.println("Wyświetlenie nazw win:");
//        showQueryResults("SELECT nazwa AS n FROM Wino");
//        System.out.println();
//        System.out.println("Wyświetlenie id i nazw win:");
//        showQueryResults("SELECT new list(id, nazwa) FROM Wino");
//        System.out.println();
//        System.out.println("Wyświetlenie liczności występowania win wg krajów:");
//        showQueryResults("SELECT new list(COUNT(krajPochodzenia), krajPochodzenia) " +
//                "FROM Wino GROUP BY krajPochodzenia order by COUNT(krajPochodzenia)");

//        // COUNT
//        System.out.println("Wyświetlenie liczby win w tabeli:");
//        showQueryResults("SELECT COUNT(w) FROM Wino w");
//        System.out.println();
//
//        // INSERT
//        ZastosowanieWina zastosowanieWina = new ZastosowanieWina(140, "nieznane", 22);
//        HashSet<ZastosowanieWina> hashSet = new HashSet<>();
//        hashSet.add(zastosowanieWina);
//
//        Wino wino = new Wino(140, "Beaujolais", "Francja", RodzajWina.czerwone, "wytrawne");
//        wino.setZastosowanieWina(hashSet);
//        session.save(wino);
//        session.save(zastosowanieWina);
//        Wino wino2 = new Wino(155, "Beaujolais", "Francja", RodzajWina.czerwone, "wytrawne");
//
//        System.out.println("Wyświetlenie wszystkich win po wstawieniu Beaujolais:");
//        showQueryResults("FROM Wino");
//        System.out.println();
//
//        System.out.println("Wyświetlenie zastosowań win:");
//        showQueryResults("FROM ZastosowanieWina");
//        System.out.println();
//
//        System.out.println("Wyświetlenie procentu win z Francji i Chorwacji:");
//        String hql1 = "SELECT COUNT(id) FROM Wino WHERE krajPochodzenia IN :countryNames";
//        Query qIN = session.createQuery(hql1);
//        List<String> krajeWin = new ArrayList<>();
//        krajeWin.add("Francja");
//        krajeWin.add("Chorwacja");
//        qIN.setParameter("countryNames", krajeWin);
//        List listIN = qIN.list();
//        Long result1 = (Long) listIN.get(0);
//
//        String hql2 = "SELECT COUNT(id) FROM Wino";
//        Long result2 = (Long) session.createQuery(hql2).getSingleResult();
//
//        System.out.printf("%d/%d = %.2f%%\n\n", result1, result2, (100.0 * result1 / result2));
//
//        System.out.println("Użycie LIKE");
//        String hql3 = "SELECT nazwa FROM wina WHERE nazwa LIKE :wineName";
//        Query qLIKE = session.createSQLQuery(hql3)
//                .addScalar("nazwa",StandardBasicTypes.STRING)
////                https://docs.jboss.org/hibernate/orm/4.3/javadocs/org/hibernate/type/StandardBasicTypes.html
//                .setParameter("wineName", "%to%");
//        List listLIKE = qLIKE.list();
//        for (int i = 0; i < listLIKE.size(); i++)
//            System.out.println(listLIKE.get(i));
//
//        System.out.println();
//
//        // widok - wyswietlenie wersja 1
//        System.out.println("Widok czerwone wina - wyswietlenie wersja 1");
//        List<CzerwoneWinoWidok> widok = session.createQuery("FROM CzerwoneWinoWidok", CzerwoneWinoWidok.class).getResultList();
//        System.out.println(widok.toString());
//        System.out.println();
//        // widok - wyswietlenie wersja 2
//        System.out.println("Widok czerwone wina - wyswietlenie wersja 2");
//        showQueryResults("FROM CzerwoneWinoWidok");
//        System.out.println();
//
//        // UPDATE
//        String qryString = "update Wino w set w.nazwa ='NIEZNANE WINO' where w.nazwa ='Beaujolais'";
//        Query query = session.createQuery(qryString);
//        int count = query.executeUpdate();
//        System.out.println("Wyświetlenie wszystkich win:");
//        System.out.println(count + " Record(s) Updated.");
//        showQueryResults("FROM Wino");

        session.getTransaction().commit();
        session.close();


    }


    public static void showQueryResults(String queryString) {

        Session session = factory.openSession();
        Query query = session.createQuery(queryString);
        List list = query.list();

        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));

    }
}

