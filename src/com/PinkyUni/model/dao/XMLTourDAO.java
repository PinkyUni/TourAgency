package com.PinkyUni.model.dao;

import com.PinkyUni.model.entity.Country;
import com.PinkyUni.model.entity.Tour;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class XMLTourDAO implements TourDAO {

    private final String filepath = "src/com/sample/tours_data.xml";
    private final File xmlFile = new File(filepath);

    @Override
    public void addTour(Tour tour) {
        try {
            Document document = XmlDAO.parseXmlFile(xmlFile);
            Node root = document.getFirstChild();
            Element newNode = createTourNode(tour, document);
            root.appendChild(newNode);
            XmlDAO.saveXmlFile(document, filepath);
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateTour(int id, Tour tour) {
        try {
            Document document = XmlDAO.parseXmlFile(xmlFile);
            NodeList tourNodes = document.getDocumentElement().getElementsByTagName("Tour");
            for (int i = 0; i < tourNodes.getLength(); i++) {
                if (Integer.parseInt(tourNodes.item(i).getAttributes().getNamedItem("id").getNodeValue()) == id) {
                    Element newTour = createTourNode(tour, document);
                    tourNodes.item(i).getParentNode().replaceChild(newTour, tourNodes.item(i));
                }
            }
            XmlDAO.saveXmlFile(document, filepath);
            System.out.println("tour updated");
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTour(int id) {
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
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Tour> getTours() {
        ArrayList<Tour> tourList = new ArrayList<>();
        try {
            Document document = XmlDAO.parseXmlFile(xmlFile);
            NodeList tourNodes = document.getDocumentElement().getElementsByTagName("Tour");
            for (int i = 0; i < tourNodes.getLength(); i++) {
                Tour tour = new Tour();
                Node tourNode = tourNodes.item(i);
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
                tourList.add(tour);
            }
            System.out.println("finished");
        } catch (SAXException | IOException | ParserConfigurationException | ParseException e) {
            e.printStackTrace();
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
}
