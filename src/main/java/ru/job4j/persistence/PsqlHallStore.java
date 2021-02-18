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

public class PsqlHallStore {
    private static final Logger LOG = LoggerFactory.getLogger(PsqlHallStore.class.getName());

    private static final PsqlHallStore INST = new PsqlHallStore();

    private final PsqlConnection pool = PsqlConnection.instOf();

    private class Order {
        private int row;
        private int seat;
        private String ownerEmail;

        public Order(int row, int seat, String ownerEmail) {
            this.row = row;
            this.seat = seat;
            this.ownerEmail = ownerEmail;
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

        public String getOwnerEmail() {
            return ownerEmail;
        }

        public void setOwnerEmail(String ownerEmail) {
            this.ownerEmail = ownerEmail;
        }
    }

    public static PsqlHallStore instOf() {
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
                            rs.getString("email")));
                }
            }
            return list;
        } catch (SQLException e) {
            LOG.error("Exception occured: ", e);
            return list;
        }
    }
}
