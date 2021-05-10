package commons.app;

import commons.elements.Worker;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import server.utils.DataBaseCenter;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактный класс, от которого наследуются все команды.
 */
public abstract class Command implements Serializable {
    /**
     * Поле, содержащее строку для вызова команды.
     */
    protected String cmdLine;
    /**
     * Поле, содержащее описание команды.
     */
    protected String description;
    protected boolean needsObject;
    protected int argumentAmount;
    protected String argument;
    protected String additionalArgument;
    protected Worker object;

    public void execute(UserInterface ui, InteractionInterface interactiveStorage, InetAddress address, int port) {

    }

    public void execute(UserInterface ui, String argument, InteractionInterface interactiveStorage, InetAddress address, int port) {

    }

    public void execute(UserInterface ui, InteractionInterface interactiveStorage, Worker worker, InetAddress address, int port, DataBaseCenter dbc) {

    }

    public void execute(UserInterface ui, InteractionInterface interactiveStorage, String argument, Worker worker, InetAddress address, int port, DataBaseCenter dbc) {

    }

    public void execute(InteractionInterface interactionInterface) {

    }

    public void execute(UserInterface ui, boolean success, InetAddress clientAddress, int clientPort) {

    }

    /**
     * Стандартный конструктор.
     */
    public Command() {

    }

    /**
     * Возвращает строку, вызывающую команду.
     *
     * @return Строка вызова команды.
     */
    public String getCommand() {
        return cmdLine;
    }

    /**
     * Возвращает описание команды.
     *
     * @return Описание команды.
     */
    public String getHelp() {
        return description;
    }

    public boolean getNeedsObject() {
        return needsObject;
    }

    public int getArgumentAmount() {
        return argumentAmount;
    }

    public void setObject(Worker object) {
        this.object = object;
    }

    public Worker getObject() {
        return this.object;
    }

    public void setArgument(String arg) {
        this.argument = arg;
    }

    public String getArgument() {
        return this.argument;
    }

    public void setStringArgs(String arg1, String arg2) {
        this.argument = arg1;
        this.additionalArgument = arg2;
    }

    public String getAdditionalArgument() {
        return this.additionalArgument;
    }
}
