package commons.utils;

import commons.elements.Status;
import commons.elements.Worker;

import java.util.List;

/**
 * Интерфейс для классов, управляющих взаимодействием с коллекцией.
 */
public interface InteractionInterface {

    String info();

    String show();

    void add(Worker worker);

    void update(long id, Worker worker);

    void removeById(long id);

    void clear();

    void save();

    void addIfMin(Worker worker);

    void removeGreater(Worker worker);

    void removeLower(Worker worker);

    long countByStatus(Status status);

    String printAscending();

    List<String> printUniqueOrganization();

    int getSize();

    boolean findById(long key);

    String returnSeparator();
}
