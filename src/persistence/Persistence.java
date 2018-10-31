
package persistence;

import model.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.XMLIO;
import xmlmessages.XMLMessages;

public class Persistence {

    static XMLIO xmlio = null;

    public static void loadXML(String XMLfilePath) {
        xmlio = new XMLIO(XMLfilePath);
        NodeList messages = xmlio.select("/mensajes/mensaje");

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
        // Get DOM and root node
        Document dom = xmlio.getDOM();
        Node root = dom.getFirstChild();

        // Create new message nodes
        Node messageNode = dom.createElement("mensaje");
        Node senderNode = dom.createElement("emisor");
        Node receiverNode = dom.createElement("receptor");
        Node textNode = dom.createElement("texto");
        Node senderText = dom.createTextNode(sender);
        Node receiverText = dom.createTextNode(receiver);
        Node textText = dom.createTextNode(text);

        // Append message DOM structure
        root.appendChild(messageNode);
        messageNode.appendChild(senderNode);
        messageNode.appendChild(receiverNode);
        messageNode.appendChild(textNode);
        senderNode.appendChild(senderText);
        receiverNode.appendChild(receiverText);
        textNode.appendChild(textText);

        xmlio.writeFromDOM(dom);
    }
}