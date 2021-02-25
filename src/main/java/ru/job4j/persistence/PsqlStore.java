package ru.job4j.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.service.PsqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PsqlStore {
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    private static final PsqlStore INST = new PsqlStore();

    private final PsqlConnection pool = PsqlConnection.instOf();

    private class Order {
        private int row;
        private int seat;
        private String ownerPhone;

        public Order(int row, int seat, String ownerPhone) {
            this.row = row;
            this.seat = seat;
            this.ownerPhone = ownerPhone;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getSeat() {
            return seat;
        }

        public void setSeat(int seat) {
            this.seat = seat;
        }

        public String getOwnerPhone() {
            return ownerPhone;
        }

        public void setOwnerPhone(String ownerPhone) {
            this.ownerPhone = ownerPhone;
        }
    }

    public static PsqlStore instOf() {
        return INST;
    }

    public List<Order> showOccupiedSeats() {
        List<Order> list = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM halls")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Order(
                            rs.getInt("row"),
                            rs.getInt("seat"),
                            rs.getString("phone")));
                }
            }
            return list;
        } catch (SQLException e) {
            LOG.error("Exception occured: ", e);
            return list;
        }
    }

    public boolean bookPlace(String username, String phone, int row, int seat) {
        try (Connection cn = pool.getConnection()) {
            cn.setAutoCommit(false);
            return saveUser(cn, username, phone, row, seat);
        } catch (SQLException e) {
            LOG.error("Exception occurred", e);
            return false;
        }
    }

    private boolean saveUser(Connection cn, String name, String phone, int row, int seat) {
        try (PreparedStatement ps = cn.prepareStatement(
                "INSERT INTO accounts(name, phone) values (?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.execute();
            if (!savePlace(cn, row, seat, phone)) {
                cn.rollback();
                return false;
            }
            cn.commit();
            return true;
        } catch (SQLException e) {
            LOG.error("Exception occurred while inserting into accounts", e);
            return false;
        }
    }

    private boolean savePlace(Connection cn, int row, int seat, String phone) {
        try (PreparedStatement ps = cn.prepareStatement(
                "INSERT INTO halls(row, seat, phone) values (?, ?, ?)")) {
            ps.setInt(1, row);
            ps.setInt(2, seat);
            ps.setString(3, phone);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            LOG.error("Exception occurred while inserting into halls", e);
            return false;
        }
    }
}
