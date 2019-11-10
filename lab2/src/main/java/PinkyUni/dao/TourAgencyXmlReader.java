package PinkyUni.dao;

import PinkyUni.dao.parser.*;
import PinkyUni.entity.*;
import PinkyUni.exception.DataSourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public class TourAgencyXmlReader {

    private static final Logger logger = LogManager.getLogger();
    private UserStaxParser userStaxParser = null;
    private TourSaxParser tourSaxParser = null;
    private OrderDomParser orderDomParser = null;
    private HotelDomParser hotelDomParser = null;
    private CountryDomParser countryDomParser = null;

    private List<User> users;
    private List<Tour> tours;
    private List<Order> orders;
    private List<Hotel> hotels;
    private List<Country> countries;

    public TourAgencyXmlReader(String file) throws XMLStreamException, SAXException, IOException, DataSourceException {
        logger.info("Xml reader starts parsing file - " + file);
        userStaxParser = new UserStaxParser(file);
        logger.info("Tour sax parser created.");
        tourSaxParser = new TourSaxParser();
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        xmlReader.setContentHandler(tourSaxParser);
        xmlReader.parse(new InputSource(file));
        orderDomParser = new OrderDomParser(file);
        hotelDomParser = new HotelDomParser(file);
        countryDomParser = new CountryDomParser(file);
    }
    public List<User> getUsers() {
        if (users == null) {
            users = userStaxParser.getUsers();
            logger.info("User list is ready.");
        }
        return users;
    }

    public List<Hotel> getHotels() {
        if (hotels == null) {
            hotels = hotelDomParser.getHotels();
        }
        return hotels;
    }

    public List<Tour> getTours() {
        if (tours == null) {
            tours = tourSaxParser.getTours();
        }
        return tours;
    }

    public List<Order> getOrders() {
        if (orders == null)
            orders = orderDomParser.getOrders();
        return orders;
    }

    public List<Country> getCountries() {
        if (countries == null) {
            countries = countryDomParser.getCountries();
        }
        return countries;
    }
}
