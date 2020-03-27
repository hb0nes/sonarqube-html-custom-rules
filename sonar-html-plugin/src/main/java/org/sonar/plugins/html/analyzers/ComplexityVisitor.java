/*
 * SonarHTML :: SonarQube Plugin
 * Copyright (c) 2010-2019 SonarSource SA and Matthijs Galesloot
 * sonarqube@googlegroups.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sonar.plugins.html.analyzers;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.plugins.html.checks.AbstractPageCheck;
import org.sonar.plugins.html.node.Attribute;
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.node.TagNode;

public class ComplexityVisitor extends AbstractPageCheck {

  private static final String[] OPERATORS = new String[] {"&&", "||", "and", "or"};
  private static final String[] TAGS = new String[] {"catch", "choose", "if", "forEach", "forTokens", "when"};

  private int complexity;

  @Override
  public void startDocument(List<Node> nodes) {
    complexity = 1;
  }

  @Override
  public void endDocument() {
    getHtmlSourceCode().addMeasure(CoreMetrics.COMPLEXITY, complexity);
  }

  @Override
  public void startElement(TagNode node) {
    // count jstl tags
    if (ArrayUtils.contains(TAGS, node.getLocalName()) || ArrayUtils.contains(TAGS, node.getNodeName())) {
      complexity++;
    } else {
      // count complexity in expressions
      for (Attribute a : node.getAttributes()) {
        if (isUnifiedExpression(a.getValue())) {
          String[] tokens = StringUtils.split(a.getValue(), " \t\n");

          for (String token : tokens) {
            if (ArrayUtils.contains(OPERATORS, token)) {
              complexity++;
            }
          }
        }
      }
    }
  }

}
