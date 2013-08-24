/**
 * Copyright 2013 Huining (Thomas) Feng (tfeng@berkeley.edu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bacoder.parser.java.adapter;

import java.util.List;

import com.google.common.base.Function;
import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.FormalParameter;
import com.srctran.backend.parser.java.JavaParser.FormalParameterContext;
import com.srctran.backend.parser.java.JavaParser.FormalParameterListContext;
import com.srctran.backend.parser.java.JavaParser.FormalParametersContext;
import com.srctran.backend.parser.java.JavaParser.LastFormalParameterContext;

public class FormalParametersAdapter
    extends JavaAdapter<FormalParametersContext, List<FormalParameter>> {

  public FormalParametersAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public List<FormalParameter> adapt(FormalParametersContext context) {
    FormalParameterListContext formalParameterListContext =
        getChild(context, FormalParameterListContext.class);
    if (formalParameterListContext == null) {
      return null;
    } else {
      List<FormalParameter> formalParameters =
          transform(formalParameterListContext, FormalParameterContext.class,
              new Function<FormalParameterContext, FormalParameter>() {
                @Override
                public FormalParameter apply(FormalParameterContext input) {
                  return null;
                }
              });

      LastFormalParameterContext lastFormalParameterContext =
          getChild(context, LastFormalParameterContext.class);
      if (lastFormalParameterContext != null) {
        FormalParameter lastFormalParameter =
            getAdapter(VariableFormalParameterAdapter.class).adapt(lastFormalParameterContext);
        if (lastFormalParameter != null) {
          formalParameters.add(lastFormalParameter);
        }
      }
      return formalParameters;
    }
  }
}
