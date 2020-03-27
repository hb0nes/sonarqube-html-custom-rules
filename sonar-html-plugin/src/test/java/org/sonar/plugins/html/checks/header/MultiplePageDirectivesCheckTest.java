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
package org.sonar.plugins.html.checks.header;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.plugins.html.checks.CheckMessagesVerifierRule;
import org.sonar.plugins.html.checks.TestHelper;
import org.sonar.plugins.html.visitor.HtmlSourceCode;

public class MultiplePageDirectivesCheckTest {

  @Rule
  public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

  @Test
  public void test1() throws Exception {
    HtmlSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/multiplePageDirectivesCheck1.html"), new MultiplePageDirectivesCheck());

    checkMessagesVerifier.verify(sourceCode.getIssues())
      .next().atLine(3).withMessage("Combine these 2 page directives into one.");
  }

  @Test
  public void test2() throws Exception {
    HtmlSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/multiplePageDirectivesCheck2.html"), new MultiplePageDirectivesCheck());

    checkMessagesVerifier.verify(sourceCode.getIssues())
      .next().atLine(3).withMessage("Combine these 2 page directives into one.");
  }

  @Test
  public void test3() throws Exception {
    HtmlSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/multiplePageDirectivesCheck3.html"), new MultiplePageDirectivesCheck());

    assertThat(sourceCode.getIssues()).isEmpty();
  }

}
