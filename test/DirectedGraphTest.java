import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DirectedGraphTest {
    @Test
    public void testAddNode() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("Node");
        assert (graph.getNodeNames().length == 1);
    }

    @Test
    public void testNodeNames() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("Node1");
        graph.addNode("Node2");
        graph.addNode("Node3");
        String[] names = graph.getNodeNames();
        List<String> list = Arrays.asList(names);
        assert (list.contains("Node1") && list.contains("Node2") && list.contains("Node3"));
    }

    @Test
    public void testChangeNodeName() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("Node");
        graph.changeNameNode("Node", "NewNode");
        String[] names = graph.getNodeNames();
        assert(names.length == 1 && names[0] == "NewNode");
    }

    @Test
    public void testRemoveNode() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("Node1");
        graph.addNode("Node2");
        graph.addNode("Node3");
        graph.removeNode("Node2");
        String[] names = graph.getNodeNames();
        List<String> list = Arrays.asList(names);
        assert (list.contains("Node1") && !list.contains("Node2") && list.contains("Node3"));
    }

    @Test
    public void testAddDuplicate() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("Node");
        try {
            graph.addNode("Node");
        } catch(IllegalArgumentException e) {
            assert true;
            return;
        }
        assert false;
    }

    @Test
    public void testAddArrow() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("Node1");
        graph.addNode("Node2");
        DirectedGraph.Arrow arrow = new DirectedGraph.Arrow(10);
        graph.addArrow(arrow, "Node1", "Node2");
        List<DirectedGraph.Arrow> out = graph.getListOut("Node1");
        List<DirectedGraph.Arrow> inn = graph.getListInn("Node2");
        assert (out.contains(arrow) && inn.contains(arrow));
    }

    @Test
    public void testChangeArrowWeight() {
        DirectedGraph.Arrow arrow = new DirectedGraph.Arrow(10);
        arrow.setWeight(20);
        assert (arrow.getWeight() == 20);
    }

    @Test
    public void testRemoveArrow() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("Node1");
        graph.addNode("Node2");
        DirectedGraph.Arrow arrow1 = new DirectedGraph.Arrow(10);
        DirectedGraph.Arrow arrow2 = new DirectedGraph.Arrow(15);
        DirectedGraph.Arrow arrow3 = new DirectedGraph.Arrow(20);
        graph.addArrow(arrow1, "Node1", "Node2");
        graph.addArrow(arrow2, "Node1", "Node2");
        graph.addArrow(arrow3, "Node1", "Node2");
        graph.removeArrow(arrow2, "Node1", "Node2");
        List<DirectedGraph.Arrow> out = graph.getListOut("Node1");
        List<DirectedGraph.Arrow> inn = graph.getListInn("Node2");
        assert (out.contains(arrow1) && inn.contains(arrow1) && !out.contains(arrow2) && !inn.contains(arrow2) && out.contains(arrow3) && inn.contains(arrow3));
    }

    @Test
    public void testEquals() {
        DirectedGraph graph = new DirectedGraph();
        graph.addNode("Node1");
        graph.addNode("Node2");
        DirectedGraph.Arrow arrow1 = new DirectedGraph.Arrow(10);
        DirectedGraph.Arrow arrow2 = new DirectedGraph.Arrow(15);
        DirectedGraph.Arrow arrow3 = new DirectedGraph.Arrow(20);
        graph.addArrow(arrow1, "Node1", "Node2");
        graph.addArrow(arrow2, "Node1", "Node2");
        graph.addArrow(arrow3, "Node1", "Node2");

        DirectedGraph graph2 = new DirectedGraph();
        graph2.addNode("Node1");
        graph2.addNode("Node2");
        graph2.addArrow(arrow1, "Node1", "Node2");
        graph2.addArrow(arrow2, "Node1", "Node2");
        graph2.addArrow(arrow3, "Node1", "Node2");

        assert (graph.equals(graph2));
    }

}
