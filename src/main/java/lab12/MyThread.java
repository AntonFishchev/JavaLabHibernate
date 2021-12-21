package lab12;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.LockModeType;

public class MyThread extends Thread{
    SessionFactory factory;

    public MyThread (SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20000; i++) {
            Session session = factory.getCurrentSession();
            try {
                session.beginTransaction();

                Items item = (Items) session.createQuery("SELECT i FROM Items i WHERE i.id = :id")
                        .setParameter("id", rnd(1L, 40L))
                        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                        .getResultList()
                        .get(0);
                item.Val();

                Thread.sleep(5);

                session.save(item);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                System.out.println(this.getName() + " rollback" );
                e.printStackTrace();
            }

            if (session != null ) {
                session.close();
            }
        }
    }

    static Long rnd(Long min, Long max) {
        return (Long) (min + (int)(Math.random()*((max - min) + 1)));
    }
}
