package PinkyUni.service.migration;

import PinkyUni.entity.User;
import PinkyUni.exception.AlreadyExistsException;
import PinkyUni.exception.DatabaseException;
import PinkyUni.service.DbManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserMigrationService {

    private static final Logger logger = LogManager.getLogger();
    private static final UserMigrationService instance = new UserMigrationService();

    private UserMigrationService() {
    }

    public static UserMigrationService getInstance() {
        return instance;
    }

    public void migrate(List<User> users) throws AlreadyExistsException, DatabaseException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DbManager dbManager = DbManager.getInstance();
        Connection connection = dbManager.getConnection();
        for (User user : users) {
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE passport = ?;");
                preparedStatement.setString(1, user.getPassport());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.first())
                    throw new AlreadyExistsException("Such user already exists!");
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO users (name, password_hash, passport, phone_number) VALUES (?,?,?,?);");
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getPasswordHash());
                preparedStatement.setString(3, user.getPassport());
                preparedStatement.setString(4, user.getPhoneNumber());
                preparedStatement.executeUpdate();
                logger.info("User was migrated to database.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                logger.error("SQLException: ", e);
            } finally {
                try {
                    if (resultSet != null)
                        resultSet.close();
                    if (preparedStatement != null)
                        preparedStatement.close();
                } catch (SQLException e) {
                    logger.error("SQLException while closing resultSet or preparedStatement.");
                }
            }
        }
    }

}
