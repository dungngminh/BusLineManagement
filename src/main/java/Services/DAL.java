package Services;
import Model.*;
import Util.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.*;

public class DAL {
    private static DAL instance;


    private DAL(){

    }

    public static DAL getInstance() {
        if (instance == null) {
            instance = new DAL();
        }
        return instance;
    }

    public List<AccountEntity> getListAcc() throws SQLException, ClassNotFoundException {
//        SessionFactory factory = new HibernateUtils.getSessionFactory();
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            // Get users
            List<AccountEntity> list_acc = session.createQuery("FROM AccountEntity ", AccountEntity.class).list();
//            list_acc.forEach(System.out::println);

            // Commit the current resource transaction, writing any unflushed changes to the database.
            session.getTransaction().commit();

            return list_acc;

        }
    }

    public void getListBus() throws SQLException, ClassNotFoundException {
//        DB_Helper.getInstance().getRecord("SELECT * FROM Bus", "Bus");
    }

    public List<TypeOfBusEntity> getListTypeOFBus() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();
            // Get users
//            List<TypeOfBusEntity> list_tob = session.createQuery("FROM TypeOfBusEntity ", TypeOfBusEntity.class).list();
            NativeQuery query = session.createNativeQuery("SELECT * FROM TypeOfBus", TypeOfBusEntity.class );
            List<TypeOfBusEntity> list_tob = query.getResultList();

//            list_acc.forEach(System.out::println);
            // Commit the current resource transaction, writing any unflushed changes to the database.
            session.getTransaction().commit();
            return list_tob;
        }
    }
}
