package PinkyUni.dao.parser;

import PinkyUni.dao.DomParser;
import PinkyUni.entity.Order;
import PinkyUni.exception.DataSourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderDomParser {

    private static final Logger logger = LogManager.getLogger();
    private List<Order> orderList = null;

    enum OrderTagName {
        TOUR_ID, USER_PASSPORT
    }

    public OrderDomParser(String filepath) throws DataSourceException {
        File xmlFile = new File(filepath);
        orderList = new ArrayList<>();
        logger.info("Order list created.");
        try {
            Document document = DomParser.parseXmlFile(xmlFile);
            NodeList orderNodes = document.getDocumentElement().getElementsByTagName("Order");
            for (int i = 0; i < orderNodes.getLength(); i++) {
                if (orderNodes.item(i).getNodeType() != Node.TEXT_NODE) {
                    orderList.add(getOrderFromNode(orderNodes.item(i)));
                    logger.info("Order added to the list.");
                }
            }
            logger.info("All orders were got by parser (" + orderList.size() + ")");
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
    }

    public List<Order> getOrders() {
        return orderList;
    }

    private Order getOrderFromNode(Node orderNode) {
        logger.info("New order created.");
        Order order = new Order();
        order.setId(Integer.parseInt(orderNode.getAttributes().getNamedItem("id").getNodeValue()));
        NodeList orderProps = orderNode.getChildNodes();
        OrderTagName orderTagName = null;
        String str = null;
        for (int j = 0; j < orderProps.getLength(); j++) {
            if ((orderProps.item(j).getNodeType() != Node.TEXT_NODE)) {
                try {
                    str = orderProps.item(j).getNodeName();
                    orderTagName = OrderTagName.valueOf(str.toUpperCase().replace("-", "_"));
                    logger.info("Tag " + str + " was found.");
                    switch (orderTagName) {
                        case TOUR_ID:
                            order.setTourId(Integer.parseInt(orderProps.item(j).getTextContent()));
                            break;
                        case USER_PASSPORT:
                            order.setUserPassport(orderProps.item(j).getTextContent());
                            break;
                    }
                } catch (Exception e) {
                    logger.warn("Tag " + str + "  was ignored.");
                }
            }
        }
        return order;
    }
}
