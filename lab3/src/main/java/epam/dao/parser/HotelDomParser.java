package epam.dao.parser;

import epam.dao.DomParser;
import epam.entity.Hotel;
import epam.exception.DataSourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HotelDomParser {

    private static final Logger logger = LogManager.getLogger();
    private List<Hotel> hotelList = null;

    enum HotelTagName {
        NAME, COUNTRY_NAME, ADDRESS, STARS, WEB_SITE, DESCRIPTION
    }

    public HotelDomParser(String filepath) throws DataSourceException {
        File xmlFile = new File(filepath);
        hotelList = new ArrayList<>();
        logger.info("List of hotels created.");
        try {
            Document document = DomParser.parseXmlFile(xmlFile);
            NodeList hotelNodes = document.getDocumentElement().getElementsByTagName("Hotel");
            for (int i = 0; i < hotelNodes.getLength(); i++) {
                if (hotelNodes.item(i).getNodeType() != Node.TEXT_NODE) {
                    hotelList.add(getOrderFromNode(hotelNodes.item(i)));
                    logger.info("a Hotel was added to the list");
                }
                logger.info("All hotels were got by parser (" + hotelList.size() + ")");
            }
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
    }

    public List<Hotel> getHotels() {
        return hotelList;
    }

    private Hotel getOrderFromNode(Node orderNode) {
        Hotel hotel = new Hotel();
        logger.info("New hotel created.");
        hotel.setId(Integer.parseInt(orderNode.getAttributes().getNamedItem("id").getNodeValue()));
        NodeList hotelProps = orderNode.getChildNodes();
        HotelTagName orderTagName = null;
        String str = null;
        for (int j = 0; j < hotelProps.getLength(); j++) {
            if ((hotelProps.item(j).getNodeType() != Node.TEXT_NODE)) {
                try {
                    str = hotelProps.item(j).getNodeName();
                    String content = hotelProps.item(j).getTextContent();
                    orderTagName = HotelTagName.valueOf(str.toUpperCase().replace("-", "_"));
                    logger.info("Tag " + str + " was found.");
                    switch (orderTagName) {
                        case NAME:
                            hotel.setName(content);
                            break;
                        case DESCRIPTION:
                            hotel.setDescription(content);
                            break;
                        case STARS:
                            hotel.setStars(Integer.parseInt(content));
                            break;
                        case ADDRESS:
                            hotel.setAddress(content);
                            break;
                        case WEB_SITE:
                            hotel.setWebSite(content);
                            break;
                        case COUNTRY_NAME:
                            hotel.setCountryName(content);
                            break;
                    }
                } catch (Exception e) {
                    logger.warn("Tag " + str + " was ignored.");
                }
            }
        }
        return hotel;
    }


}
