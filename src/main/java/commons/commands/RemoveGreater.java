package commons.commands;

import commons.app.Command;
import commons.elements.Worker;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import server.utils.DataBaseCenter;

import java.net.InetAddress;

/**
 * Класс команды remove_greater.
 */
public class RemoveGreater extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public RemoveGreater() {
        cmdLine = "remove_greater";
        description = "удалить из коллекции все элементы, превышающие заданный";
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
        interactiveStorage.removeGreater(worker);
        int size2 = interactiveStorage.getSize();
        if ((size2 < size1) && dbc.removeWorker(worker.getId())) {
            ui.messageToClient("Операция успешно выполнена", address, port);
            dbc.retrieveCollectionFromDB(interactiveStorage);
        }
        if (ui.isInteractionMode()) {
            ui.messageToClient("Awaiting further client instructions.", address, port);
        }
    }
}
