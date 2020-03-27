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
package org.sonar.plugins.html.checks.scripting;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.plugins.html.checks.CheckMessagesVerifierRule;
import org.sonar.plugins.html.checks.TestHelper;
import org.sonar.plugins.html.visitor.HtmlSourceCode;

import java.io.File;

public class UnsafeBladeExpressionCheckTest {

  @Rule
  public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

  @Test
  public void custom() {
    UnsafeBladeExpressionCheck check = new UnsafeBladeExpressionCheck();
    HtmlSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/UnsafeBladeExpression.html"), check);
    checkMessagesVerifier.verify(sourceCode.getIssues())
            .next().atLine(3).withMessage("An unsafe use of Blade Expressions was found. Use {{ }} to make sure output is escaped to prevent XSS.")
            .next().atLine(4).withMessage("An unsafe use of Blade Expressions was found. Use {{ }} to make sure output is escaped to prevent XSS.")
            .next().atLine(5).withMessage("An unsafe use of Blade Expressions was found. Use {{ }} to make sure output is escaped to prevent XSS.");
  }

}