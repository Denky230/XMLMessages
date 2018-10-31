
package persistence;

import model.Message;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.XMLIO;
import xmlmessages.XMLMessages;

public class Persistence {
    
    static XMLIO XMLIO = null;

    public static void loadXML(String XMLfilePath) {
        XMLIO = new XMLIO(XMLfilePath);
        NodeList messages = XMLIO.select("/mensajes/mensaje");

        for (int i = 0; i < messages.getLength(); i++) {
            Node message = messages.item(i);
            if (message.getNodeType() == Node.ELEMENT_NODE)
                loadMessage(message);
        }
    }
    static void loadMessage(Node message) {
        // Message fields
        String sender = "", receiver = "", text = "", date = "", time = "";
        
        // Get message attributes
        NodeList messageAttributes = message.getChildNodes();
        for (int i = 0; i < messageAttributes.getLength(); i++) {
            Node currMessageAttribute = messageAttributes.item(i);
            
            if (currMessageAttribute.getNodeType() == Node.ELEMENT_NODE) {
                // Check which attribute is currMessageAttribute
                // Get value from its only child since we know the tree ends here
                switch (currMessageAttribute.getNodeName()) {
                    case "emisor":
                        sender = currMessageAttribute.getFirstChild().getNodeValue();
                        break;
                    case "receptor":
                        receiver = currMessageAttribute.getFirstChild().getNodeValue();
                        break;
                    case "texto":
                        text = currMessageAttribute.getFirstChild().getNodeValue();
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }

        XMLMessages.messages.add(new Message(sender, receiver, text, "today", "now"));
    }
    
    public static void writeMessage(Message message) {
        String sender = message.getSender();
        String receiver = message.getReceiver();
        String text = message.getText();
    }
}