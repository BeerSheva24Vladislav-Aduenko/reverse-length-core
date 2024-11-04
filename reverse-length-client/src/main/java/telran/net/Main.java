package telran.net;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandardInputOutput;

public class Main {
    static ReverseClient ReverseClient;

    public static void main(String[] args) {
        Item[] items = {
                Item.of("Start session", Main::startSession),
                Item.of("Exit", Main::exit, true)
        };
        Menu menu = new Menu("Reverse Client Application", items);
        menu.perform(new StandardInputOutput());
    }

    static void startSession(InputOutput io) {
        String host = io.readString("Enter hostName: ");
        int port = io.readNumberRange("Enter port: ", "Wrong port", 3000, 50000).intValue();
        if (ReverseClient != null) {
            ReverseClient.close();
        }
        ReverseClient = new ReverseClient(host, port);
        Menu menu = new Menu("Run session",
                Item.of("Send reverse string", Main::sendStringReverse),
                Item.of("Send string lenght", Main::sendStringLength),
                Item.ofExit());
        menu.perform(io);
    }

    static void sendStringReverse(InputOutput io) {
        String string = io.readString("Enter string: ");
        String result = ReverseClient.sendAndReceive("reverse " + string);
        io.writeLine(result);
    }

    static void sendStringLength(InputOutput io) {
        String string = io.readString("Enter string: ");
        String result = ReverseClient.sendAndReceive("length " + string);
        io.writeLine(result);
    }

    static void exit(InputOutput io) {
        if (ReverseClient != null) {
            ReverseClient.close();
        }
    }
}