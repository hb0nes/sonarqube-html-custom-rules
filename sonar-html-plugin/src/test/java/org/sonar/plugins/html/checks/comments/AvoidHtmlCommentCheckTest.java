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

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.plugins.html.checks.CheckMessagesVerifierRule;
import org.sonar.plugins.html.checks.TestHelper;
import org.sonar.plugins.html.visitor.HtmlSourceCode;

public class AvoidHtmlCommentCheckTest {

  @Rule
  public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

  @Test
  public void should_detect_on_jsp_documents() {
    HtmlSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/AvoidHtmlCommentCheck/document.jsp"), new AvoidHtmlCommentCheck());

    checkMessagesVerifier.verify(sourceCode.getIssues())
        .next().atLine(2).withMessage("Remove this comment or change its style so that it is not output to the client.")
        .next().atLine(4);
  }

  @Test
  public void should_detect_on_php_documents() {
    HtmlSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/AvoidHtmlCommentCheck/document.php"), new AvoidHtmlCommentCheck());

    checkMessagesVerifier.verify(sourceCode.getIssues())
      .next().atLine(6).withMessage("Remove this comment or change its style so that it is not output to the client.");
  }

  @Test
  public void should_detect_on_erb_documents() {
    HtmlSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/AvoidHtmlCommentCheck/document.html.erb"), new AvoidHtmlCommentCheck());

    checkMessagesVerifier.verify(sourceCode.getIssues())
      .next().atLine(6).withMessage("Remove this comment or change its style so that it is not output to the client.");
  }

  @Test
  public void should_not_detect_on_html_documents() {
    HtmlSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/AvoidHtmlCommentCheck/document.html"), new AvoidHtmlCommentCheck());

    checkMessagesVerifier.verify(sourceCode.getIssues());
  }

  @Test
  public void should_not_detect_on_html5_documents() {
    HtmlSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/AvoidHtmlCommentCheck/documenthtml5.html"), new AvoidHtmlCommentCheck());

    checkMessagesVerifier.verify(sourceCode.getIssues());
  }

  @Test
  public void should_not_detect_on_xml_documents() {
    HtmlSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/AvoidHtmlCommentCheck/document.xml"), new AvoidHtmlCommentCheck());

    checkMessagesVerifier.verify(sourceCode.getIssues());
  }

  @Test
  public void should_not_detect_on_xhtml_documents() {
    HtmlSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/AvoidHtmlCommentCheck/document.xhtml"), new AvoidHtmlCommentCheck());

    checkMessagesVerifier.verify(sourceCode.getIssues());
  }

}
