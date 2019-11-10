package PinkyUni.service.migration;

import PinkyUni.entity.Order;
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

public class OrderMigrationService {

    private static final Logger logger = LogManager.getLogger();
    private static final OrderMigrationService instance = new OrderMigrationService();

    private OrderMigrationService(){
    }

    public static OrderMigrationService getInstance() {
        return instance;
    }

    public void migrate(List<Order> orders)  throws DatabaseException, AlreadyExistsException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DbManager dbManager = DbManager.getInstance();
        Connection connection = dbManager.getConnection();
        for (Order order : orders) {
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM orders WHERE id = ?;");
                preparedStatement.setInt(1, order.getId());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.first()) {
                    throw new AlreadyExistsException("Such order already exists!");
                }
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO orders (id, user_passport, tour_id) VALUES (?,?,?);");
                preparedStatement.setInt(1, order.getId());
                preparedStatement.setString(2, order.getUserPassport());
                preparedStatement.setInt(3, order.getTourId());
                preparedStatement.executeUpdate();
                logger.info("Order was migrated to database.");
            } catch (SQLException e) {
                logger.error("SQLException: ", e);
                System.out.println(e.getMessage());
            } finally {
                try {
                    if (resultSet != null)
                        resultSet.close();
                    if (preparedStatement != null)
                        preparedStatement.close();
                } catch (SQLException e) {
                    logger.info("SQLException while closing resultSet or preparedStatement.");
                }
            }
        }
    }

}
