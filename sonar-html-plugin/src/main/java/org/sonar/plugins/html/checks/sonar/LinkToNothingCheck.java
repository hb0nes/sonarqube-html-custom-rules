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
package org.sonar.plugins.html.checks.sonar;

import org.sonar.check.Rule;
import org.sonar.plugins.html.checks.AbstractPageCheck;
import org.sonar.plugins.html.node.TagNode;

@Rule(key = "LinkToNothingCheck")
public class LinkToNothingCheck extends AbstractPageCheck {

  @Override
  public void startElement(TagNode node) {
    if (isATag(node) && hasHrefToNothing(node)) {
      createViolation(node, "Give this link a valid reference or remove the reference.");
    }
  }

  private static boolean isATag(TagNode node) {
    return "A".equalsIgnoreCase(node.getNodeName());
  }

  private static boolean hasHrefToNothing(TagNode node) {
    String href = node.getAttribute("href");

    return href != null &&
      isPoitingToNothing(href);
  }

  private static boolean isPoitingToNothing(String target) {
    return "#".equalsIgnoreCase(target) ||
      "JAVASCRIPT:VOID(0)".equalsIgnoreCase(target) ||
      "JAVASCRIPT:VOID(0);".equalsIgnoreCase(target);
  }

}
