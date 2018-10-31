
package xmlmessages;

import java.io.IOException;
import java.util.ArrayList;
import model.Message;
import persistence.Persistence;
import utils.Reader;

public class XMLMessages {

    public static ArrayList<Message> messages = new ArrayList<>();
    static String PERSISTENCE = "messages.xml";

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
                + "0 - Exit\n"
                + "****************"
            );

            do {
                validOption = true;

                try {
                    menuOption = Reader.nextInt();
                    switch (menuOption) {
                        case 1: // CHECK MESSAGES
                            System.out.println("Whose messages? (name)");
                            checkMessages(Reader.nextString());
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
                        case 0: // EXIT
                            exit = true;
                            break;
                        default:
                            System.out.println("Invalid option selected. Choose a valid one!");
                            validOption = false;
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } while (!validOption);
        }
    }
    
    static void checkMessages(String sender) {
        System.out.println("\n>> " + sender + "'s messages:");
        for (Message message : messages) {
            if (message.getSender().equalsIgnoreCase(sender)) {
                System.out.println(message);
                System.out.println();
            }
        }
    }
    
    static void sendMessage(String sender, String receiver, String text) {
        Message message = new Message(sender, receiver, text, "today", "now");
        messages.add(message);
        Persistence.writeMessage(message);
    }
}