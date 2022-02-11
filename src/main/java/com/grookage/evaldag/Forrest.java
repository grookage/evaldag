/*
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
 */
package com.grookage.evaldag;

import com.grookage.evaldag.exceptions.Exceptions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.synchronizedSet;
import static java.util.stream.Stream.*;

/**
 * Created by koushikr on 19/09/17.
 *
 * The abstract class that has to be extended by all the implementations that require a DAG usage.
 * Type to <T> if you were to introduce weights for edges later on. Right now there aren't any
 * edges.
 */
@SuppressWarnings("unused")
@Getter
@Setter
@NoArgsConstructor
public abstract class Forrest {

  private Map<String, Vertex> nodes = new HashMap<>();

  /**
   * Constructor : Holds to the entire nodeList regardless of their mapping. A addEdges method needs
   * to be called to make sure that the DAG is well constructed.
   */
  public Forrest(Map<String, Vertex> allNodes) {
    this.nodes = allNodes;
  }

  private Vertex getNormalizedVertex(String vertexId) {
    if (!nodes.containsKey(vertexId)) {
        Exceptions.illegalState("NO NODE FOUND : Can't construct the entity graph");
    }
    return nodes.get(vertexId);
  }

  /**
   * An internal function to add an edge to the existing categoryGraph. Checks for cycles before an
   * edge gets added.
   *
   */
  private void addEdge(Edge edge) {
    if (cycleExists(edge)) {
      Exceptions.illegalState("CYCLIC DEPENDENCY : Can't construct the entity graph");
    }
    getNormalizedVertex(edge.getSource()).addOutgoingEdge(edge);
    getNormalizedVertex(edge.getDestination()).addIncomingEdge(edge);
  }

  /**
   * A function used to check if there is a path between two vertices.
   *
   * @param start the starting vertex.
   * @param goal the goal vertex.
   * @return true if there exists a path, if not false.
   */
  private boolean cycleExists(Vertex start, Vertex goal) {
      final var incomingEdges = start.getIncomingEdges();
      return incomingEdges.stream().anyMatch(edge -> edge.getSource().equalsIgnoreCase(goal.getId()) || cycleExists(getNormalizedVertex(edge.getSource()), goal));
  }

  /**
   * Check cycle existence, with edge as the supplied argument
   *
   * @return true if there exists a path, false otherwise
   */
  public boolean cycleExists(Edge edge) {
    return cycleExists(
        getNormalizedVertex(edge.getSource()),
        getNormalizedVertex(edge.getDestination())
    );
  }

  /**
   * A function to add all the edges to the given DAG. Checks for cycle existence and throws an
   * error it there exists a cycle
   */
  public void addEdges(Set<Edge> edges) {
      edges.forEach(this::addEdge);
  }

  /**
   * Helper to support the above BFS operation
   *
   * @return A stream of verticies.
   */
  private Stream<Vertex> flattened(Vertex origin, Set<Vertex> closedSet) {
    if (!closedSet.add(origin)) {
      return empty();
    } else {
      return concat(
          Stream.of(origin),
          origin.getOutgoingEdges().stream()
              .flatMap(each -> flattened(getNormalizedVertex(each.getDestination()), closedSet))
      );
    }
  }

  /**
   * Gets a Subgraph by performing BFS, on the DAG.
   *
   * @return the list of verticies that can reached from the given id
   */
  public List<Vertex> subTree(String id) {
    final var closedSet = synchronizedSet(new HashSet<Vertex>());
    return flattened(getNormalizedVertex(id), closedSet).parallel().collect(Collectors.toList());
  }
}
