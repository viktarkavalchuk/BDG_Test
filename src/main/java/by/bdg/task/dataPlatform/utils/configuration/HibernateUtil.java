//package by.bgd.task.announcementPlatform.utils.configuration;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//public class HibernateUtil {
//    private static final Logger logger = LogManager.getLogger();
//    private static final SessionFactory sessionFactory;
//
//    static {
//        try {
//            sessionFactory = new Configuration()
//                    .configure()
//                    .buildSessionFactory();
//        } catch (Throwable e) {
//            logger.error("Initial SessionFactory creation failed." + e);
//            throw new ExceptionInInitializerError(e);
//        }
//    }
//
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
//
//    public static Session getSession() {
//        return sessionFactory.openSession();
//    }
//}