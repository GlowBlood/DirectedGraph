import java.util.*;
import java.util.Map;
import java.util.HashMap;


public class DirectedGraph {

    private static final class Node {
    }

    public static final class Arrow {
        private int weight;

        Arrow(int weight) {
            this.weight = weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return "Вес дуги " + this.weight;
        }
    }


    private final Map<String, Node> nodeMap = new HashMap<>();

    public void addNode(String name) {
        if (name == null) throw new IllegalArgumentException("Передан null");
        if (nodeMap.containsKey(name)) throw new IllegalArgumentException("Узел " + name + " уже существует");
        Node node = new Node();
        nodeMap.put(name, node);
        incomingArrows.put(node, new ArrayList<>());
        outgoingArrows.put(node, new ArrayList<>());
    }

    public void removeNode(String name) {
        Node node = nodeMap.get(name);
        if (node == null) throw new NoSuchElementException("Не существует узла " + name);
        if (getListInn(name).size() != 0)
            throw new IllegalArgumentException("Удалите входящие в узел под именем " + name + " дуги");
        if (getListOut(name).size() != 0)
            throw new IllegalArgumentException("Удалите исходящие из узла под именем " + name + " дуги");
        nodeMap.remove(name);
        incomingArrows.remove(node);
        outgoingArrows.remove(node);
    }

    public void changeNameNode(String name, String newName) {
        final Node node = nodeMap.get(name);
        if (node == null) throw new NoSuchElementException("Узла под именем " + name + " не существует");
        if (nodeMap.containsKey(newName))
            throw new IllegalArgumentException("Узел под именем " + newName + " уже существует");
        nodeMap.remove(name);
        nodeMap.put(newName, node);
    }

    private final Map<Node, ArrayList<Arrow>> incomingArrows = new HashMap<>();
    private final Map<Node, ArrayList<Arrow>> outgoingArrows = new HashMap<>();


    public void addArrow(Arrow arrow, String nameStart, String nameFinish) {
        final Node in = nodeMap.get(nameStart);
        if (in == null) throw new NoSuchElementException("Не существует начального узла " + nameStart);
        ArrayList<Arrow> arrayStart = outgoingArrows.get(in);

        final Node out = nodeMap.get(nameFinish);
        if (out == null) throw new NoSuchElementException("Не существует конечного узла " + nameFinish);
        ArrayList<Arrow> arrayFinish = incomingArrows.get(out);

        if (arrayStart.contains(arrow) && (arrayFinish.contains(arrow)))
            throw new IllegalArgumentException("Дуга между узлами " + nameStart + "и " + nameFinish + " уже существует");

        arrayStart.add(arrow);
        arrayFinish.add(arrow);
    }

    public void removeArrow(Arrow arrow, String nameStart, String nameFinish) {
        final Node in = nodeMap.get(nameStart);
        if (in == null) throw new NoSuchElementException("Не существует начального узла " + nameStart);
        final Node out = nodeMap.get(nameFinish);
        if (out == null) throw new NoSuchElementException("Не существует конечного узла " + nameFinish);
        ArrayList<Arrow> arrayStart = outgoingArrows.get(in);
        ArrayList<Arrow> arrayFinish = incomingArrows.get(out);
        arrayStart.remove(arrow);
        arrayFinish.remove(arrow);
    }

    public ArrayList<Arrow> getListInn(String name) {
        final ArrayList<Arrow> in = incomingArrows.get(nodeMap.get(name));
        if (in == null) throw new NoSuchElementException("Узла " + name + " не существует");
        return new ArrayList<>(in);
    }

    public ArrayList<Arrow> getListOut(String name) {
        final ArrayList<Arrow> out = outgoingArrows.get(nodeMap.get(name));
        if (out == null) throw new NoSuchElementException("Узла " + name + " не существует");
        return new ArrayList<>(out);
    }

    private String listOfNode(String key) {
        StringBuilder result = new StringBuilder();
        result.append("имя узла " + key);
        result.append("Список исходящих дуг");
        result.append(getListOut(key));
        result.append("Список входящих дуг");
        result.append(getListInn(key));
        return result.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + incomingArrows.hashCode();
        result = prime * result + outgoingArrows.hashCode();
        result = prime * result + nodeMap.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder newResult = new StringBuilder();
        for (String key : nodeMap.keySet()) {
            newResult.append(listOfNode(key));
        }
        return newResult.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DirectedGraph other = (DirectedGraph) obj;
        if (!nodeMap.keySet().equals(other.nodeMap.keySet())) {
            return false;
        }
        for (String key : nodeMap.keySet()) {
            if (!(compareArrayLists(getListInn(key), other.getListInn(key)) && compareArrayLists(getListOut(key), other.getListOut(key)))) {
                return false;
            }
        }
        return true;
    }

    public String[] getNodeNames() {
        return nodeMap.keySet().toArray(new String[0]);
    }

    private static boolean compareArrayLists(ArrayList<Arrow> a1, ArrayList<Arrow> a2) {
        if (a1 == null || a2 == null) {
            return false;
        }
        a1.sort((o1, o2) -> o1.hashCode() > o2.hashCode() ? 1 : -1);
        a2.sort((o1, o2) -> o1.hashCode() > o2.hashCode() ? 1 : -1);
        return a1.equals(a2);
    }
}
