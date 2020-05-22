package com.example.AndroidStudiolab6;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ItemInfo {
    int maxId = 0;
    private List<Item> items;
    private List<DataChangedListener> listeners;
    private static final ItemInfo ourInstance = new ItemInfo();

    public static ItemInfo getInstance() {
        return ourInstance;
    }

    public List<Item> getAvailableItems() {
        List<Item> availableItems = new LinkedList<>();
        for(Item item : items) {
            if(item.getCount() > 0)
                availableItems.add(item);
        }
        return availableItems;
    }

    public void addDataChangedListener(DataChangedListener listener) {
        listeners.add(listener);
    }

    public void addItem(Item newItem) {
        newItem.setId(maxId + 1);
        maxId +=1;
        items.add(newItem);
        listeners.forEach(new Consumer<DataChangedListener>() {
            @Override
            public void accept(DataChangedListener e) {
                e.notifyDataChanged();
            }
        });
    }

    public void deleteItem(int id) {
        for(Item item : items) {
            if(item.getId() == id)
                items.remove(item);
        }
        listeners.forEach(new Consumer<DataChangedListener>() {
            @Override
            public void accept(DataChangedListener e) {
                e.notifyDataChanged();
            }
        });
    }

    public void updateItem(Item updatedItem) {
        for(Item item :items) {
            if(item.getId() == updatedItem.getId()) {
                    items.set(items.indexOf(item), updatedItem);
                    Cart.getInstance().updateItem(updatedItem);
            }
        }
        listeners.forEach(new Consumer<DataChangedListener>() {
            @Override
            public void accept(DataChangedListener e) {
                e.notifyDataChanged();
            }
        });
    }

    public void removeListener(DataChangedListener listener) {
        listeners.remove(listener);
    }

    public void clearListeners() {
        listeners.clear();
    }

    public List<Item> getItems() {
        return items;
    }

    public void performPurchase(Cart cart) {
        for(Item item : cart.getItemsArray()) {
            item.setCount(item.getCount() - cart.getCount(item));
        }
        listeners.forEach(new Consumer<DataChangedListener>() {
            @Override
            public void accept(DataChangedListener e) {
                e.notifyDataChanged();
            }
        });
    }
    private ItemInfo() {
        items = new LinkedList<>();
        listeners = new LinkedList<>();
        addItem(new Item(1,"Очиститель воздуха", 6000, 2, "Средство очистки воздуха"));
        addItem(new Item(2,"Кофеварка", 4000, 3, "Машина для варки кофе"));
        addItem(new Item(3,"Электрический чайник",  1500, 1, "Обычный электрический чайник"));
    }
}
