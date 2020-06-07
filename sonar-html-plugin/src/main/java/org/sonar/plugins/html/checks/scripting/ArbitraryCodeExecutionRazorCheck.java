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

import org.sonar.check.Rule;
import org.sonar.plugins.html.checks.AbstractPageCheck;
import org.sonar.plugins.html.node.TextNode;

@Rule(key = "ArbitraryCodeExecutionRazorCheck")
public class ArbitraryCodeExecutionRazorCheck extends AbstractPageCheck {

    public static String arbitraryCodeExecutionRazorWarning = "Using @( ) and @{ } is unsafe when used with untrusted user input. It allows arbitrary code execution, make sure no untrusted user input is used or whether you really need to make use of @( ) and @{ }.";

    @Override
    public void characters(TextNode textNode) {
        if (!textNode.isBlank()) {
            String temp = textNode.toString().replaceAll("(\r\n|\r|\n|\n\r)", "");
            if (temp.matches(".*@\\{.*}.*") || temp.matches(".*@\\(.*\\).*")) {
                createViolation(textNode, arbitraryCodeExecutionRazorWarning);
            }
        }
        super.characters(textNode);
    }
}