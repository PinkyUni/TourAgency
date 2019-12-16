package epam.dao.parser;

import epam.dao.DomParser;
import epam.entity.Country;
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

public class CountryDomParser {

    private static final Logger logger = LogManager.getLogger();
    private List<Country> countries = null;

    enum CountryTagName {
        NAME, DESCRIPTION
    }

    public CountryDomParser(String filepath) throws DataSourceException {
        File xmlFile = new File(filepath);
        countries = new ArrayList<>();
        logger.info("A list of Countries was created.");
        try {
            Document document = DomParser.parseXmlFile(xmlFile);
            NodeList countryNodes = document.getDocumentElement().getElementsByTagName("Country");
            for (int i = 0; i < countryNodes.getLength(); i++) {
                if (countryNodes.item(i).getNodeType() != Node.TEXT_NODE) {
                    countries.add(getCountryFromNode(countryNodes.item(i)));
                }
            }
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
    }

    public List<Country> getCountries() {
        return countries;
    }

    private Country getCountryFromNode(Node orderNode) {
        Country country = new Country();
        NodeList countryProps = orderNode.getChildNodes();
        CountryTagName orderTagName = null;
        String str = null;
        for (int j = 0; j < countryProps.getLength(); j++) {
            if (countryProps.item(j).getNodeType() != Node.TEXT_NODE) {
                try {
                    str = countryProps.item(j).getNodeName();
                    orderTagName = CountryTagName.valueOf(str.toUpperCase().replace("-", "_"));
                    switch (orderTagName) {
                        case NAME:
                            country.setName(countryProps.item(j).getTextContent());
                            break;
                        case DESCRIPTION:
                            country.setDescription(countryProps.item(j).getTextContent());
                            break;
                    }
                } catch (Exception e) {
                }
            }
        }
        return country;
    }

}
