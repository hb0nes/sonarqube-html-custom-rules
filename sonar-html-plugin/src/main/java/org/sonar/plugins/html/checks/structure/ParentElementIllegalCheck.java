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
package org.sonar.plugins.html.checks.structure;

import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.html.checks.AbstractPageCheck;
import org.sonar.plugins.html.node.TagNode;

/**
 * Checker for illegal parent element.
 *
 * e.g. title must not have parent element body.
 */
@Rule(key = "ParentElementIllegalCheck")
public class ParentElementIllegalCheck extends AbstractPageCheck {

  private static final String DEFAULT_CHILD = "";
  private static final String DEFAULT_PARENT = "";

  @RuleProperty(
    key = "child",
    description = "Name of the child element",
    defaultValue = DEFAULT_CHILD)
  public String child = DEFAULT_CHILD;

  @RuleProperty(
    key = "parent",
    description = "Name of the parent element",
    defaultValue = DEFAULT_PARENT)
  public String parent = DEFAULT_PARENT;

  @Override
  public void startElement(TagNode element) {
    if (element.equalsElementName(child) && element.getParent() != null && element.getParent().equalsElementName(parent)) {
      createViolation(element, "The element '" + child + "' must not have a '" + parent + "' parent.");
    }
  }

}
