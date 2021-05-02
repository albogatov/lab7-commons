package commons.commands;

import commons.app.Command;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;

import java.net.InetAddress;

/**
 * Класс команды info.
 */
public class Info extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Info() {
        cmdLine = "info";
        description = "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
        needsObject = false;
        argumentAmount = 0;
    }

    /**
     * Метод исполнения
     *
     * @param ui                 объект, через который ведется взаимодействие с пользователем.
     * @param interactiveStorage объект для взаимодействия с коллекцией.
     */
    public void execute(UserInterface ui, InteractionInterface interactiveStorage, InetAddress address, int port) {
        ui.messageToClient(interactiveStorage.info(), address, port);
        if (ui.isInteractionMode()) {
            ui.messageToClient("Awaiting further client instructions.", address, port);
        }
    }
}
