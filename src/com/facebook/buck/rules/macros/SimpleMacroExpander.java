/*
 * Copyright 2017-present Facebook, Inc.
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

package com.facebook.buck.rules.macros;

import com.facebook.buck.model.BuildTarget;
import com.facebook.buck.model.macros.MacroException;
import com.facebook.buck.rules.BuildRuleResolver;
import com.facebook.buck.rules.CellPathResolver;
import com.facebook.buck.rules.args.Arg;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

/** A macro expander with no inputs or precomputed work */
public abstract class SimpleMacroExpander
    extends AbstractMacroExpanderWithoutPrecomputedWork<Void> {

  @Override
  protected final Void parse(
      BuildTarget target, CellPathResolver cellNames, ImmutableList<String> input)
      throws MacroException {
    return null;
  }

  @Override
  public final Class<Void> getInputClass() {
    return Void.class;
  }

  @Override
  public final Arg expandFrom(
      BuildTarget target, CellPathResolver cellNames, BuildRuleResolver resolver, Void input)
      throws MacroException {
    return expandFrom(target, cellNames, resolver);
  }

  public abstract Arg expandFrom(
      BuildTarget target, CellPathResolver cellNames, BuildRuleResolver resolver);

  @Override
  public final void extractParseTimeDepsFrom(
      BuildTarget target,
      CellPathResolver cellNames,
      Void input,
      ImmutableCollection.Builder<BuildTarget> buildDepsBuilder,
      ImmutableCollection.Builder<BuildTarget> targetGraphOnlyDepsBuilder)
      throws MacroException {
    extractParseTimeDepsFrom(target, cellNames, buildDepsBuilder, targetGraphOnlyDepsBuilder);
  }

  @SuppressWarnings("unused")
  public void extractParseTimeDepsFrom(
      BuildTarget target,
      CellPathResolver cellNames,
      ImmutableCollection.Builder<BuildTarget> buildDepsBuilder,
      ImmutableCollection.Builder<BuildTarget> targetGraphOnlyDepsBuilder) {}
}
