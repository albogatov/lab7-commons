package commons.commands;

import commons.app.Command;
import commons.app.CommandCenter;
import commons.elements.Worker;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;

import java.io.*;
import java.net.InetAddress;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * Класс команды execute_script.
 */
public class ExecuteScript extends Command {
    /**
     * Сет, содержащий пути ко всем скриптам вызванным на разных уровнях.
     */
    private static final HashSet<String> paths = new HashSet<>();
    /**
     * Переменная, отображающая результат выполнения скрипта.
     */
    private static boolean success;

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public ExecuteScript() {
        cmdLine = "execute_script";
        description = "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме";
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
    public void execute(UserInterface ui, String argument, InteractionInterface interactiveStorage, InetAddress address, int port) {
        try {
            paths.add(argument);
            UserInterface scriptInteraction = new UserInterface(new FileReader(argument), new OutputStreamWriter(System.out), false);
            scriptInteraction.connectToServer(ui.getConnection());
            String line;
            success = true;
            //ui.messageToClient("Запущено выполнение скрипта:", address, port);
            while (scriptInteraction.hasNextLine()) {
                line = scriptInteraction.read();
                String cmdLine = line.split(" ")[0];
                String cmdArgument;
                try {
                    cmdArgument = line.split(" ")[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    cmdArgument = null;
                }
                Command cmd = CommandCenter.getInstance().getCmd(cmdLine);
                if (cmd.getArgumentAmount() == 0) {
                    CommandCenter.getInstance().executeCommand(scriptInteraction, cmd, interactiveStorage);
                } else {
                    if (cmd.getArgumentAmount() == 1 && cmd.getNeedsObject()) {
                        Worker worker = scriptInteraction.readWorker(scriptInteraction);
                        CommandCenter.getInstance().executeCommand(scriptInteraction, cmd, interactiveStorage, worker);
                    }
                    if (cmd.getArgumentAmount() == 1 && !cmd.getNeedsObject()) {
                        if (cmd.getCommand().equals("execute_script")) {
                            if (!paths.contains(argument)) {
                                //                  ui.messageToClient("Рекурсия", address, port);
                                paths.add(argument);
                                CommandCenter.getInstance().executeCommand(scriptInteraction, cmd, cmdArgument, interactiveStorage);
                            } else {
                                paths.clear();
                                throw new InvalidAlgorithmParameterException("Выполнение скрипта остановлено, т.к. возможна рекурсия");
                            }
                        }
                        CommandCenter.getInstance().executeCommand(scriptInteraction, cmd, cmdArgument, interactiveStorage);
                    }
                    if (cmd.getArgumentAmount() == 2 && cmd.getNeedsObject()) {
                        Worker worker = scriptInteraction.readWorker(scriptInteraction);
                        CommandCenter.getInstance().executeCommand(scriptInteraction, cmd, cmdArgument, interactiveStorage, worker);
                    }
                }
            }
            paths.clear();
            if (success) {
                ui.messageToClient("Скрипт выполнен", address, port);
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
            //  else ui.messageToClient("Скрипт не выполнен", address, port);
        } catch (InvalidParameterException e) {
            ui.messageToClient("Неверный скрипт", address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
            success = false;
            paths.clear();
        } catch (FileNotFoundException e) {
            ui.messageToClient("В качестве аргумента указан путь к несуществуюшему файлу", address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
            success = false;
            paths.clear();
        } catch (NoSuchElementException e) {
            ui.messageToClient("Скрипт некорректен, проверьте верность введенных команд", address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
            success = false;
            paths.clear();
        } catch (InvalidAlgorithmParameterException e) {
            ui.messageToClient("Выполнение скрипта остановлено, т.к. возможна рекурсия", address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
            success = false;
            paths.clear();
        }
    }
}
