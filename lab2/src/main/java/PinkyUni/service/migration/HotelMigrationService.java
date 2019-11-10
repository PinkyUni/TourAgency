package PinkyUni.service.migration;

import PinkyUni.entity.Hotel;
import PinkyUni.entity.Tour;
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

public class HotelMigrationService {

    private static final Logger logger = LogManager.getLogger();
    private static final HotelMigrationService instance = new HotelMigrationService();
    private HotelMigrationService(){}

    public static HotelMigrationService getInstance() {
        return instance;
    }

    public void migrate(List<Hotel> hotels) throws DatabaseException, AlreadyExistsException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DbManager dbManager = DbManager.getInstance();
        Connection connection = dbManager.getConnection();
        for (Hotel hotel : hotels) {
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM hotels WHERE id = ?;");
                preparedStatement.setInt(1, hotel.getId());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.first())
                    throw new AlreadyExistsException("Such hotel already exists!");
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO hotels (id, name, stars, country_name, web_site, description, address) VALUES (?,?,?,?,?,?,?);");
                preparedStatement.setInt(1, hotel.getId());
                preparedStatement.setString(2, hotel.getName());
                preparedStatement.setInt(3, hotel.getStars());
                preparedStatement.setString(4, hotel.getCountryName());
                preparedStatement.setString(5, hotel.getWebSite());
                preparedStatement.setString(6, hotel.getDescription());
                preparedStatement.setString(7, hotel.getAddress());
                preparedStatement.executeUpdate();
                logger.info("Hotel was migrated to database.");
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
                    logger.error("SQLException while closing resultSet or preparedStatement.");
                }
            }
        }
    }

}
