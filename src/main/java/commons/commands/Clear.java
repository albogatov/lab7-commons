package commons.commands;

import commons.app.Command;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;

import java.net.InetAddress;

/**
 * Класс команды clear.
 */
public class Clear extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Clear() {
        cmdLine = "clear";
        description = "очистить коллекцию";
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
        interactiveStorage.clear();
        if (interactiveStorage.getSize() > 0)
            ui.messageToClient("Что-то пошло не так, попробуйте еще раз", address, port);
        else ui.messageToClient("Коллекция очищена", address, port);
        if (ui.isInteractionMode()) {
            ui.messageToClient("Awaiting further client instructions.", address, port);
        }
    }
}
