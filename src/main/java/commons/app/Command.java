package commons.app;

import commons.elements.Worker;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;

import java.io.Serializable;
import java.net.InetAddress;

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
    protected Worker object;

    public void execute(UserInterface ui, InteractionInterface interactiveStorage, InetAddress address, int port) {

    }

    public void execute(UserInterface ui, String argument, InteractionInterface interactiveStorage, InetAddress address, int port) {

    }

    public void execute(UserInterface ui, InteractionInterface interactiveStorage, Worker worker, InetAddress address, int port) {

    }

    public void execute(UserInterface ui, InteractionInterface interactiveStorage, String argument, Worker worker, InetAddress address, int port) {

    }

    public void execute(InteractionInterface interactionInterface) {

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
}
