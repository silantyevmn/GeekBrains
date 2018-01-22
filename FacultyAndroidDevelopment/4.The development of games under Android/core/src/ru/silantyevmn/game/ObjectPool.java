package ru.silantyevmn.game;

import java.util.ArrayList;
import java.util.List;
/**
 * ru.silantyevmn.game
 * Created by Михаил Силантьев on 04.01.2018.
 */

public abstract class ObjectPool<T extends Poolable> {
    protected List<T> activeList;
    protected List<T> freeList;

    public List<T> getActiveList() {
        return activeList;
    }

    public List<T> getFreeList() {
        return freeList;
    }

    protected abstract T newObject();

    public void free(int index) {
        freeList.add(activeList.remove(index));
    }

    public ObjectPool() {
        this.activeList = new ArrayList<T>();
        this.freeList = new ArrayList<T>();
    }

    public ObjectPool(int size) {
        this.activeList = new ArrayList<T>();
        this.freeList = new ArrayList<T>();
        for (int i = 0; i < size; i++) {
            freeList.add(newObject());
        }
    }

    public T getActiveElement() {
        if (freeList.size() == 0) {
            freeList.add(newObject());
        }
        T temp = freeList.remove(freeList.size() - 1);
        activeList.add(temp);
        return temp;
    }

    public void checkPool() {
        for (int i = activeList.size() - 1; i >= 0; i--) {
            if (!activeList.get(i).isActive()) {
                free(i);
            }
        }
    }
}
