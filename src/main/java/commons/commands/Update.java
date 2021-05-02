package commons.commands;

import commons.app.Command;
import commons.elements.Worker;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;

import java.net.InetAddress;

/**
 * Класс команды update
 */
public class Update extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Update() {
        cmdLine = "update";
        description = "обновить значение элемента коллекции, id которого равен заданному";
        needsObject = true;
        argumentAmount = 2;
    }

    /**
     * Метод исполнения
     *
     * @param ui                 объект, через который ведется взаимодействие с пользователем.
     * @param argument           необходимый для исполнения аргумент.
     * @param interactiveStorage объект для взаимодействия с коллекцией.
     */
    public void execute(UserInterface ui, String argument, InteractionInterface interactiveStorage, Worker worker, InetAddress address, int port) {
        long id = Long.parseLong(argument);
        if (interactiveStorage.findById(id)) {
            interactiveStorage.update(id, worker);
            ui.messageToClient("Сотрудник обновлен", address, port);
        } else ui.messageToClient("Сотрудника с таким идентификатором нет", address, port);
        if (ui.isInteractionMode()) {
            ui.messageToClient("Awaiting further client instructions.", address, port);
        }
    }
}