package epam.dao.parser;

import epam.dao.DomParser;
import epam.entity.Order;
import epam.exception.DataSourceException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderDomParser {

    private List<Order> orderList = null;

    enum OrderTagName {
        TOUR_ID, USER_PASSPORT
    }

    public OrderDomParser(String filepath) throws DataSourceException {
        File xmlFile = new File(filepath);
        orderList = new ArrayList<>();
        try {
            Document document = DomParser.parseXmlFile(xmlFile);
            NodeList orderNodes = document.getDocumentElement().getElementsByTagName("Order");
            for (int i = 0; i < orderNodes.getLength(); i++) {
                if (orderNodes.item(i).getNodeType() != Node.TEXT_NODE) {
                    orderList.add(getOrderFromNode(orderNodes.item(i)));
                }
            }
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
    }

    public List<Order> getOrders() {
        return orderList;
    }

    private Order getOrderFromNode(Node orderNode) {
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
                    switch (orderTagName) {
                        case TOUR_ID:
                            order.setTourId(Integer.parseInt(orderProps.item(j).getTextContent()));
                            break;
                        case USER_PASSPORT:
                            order.setUserPassport(orderProps.item(j).getTextContent());
                            break;
                    }
                } catch (Exception e) {
                }
            }
        }
        return order;
    }
}
