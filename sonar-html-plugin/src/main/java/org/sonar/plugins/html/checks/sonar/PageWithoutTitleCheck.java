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
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.node.TagNode;

import java.util.List;

@Rule(key = "PageWithoutTitleCheck")
public class PageWithoutTitleCheck extends AbstractPageCheck {

  private int currentHtmlTagLine;
  private int currentHeadTagLine;
  private boolean foundTitleTag;
  private boolean isReported;

  @Override
  public void startDocument(List<Node> nodes) {
    currentHtmlTagLine = 0;
    currentHeadTagLine = 0;
  }

  @Override
  public void startElement(TagNode node) {
    if (isHtmlTag(node)) {
      currentHtmlTagLine = node.getStartLinePosition();
      isReported = false;
      foundTitleTag = false;
    } else if (isHeadTag(node)) {
      currentHeadTagLine = node.getStartLinePosition();
      isReported = false;
      foundTitleTag = false;
    } else if (currentHeadTagLine != 0 && isTitleTag(node)) {
      foundTitleTag = true;
    }
  }

  @Override
  public void endElement(TagNode node) {
    int line = 0;

    if (isHtmlTag(node)) {
      line = currentHtmlTagLine;
      currentHtmlTagLine = 0;
      currentHeadTagLine = 0;
    } else if (isHeadTag(node)) {
      line = currentHeadTagLine;
      currentHeadTagLine = 0;
    }

    if (!foundTitleTag && line != 0 && !isReported) {
      createViolation(line, "Add a <title> tag to this page.");
      isReported = true;
    }
  }

  private static boolean isHtmlTag(TagNode node) {
    return "HTML".equalsIgnoreCase(node.getLocalName());
  }

  private static boolean isHeadTag(TagNode node) {
    return "HEAD".equalsIgnoreCase(node.getLocalName());
  }

  private static boolean isTitleTag(TagNode node) {
    return "TITLE".equalsIgnoreCase(node.getLocalName());
  }

}
