package commons.commands;

import commons.app.Command;
import commons.elements.Worker;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import server.utils.DataBaseCenter;

import java.net.InetAddress;

/**
 * Класс команды remove_lower.
 */
public class RemoveLower extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public RemoveLower() {
        cmdLine = "remove_lower";
        description = "удалить из коллекции все элементы, меньшие, чем заданный";
        needsObject = true;
        argumentAmount = 1;
    }

    /**
     * Метод исполнения
     *
     * @param ui                 объект, через который ведется взаимодействие с пользователем.
     * @param interactiveStorage объект для взаимодействия с коллекцией.
     */
    public void execute(UserInterface ui, InteractionInterface interactiveStorage, Worker worker, InetAddress address, int port, DataBaseCenter dbc) {
        int size1 = interactiveStorage.getSize();
        interactiveStorage.removeLower(worker);
        int size2 = interactiveStorage.getSize();
        if ((size2 < size1) && dbc.removeWorker(worker.getId())) {
            dbc.retrieveCollectionFromDB(interactiveStorage);
            ui.messageToClient("Операция успешно выполнена", address, port);
        }
        if (ui.isInteractionMode()) {
            ui.messageToClient("Awaiting further client instructions.", address, port);
        }
    }
}
