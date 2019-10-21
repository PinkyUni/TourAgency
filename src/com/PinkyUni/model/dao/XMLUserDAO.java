package com.PinkyUni.model.dao;

import com.PinkyUni.model.entity.User;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XMLUserDAO implements UserDAO {

    private final String filepath = "src/com/sample/users_data.xml";
    private final File xmlFile = new File(filepath);

    @Override
    public void addUser(User user) {
        try {
            Document document = XmlDAO.parseXmlFile(xmlFile);
            Node root = document.getFirstChild();
            Element newNode = createUserNode(user, document);
            root.appendChild(newNode);
            XmlDAO.saveXmlFile(document, filepath);
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            Document document = XmlDAO.parseXmlFile(xmlFile);
            NodeList userNodes = document.getDocumentElement().getElementsByTagName("User");
            for (int i = 0; i < userNodes.getLength(); i++) {
                userList.add(getUserFromNode(userNodes.item(i)));
            }
            System.out.println("finished");
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public boolean exists(User user) {
        ArrayList<User> users = getUsers();
        for (User u: users) {
            if (u.getId() == user.getId())
                return true;
        }
        return false;
    }

    private Element createUserNode(User user, Document document) {
        Element newNode = document.createElement("User");
        newNode.setAttribute("id", String.valueOf(user.getId()));
        newNode.setAttribute("name", user.getName());
        newNode.setAttribute("passwordHash", user.getPasswordHash());
        newNode.setAttribute("passport", user.getPassport());
        newNode.setAttribute("phoneNumber", user.getPhoneNumber());
        return newNode;
    }

    private User getUserFromNode(Node userNode) {
        User user = new User();
        NamedNodeMap attributes = userNode.getAttributes();
        user.setId(Integer.parseInt(attributes.getNamedItem("id").getNodeValue()));
        user.setName(attributes.getNamedItem("name").getNodeValue());
        user.setPasswordHash(attributes.getNamedItem("passwordHash").getNodeValue());
        user.setPassport(attributes.getNamedItem("passport").getNodeValue());
        user.setPhoneNumber(attributes.getNamedItem("phoneNumber").getNodeValue());
        return user;
    }
}
