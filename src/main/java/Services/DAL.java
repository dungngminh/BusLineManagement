package Services;


import Controller.TicketSeller.TicketOrder;

import Model.*;
import Util.HibernateUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.lang.annotation.Native;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    // for LogIn
    public List<AccountEntity> getListAcc() throws SQLException, ClassNotFoundException {
//        SessionFactory factory = new HibernateUtils.getSessionFactory();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();

            // Get users
            Query<AccountEntity> query = session.createQuery("from AccountEntity WHERE isDelete = false", AccountEntity.class);

            List<AccountEntity> list_acc = query.getResultList();
//            list_acc.forEach(System.out::println);

            // Commit the current resource transaction, writing any unflushed changes to the database.
            session.getTransaction().commit();
            session.close();
            return list_acc;

        }
    }

    public void toggleIsOnlineForAccout(AccountEntity acc, Boolean isOnline) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("update AccountEntity set isOnline = :isOnline" +
                " where idUser = :idUser");
        query.setParameter("isOnline", isOnline);
        query.setParameter("idUser", acc.getIdUser());
        int result = query.executeUpdate();

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    //DONE

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
    public RoleEntity getRole(Integer idRole) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();
            // Get drivers
//            List<TypeOfBusEntity> list_tob = session.createQuery("FROM TypeOfBusEntity ", TypeOfBusEntity.class).list();
            Query<RoleEntity> query = session.createQuery("FROM RoleEntity WHERE idRole = :idRole ", RoleEntity.class);
            query.setParameter("idRole", idRole);
            RoleEntity role = query.getResultList().get(0);

            // Commit the current resource transaction, writing any unflushed changes to the database.
            session.getTransaction().commit();
            session.close();
            return role;
        }
    }
    public void addUserToAccount(String username, String password, Integer idRole, RoleEntity role) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        AccountEntity acc = new AccountEntity();
        acc.setUsername(username);
        acc.setPassword(password);
        System.out.println(idRole);
        acc.setIdRole(idRole);
        acc.setIsOnline(false);
        acc.setIsDelete(false);
        acc.setRoleByIdRole(role);

        session.save(acc);

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    public void updateRole(AccountEntity acc, int idRole) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query<AccountEntity> query = session.createQuery("update AccountEntity set idRole = :idRole " +
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

        Query<AccountEntity> query = session.createQuery("update AccountEntity set password = :password " +
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
        Query query = session.createQuery("UPDATE AccountEntity SET isDelete = true WHERE idUser = :idUser");
        query.setParameter("idUser", acc.getIdUser());

        query.executeUpdate();

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
        Query<ScheduleEntity> query = session.createQuery("Select SCH from ScheduleEntity SCH, DriverEntity DRI, " +
                "RouteEntity ROU, BusEntity BUS, TypeOfBusEntity TYPE where SCH.idDriver = DRI.idDriver AND DRI.status = 0 AND DRI.isDelete = false " +
                "AND SCH.idRoute = ROU.idRoute AND ROU.status = 0 AND SCH.idBus = BUS.idBus AND BUS.status = 0 AND BUS.isDelete = false " +
                "AND BUS.idType = TYPE.idType AND TYPE.isDelete = false",ScheduleEntity.class);
        List<ScheduleEntity> list = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public Date getOutDateUpdate(int idSchedule){
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        var query = session.createQuery("select max(TRIP.departDate) from TripInformationEntity TRIP" +
                " where TRIP.idSchedule = :idSchedule");
        query.setParameter("idSchedule", idSchedule);
        List<Date> list = query.getResultList();
        return list.get(0);
    }

    public List<TripInformationEntity> getFilterTrip(List<RouteEntity> listRoute) {
        List<TripInformationEntity> result = new ArrayList<>();
        Session session = HibernateUtils.getSessionFactory().openSession();


        for (RouteEntity x : listRoute) {
//            session.beginTransaction();
//            Query<TripInformationEntity> query = session.createQuery("SELECT TRIP FROM  TripInformationEntity TRIP, " +
//                            "ScheduleEntity SCH, RouteEntity ROU, BusEntity BUS, TypeOfBusEntity TYPE, DriverEntity DRI " +
//                            "WHERE TRIP.idSchedule = SCH.idSchedule AND SCH.isDelete = false AND SCH.idRoute = :idRoute " +
//                            "AND ROU.status = 0 AND DRI.isDelete = false " +
//                            "AND SCH.idBus = BUS.idBus AND BUS.isDelete = false " +
//                            "AND BUS.status = 0 AND BUS.idType = TYPE.idType AND TYPE.isDelete = false",
//                    TripInformationEntity.class);
//            query.setParameter("idRoute", x.getIdRoute());
////            System.out.println(x.getIdRoute());
//            query.getResultList().forEach(qr -> {
//                if (!result.contains(qr)) result.add(qr);
//
//            });

            session.beginTransaction();
            String sql = "SELECT DISTINCT TI.* FROM TripInformation TI\n" +
                    "INNER JOIN Schedule S on S.idSchedule = TI.idSchedule\n" +
                    "INNER JOIN Route R2 on S.idRoute = R2.idRoute\n" +
                    "INNER JOIN Driver D on D.idDriver = S.idDriver\n" +
                    "INNER JOIN Bus B on B.idBus = S.idBus\n" +
                    "INNER JOIN TypeOfBus TOB on TOB.idType = B.idType\n" +
                    "WHERE S.isDelete = 0 AND D.isDelete = 0 AND B.status = 0 \n" +
                    " AND TOB.isDelete = 0 AND R2.status = 0 AND S.idRoute = :idRoute";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("idRoute", x.getIdRoute());
            query.addEntity(TripInformationEntity.class);
            List<TripInformationEntity> res = query.getResultList();
            result.addAll(res);
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

    public Integer getIdTicketToClose() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "SELECT TIC.* FROM Ticket TIC\n" +
                "WHERE TIC.status = 0 AND TIC.idUser = :idUser";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(TicketEntity.class);
        query.setParameter("idUser", DAL.getInstance().getCurrent().getIdUser());
        List<TicketEntity> result = query.getResultList();

        session.getTransaction().commit();
        session.close();
        return !result.isEmpty() ? result.get(0).getIdTicket() : -1;

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

    public void updateSchedule(int idSchedule, RouteEntity routeSelected, BusEntity busSelected, DriverEntity driverSelected,Date departTimeInput, int durationInput, int durationInput1, int priceInput, int dprInput) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        //TODO Query update Schedule
        Query query = session.createQuery("Update ScheduleEntity set idRoute = :idRoute, idBus = :idBus, idDriver = :idDriver,departTime = :departTime" +
                ", price = :price, dpr = :dpr where idSchedule = :idSchedule");
        query.setParameter("idRoute", routeSelected.getIdRoute());
        query.setParameter("idBus", busSelected.getIdBus());
        query.setParameter("idDriver",driverSelected.getIdDriver());
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
    public List<TicketEntity> getAllTicket() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "SELECT * FROM Ticket TIC, TripInformation TRIP " +
                "WHERE TIC.idTrip = TRIP.idTrip AND TRIP.departDate >= :date";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(TicketEntity.class);
        query.setParameter("date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        List<TicketEntity> result = query.getResultList();

        session.getTransaction().commit();
        session.close();
        return result;
    }
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

    // NOTICE DAL for MainWindow(Dashboard of Admin)

    public List<TicketEntity> getListTicket1YearAgo() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "SELECT * FROM Ticket TIC, TripInformation TRIP " +
                "WHERE TIC.idTrip = TRIP.idTrip AND TRIP.departDate BETWEEN DATEADD(year, -1, GETDATE()) AND GETDATE()";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(TicketEntity.class);
        List<TicketEntity> result = query.getResultList();

        session.getTransaction().commit();
        session.close();
        return result;
    }


    public long getNumberRoutesToday() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "SELECT ROU.* FROM Route ROU\n" +
                "INNER JOIN Schedule S on ROU.idRoute = S.idRoute\n" +
                "INNER JOIN TripInformation TI on S.idSchedule = TI.idSchedule\n" +
                "WHERE TI.departDate = cast(GETDATE() as date)";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(RouteEntity.class);
        List<RouteEntity> result = query.getResultList();

        session.getTransaction().commit();
        session.close();
        return result.size();
    }

    public List<ScheduleEntity> getOutDatedSchedule() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "SELECT DISTINCT SCH.* FROM Schedule SCH\n" +
                "INNER JOIN TripInformation TI on SCH.idSchedule = TI.idSchedule\n" +
                "WHERE (SELECT max(departDate) FROM TripInformation) < DATEADD(day, 10, GETDATE())";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(ScheduleEntity.class);
        List<ScheduleEntity> result = query.getResultList();

        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<AccountEntity> getListSeller() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query<AccountEntity> query = session.createQuery("From AccountEntity WHERE idRole = 2", AccountEntity.class);

        List<AccountEntity> result = query.getResultList();

        session.getTransaction().commit();
        session.close();
        return result;
    }


    //DONE
}
