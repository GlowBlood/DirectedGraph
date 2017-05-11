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

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + weight;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            return (this == obj); //дуги равны только если они указывают на одну дугу
        }
    }


    private final Map<String, Node> nodeMap = new HashMap<>();

    public void addNode(String name) {
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
        return in;
    }

    public ArrayList<Arrow> getListOut(String name) {
        final ArrayList<Arrow> out = outgoingArrows.get(nodeMap.get(name));
        if (out == null) throw new NoSuchElementException("Узла " + name + " не существует");
        return out;
    }

    private ArrayList listOfNode(String key) {
        ArrayList result = new ArrayList<>();
        result.add("имя узла " + key);
        Node i = nodeMap.get(key);
        result.add("Список исходящих дуг");
        result.add(getListOut(key));
        result.add("Список входящих дуг");
        result.add(getListInn(key));
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + incomingArrows.hashCode() + outgoingArrows.hashCode() + nodeMap.hashCode();
        return result;
    }

    @Override
    public String toString() {
        ArrayList newResult = new ArrayList<>();
        for (String key : nodeMap.keySet()) {
            newResult.add(listOfNode(key));
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
        return (nodeMap.equals(other.nodeMap) && incomingArrows.equals(other.incomingArrows) && outgoingArrows.equals(other.outgoingArrows));
    }

    public String[] getNodeNames() {
        return nodeMap.keySet().toArray(new String[0]);
    }


}
