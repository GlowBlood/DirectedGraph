/**
 * Created by sofaberezovskaa on 07.03.17.
 */
import java.util.*;
import java.util.Map;

public class DirectedGraph {
    final private class Node {}

    final private class Arrow {
        int weight;
        public void changeWeight(int weight) {
            this.weight = weight;
        }
    }

    private final Map<String, Node> nodeMap = new HashMap<>();

    public void addNode(String name, Node node) {
        if (!nodeMap.containsKey(name)) throw new NoSuchElementException();
        nodeMap.put(name, node);
    }

    public void removeNode(String name) {
        nodeMap.remove(name);
    }

    public void changeNameNode(String name, String newName, Node node) {
        if (!nodeMap.containsKey(newName)) throw new NoSuchElementException();
        nodeMap.remove(name);
        nodeMap.put(newName, node);
    }

    private final Map<Node, ArrayList<Arrow>> incomingArrows = new HashMap<>();
    private final Map<Node, ArrayList<Arrow>> outcomingArrows = new HashMap<>();


    public void addArrow(Arrow arrow, String nameStart, String nameFinish){
        Node in = nodeMap.get(nameStart);
        if (in == null) throw new NoSuchElementException("Не существует начального узла");
        ArrayList<Arrow> arrayStart = incomingArrows.get(in);
        arrayStart.add(arrow);

        Node out = nodeMap.get(nameFinish);
        if (out == null) throw new NoSuchElementException("Не существует конечного узла");
        ArrayList<Arrow> arrayFinish = outcomingArrows.get(out);
        arrayFinish.add(arrow);
    }

    public void removeArrow(Arrow arrow, String nameStart, String nameFinish){
        Node in = nodeMap.get(nameStart);
        if (in == null) throw new NoSuchElementException("Не существует начального узла");
        ArrayList<Arrow> arrayStart = incomingArrows.get(in);
        arrayStart.remove(arrow);

        Node out = nodeMap.get(nameFinish);
        if (out == null) throw new NoSuchElementException("Не существует конечного узла");
        ArrayList<Arrow> arrayFinish = outcomingArrows.get(out);
        arrayFinish.remove(arrow);
    }

    public ArrayList<Arrow> getListInn(Node node){
        ArrayList<Arrow> in = incomingArrows.get(node);
        if (in == null) throw new NoSuchElementException("Такого узла не существует");
        return incomingArrows.get(node);
    }

    public ArrayList<Arrow> getListOut(Node node){
        ArrayList<Arrow> out = outcomingArrows.get(node);
        if (out == null) throw new NoSuchElementException("Такого узла не существует");
        return outcomingArrows.get(node);
    }
}

