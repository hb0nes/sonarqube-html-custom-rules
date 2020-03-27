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
package org.sonar.plugins.html.checks.style;

import org.sonar.check.Rule;
import org.sonar.plugins.html.checks.AbstractPageCheck;
import org.sonar.plugins.html.node.TagNode;

/**
 * Checker for occurrence of inline style.
 *
 *
 * @author Matthijs Galesloot
 * @since 1.0
 */
@Rule(key = "InlineStyleCheck")
public class InlineStyleCheck extends AbstractPageCheck {

  @Override
  public void startElement(TagNode element) {

    if ("style".equalsIgnoreCase(element.getNodeName())) {
      createViolation(element, "Use CSS classes instead.");
    }
  }
}
