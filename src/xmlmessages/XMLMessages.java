
package xmlmessages;

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
                menuOption = Reader.nextInt();

                try {
                    switch (menuOption) {
                        case 1: // CHECK MESSAGES
                            break;
                        case 2: // SEND MESSAGE
                            break;
                        case 0: // EXIT
                            exit = true;
                            break;
                        default:
                            System.out.println("Invalid option selected. Choose a valid one!");
                            validOption = false;
                    }
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            } while (!validOption);
        }
    }
}