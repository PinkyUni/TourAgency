package com.PinkyUni.model.dao.impl;

import com.PinkyUni.exceptions.DataSourceException;
import com.PinkyUni.model.dao.OrderDAO;
import com.PinkyUni.model.dao.XmlDAO;
import com.PinkyUni.model.entity.Order;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLOrderDAO implements OrderDAO {

    private final String filepath = "src/com/sample/orders_data.xml";
    private final File xmlFile = new File(filepath);

    @Override
    public void addOrder(int tourId, String userPassport) throws DataSourceException {
        try {
            Document document = XmlDAO.parseXmlFile(xmlFile);
            Node root = document.getFirstChild();
            Element newNode = createOrderNode(new Order(userPassport, tourId), document);
            root.appendChild(newNode);
            XmlDAO.saveXmlFile(document, filepath);
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException ex) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
    }

    @Override
    public List<Order> getOrders() throws DataSourceException {
        ArrayList<Order> orderList = new ArrayList<>();
        try {
            Document document = XmlDAO.parseXmlFile(xmlFile);
            NodeList orderNodes = document.getDocumentElement().getElementsByTagName("Order");
            for (int i = 0; i < orderNodes.getLength(); i++) {
                orderList.add(getOrderFromNode(orderNodes.item(i)));
            }
            System.out.println("finished");
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
        return orderList;
    }

    @Override
    public List<Order> getByUser(String passport) throws DataSourceException {
        List<Order> orders = getOrders();
        ArrayList<Order> userOrders = new ArrayList<>();
        for (Order order: orders) {
            if (order.getUserPassport().equals(passport))
                userOrders.add(order);
        }
        return userOrders;
    }

    private Element createOrderNode(Order order, Document document) {
        Element newNode = document.createElement("Order");
        newNode.setAttribute("id", String.valueOf(order.getId()));
        newNode.setAttribute("tourId", String.valueOf(order.getTourId()));
        newNode.setAttribute("userPassport", order.getUserPassport());
        return newNode;
    }

    private Order getOrderFromNode(Node orderNode) {
        Order order = new Order();
        NamedNodeMap attributes = orderNode.getAttributes();
        order.setId(Integer.parseInt(attributes.getNamedItem("id").getNodeValue()));
        order.setTourId(Integer.parseInt(attributes.getNamedItem("tourId").getNodeValue()));
        order.setUserPassport(attributes.getNamedItem("userPassport").getNodeValue());
        return order;
    }
}
