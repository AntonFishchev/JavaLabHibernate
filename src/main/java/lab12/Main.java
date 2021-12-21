package lab12;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Items.class)
                .buildSessionFactory();

        for (int i = 0; i < 8; i++) {
            new MyThread(factory).start();
        }

    }
}
