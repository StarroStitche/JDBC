package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String createUT = "CREATE TABLE IF NOT EXISTS users (id INT UNSIGNED NOT NULL AUTO_INCREMENT, name VARCHAR(45) NOT NULL, lastName VARCHAR(45) NOT NULL, age INT(3) NOT NULL, PRIMARY KEY (id), UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;";
    private static final String dropUT = "DROP TABLE IF EXISTS users;";
    private static final String saveUser = "INSERT INTO users (name, lastName, age) VALUE (?, ?, ?);";
    private static final String removeUserById = "DELETE FROM users WHERE ?";
    private static final String getAllUsers = "SELECT * FROM users";
    private static final String cleanUT = "TRUNCATE users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try(Connection con = Util.getConnection(); Statement st = con.createStatement()) {
            st.execute(createUT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection con = Util.getConnection(); Statement st = con.createStatement()) {
            st.execute(dropUT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection con = Util.getConnection(); PreparedStatement pSt = con.prepareStatement(saveUser)) {
            pSt.setString(1, name);
            pSt.setString(2, lastName);
            pSt.setInt(3, age);
            pSt.execute();
            System.out.println("User с именем - " + name + " добавлен в базу данных ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection con = Util.getConnection(); PreparedStatement pSt = con.prepareStatement(removeUserById)) {
            pSt.setLong(1, id);
            pSt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection con = Util.getConnection(); Statement st = con.createStatement()) {
            st.execute(getAllUsers);
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection con = Util.getConnection(); Statement st = con.createStatement()) {
            st.execute(cleanUT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
