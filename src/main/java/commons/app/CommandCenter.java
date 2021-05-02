package commons.app;

import commons.commands.*;
import commons.elements.Worker;
//import server.interaction.InteractionInterface;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
//import server.Server;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Класс отвечающий за распознование и вызов команд.
 */
public class CommandCenter {
//    private static final Logger logger = Logger.getLogger(
//            Server.class.getName());
    private static InetAddress clientAddress;
    private static int clientPort;
    /**
     * Объект центра управления командами.
     */
    public static CommandCenter commandCenter;
    /**
     * Список всех возможных команд.
     */
    private final HashMap<String, Command> commands = new HashMap<>();

    /**
     * Конструктор центра комманд, где добавляются все возможные команды.
     */
    private CommandCenter() {
        addCmd(new Add());
        addCmd(new Help());
        addCmd(new AddIfMin());
        addCmd(new Info());
        addCmd(new Show());
        addCmd(new Save());
        addCmd(new Update());
        addCmd(new RemoveById());
        addCmd(new Clear());
        addCmd(new ExecuteScript());
        addCmd(new Exit());
        addCmd(new RemoveGreater());
        addCmd(new RemoveLower());
        addCmd(new CountByStatus());
        addCmd(new PrintAscending());
        addCmd(new PrintUniqueOrganization());
    }

    /**
     * Метод передачи команды в конструктор.
     *
     * @param cmd Команда.
     */
    public void addCmd(Command cmd) {
        commands.put(cmd.getCommand(), cmd);
    }

    /**
     * Метод, распознающий команду в строке, введенной пользователем.
     *
     * @param cmdLine Строка, содержащая команду.
     * @return Объект класса соответсвующей команды.
     */
    public Command getCmd(String cmdLine) {
        return commands.getOrDefault(cmdLine, null);
    }

    /**
     * Метод, возвращающий единственный объект класса. Реализация шаблона "Синглтон".
     *
     * @return Объект центра управления командами.
     */
    public static CommandCenter getInstance() {
        if (commandCenter == null)
            return new CommandCenter();
        return commandCenter;
    }

    /**
     * Метод, возврашающий полный список всех команд.
     *
     * @return Список команд.
     */
    public List<Command> retrieveAllCommands() {
        return commands.keySet().stream().map(commands::get).collect(Collectors.toList());
    }

    /**
     * Метод, вызывающий исполнение команды.
     *
     * @param ui                 объект, через который ведется взаимодействие с пользователем.
     * @param line               часть строки пользовательского ввода, содержающая команду.
     * @param interactiveStorage объект для взаимодействия с коллекцией.
     */
    public void executeCommand(UserInterface ui, String line, InteractionInterface interactiveStorage) {
//        logger.log(Level.INFO, "Executing server command initiated by user's actions");
        Command cmd = getCmd(line);
        cmd.execute(ui, interactiveStorage, clientAddress, clientPort);
    }

    public void executeCommand(UserInterface ui, Command cmd, InteractionInterface interactiveStorage) {
//        logger.log(Level.INFO, "Executing user command with no arguments");
        cmd.execute(ui, interactiveStorage, clientAddress, clientPort);
    }

    public void executeCommand(UserInterface ui, Command cmd, String argument, InteractionInterface interactiveStorage) {
//        logger.log(Level.INFO, "Executing user command with a string argument");
        cmd.execute(ui, argument, interactiveStorage, clientAddress, clientPort);
    }

    public void executeCommand(UserInterface ui, Command cmd, InteractionInterface interactiveStorage, Worker worker) {
//        logger.log(Level.INFO, "Executing user command with an object argument");
        cmd.execute(ui, interactiveStorage, worker, clientAddress, clientPort);
    }

    public void executeCommand(UserInterface ui, Command cmd, String argument, InteractionInterface interactiveStorage, Worker worker) {
//        logger.log(Level.INFO, "Executing user command with two arguments");
        cmd.execute(ui, interactiveStorage, argument, worker, clientAddress, clientPort);
    }

    public void executeServerCommand(Command cmd, InteractionInterface interactiveStorage) {
//        logger.log(Level.INFO, "Executing server command");
        cmd.execute(interactiveStorage);
    }

    public static void setClientAddress(InetAddress address) {
//        logger.log(Level.INFO, "Tying client's address to the command center");
        clientAddress = address;
    }

    public static void setClientPort(int port) {
//        logger.log(Level.INFO, "Tying client's port to the command center");
        clientPort = port;
    }
}
