package com.PinkyUni.model.dao.impl;

import com.PinkyUni.exceptions.DataSourceException;
import com.PinkyUni.exceptions.NotFoundException;
import com.PinkyUni.model.dao.TourDAO;
import com.PinkyUni.model.dao.XmlDAO;
import com.PinkyUni.model.entity.Country;
import com.PinkyUni.model.entity.Order;
import com.PinkyUni.model.entity.Tour;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XMLTourDAO implements TourDAO {

    private final String filepath = "src/com/sample/tours_data.xml";
    private final File xmlFile = new File(filepath);

    @Override
    public void addTour(Tour tour) throws DataSourceException {
        try {
            Document document = XmlDAO.parseXmlFile(xmlFile);
            Node root = document.getFirstChild();
            Element newNode = createTourNode(tour, document);
            root.appendChild(newNode);
            XmlDAO.saveXmlFile(document, filepath);
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException ex) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
    }

    @Override
    public void updateTour(int id, Tour tour) throws DataSourceException, NotFoundException {
        try {
            Tour oldTour = getById(id);

            Document document = XmlDAO.parseXmlFile(xmlFile);
            NodeList tourNodes = document.getDocumentElement().getElementsByTagName("Tour");
            Node oldNode = null;
            for (int i = 0; i < tourNodes.getLength(); i++)
                if (Integer.parseInt(tourNodes.item(i).getAttributes().getNamedItem("id").getNodeValue()) == id)
                    oldNode = tourNodes.item(i);
            Element newNode = createTourNode(tour, document);
            newNode.setAttribute("id", String.valueOf(oldTour.getId()));
            document.replaceChild(newNode, oldNode);

            XmlDAO.saveXmlFile(document, filepath);
            System.out.println("tour updated");
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
    }

    @Override
    public void deleteTour(int id) throws DataSourceException {
        try {
            Document document = XmlDAO.parseXmlFile(xmlFile);
            NodeList tourNodes = document.getDocumentElement().getElementsByTagName("Tour");
            for (int i = 0; i < tourNodes.getLength(); i++) {
                if (Integer.parseInt(tourNodes.item(i).getAttributes().getNamedItem("id").getNodeValue()) == id) {
                    tourNodes.item(i).getParentNode().removeChild(tourNodes.item(i));
                }
            }
            XmlDAO.saveXmlFile(document, filepath);
            System.out.println("tour deleted");
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
    }

    @Override
    public ArrayList<Tour> getTours() throws DataSourceException {
        ArrayList<Tour> tourList = new ArrayList<>();
        try {
            Document document = XmlDAO.parseXmlFile(xmlFile);
            NodeList tourNodes = document.getDocumentElement().getElementsByTagName("Tour");
            for (int i = 0; i < tourNodes.getLength(); i++) {
                tourList.add(getTourFromNode(tourNodes.item(i)));
            }
            System.out.println("finished");
        } catch (SAXException | IOException | ParserConfigurationException | ParseException e) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
        return tourList;
    }

    private Element createTourNode(Tour tour, Document document) {
        Element newNode = document.createElement("Tour");
        newNode.setAttribute("id", String.valueOf(tour.getId()));
        newNode.setAttribute("name", tour.getName());
        newNode.setAttribute("departureTime", String.valueOf(tour.getDepartureTime()));
        newNode.setAttribute("arrivalTime", String.valueOf(tour.getArrivalTime()));
        newNode.setAttribute("description", tour.getDescription());
        newNode.setAttribute("transport", String.valueOf(tour.getTransport()));
        newNode.setAttribute("type", String.valueOf(tour.getType()));
        newNode.setAttribute("price", String.valueOf(tour.getPrice()));
        newNode.setAttribute("image", tour.getImage());
        newNode.setAttribute("countryCodes", Arrays.toString(tour.getCountryCodes()));
        newNode.setAttribute("hotelIds", Arrays.toString(tour.getHotelIds()));
        return newNode;
    }

    private Tour getTourFromNode(Node tourNode) throws ParseException {
        Tour tour = new Tour();
        NamedNodeMap attributes = tourNode.getAttributes();
        tour.setId(Integer.parseInt(attributes.getNamedItem("id").getNodeValue()));
        tour.setName(attributes.getNamedItem("name").getNodeValue());
        tour.setDepartureTime(new SimpleDateFormat("dd.MM.yyyy").parse(attributes.getNamedItem("departureTime").getNodeValue()));
        tour.setArrivalTime(new SimpleDateFormat("dd.MM.yyyy").parse(attributes.getNamedItem("arrivalTime").getNodeValue()));
        tour.setTransport(attributes.getNamedItem("transport").getNodeValue());
        tour.setType(attributes.getNamedItem("type").getNodeValue());
        tour.setDescription(attributes.getNamedItem("description").getNodeValue());
        tour.setPrice(Float.parseFloat(attributes.getNamedItem("price").getNodeValue()));
        tour.setImage(attributes.getNamedItem("image").getNodeValue());
        String[] codes = attributes.getNamedItem("countryCodes").getNodeValue().split(",");
        Country.CountryCode[] countryCodes = new Country.CountryCode[codes.length];
        for (int j = 0; j < codes.length; j++) {
            countryCodes[j] = Country.CountryCode.valueOf(codes[j]);
        }
        tour.setCountryCodes(countryCodes);
        tour.setHotelIds(attributes.getNamedItem("hotelIds").getNodeValue().split(","));
        return tour;
    }

    public Tour getById(int id) throws DataSourceException, NotFoundException {
        ArrayList<Tour> tours = getTours();
        for (Tour tour : tours) {
            if (tour.getId() == id) {
                return tour;
            }
        }
        throw new NotFoundException("Tour " + id + " not found");
    }

    public List<Tour> getByUser(List<Order> orders) throws NotFoundException, DataSourceException {
        List<Tour> tours = new ArrayList<>();
        for (Order order: orders) {
            tours.add(getById(order.getTourId()));
        }
        return tours;
    }
}
