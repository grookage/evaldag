package io.github.jforrest;

/**
 * Copyright 2017 Koushik R <rkoushik.14@gmail.com>.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/


import com.google.common.collect.ComparisonChain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Vertex implements Comparable<Vertex> {

  private String id;
  private ArrayList<Edge> incomingEdges = new ArrayList<>();
  private ArrayList<Edge> outgoingEdges = new ArrayList<>();

  public Vertex(String id) {
    this.id = id;
  }

  /**
   * Adds an edge as an incoming edge to this vertex.
   *
   * @param edge an edge to be added as incoming.
   */
  public void addIncomingEdge(Edge edge) {
    incomingEdges.add(edge);
  }

  /**
   * Adds an edge as an outgoing edge from this vertex.
   *
   * @param edge an edge to be added as outgoing.
   */
  public void addOutgoingEdge(Edge edge) {
    outgoingEdges.add(edge);
  }

  /**
   * Checks if a given entity is a part of its incoming edges, immediate ones.
   */
  public boolean isPartOfIncoming(String categoryId) {
    return this.getIncomingEdges().stream()
        .filter(each -> each.getSource().equalsIgnoreCase(categoryId)).count() > 0;
  }

  /**
   * Checks if a given entity is a part of its outgoing edges, immediate ones.
   */
  public boolean isPartOfOutgoing(String categoryId) {
    return this.getOutgoingEdges().stream()
        .filter(each -> each.getDestination().equalsIgnoreCase(categoryId)).count() > 0;
  }

  @Override
  public int compareTo(Vertex o) {
    return ComparisonChain.start()
        .compare(this.getId(), o.getId())
        .result();
  }
}
