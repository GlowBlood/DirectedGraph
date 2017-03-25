

import java.util.*;
import java.util.Map;
import java.util.HashMap;


public class DirectedGraph {
    final private class Node {}

    final public class Arrow {
        private int weight;

        Arrow(int weight){
            this.weight = weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
        private int getWeight(){ return weight;}

        @Override
        public String toString() {
            return "Вес дуги " + this.weight;
        }

        @Override
        public int hashCode(){
            final int prime = 31;
            int result = 1;
            result = prime * result + weight;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Arrow other = (Arrow) obj;
        if (weight != other.weight) return false;
        return true;
        }
    }


    private final Map<String, Node> nodeMap = new HashMap<>();

    public void addNode(String name) {
        if (nodeMap.containsKey(name)) throw new NoSuchElementException();
        nodeMap.put(name, new Node());
    }

    public void removeNode(String name) {
        Node node = nodeMap.get(name);
        if (getListInn(node) != null) throw new IllegalArgumentException("Удалите входящие в этот узел дуги");
        if (getListOut(node) != null) throw new IllegalArgumentException("Удалите исходящие из этого узла дуги");
        nodeMap.remove(name);
    }

    public void changeNameNode(String name, String newName) {
        if (!nodeMap.containsKey(newName)) throw new NoSuchElementException();
        Node node = nodeMap.get(name);
        nodeMap.remove(name);
        nodeMap.put(newName, node);
    }

    private final Map<Node, ArrayList<Arrow>> incomingArrows = new HashMap<>();
    private final Map<Node, ArrayList<Arrow>> outcomingArrows = new HashMap<>();


    public void addArrow(Arrow arrow, String nameStart, String nameFinish){
        final Node in = nodeMap.get(nameStart);
        if (in == null) throw new NoSuchElementException("Не существует начального узла " + nameStart);
        ArrayList<Arrow> arrayStart = incomingArrows.get(in);
        arrayStart.add(arrow);

        final Node out = nodeMap.get(nameFinish);
        if (out == null) throw new NoSuchElementException("Не существует конечного узла " + nameFinish);
        ArrayList<Arrow> arrayFinish = outcomingArrows.get(out);

        if (arrayStart.contains(arrow) && (arrayFinish.contains(arrow))) throw new IllegalArgumentException("Дуга " + arrow + " уже существует");

        arrayStart.add(arrow);
        arrayFinish.add(arrow);
    }

    public void removeArrow(Arrow arrow, String nameStart, String nameFinish){
        final Node in = nodeMap.get(nameStart);
        if (in == null) throw new NoSuchElementException("Не существует начального узла " + nameStart);
        ArrayList<Arrow> arrayStart = incomingArrows.get(in);
        arrayStart.remove(arrow);

        final Node out = nodeMap.get(nameFinish);
        if (out == null) throw new NoSuchElementException("Не существует конечного узла " + nameFinish);
        ArrayList<Arrow> arrayFinish = outcomingArrows.get(out);
        arrayFinish.remove(arrow);
    }

    public ArrayList<Arrow> getListInn(Node node){
        final ArrayList<Arrow> in = incomingArrows.get(node);
        if (in == null) throw new NoSuchElementException("Узла " + node + " не существует");
        return incomingArrows.get(node);
    }

    public ArrayList<Arrow> getListOut(Node node){
        final ArrayList<Arrow> out = outcomingArrows.get(node);
        if (out == null) throw new NoSuchElementException("Узла " + node + " не существует");
        return outcomingArrows.get(node);
    }

@Override
public int hashCode(){
        return 2 * incomingArrows.hashCode();
}

@Override
public String toString() {
    return nodeMap.toString();
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
    return (nodeMap != other.nodeMap);
}
}

