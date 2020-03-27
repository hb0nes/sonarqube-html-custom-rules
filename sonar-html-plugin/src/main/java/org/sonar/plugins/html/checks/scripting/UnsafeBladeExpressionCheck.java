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
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.node.TextNode;

import java.util.List;

@Rule(key = "UnsafeBladeExpressionCheck")
public class UnsafeBladeExpressionCheck extends AbstractPageCheck {

    @Override
    public void characters(TextNode textNode) {
        if (!textNode.isBlank()) {
            if (textNode.toString().matches(".*\\{!!.*!!}.*")) {
                createViolation(textNode, "An unsafe use of Blade Expressions was found. Use {{ }} to make sure output is escaped to prevent XSS.");
            }
        }
        super.characters(textNode);
    }

}
