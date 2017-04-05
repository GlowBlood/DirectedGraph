

import java.util.*;
import java.util.Map;
import java.util.HashMap;


public class DirectedGraph {

    final public class Node {}

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
    public void nameNode (Node node){

    }

    public void addNode(String name) {
        if (nodeMap.containsKey(name)) throw new IllegalArgumentException("Узел " + name + " уже существует");
        nodeMap.put(name, new Node());
    }

    public Node getNode(String name){
        Node result = nodeMap.get(name);
        if (result == null) throw new NoSuchElementException("Узла под именем " + name + " не существует");
        return result;
    }

    public void removeNode(String name) {
        Node node = nodeMap.get(name);
        if (getListInn(node) != null) throw new IllegalArgumentException("Удалите входящие в узел под именем " + name + " дуги");
        if (getListOut(node) != null) throw new IllegalArgumentException("Удалите исходящие из узла под именем " + name + " дуги");
        nodeMap.remove(name);
    }

    public void changeNameNode(String name, String newName) {
        if (!nodeMap.containsKey(name)) throw new NoSuchElementException("Узла под именем " + name + " не существует");
        if (nodeMap.containsKey(newName)) throw new IllegalArgumentException("Узел под именем " + newName + " уже существует");
        Node node = nodeMap.get(name);
        nodeMap.remove(name);
        nodeMap.put(newName, node);
    }

    private final Map<Node, ArrayList<Arrow>> incomingArrows = new HashMap<>();
    private final Map<Node, ArrayList<Arrow>> outcomingArrows = new HashMap<>();


    public void addArrow(Arrow arrow, String nameStart, String nameFinish){
        ArrayList<Arrow> arrayStart = new ArrayList<>();
        final Node in = nodeMap.get(nameStart);
        if (in == null) throw new NoSuchElementException("Не существует начального узла " + nameStart);
        arrayStart = incomingArrows.get(in);

        final Node out = nodeMap.get(nameFinish);
        if (out == null) throw new NoSuchElementException("Не существует конечного узла " + nameFinish);
        ArrayList<Arrow> arrayFinish = outcomingArrows.get(out);

        if (arrayStart.contains(arrow) && (arrayFinish.contains(arrow))) throw new IllegalArgumentException("Дуга между узлами " + nameStart + "и " + nameFinish + " уже существует");

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
        return in;
    }

    public ArrayList<Arrow> getListOut(Node node){
        final ArrayList<Arrow> out = outcomingArrows.get(node);
        if (out == null) throw new NoSuchElementException("Узла " + node + " не существует");
        return out;
    }

    private ArrayList listOfNode(String key){
        ArrayList result = new ArrayList<>();
        result.add("имя узла " + key);
        Node i = getNode(key);
        result.add("Список исходящих дуг");
        result.add(getListOut(i));
        result.add("Список входящих дуг");
        result.add(getListInn(i));
        return result;
    }

@Override
public int hashCode(){
    final int prime = 31;
    int result = 1;
    result = prime * result + incomingArrows.hashCode();
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
    return (nodeMap != other.nodeMap);
}
}

