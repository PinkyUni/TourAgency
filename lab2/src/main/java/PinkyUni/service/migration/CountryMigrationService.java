package PinkyUni.service.migration;

import PinkyUni.entity.Country;
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

public class CountryMigrationService {

    private static final Logger logger = LogManager.getLogger();
    private static final CountryMigrationService instance = new CountryMigrationService();

    private CountryMigrationService() {
    }

    public static CountryMigrationService getInstance() {
        return instance;
    }

    public void migrate(List<Country> countries) throws DatabaseException, AlreadyExistsException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DbManager dbManager = DbManager.getInstance();
        Connection connection = dbManager.getConnection();
        for (Country country : countries) {
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM countries WHERE name = ?;");
                preparedStatement.setString(1, country.getName());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.first())
                    throw new AlreadyExistsException("Such country already exists!");
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO countries (name, description) VALUES (?,?);");
                preparedStatement.setString(1, country.getName());
                preparedStatement.setString(2, country.getDescription());
                preparedStatement.executeUpdate();
                logger.info("Country was migrated to database.");
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
                    logger.error("SQLException during closing resultSet or preparedStatement.");
                }
            }
        }
    }

}
