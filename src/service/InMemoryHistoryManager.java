package service;

import model.Node;
import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    Node first;
    Node last;

    Map<Integer, Node> nodes = new HashMap<>();

    public void linkLast(Task task) {
        Node newNode = new Node(last, task, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
    }

    public void removeNode(int id) {
        Node node = nodes.remove(id);
        if (node == null) {
            return;
        }
        if (node.prev == null) {
            first = first.next;
            if (first == null) {
                last = null;
            } else {
                first.prev = null;
            }
        } else if (node.next == null){
            last = last.prev;
            last.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    public List<Task> getTasks() {
        List<Task> history = new ArrayList<>();
        Node curNode = first;
        while (curNode != null) {
            history.add(curNode.task);
            curNode = curNode.next;
        }
        return history;
    }

    @Override
    public void add(Task task) {
        removeNode(task.getId());
        linkLast(task);
        nodes.put(task.getId(), last);
    }

    @Override
    public void remove(int id) {
        removeNode(id);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

}
