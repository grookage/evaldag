package io.github.jforrest;

import com.google.common.collect.ComparisonChain;
import lombok.*;

/**
 * Created by koushikr on 19/09/17.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Edge implements Comparable<Edge> {

  private String source;

  private String destination;

  @Override
  public int compareTo(Edge o) {
    return ComparisonChain.start()
        .compare(this.getSource(), o.getSource())
        .compare(this.getDestination(), o.getDestination())
        .result();
  }
}
