/*
 * Copyright 2013-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.cli;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.facebook.buck.testutil.integration.DebuggableTemporaryFolder;
import com.facebook.buck.testutil.integration.ProjectWorkspace;
import com.facebook.buck.testutil.integration.ProjectWorkspace.ProcessResult;
import com.facebook.buck.testutil.integration.TestDataHelper;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

public class AuditRulesCommandIntegrationTest {

  @Rule
  public DebuggableTemporaryFolder tmp = new DebuggableTemporaryFolder();

  @Test
  public void testBuckAuditRules() throws IOException {
    ProjectWorkspace workspace = TestDataHelper.createProjectWorkspaceForScenario(
        this, "audit_rules", tmp);
    workspace.setUp();

    // Print all of the rules in a file.
    ProcessResult result1 = workspace.runBuckCommand("audit", "rules", "example/BUCK");
    result1.assertSuccess();
    assertThat(result1.getStdout(), is(equalTo(workspace.getFileContents("stdout.all"))));

    // Print all of the rules filtered by type.
    ProcessResult result2 = workspace.runBuckCommand(
        "audit", "rules", "--type", "genrule", "example/BUCK");
    result2.assertSuccess();
    assertThat(result2.getStdout(), is(equalTo(workspace.getFileContents("stdout.genrule"))));

    // Print all of the rules using multiple filters.
    ProcessResult result3 = workspace.runBuckCommand(
        "audit", "rules", "-t", "genrule", "-t", "keystore", "example/BUCK");
    result3.assertSuccess();
    assertThat(result3.getStdout(), is(equalTo(workspace.getFileContents("stdout.all"))));
  }

  @Test
  public void testBuckAuditRulesJsonOutput() throws IOException {
    ProjectWorkspace workspace = TestDataHelper.createProjectWorkspaceForScenario(
        this, "audit_rules", tmp);
    workspace.setUp();

    // Print all of the rules in a file.
    ProcessResult result1 = workspace.runBuckCommand("audit", "rules", "--json", "example/BUCK");
    result1.assertSuccess();
    assertThat(result1.getStdout(), is(equalTo(workspace.getFileContents("stdout.all.json"))));

    // Print all of the rules filtered by type.
    ProcessResult result2 = workspace.runBuckCommand(
        "audit", "rules", "--type", "genrule", "--json", "example/BUCK");
    result2.assertSuccess();
    assertThat(result2.getStdout(), is(equalTo(workspace.getFileContents("stdout.genrule.json"))));
  }
}
