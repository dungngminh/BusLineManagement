package Services;


import Controller.TicketSeller.TicketOrder;

import Model.*;
import Util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Date;

import java.util.List;

public class DAL {
    private static DAL instance;


    public static AccountEntity current;


    private DAL() {

    }

    public static DAL getInstance() {
        if (instance == null) {
            instance = new DAL();
        }
        return instance;
    }

    public AccountEntity getCurrent() {
        return current;
    }

    public void setCurrent(AccountEntity crr) {
        current = crr;
    }

    public String encryptSHA1(String input) {
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
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();

            // Get users
            Query<AccountEntity> query = session.createQuery("from AccountEntity ", AccountEntity.class);

            List<AccountEntity> list_acc = query.getResultList();
//            list_acc.forEach(System.out::println);

            // Commit the current resource transaction, writing any unflushed changes to the database.
            session.getTransaction().commit();
            session.close();
            return list_acc;

        }
    }

    // DAL for Bus
    public List<TypeOfBusEntity> getListTypeOfBus() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();
            // Get types
//            List<TypeOfBusEntity> list_tob = session.createQuery("FROM TypeOfBusEntity ", TypeOfBusEntity.class).list();
            Query<TypeOfBusEntity> query = session.createQuery("FROM TypeOfBusEntity ", TypeOfBusEntity.class);
            List<TypeOfBusEntity> list_tob = query.getResultList();

//            list_acc.forEach(System.out::println);
            // Commit the current resource transaction, writing any unflushed changes to the database.
            session.getTransaction().commit();
            session.close();
            return list_tob;
        }
    }

    public List<BusEntity> getDataForBusPage() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();
            Query<BusEntity> query = session.createQuery("SELECT BUS " +
                    "FROM BusEntity BUS INNER JOIN BUS.typeOfBusByIdType as TOB WHERE BUS.isDelete = false", BusEntity.class);
            final List<BusEntity> list = query.getResultList();

//            list_acc.forEach(System.out::println);
            // Commit the current resource transaction, writing any unflushed changes to the database.
            session.getTransaction().commit();
            session.close();
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
        bus.setIsDelete(false);
        bus.setStatus(1);
        bus.setTypeOfBusByIdType(tob);

        //Save the BusEntity in database

        session.save(bus);

        //Commit the transaction
        session.getTransaction().commit();
        session.close();

    }

    public void updateBus(int idBus, String busName, String plateNumber, TypeOfBusEntity tob, int stt) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("update BusEntity set busName = :busName, plateNumber = :plateNumber, typeOfBusByIdType = :tob, status = :stt" +
                " where idBus = :idBus");
        query.setParameter("busName", busName);
        query.setParameter("plateNumber", plateNumber);
        query.setParameter("tob", tob);
        query.setParameter("stt", stt);
        query.setParameter("idBus", idBus);
        int result = query.executeUpdate();

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    public void deleteBus(int idBus) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query<BusEntity> query = session.createQuery("update BusEntity set isDelete = :del" +
                " where idBus = :idBus");
        query.setParameter("del", true);
        query.setParameter("idBus", idBus);
        int result = query.executeUpdate();

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    // Done Bus here ?

    // DAL for Decentralize ?
    public void addUserToAccount(String username, String password, int idRole) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        AccountEntity acc = new AccountEntity();
        acc.setUsername(username);
        acc.setPassword(password);

        int idUser = (Integer) session.save(acc);

        RoleEntity role_obj = session.find(RoleEntity.class, idRole);
        RoleAccountEntity role_acc = new RoleAccountEntity();
        role_acc.setIdRole(idRole);
        role_acc.setIdUser(idUser);
        role_acc.setAccountByIdUser(acc);
        role_acc.setRoleByIdRole(role_obj);

        session.save(role_acc);

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    public void updateRole(AccountEntity acc, int idRole) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query<RoleAccountEntity> query = session.createQuery("update RoleAccountEntity set idRole = :idRole " +
                "where idUser = :idUser");

        query.setParameter("idRole", idRole);
        query.setParameter("idUser", acc.getIdUser());

        int result = query.executeUpdate();

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    public void updateAccount(AccountEntity acc) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query<RoleAccountEntity> query = session.createQuery("update AccountEntity set password = :password " +
                "where idUser = :idUser");

        query.setParameter("password", acc.getPassword());
        query.setParameter("idUser", acc.getIdUser());

        int result = query.executeUpdate();

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    public void deleteUser(AccountEntity acc) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query query1 = session.createQuery("DELETE FROM RoleAccountEntity WHERE idUser = :idUser");
        query1.setParameter("idUser", acc.getIdUser());

        Query query2 = session.createQuery("DELETE FROM AccountEntity WHERE idUser = :idUser");
        query2.setParameter("idUser", acc.getIdUser());

        query1.executeUpdate();
        query2.executeUpdate();

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    // Done Decentralize here ?

    // DAL for Driver ?
    public List<DriverEntity> getListDriver() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();
            // Get drivers
//            List<TypeOfBusEntity> list_tob = session.createQuery("FROM TypeOfBusEntity ", TypeOfBusEntity.class).list();
            Query<DriverEntity> query = session.createQuery("FROM DriverEntity WHERE isDelete = false ", DriverEntity.class);
            List<DriverEntity> list_driver = query.getResultList();

            // Commit the current resource transaction, writing any unflushed changes to the database.
            session.getTransaction().commit();
            session.close();
            return list_driver;
        }
    }

    public void insertDriver(DriverEntity driver) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        //Save the employee in database
        session.save(driver);

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    public void updateDriver(int id, String driverName, String phoneNumber, String address, int stt) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("update DriverEntity set nameDriver = :name, phone = :phoneNumber, address = :address, status = :stt" +
                " where idDriver = :id");
        query.setParameter("name", driverName);
        query.setParameter("phoneNumber", phoneNumber);
        query.setParameter("address", address);
        query.setParameter("stt", stt);
        query.setParameter("id", id);
        int result = query.executeUpdate();

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    public void deleteDriver(int idDriver) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query<DriverEntity> query = session.createQuery("update DriverEntity set isDelete = :del" +
                " where idDriver = :idDriver");
        query.setParameter("del", true);
        query.setParameter("idDriver", idDriver);
        int result = query.executeUpdate();

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    // done Driver ?

    // DAL for Province ??

    public List<ProvinceEntity> getProvinceName() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query<ProvinceEntity> query = session.createQuery("From ProvinceEntity ", ProvinceEntity.class);
        List<ProvinceEntity> result = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    // done Province ?

    //DAL for Route
    public void insertRoute(RouteEntity route) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(route);
        session.getTransaction().commit();
        session.close();
    }

    public List<RouteEntity> getRoutes() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query<RouteEntity> query = session.createQuery("From RouteEntity", RouteEntity.class);
        List<RouteEntity> result = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return result;
    }


    public void updateRoute(int idRoute, String startStation, String endStation, String note, int distance, int stt) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        var query = session.createQuery("update RouteEntity set startStation=:startStation,endStation =:endStation,note =:note, distance =:distance, status =:stt" +
                " where idRoute = :idRoute");
        query.setParameter("startStation", startStation);
        query.setParameter("endStation", endStation);
        query.setParameter("distance", distance);

        query.setParameter("note", note);

        query.setParameter("stt", stt);
        query.setParameter("idRoute", idRoute);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void deleteRoute(int idRoute) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        var query = session.createQuery("update RouteEntity set status = :stt" +
                " where idRoute = :idRoute");
        query.setParameter("stt", 2);

        query.setParameter("idRoute", idRoute);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
    //done Route?


    //DAL for Schedule

    public void insertSchedule(ScheduleEntity schedule) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(schedule);
        session.getTransaction().commit();
        session.close();
    }

    //DAL for Ticket Seller
    // DAL for FilterRoute ?
    /// This method: Get route have start station and end station from two province have been known
    public List<RouteEntity> getFilterRoute(List<List<String>> listPairStation) {
        List<RouteEntity> result = new ArrayList<>();

        Session session = HibernateUtils.getSessionFactory().openSession();


        for (List<String> x : listPairStation) {

            session.beginTransaction();
            Query<RouteEntity> query = session.createQuery("FROM RouteEntity ROU" +
                    " WHERE ROU.startStation = :stStat and ROU.endStation = :enStat", RouteEntity.class);

            query.setParameter("stStat", x.get(0));
            query.setParameter("enStat", x.get(1));

//            System.out.println(query.getResultList().size());
            result.addAll(query.getResultList());
            session.getTransaction().commit();


        }
        session.close();

        return result;
    }


    public List<ScheduleEntity> getScheduleData() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query<ScheduleEntity> query = session.createQuery("select SCH FROM ScheduleEntity SCH, RouteEntity ROU, " +
                " BusEntity BUS, TypeOfBusEntity TYPE WHERE  SCH.idRoute = ROU.idRoute AND SCH.isDelete = false AND ROU.status = 0 AND SCH.idBus = BUS.idBus" +
                " AND BUS.status = 0 AND BUS.isDelete = false AND BUS.idType = TYPE.idType", ScheduleEntity.class);
        List<ScheduleEntity> list = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return list;
    }


    public List<TripInformationEntity> getFilterTrip(List<RouteEntity> listRoute) {
        List<TripInformationEntity> result = new ArrayList<>();
        Session session = HibernateUtils.getSessionFactory().openSession();


        for (RouteEntity x : listRoute) {
            session.beginTransaction();
            Query<TripInformationEntity> query = session.createQuery("SELECT TRIP FROM  TripInformationEntity TRIP, " +
                            "ScheduleEntity SCH, RouteEntity ROU, BusEntity BUS, TypeOfBusEntity TYPE, DriverEntity DRI " +
                            "WHERE TRIP.idSchedule = SCH.idSchedule AND SCH.isDelete = false AND SCH.idRoute = :idRoute " +
                            "AND ROU.status = 0 AND TRIP.idDriver = DRI.idDriver AND DRI.status = 0 AND DRI.isDelete = false " +
                            "AND SCH.idBus = BUS.idBus AND BUS.isDelete = false " +
                            "AND BUS.status = 0 AND BUS.idType = TYPE.idType AND TYPE.isDelete = false",
                    TripInformationEntity.class);
            query.setParameter("idRoute", x.getIdRoute());
//            System.out.println(x.getIdRoute());
            query.getResultList().forEach(qr -> {
                if (!result.contains(qr)) result.add(qr);

            });
            session.getTransaction().commit();

        }

//        System.out.println(result.size());
        session.close();
        return result;
    }

    // done FilterRoute ?

    // DAL for TicketOrder ?
    public TicketEntity pendingTicketOrderToTicket(AccountEntity acc, TripInformationEntity trip) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        TicketEntity ticket = new TicketEntity();
        ticket.setNameTicket("");
        ticket.setIdUser(acc.getIdUser());
        ticket.setIdTrip(trip.getIdTrip());
        ticket.setAccountByIdUser(acc);
        ticket.setTripInformationByIdTrip(trip);
        ticket.setIsDelete(false);
        ticket.setIsPaid(false);
        ticket.setPrice(0);
        // Pending: 0 || Ordered: 1
        ticket.setStatus(0);

        session.save(ticket);

        //Commit the transaction
        session.getTransaction().commit();
        session.close();

        return ticket;
    }

    public List<TicketEntity> getListTicket(Integer idTrip) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query<TicketEntity> query = session.createQuery("From TicketEntity WHERE idTrip = :idTrip", TicketEntity.class);
        query.setParameter("idTrip", idTrip);

        List<TicketEntity> result = query.getResultList();

        session.getTransaction().commit();
        session.close();
        return result;
    }

    public void updateCurrentTicket(TicketEntity ticket) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("update TicketEntity set nameTicket = :nameTicket, nameCustomer = :nameCustomer" +
                ", phoneNumber = :phone, status = :stt, isPaid = :isPaid, price = :price where idTicket = :idTicket");
        query.setParameter("nameTicket", ticket.getNameTicket());
        query.setParameter("nameCustomer", ticket.getNameCustomer());
        query.setParameter("phone", ticket.getPhoneNumber());
        query.setParameter("stt", ticket.getStatus());
        query.setParameter("isPaid", ticket.getIsPaid());
        query.setParameter("price", ticket.getPrice());
        query.setParameter("idTicket", ticket.getIdTicket());
        int result = query.executeUpdate();

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    public void deleteCurrentTicket(Integer idTicket) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("DELETE FROM TicketEntity WHERE idTicket = :idTicket");

        query.setParameter("idTicket", idTicket);
        int result = query.executeUpdate();

        //Commit the transaction
        session.getTransaction().commit();
        session.close();

    }

    public void removeSchedule(int idSchedule) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("update ScheduleEntity set isDelete = :del" +
                " where idSchedule = :id");
        query.setParameter("del", true);
        query.setParameter("id", idSchedule);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void updateSchedule(int idSchedule, RouteEntity routeSelected, BusEntity busSelected, Date departTimeInput, int durationInput, int durationInput1, int priceInput, int dprInput) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        //TODO Query update Schedule
        Query query = session.createQuery("Update ScheduleEntity set idRoute = :idRoute, idBus = :idBus, departTime = :departTime" +
                ", price = :price, dpr = :dpr where idSchedule = :idSchedule");
        query.setParameter("idRoute", routeSelected.getIdRoute());
        query.setParameter("idBus", busSelected.getIdBus());
        query.setParameter("departTime", departTimeInput);
        query.setParameter("price", priceInput);
        query.setParameter("dpr", dprInput);
        query.setParameter("idSchedule", idSchedule);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    // done TicketOrder ?

    // NOTICE DAL ticket
    public void setPaidTicket(Integer idTicket) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("Update TicketEntity set isPaid = true WHERE idTicket = :idTicket");
        query.setParameter("idTicket", idTicket);
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    public TicketEntity getOneTicket(Integer idTicket) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query<TicketEntity> query = session.createQuery("From TicketEntity WHERE idTicket = :idTicket", TicketEntity.class);
        query.setParameter("idTicket", idTicket);

        TicketEntity result = query.getResultList().get(0);

        session.getTransaction().commit();
        session.close();
        return result;
    }



    // DONE
}
