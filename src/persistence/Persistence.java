
package persistence;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.XMLIO;
import xmlmessages.XMLMessages;
import model.Message;

public class Persistence {

    public static void loadXML(String XMLfile) {
        Document dom = XMLIO.getDOMFromFile(XMLfile);
        NodeList messages = XMLIO.select(dom, "/mensajes/mensaje");

        for (int i = 0; i < messages.getLength(); i++) {
            Node message = messages.item(i);
            System.out.println(i + 1);
            if (message.getNodeType() == Node.ELEMENT_NODE)
                loadMessage(message);
        }
    }
    static void loadMessage(Node message) {
        // Message fields
        String sender, receiver, text, date, time;
        // Get message attributes
        NodeList messageAttributes = message.getChildNodes();
        for (int i = 0; i < messageAttributes.getLength(); i++) {
            Node messageAttribute = messageAttributes.item(i);

            System.out.println("Name: "+messageAttribute.getNodeName()
                    +" - Value: "+messageAttribute.getNodeValue()
                    +" - Childs: "+messageAttribute.getChildNodes().getLength()
            );

            if (messageAttribute.getNodeType() == Node.ELEMENT_NODE) {
                switch (messageAttribute.getNodeName()) {
                    case "emisor":
                        sender = messageAttribute.getNodeValue();
                        break;
                    case "receptor":
                        receiver = messageAttribute.getNodeValue();
                        break;
                    case "texto":
                        text = messageAttribute.getNodeValue();
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }

//        XMLMessages.messages.add(new Message(sender, receiver, text, text, text))
    }
}