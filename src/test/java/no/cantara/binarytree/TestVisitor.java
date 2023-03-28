package no.cantara.binarytree;

import java.util.ArrayList;
import java.util.List;

public class TestVisitor implements Visitor<List<Long>> {

  private List<Long> dataList = new ArrayList<>();

  @Override
  public void visit(TraversalContext<List<Long>> ctx, Node node) {
    dataList.add(node.data());
  }

  public List<Long> getDataList() {
    return dataList;
  }
}
