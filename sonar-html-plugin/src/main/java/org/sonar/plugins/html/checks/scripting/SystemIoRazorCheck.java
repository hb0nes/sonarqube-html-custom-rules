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

@Rule(key = "SystemIoRazorCheck")
public class SystemIoRazorCheck extends AbstractPageCheck {

    public static String systemIoRazorWarning = "Using '@using System.IO' is potentially unsafe. Consider using system in and output operations in a controller.";

    @Override
    public void characters(TextNode textNode) {
        if (!textNode.isBlank()) {
            String temp = textNode.toString().replaceAll("(\r\n|\r|\n|\n\r)", "");
            if (temp.matches(".*@using[\\s]{1,}System\\.IO.*")) {
                createViolation(textNode, systemIoRazorWarning);
            }
        }
        super.characters(textNode);
    }
}