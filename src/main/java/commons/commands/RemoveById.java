package commons.commands;

import commons.app.Command;
import commons.utils.InteractionInterface;

import commons.utils.UserInterface;
import server.utils.DataBaseCenter;

import java.net.InetAddress;

/**
 * Класс команды remove_by_id.
 */
public class RemoveById extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public RemoveById() {
        cmdLine = "remove_by_id";
        description = "удалить элемент из коллекции по его id";
        needsObject = false;
        argumentAmount = 1;
    }

    /**
     * Метод исполнения
     *
     * @param ui                 объект, через который ведется взаимодействие с пользователем.
     * @param argument           необходимый для исполнения аргумент.
     * @param interactiveStorage объект для взаимодействия с коллекцией.
     */
    public void execute(UserInterface ui, String argument, InteractionInterface interactiveStorage, InetAddress address, int port, DataBaseCenter dbc) {
        try {
            long id = Long.parseLong(argument);
            interactiveStorage.getStorage().getIdList().stream().forEach(System.out::println);
            if(interactiveStorage.findById(id))
                System.out.println("FOUND");
            if (interactiveStorage.findById(id) && dbc.removeWorker(id)) {
                interactiveStorage.removeById(id);
                dbc.retrieveCollectionFromDB(interactiveStorage);
                ui.messageToClient("Сотрудник удален", address, port);
            } else ui.messageToClient("Сотрудник с таким id не найден", address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
        } catch (NumberFormatException e) {
            ui.messageToClient("Введен неверный аргумент команды", address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
        }
    }
}
