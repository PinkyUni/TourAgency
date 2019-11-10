package PinkyUni.dao.parser;

import PinkyUni.entity.Tour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TourSaxParser extends DefaultHandler {

    enum TourTagName {
        NAME, TYPE, IMAGE, DEPARTURE_TIME, ARRIVAL_TIME, COUNTRIES, PRICE, TRANSPORT, HOTEL_IDS, DESCRIPTION, TOUR, HOTEL_ID, COUNTRY_NAME, TOURS
    }

    private static final Logger logger = LogManager.getLogger();

    private List<Tour> tourList = null;
    private Tour tour;
    private StringBuilder text;
    private List<Integer> hotels;
    private List<String> countryNames;

    public List<Tour> getTours() {
        return tourList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        text = new StringBuilder();
        TourTagName tourTagName = null;
        try {
            tourTagName = TourTagName.valueOf(qName.toUpperCase().replace("-", "_"));
            if (tourTagName.equals(TourTagName.TOURS)) {
                tourList = new ArrayList<>();
                logger.info("Tour list created.");
            }
            if (tourList != null)
                switch (tourTagName) {
                    case TOUR:
                        tour = new Tour();
                        logger.info("New tour created.");
                        tour.setId(Integer.parseInt(attributes.getValue("id")));
                        break;
                    case HOTEL_IDS:
                        hotels = new ArrayList<>();
                        logger.info("List of hotels created.");
                        break;
                    case COUNTRIES:
                        countryNames = new ArrayList<>();
                        logger.info("List of country names created.");
                        break;
                }
        } catch (Exception e) {
            logger.warn("Tag " + qName + "  was ignored.");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        text.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        TourTagName tourTagName = null;
        try {
            tourTagName = TourTagName.valueOf(qName.toUpperCase().replace("-", "_"));
            logger.info("Tag " + tourTagName + " was found.");
        } catch (Exception e) {
            logger.warn("Tag " + tourTagName + "  was ignored.");
        }
        if (tourTagName != null) {
            if (tourTagName.equals(TourTagName.TOURS))
                logger.info("All tours were read by parser (" + tourList.size() + ").");
            if (tour != null)
                switch (tourTagName) {
                    case NAME:
                        tour.setName(text.toString());
                        break;
                    case TYPE:
                        tour.setType(text.toString());
                        break;
                    case IMAGE:
                        tour.setImage(text.toString());
                        break;
                    case PRICE:
                        tour.setPrice(Float.parseFloat(text.toString()));
                        break;
                    case HOTEL_ID:
                        hotels.add(Integer.parseInt(text.toString()));
                        break;
                    case HOTEL_IDS:
                        tour.setHotelIds(hotels);
                        break;
                    case TRANSPORT:
                        tour.setTransport(text.toString());
                        break;
                    case DESCRIPTION:
                        tour.setDescription(text.toString());
                        break;
                    case COUNTRY_NAME:
                        countryNames.add(text.toString());
                        break;
                    case COUNTRIES:
                        logger.info("All countries were got by parser");
                        tour.setCountryNames(countryNames);
                        break;
                    case ARRIVAL_TIME:
                        try {
                            tour.setArrivalTime(new SimpleDateFormat("dd.MM.yyyy").parse(text.toString()));
                        } catch (ParseException e) {
                            logger.error("Error while setting tour arrival time.", e);
                        }
                        break;
                    case DEPARTURE_TIME:
                        try {
                            tour.setDepartureTime(new SimpleDateFormat("dd.MM.yyyy").parse(text.toString()));
                        } catch (ParseException e) {
                            logger.error("Error while setting tour departure time.", e);
                        }
                        break;
                    case TOUR:
                        tourList.add(tour);
                        logger.info("Tour was added to the list.");
                        tour = null;
                        break;
                }
        }
    }

}
