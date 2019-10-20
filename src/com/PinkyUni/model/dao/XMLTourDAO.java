package com.PinkyUni.model.dao;

import com.PinkyUni.model.entity.Country;
import com.PinkyUni.model.entity.Tour;
import com.sun.javafx.scene.paint.GradientUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
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

    private final String filepath = "src/com/sample/data.xml";
    private final File xmlFile = new File(filepath);
    private ArrayList<Tour> tourList = new ArrayList<>();

    @Override
    public void addTour(Tour tour) {
        try {
            Document document = parseXmlFile();
            Node root = document.getFirstChild();
            Element newNode = createTourNode(tour, document);
            root.appendChild(newNode);
            saveXmlFile(document);
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateTour(int id, Tour tour) {
        try {
            Document document = parseXmlFile();
            NodeList tourNodes = document.getDocumentElement().getElementsByTagName("Tour");
            for (int i = 0; i < tourNodes.getLength(); i++) {
                if (Integer.parseInt(tourNodes.item(i).getAttributes().getNamedItem("id").getNodeValue()) == id) {
                    Element newTour = createTourNode(tour, document);
                    tourNodes.item(i).getParentNode().replaceChild(newTour, tourNodes.item(i));
                }
            }
            saveXmlFile(document);
            System.out.println("tour updated");
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTour(int id) {
        try {
            Document document = parseXmlFile();
            NodeList tourNodes = document.getDocumentElement().getElementsByTagName("Tour");
            for (int i = 0; i < tourNodes.getLength(); i++) {
                if (Integer.parseInt(tourNodes.item(i).getAttributes().getNamedItem("id").getNodeValue()) == id) {
                    tourNodes.item(i).getParentNode().removeChild(tourNodes.item(i));
                }
            }
            saveXmlFile(document);
            System.out.println("tour deleted");
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getTours() {
        try {
            Document document = parseXmlFile();
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
//                tour.setId(tours.item(i).getNo);
//                Node tour = tours.item(i);
                // Если нода не текст, то это книга - заходим внутрь
//                if (tour.getNodeType() != Node.TEXT_NODE) {
//                    NodeList tourProps = tour.getChildNodes();
//                    for(int j = 0; j < tourProps.getLength(); j++) {
//                        Node tourProp = tourProps.item(j);
//
//                        // Если нода не текст, то это один из параметров книги - печатаем
//                        if (tourProp.getNodeType() != Node.TEXT_NODE) {
//                            System.out.println(tourProp.getNodeName() + ":" + tourProp.getChildNodes().item(0).getTextContent());
//                        }
//                    }
//                    System.out.println("===========>>>>");
//                }
            }
            System.out.println("finished");
        } catch (SAXException | IOException | ParserConfigurationException | ParseException e) {
            e.printStackTrace();
        }

    }

    private Document parseXmlFile() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        document.normalize();
        return document;
    }

    private void saveXmlFile(Document document) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filepath));
        transformer.transform(source, result);
    }

    private Element createTourNode(Tour tour, Document document) {
        Element newNode = document.createElement("Tour");

        Element id = document.createElement("id");
        id.setTextContent(String.valueOf(tour.getId()));
        newNode.appendChild(id);
        Element name = document.createElement("name");
        name.setTextContent(tour.getName());
        newNode.appendChild(name);
        Element departureTime = document.createElement("departureTime");
        departureTime.setTextContent(String.valueOf(tour.getDepartureTime()));
        newNode.appendChild(departureTime);
        Element arrivalTime = document.createElement("arrivalTime");
        arrivalTime.setTextContent(String.valueOf(tour.getArrivalTime()));
        newNode.appendChild(arrivalTime);
        Element transport = document.createElement("transport");
        transport.setTextContent(String.valueOf(tour.getTransport()));
        newNode.appendChild(transport);
        Element type = document.createElement("type");
        type.setTextContent(String.valueOf(tour.getType()));
        newNode.appendChild(type);
        Element description = document.createElement("description");
        description.setTextContent(String.valueOf(tour.getDescription()));
        newNode.appendChild(description);
        Element price = document.createElement("price");
        price.setTextContent(String.valueOf(tour.getPrice()));
        newNode.appendChild(price);
        Element image = document.createElement("image");
        image.setTextContent(String.valueOf(tour.getImage()));
        newNode.appendChild(image);
        Element countryCodes = document.createElement("countryCodes");
        countryCodes.setTextContent(Arrays.toString(tour.getCountryCodes()));
        newNode.appendChild(countryCodes);
        Element hotelIds = document.createElement("hotelIds");
        hotelIds.setTextContent(Arrays.toString(tour.getHotelIds()));
        newNode.appendChild(hotelIds);
        return newNode;
    }
}
