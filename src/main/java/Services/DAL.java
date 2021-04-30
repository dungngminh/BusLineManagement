package Services;
import Model.*;
import Model.DataTable.TableBusPage;
import Util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public String encryptSHA1(String input)
    {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            StringBuilder hashtext = new StringBuilder(no.toString(16));

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }

            // return the HashText
            return hashtext.toString();
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
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
            NativeQuery<TypeOfBusEntity> query = session.createNativeQuery("SELECT * FROM TypeOfBus", TypeOfBusEntity.class );
            List<TypeOfBusEntity> list_tob = query.getResultList();

//            list_acc.forEach(System.out::println);
            // Commit the current resource transaction, writing any unflushed changes to the database.
            session.getTransaction().commit();
            return list_tob;
        }
    }

    public List<BusEntity> getDataForBusPage() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();
            Query<BusEntity> query = session.createQuery("SELECT BUS " +
                    "FROM BusEntity BUS INNER JOIN BUS.typeOfBusByIdType as TOB", BusEntity.class);
            final List<BusEntity> list = query.getResultList();

//            list_acc.forEach(System.out::println);
            // Commit the current resource transaction, writing any unflushed changes to the database.
            session.getTransaction().commit();
            return list;
        }
    }

    public void insertBus(String busName, String plateNumber, TypeOfBusEntity tob, boolean del, int stt) {
            Session session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            BusEntity bus = new BusEntity();
            bus.setBusName(busName);
            bus.setPlateNumber(plateNumber);
            bus.setIdType(tob.getIdType());
            bus.setDelete(false);
            bus.setStatus(1);
            bus.setTypeOfBusByIdType(tob);
            //Save the employee in database
            session.save(bus);

            //Commit the transaction
            session.getTransaction().commit();
            HibernateUtils.close();

    }
}
