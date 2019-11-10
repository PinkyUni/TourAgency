package PinkyUni;

import PinkyUni.dao.TourAgencyXmlReader;
import PinkyUni.service.migration.*;
import PinkyUni.exception.AlreadyExistsException;
import PinkyUni.exception.DataSourceException;
import PinkyUni.exception.DatabaseException;
import PinkyUni.exception.ValidationException;
import PinkyUni.service.XSDValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

public class Main {

    private final static String xmlFile = "src/main/resources/data.xml";
    private final static String xsdFile = "src/main/resources/data_schema.xsd";
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            logger.info("Application started...");
            XSDValidator.validate(new File(xmlFile), new File(xsdFile));

            TourAgencyXmlReader tourAgencyXmlReader = new TourAgencyXmlReader(xmlFile);

            CountryMigrationService countryMigrationService = CountryMigrationService.getInstance();
            countryMigrationService.migrate(tourAgencyXmlReader.getCountries());

            HotelMigrationService hotelMigrationService = HotelMigrationService.getInstance();
            hotelMigrationService.migrate(tourAgencyXmlReader.getHotels());

            TourMigrationService tourMigrationService = TourMigrationService.getInstance();
            tourMigrationService.migrate(tourAgencyXmlReader.getTours());

            UserMigrationService userMigrationService = UserMigrationService.getInstance();
            userMigrationService.migrate(tourAgencyXmlReader.getUsers());

            OrderMigrationService orderMigrationService = OrderMigrationService.getInstance();
            orderMigrationService.migrate(tourAgencyXmlReader.getOrders());

            System.out.println("Users - " + tourAgencyXmlReader.getUsers().size());
            System.out.println("Tours - " + tourAgencyXmlReader.getTours().size());
            System.out.println("Orders - " + tourAgencyXmlReader.getOrders().size());
            System.out.println("Hotels - " + tourAgencyXmlReader.getHotels().size());
            System.out.println("Countries - " + tourAgencyXmlReader.getCountries().size());

            logger.info("Success.");
        } catch (XMLStreamException | SAXException | IOException | DataSourceException e) {
            logger.error("Problems with file " + xmlFile, e);
            System.out.println("Problems with file " + xmlFile);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            logger.error("File " + xmlFile + " is not valid", e);
        } catch (AlreadyExistsException e) {
            logger.error("AlreadyExistsException: ", e);
            System.out.println("AlreadyExistsException: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("DatabaseException " + e.getMessage());
            logger.error("DatabaseException: ", e);
        }
        logger.info("Application finished.");
    }

}
