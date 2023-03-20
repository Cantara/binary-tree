package eu.happycoders.binarytree;

import java.util.ArrayList;
import java.util.List;

public class TestNodeVisitor implements NodeVisitor {

  private List<Long> dataList = new ArrayList<>();

  @Override
  public void visit(Node node) {
    dataList.add(node.data());
  }

  public List<Long> getDataList() {
    return dataList;
  }
}
