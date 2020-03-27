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

import java.util.Arrays;
import java.util.HashSet;
import org.apache.commons.lang.StringUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.html.checks.AbstractPageCheck;
import org.sonar.plugins.html.node.CommentNode;
import org.sonarsource.analyzer.recognizers.CodeRecognizer;
import org.sonarsource.analyzer.recognizers.ContainsDetector;
import org.sonarsource.analyzer.recognizers.EndWithDetector;
import org.sonarsource.analyzer.recognizers.LanguageFootprint;

@Rule(key = "AvoidCommentedOutCodeCheck")
public class AvoidCommentedOutCodeCheck extends AbstractPageCheck {

  private static final double THRESHOLD = 0.9;

  private static final LanguageFootprint LANGUAGE_FOOTPRINT = () -> new HashSet<>(Arrays.asList(
    new ContainsDetector(0.7, "=\"", "='"),
    new ContainsDetector(0.8, "/>", "</", "<%", "%>"),
    new EndWithDetector(0.9, '>')));

  private static final CodeRecognizer CODE_RECOGNIZER = new CodeRecognizer(THRESHOLD, LANGUAGE_FOOTPRINT);

  @Override
  public void comment(CommentNode node) {
    if (node.isHtml()) {
      String comment = node.getCode();

      if (!comment.startsWith("<!--[if") && !StringUtils.containsIgnoreCase(comment, "copyright") && CODE_RECOGNIZER.isLineOfCode(comment)) {
        createViolation(node.getStartLinePosition(), "Remove this commented out code.");
      }
    }
  }

}
