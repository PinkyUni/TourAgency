package PinkyUni.service.migration;

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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

public class TourMigrationService {

    private static final Logger logger = LogManager.getLogger();
    private static final TourMigrationService instance = new TourMigrationService();

    private TourMigrationService() {
    }

    public static TourMigrationService getInstance() {
        return instance;
    }

    public void migrate(List<Tour> tours) throws AlreadyExistsException, DatabaseException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DbManager dbManager = DbManager.getInstance();
        Connection connection = dbManager.getConnection();
        Format formatter = new SimpleDateFormat("dd.MM.yyyy");
        for (Tour tour : tours) {
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM tours WHERE id = ?;");
                preparedStatement.setInt(1, tour.getId());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.first())
                    throw new AlreadyExistsException("Such tour already exists!");
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO tours (id, name, departure_time, arrival_time, transport, type, description, price, image) VALUES (?,?,?,?,?,?,?,?,?);");
                preparedStatement.setInt(1, tour.getId());
                preparedStatement.setString(2, tour.getName());
                preparedStatement.setString(3, formatter.format(tour.getDepartureTime()));
                preparedStatement.setString(4, formatter.format(tour.getArrivalTime()));
                preparedStatement.setString(5, tour.getTransport().toString());
                preparedStatement.setString(6, tour.getType().toString());
                preparedStatement.setString(7, tour.getDescription());
                preparedStatement.setFloat(8, tour.getPrice());
                preparedStatement.setString(9, tour.getImage());
                preparedStatement.executeUpdate();
                logger.info("Tour was migrated to database.");
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
