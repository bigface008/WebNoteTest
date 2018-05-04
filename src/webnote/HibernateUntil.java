package webnote;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUntil {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
