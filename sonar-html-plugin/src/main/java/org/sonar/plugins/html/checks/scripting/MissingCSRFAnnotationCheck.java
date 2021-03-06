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
import org.sonar.plugins.html.node.TagNode;
import org.sonar.plugins.html.node.TextNode;

@Rule(key = "MissingCSRFAnnotationCheck")
public class MissingCSRFAnnotationCheck extends AbstractPageCheck {

    boolean annotationPresent;
    TagNode formTag;
    public static final String ERROR_MESSAGE = "Using a form without @csrf annotation is unsafe. Include this annotation to prevent CSRF.";
    public static final String FORM_TAG = "form";

    @Override
    public void startElement(TagNode element) {
        if (element.equalsElementName(FORM_TAG))
            formTag = element;
    }

    @Override
    public void endElement(TagNode element) {
        if (element.equalsElementName(FORM_TAG)) {
            if (!annotationPresent)
                createViolation(formTag, ERROR_MESSAGE);
            formTag = null;
            annotationPresent = false;
        }
    }

    @Override
    public void characters(TextNode textNode) {
        if (!textNode.isBlank() && textNode.toString().contains("@csrf") && formTag != null)
            annotationPresent = true;
    }
}
