
package xmlmessages;

import java.io.IOException;
import java.util.ArrayList;
import model.Message;
import persistence.Persistence;
import utils.Reader;

public class XMLMessages {

    public static ArrayList<Message> messages = new ArrayList<>();
    static String PERSISTENCE = "messages_test.xml";

    public static void main(String[] args) {
        // Menu variables
        boolean exit = false;
        boolean validOption;
        int menuOption;

        Persistence.loadXML(PERSISTENCE);

        // App loop
        while (!exit) {
            System.out.println(
                "\n*** Messages ***\n"
                + "1 - Check my messages\n"
                + "2 - Send message\n"
                + "3 - Delete message\n"
                + "0 - Exit\n"
                + "****************"
            );

            do {
                validOption = true;

                try {
                    menuOption = Reader.nextInt();
                    switch (menuOption) {
                        case 1: // CHECK MESSAGES
                            System.out.println("Whose messages? (your name)");
                            showMessages(Reader.nextString(), false);
                            break;
                        case 2: // SEND MESSAGE
                            System.out.println("Who's this coming from? (your name)");
                            String sender = Reader.nextString();
                            System.out.println("Who to? (receiver's name)");
                            String receiver = Reader.nextString();
                            System.out.println("Type the message");
                            String txt = Reader.nextLine();

                            sendMessage(sender, receiver, txt);
                            break;
                        case 3: // DELETE MESSAGE
                            System.out.println("Whose message? (your name)");
                            sender = Reader.nextString();
                            showMessages(sender, true);

                            System.out.println("Choose the message to delete");
                            int messageIndex = Reader.nextInt();

                            deleteMessage(messages.get(messageIndex));
                            break;
                        case 0: // EXIT
                            exit = true;
                            break;
                        default:
                            System.out.println("Invalid option selected. Choose a valid one!");
                            validOption = false;
                    }
                } catch (IOException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            } while (!validOption);
        }
    }

    static ArrayList<Message> getMessagesBySender(String sender) {
        ArrayList<Message> messagesFromSender = new ArrayList<>();
        for (Message message : messages) {
            if (message.getSender().equalsIgnoreCase(sender)) {
                messagesFromSender.add(message);
            }
        }

        return messagesFromSender;
    }

    static void showMessages(String sender, boolean indexed) {
        ArrayList<Message> messages = getMessagesBySender(sender);

        System.out.println("\n>> " + sender + "'s messages:");
        if (indexed) {
            for (Message message : messages) {
                System.out.println(messages.indexOf(message) + 1 + ".\n" + message.toString() + "\n");
            }
        } else {
            for (Message message : messages) {
                System.out.println(message + "\n");
            }
        }
    }

    static void sendMessage(String sender, String receiver, String text) {
        Message message = new Message(sender, receiver, text, "today", "now");
        messages.add(message);
        Persistence.writeMessage(message);
    }

    static void deleteMessage(Message message) {
        messages.remove(message);
    }
}