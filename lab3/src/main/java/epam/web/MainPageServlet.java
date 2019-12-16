package epam.web;

import epam.dao.TourAgencyXmlReader;
import epam.entity.*;
import epam.exception.DataSourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public class MainPageServlet extends HttpServlet {

    private final static String xmlFile = "src/main/resources/data.xml";
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.info("Application started...");
        try {
            TourAgencyXmlReader tourAgencyXmlReader = new TourAgencyXmlReader(xmlFile);
            List<User> users = tourAgencyXmlReader.getUsers();
            List<Order> orders = tourAgencyXmlReader.getOrders();
            List<Hotel> hotels = tourAgencyXmlReader.getHotels();
            List<Country> countries = tourAgencyXmlReader.getCountries();
            List<Tour> tours = tourAgencyXmlReader.getTours();
            req.setAttribute("users", users);
            req.setAttribute("tours", tours);
            req.setAttribute("orders", orders);
            req.setAttribute("hotels", hotels);
            req.setAttribute("countries", countries);
            logger.info("Success.");
            req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
        } catch (XMLStreamException | SAXException | DataSourceException e) {
            e.printStackTrace();
        }
    }

}
