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
package org.sonar.plugins.html.checks.comments;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.sonar.check.Rule;
import org.sonar.plugins.html.checks.AbstractPageCheck;
import org.sonar.plugins.html.node.CommentNode;

@Rule(key = "S1135")
public class TodoCommentCheck extends AbstractPageCheck {

  private static final Pattern TODO_PATTERN = Pattern.compile("(?i)(^|[^\\p{L}])(todo)");

  @Override
  public void comment(CommentNode node) {
    Matcher matcher = TODO_PATTERN.matcher(node.getCode());
    if (matcher.find()) {
      int lineNumber = CommentUtils.lineNumber(node, matcher.start(2));
      createViolation(lineNumber, "Complete the task associated to this \"TODO\" comment.");
    }
  }

}
