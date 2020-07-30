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
import lombok.*;

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
