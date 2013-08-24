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

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.google.common.base.Function;
import com.bacoder.parser.core.Adapter;
import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.Annotation;
import com.bacoder.parser.java.api.NodeWithAnnotations;
import com.bacoder.parser.java.api.NodeWithClassOrInterfaceModifiers;
import com.bacoder.parser.java.api.NodeWithModifiers;
import com.bacoder.parser.java.api.NodeWithVariableModifiers;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.AnnotationContext;
import com.srctran.backend.parser.java.JavaParser.ClassOrInterfaceModifierContext;
import com.srctran.backend.parser.java.JavaParser.ModifierContext;
import com.srctran.backend.parser.java.JavaParser.VariableModifierContext;

public abstract class JavaAdapter<C extends ParseTree, D> extends Adapter<C, D> {

  public JavaAdapter(Adapters adapters) {
    super(adapters);
  }

  protected void setAnnotations(ParserRuleContext context, final NodeWithAnnotations node) {
    List<Annotation> annotations =
        transform(context, ClassOrInterfaceModifierContext.class,
            new Function<ClassOrInterfaceModifierContext, Annotation>() {
              @Override
              public Annotation apply(ClassOrInterfaceModifierContext context) {
                if (context.getChildCount() > 0
                    && context.getChild(0) instanceof AnnotationContext) {
                  AnnotationContext annotationContext = (AnnotationContext) context.getChild(0);
                  return getAdapter(AnnotationAdapter.class).adapt(annotationContext);
                } else {
                  return null;
                }
              }
            });
    node.setAnnotations(annotations);
  }

  protected void setClassOrInterfaceModifiers(ParserRuleContext context,
      final NodeWithClassOrInterfaceModifiers node) {
    setAnnotations(context, node);

    forEachChild(context, ClassOrInterfaceModifierContext.class,
        new Function<ClassOrInterfaceModifierContext, Void>() {
      @Override
      public Void apply(ClassOrInterfaceModifierContext context) {
        if (context.getChildCount() > 0 && context.getChild(0) instanceof TerminalNode) {
          TerminalNode child = (TerminalNode) context.getChild(0);
          int type = child.getSymbol().getType();
          switch (type) {
          case JavaParser.PUBLIC:
            node.setPublic(true);
            break;
          case JavaParser.PROTECTED:
            node.setProtected(true);
            break;
          case JavaParser.PRIVATE:
            node.setPrivate(true);
            break;
          case JavaParser.STATIC:
            node.setStatic(true);
            break;
          case JavaParser.ABSTRACT:
            node.setAbstract(true);
            break;
          case JavaParser.FINAL:
            node.setFinal(true);
            break;
          case JavaParser.STRICTFP:
            node.setStrictfp(true);
            break;
          default:
          }
        }
        return null;
      }
    });
  }

  protected void setModifiers(ParserRuleContext context, final NodeWithModifiers node) {
    setClassOrInterfaceModifiers(context, node);

    forEachChild(context, ModifierContext.class, new Function<ModifierContext, Void>() {
      @Override
      public Void apply(ModifierContext context) {
        if (context.getChildCount() > 0 && context.getChild(0) instanceof TerminalNode) {
          TerminalNode child = (TerminalNode) context.getChild(0);
          int type = child.getSymbol().getType();
          switch (type) {
          case JavaParser.NATIVE:
            node.setNative(true);
            break;
          case JavaParser.SYNCHRONIZED:
            node.setSynchronized(true);
            break;
          case JavaParser.TRANSIENT:
            node.setTransient(true);
            break;
          case JavaParser.VOLATILE:
            node.setVolatile(true);
            break;
          default:
          }
        }
        return null;
      }
    });
  }

  protected void setVariableModifiers(ParserRuleContext context,
      final NodeWithVariableModifiers node) {
    setAnnotations(context, node);

    forEachChild(context, VariableModifierContext.class,
        new Function<VariableModifierContext, Void>() {
      @Override
      public Void apply(VariableModifierContext context) {
        if (hasTerminalNode(context, JavaParser.FINAL)) {
          node.setFinal(true);
        }
        return null;
      }
    });
  }
}
