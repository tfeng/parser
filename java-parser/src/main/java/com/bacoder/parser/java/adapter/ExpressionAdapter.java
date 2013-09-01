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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.JavaParser;
import com.bacoder.parser.java.JavaParser.ArgumentsContext;
import com.bacoder.parser.java.JavaParser.ArrayCreatorRestContext;
import com.bacoder.parser.java.JavaParser.ArrayInitializerContext;
import com.bacoder.parser.java.JavaParser.ClassBodyContext;
import com.bacoder.parser.java.JavaParser.ClassCreatorRestContext;
import com.bacoder.parser.java.JavaParser.CreatedNameContext;
import com.bacoder.parser.java.JavaParser.CreatorContext;
import com.bacoder.parser.java.JavaParser.ExplicitGenericInvocationContext;
import com.bacoder.parser.java.JavaParser.ExplicitGenericInvocationSuffixContext;
import com.bacoder.parser.java.JavaParser.ExpressionContext;
import com.bacoder.parser.java.JavaParser.ExpressionListContext;
import com.bacoder.parser.java.JavaParser.InnerCreatorContext;
import com.bacoder.parser.java.JavaParser.NonWildcardTypeArgumentsContext;
import com.bacoder.parser.java.JavaParser.NonWildcardTypeArgumentsOrDiamondContext;
import com.bacoder.parser.java.JavaParser.PrimaryContext;
import com.bacoder.parser.java.JavaParser.PrimitiveTypeContext;
import com.bacoder.parser.java.JavaParser.SuperSuffixContext;
import com.bacoder.parser.java.JavaParser.TypeArgumentsContext;
import com.bacoder.parser.java.JavaParser.TypeArgumentsOrDiamondContext;
import com.bacoder.parser.java.JavaParser.TypeContext;
import com.bacoder.parser.java.api.ArrayAccess;
import com.bacoder.parser.java.api.ArrayCreation;
import com.bacoder.parser.java.api.ArrayCreationDimension;
import com.bacoder.parser.java.api.ArrayInitializer;
import com.bacoder.parser.java.api.ClassInstantiation;
import com.bacoder.parser.java.api.ClassMemberDeclaration;
import com.bacoder.parser.java.api.Expression;
import com.bacoder.parser.java.api.ExpressionInScope;
import com.bacoder.parser.java.api.GeneralInvocation;
import com.bacoder.parser.java.api.Identifier;
import com.bacoder.parser.java.api.IdentifierWithTypeArguments;
import com.bacoder.parser.java.api.InfixExpression;
import com.bacoder.parser.java.api.InstanceOf;
import com.bacoder.parser.java.api.InstantiableType;
import com.bacoder.parser.java.api.Invocation;
import com.bacoder.parser.java.api.MethodInvocation;
import com.bacoder.parser.java.api.PostfixExpression;
import com.bacoder.parser.java.api.PrefixExpression;
import com.bacoder.parser.java.api.ScopedExpression;
import com.bacoder.parser.java.api.SuperExpression;
import com.bacoder.parser.java.api.SuperInvocation;
import com.bacoder.parser.java.api.TernaryExpression;
import com.bacoder.parser.java.api.ThisExpression;
import com.bacoder.parser.java.api.ThisInvocation;
import com.bacoder.parser.java.api.Type;
import com.bacoder.parser.java.api.TypeCast;
import com.bacoder.parser.java.api.TypeWithArguments;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

public class ExpressionAdapter extends JavaAdapter<ExpressionContext, Expression> {

  private static final Map<Integer, InfixExpression.Operator> INFIX_OPERATOR_MAP =
      ImmutableMap.<Integer, InfixExpression.Operator>builder()
                  .put(JavaParser.ADD, InfixExpression.Operator.ADD)
                  .put(JavaParser.ADD_ASSIGN, InfixExpression.Operator.ADD_ASSIGN)
                  .put(JavaParser.AND, InfixExpression.Operator.AND)
                  .put(JavaParser.AND_ASSIGN, InfixExpression.Operator.BIT_AND_ASSIGN)
                  .put(JavaParser.ASSIGN, InfixExpression.Operator.ASSIGN)
                  .put(JavaParser.BITAND, InfixExpression.Operator.BIT_AND)
                  .put(JavaParser.BITOR, InfixExpression.Operator.BIT_OR)
                  .put(JavaParser.CARET, InfixExpression.Operator.BIT_XOR)
                  .put(JavaParser.DIV, InfixExpression.Operator.DIVIDE)
                  .put(JavaParser.DIV_ASSIGN, InfixExpression.Operator.DIVIDE_ASSIGN)
                  .put(JavaParser.EQUAL, InfixExpression.Operator.EQUAL)
                  .put(JavaParser.GE, InfixExpression.Operator.GREATER_EQUAL)
                  .put(JavaParser.GT, InfixExpression.Operator.GREATER)
                  .put(JavaParser.LE, InfixExpression.Operator.LESS_EQUAL)
                  .put(JavaParser.LSHIFT_ASSIGN, InfixExpression.Operator.LEFT_SHIFT_ASSIGN)
                  .put(JavaParser.LT, InfixExpression.Operator.LESS)
                  .put(JavaParser.MOD, InfixExpression.Operator.MOD)
                  .put(JavaParser.MOD_ASSIGN, InfixExpression.Operator.MOD_ASSIGN)
                  .put(JavaParser.MUL, InfixExpression.Operator.MULTIPLY)
                  .put(JavaParser.MUL_ASSIGN, InfixExpression.Operator.MULTIPLY_ASSIGN)
                  .put(JavaParser.NOTEQUAL, InfixExpression.Operator.NOT_EQUAL)
                  .put(JavaParser.OR, InfixExpression.Operator.OR)
                  .put(JavaParser.OR_ASSIGN, InfixExpression.Operator.BIT_OR_ASSIGN)
                  .put(JavaParser.RSHIFT_ASSIGN,
                      InfixExpression.Operator.SIGNED_RIGHT_SHIFT_ASSIGN)
                  .put(JavaParser.SUB, InfixExpression.Operator.SUBTRACT)
                  .put(JavaParser.SUB_ASSIGN, InfixExpression.Operator.SUBTRACT_ASSIGN)
                  .put(JavaParser.URSHIFT_ASSIGN,
                      InfixExpression.Operator.UNSIGNED_RIGHT_SHIFT_ASSIGN)
                  .put(JavaParser.XOR_ASSIGN, InfixExpression.Operator.BIT_XOR_ASSIGN)
                  .build();

  private static final Map<Integer, PostfixExpression.Operator> POSTFIX_OPERATOR_MAP =
      ImmutableMap.of(
          JavaParser.DEC, PostfixExpression.Operator.DECREMENT,
          JavaParser.INC, PostfixExpression.Operator.INCREMENT);

  private static final Map<Integer, PrefixExpression.Operator> PREFIX_OPERATOR_MAP =
      ImmutableMap.<Integer, PrefixExpression.Operator>builder()
                  .put(JavaParser.ADD, PrefixExpression.Operator.POSITIVE)
                  .put(JavaParser.BANG, PrefixExpression.Operator.NOT)
                  .put(JavaParser.DEC, PrefixExpression.Operator.DECREMENT)
                  .put(JavaParser.INC, PrefixExpression.Operator.INCREMENT)
                  .put(JavaParser.SUB, PrefixExpression.Operator.NEGATIVE)
                  .put(JavaParser.TILDE, PrefixExpression.Operator.INVERT)
                  .build();

  public ExpressionAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public Expression adapt(ExpressionContext context) {
    PrimaryContext primaryContext = getChild(context, PrimaryContext.class);
    if (primaryContext != null) {
      return getAdapter(PrimaryExpressionAdapter.class).adapt(primaryContext);
    }

    if (hasTerminalNode(context, JavaParser.DOT)) {
      return processScopedExpression(context);
    }

    if (hasTerminalNode(context, JavaParser.LBRACK)) {
      ArrayAccess arrayAccess = createNode(context, ArrayAccess.class);

      if (context.getChildCount() > 0 && context.getChild(0) instanceof ExpressionContext) {
        ExpressionContext expressionContext = (ExpressionContext) context.getChild(0);
        arrayAccess.setArray(getAdapter(ExpressionAdapter.class).adapt(expressionContext));
      }

      if (context.getChildCount() > 2 && context.getChild(2) instanceof ExpressionContext) {
        ExpressionContext expressionContext = (ExpressionContext) context.getChild(2);
        arrayAccess.setIndex(getAdapter(ExpressionAdapter.class).adapt(expressionContext));
      }

      return arrayAccess;
    }

    if (context.getChildCount() > 2
        && context.getChild(0) instanceof ExpressionContext
        && isTerminalNode(context, 1, JavaParser.LPAREN)) {
      return processExpressionInvocation(context);
    }

    if (context.getChildCount() > 0 && isTerminalNode(context, 0, JavaParser.NEW)) {
      return processCreator(context);
    }

    if (context.getChildCount() > 0
        && context.getChild(0) instanceof TerminalNode
        && ((TerminalNode) context.getChild(0)).getSymbol().getType() == JavaParser.LPAREN) {
      TypeCast typeCast = createNode(context, TypeCast.class);

      TypeContext typeContext = getChild(context, TypeContext.class);
      if (typeContext != null) {
        typeCast.setType(getAdapter(TypeAdapter.class).adapt(typeContext));
      }

      ExpressionContext expressionContext = getChild(context, ExpressionContext.class);
      if (expressionContext != null) {
        typeCast.setExpression(getAdapter(ExpressionAdapter.class).adapt(expressionContext));
      }

      return typeCast;
    }

    if (context.getChildCount() == 2
        && context.getChild(1) instanceof TerminalNode
        && POSTFIX_OPERATOR_MAP.containsKey(
            ((TerminalNode) context.getChild(1)).getSymbol().getType())) {
      PostfixExpression postfixExpression = createNode(context, PostfixExpression.class);

      postfixExpression.setOperator(
          POSTFIX_OPERATOR_MAP.get(((TerminalNode) context.getChild(1)).getSymbol().getType()));

      ExpressionContext expressionContext = getChild(context, ExpressionContext.class);
      if (expressionContext != null) {
        postfixExpression.setExpression(
            getAdapter(ExpressionAdapter.class).adapt(expressionContext));
      }

      return postfixExpression;
    }

    if (context.getChildCount() == 2
        && context.getChild(0) instanceof TerminalNode
        && PREFIX_OPERATOR_MAP.containsKey(
            ((TerminalNode) context.getChild(0)).getSymbol().getType())) {
      PrefixExpression prefixExpression = createNode(context, PrefixExpression.class);

      prefixExpression.setOperator(
          PREFIX_OPERATOR_MAP.get(((TerminalNode) context.getChild(0)).getSymbol().getType()));

      ExpressionContext expressionContext = getChild(context, ExpressionContext.class);
      if (expressionContext != null) {
        prefixExpression.setExpression(
            getAdapter(ExpressionAdapter.class).adapt(expressionContext));
      }

      return prefixExpression;
    }

    if (context.getChildCount() > 2
        && context.getChild(1) instanceof TerminalNode
        && INFIX_OPERATOR_MAP.containsKey(
            ((TerminalNode) context.getChild(1)).getSymbol().getType())
        && context.getChild(0) instanceof ExpressionContext
        && context.getChild(context.getChildCount() - 1) instanceof ExpressionContext) {
      InfixExpression.Operator operator = null;
      if (context.getChildCount() == 3) {
        operator =
            INFIX_OPERATOR_MAP.get(((TerminalNode) context.getChild(1)).getSymbol().getType());
      } else if (context.getChildCount() == 4
          && isTerminalNode(context, 1, JavaParser.LT)
          && isTerminalNode(context, 2, JavaParser.LT)) {
        operator = InfixExpression.Operator.LEFT_SHIFT;
      } else if (context.getChildCount() == 4
          && isTerminalNode(context, 1, JavaParser.GT)
          && isTerminalNode(context, 2, JavaParser.GT)) {
        operator = InfixExpression.Operator.SIGNED_RIGHT_SHIFT;
      } else if (context.getChildCount() == 5
          && isTerminalNode(context, 1, JavaParser.GT)
          && isTerminalNode(context, 2, JavaParser.GT)
          && isTerminalNode(context, 3, JavaParser.GT)) {
        operator = InfixExpression.Operator.UNSIGNED_RIGHT_SHIFT;
      }

      if (operator != null) {
        InfixExpression infixExpression = createNode(context, InfixExpression.class);
        infixExpression.setLeftHandSide(
            getAdapter(ExpressionAdapter.class).adapt((ExpressionContext) context.getChild(0)));
        infixExpression.setOperator(operator);
        infixExpression.setRightHandSide(
            getAdapter(ExpressionAdapter.class).adapt(
                (ExpressionContext) context.getChild(context.getChildCount() - 1)));
        return infixExpression;
      }
    }

    if (hasTerminalNode(context, JavaParser.INSTANCEOF)) {
      InstanceOf instanceOf = createNode(context, InstanceOf.class);

      ExpressionContext expressionContext = getChild(context, ExpressionContext.class);
      if (expressionContext != null) {
        instanceOf.setExpression(getAdapter(ExpressionAdapter.class).adapt(expressionContext));
      }

      TypeContext typeContext = getChild(context, TypeContext.class);
      if (typeContext != null) {
        instanceOf.setType(getAdapter(TypeAdapter.class).adapt(typeContext));
      }

      return instanceOf;
    }

    if (hasTerminalNode(context, JavaParser.QUESTION)) {
      TernaryExpression ternaryExpression = createNode(context, TernaryExpression.class);

      Iterator<ExpressionContext> expressionContexts =
          getChildren(context, ExpressionContext.class).iterator();
      if (expressionContexts.hasNext()) {
        ternaryExpression.setCondition(
            getAdapter(ExpressionAdapter.class).adapt(expressionContexts.next()));
      }
      if (expressionContexts.hasNext()) {
        ternaryExpression.setThenExpression(
            getAdapter(ExpressionAdapter.class).adapt(expressionContexts.next()));
      }
      if (expressionContexts.hasNext()) {
        ternaryExpression.setElseExpression(
            getAdapter(ExpressionAdapter.class).adapt(expressionContexts.next()));
      }

      return ternaryExpression;
    }

    return null;
  }

  protected List<IdentifierWithTypeArguments> convertIdentifiersWithTypeArguments(
      ParserRuleContext context) {
    final List<IdentifierWithTypeArguments> identifierWithTypeArguments =
        new ArrayList<IdentifierWithTypeArguments>();

    forEachChild(context, new Function<ParseTree, Void>() {
      @Override
      public Void apply(ParseTree input) {
        if (input instanceof TerminalNode) {
          TerminalNode terminalNode = (TerminalNode) input;
          if (terminalNode.getSymbol().getType() == JavaParser.Identifier) {
            IdentifierWithTypeArguments element =
                createNode(input, IdentifierWithTypeArguments.class);
            element.setIdentifier(getAdapter(IdentifierAdapter.class).adapt(terminalNode));
            identifierWithTypeArguments.add(element);
          }
        } else if (input instanceof TypeArgumentsOrDiamondContext) {
          int size = identifierWithTypeArguments.size();
          if (size > 0) {
            IdentifierWithTypeArguments element = identifierWithTypeArguments.get(size - 1);
            setNodeAttributes(element, null, input);

            TypeArgumentsOrDiamondContext typeArgumentsOrDiamondContext =
                (TypeArgumentsOrDiamondContext) input;
            TypeArgumentsContext typeArgumentsContext =
                getChild(typeArgumentsOrDiamondContext, TypeArgumentsContext.class);
            if (typeArgumentsContext != null) {
              element.setTypeArguments(
                  getAdapter(TypeArgumentsAdapter.class).adapt(typeArgumentsContext));
            }
          }
        } else if (input instanceof NonWildcardTypeArgumentsOrDiamondContext) {
          int size = identifierWithTypeArguments.size();
          if (size > 0) {
            IdentifierWithTypeArguments element = identifierWithTypeArguments.get(size - 1);
            setNodeAttributes(element, null, input);

            NonWildcardTypeArgumentsContext nonWildcardTypeArgumentsContext =
                (NonWildcardTypeArgumentsContext) input;
            element.setTypeArguments(getAdapter(NonWildcardTypeArgumentsAdapter.class).adapt(
                nonWildcardTypeArgumentsContext));
          }
        }
        return null;
      }
    });

    return identifierWithTypeArguments;
  }

  protected Expression processCreator(ExpressionContext context) {
    CreatorContext creatorContext = getChild(context, CreatorContext.class);
    if (creatorContext != null) {
      List<Type> nonWildcardTypeArguments = Collections.emptyList();
      InstantiableType instantiableType = null;
      List<Expression> arguments = Collections.emptyList();
      List<ClassMemberDeclaration> classMemberDeclarations = Collections.emptyList();
      List<ArrayCreationDimension> arrayDimensions = null;
      ArrayInitializer arrayInitializer = null;

      NonWildcardTypeArgumentsContext nonWildcardTypeArgumentsContext =
          getChild(creatorContext, NonWildcardTypeArgumentsContext.class);
      if (nonWildcardTypeArgumentsContext != null) {
        nonWildcardTypeArguments =
            getAdapter(NonWildcardTypeArgumentsAdapter.class).adapt(
                nonWildcardTypeArgumentsContext);
      }

      CreatedNameContext createdNameContext = getChild(creatorContext, CreatedNameContext.class);
      if (createdNameContext != null)
      {
        PrimitiveTypeContext primitiveTypeContext =
            getChild(createdNameContext, PrimitiveTypeContext.class);
        if (primitiveTypeContext != null) {
          instantiableType = getAdapter(PrimitiveTypeAdapter.class).adapt(primitiveTypeContext);
        } else {
          TypeWithArguments typeWithArguments =
              createNode(createdNameContext, TypeWithArguments.class);
          typeWithArguments.setIdentifiersWithTypeArguments(
              convertIdentifiersWithTypeArguments(createdNameContext));
          instantiableType = typeWithArguments;
        }
      }

      ClassCreatorRestContext classCreatorRestContext =
          getChild(creatorContext, ClassCreatorRestContext.class);
      if (classCreatorRestContext != null) {
        ArgumentsContext argumentsContext = getChild(classCreatorRestContext, ArgumentsContext.class);
        if (argumentsContext != null) {
          arguments = getAdapter(ArgumentsAdapter.class).adapt(argumentsContext);
        }

        ClassBodyContext classBodyContext = getChild(classCreatorRestContext, ClassBodyContext.class);
        if (classBodyContext != null) {
          classMemberDeclarations = getAdapter(ClassBodyAdapter.class).adapt(classBodyContext);
        }
      }

      ArrayCreatorRestContext arrayCreatorRestContext =
          getChild(creatorContext, ArrayCreatorRestContext.class);
      if (arrayCreatorRestContext != null) {
        final List<ArrayCreationDimension> dimensions = new ArrayList<ArrayCreationDimension>();
        forEachChild(arrayCreatorRestContext, new Function<ParseTree, Void>() {
          @Override
          public Void apply(ParseTree input) {
            if (input instanceof TerminalNode) {
              if (((TerminalNode) input).getSymbol().getType() == JavaParser.LBRACK) {
                ArrayCreationDimension arrayCreationDimension =
                    createNode(input, ArrayCreationDimension.class);
                dimensions.add(arrayCreationDimension);
              } else if (((TerminalNode) input).getSymbol().getType() == JavaParser.RBRACK) {
                setNodeAttributes(dimensions.get(dimensions.size() - 1), null, input);
              }
            } else if (input instanceof ExpressionContext) {
              dimensions.get(dimensions.size() - 1).setSize(
                  getAdapter(ExpressionAdapter.class).adapt((ExpressionContext) input));
            }
            return null;
          }
        });
        arrayDimensions = dimensions;

        ArrayInitializerContext arrayInitializerContext =
            getChild(arrayCreatorRestContext, ArrayInitializerContext.class);
        if (arrayInitializerContext != null) {
          arrayInitializer =
              getAdapter(ArrayInitializerAdapter.class).adapt(arrayInitializerContext);
        }
      }

      if (arrayDimensions != null) {
        ArrayCreation arrayCreation = createNode(context, ArrayCreation.class);
        arrayCreation.setElementType(instantiableType);
        arrayCreation.setDimensions(arrayDimensions);
        arrayCreation.setInitializer(arrayInitializer);
        return arrayCreation;
      } else {
        ClassInstantiation newInvocation = createNode(context, ClassInstantiation.class);
        newInvocation.setTypeArguments(nonWildcardTypeArguments);
        newInvocation.setType(instantiableType);
        newInvocation.setArguments(arguments);
        newInvocation.setMemberDeclarations(classMemberDeclarations);
        return newInvocation;
      }
    }

    return null;
  }

  protected Invocation processExpressionInvocation(ExpressionContext context) {
    ExpressionContext expressionContext = getChild(context, ExpressionContext.class);
    if (expressionContext == null) {
      return null;
    }

    Expression expression = getAdapter(ExpressionAdapter.class).adapt(expressionContext);

    Invocation invocation = null;
    if (expression instanceof ThisExpression) {
      invocation = createNode(context, ThisInvocation.class);
    } else if (expression instanceof SuperExpression) {
      invocation = createNode(context, SuperInvocation.class);
    } else if (expression instanceof Identifier) {
      MethodInvocation methodInvocation = createNode(context, MethodInvocation.class);
      methodInvocation.setName((Identifier) expression);
    } else if (expression instanceof ScopedExpression) {
      ScopedExpression scopedExpression = (ScopedExpression) expression;
      if (scopedExpression.getExpression() instanceof ThisExpression) {
        invocation = createNode(context, ThisInvocation.class);
        invocation.setScope(scopedExpression.getScope());
      } else if (scopedExpression.getExpression() instanceof SuperExpression) {
        invocation = createNode(context, SuperInvocation.class);
        invocation.setScope(scopedExpression.getScope());
      } else if (scopedExpression.getExpression() instanceof Identifier) {
        MethodInvocation methodInvocation = createNode(context, MethodInvocation.class);
        methodInvocation.setScope(scopedExpression.getScope());
        methodInvocation.setName((Identifier) scopedExpression.getExpression());
        invocation = methodInvocation;
      }
    }

    if (invocation == null) {
      GeneralInvocation generalInvocation = createNode(context, GeneralInvocation.class);
      if (expression instanceof ScopedExpression) {
        ScopedExpression scopedExpression = (ScopedExpression) expression;
        generalInvocation.setScope(scopedExpression.getScope());
        generalInvocation.setExpression(scopedExpression.getExpression());
        invocation = generalInvocation;
      } else {
        generalInvocation.setExpression(expression);
      }
      invocation = generalInvocation;
    }

    ExpressionListContext expressionListContext = getChild(context, ExpressionListContext.class);
    if (expressionListContext != null) {
      invocation.setArguments(getAdapter(ExpressionListAdapter.class).adapt(expressionListContext));
    }

    return invocation;
  }

  protected ScopedExpression processScopedExpression(ExpressionContext context) {
    ScopedExpression scopedExpression = createNode(context, ScopedExpression.class);

    ExpressionContext expressionContext = getChild(context, ExpressionContext.class);
    if (expressionContext != null) {
      scopedExpression.setScope(getAdapter(ExpressionAdapter.class).adapt(expressionContext));
    }

    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      scopedExpression.setExpression(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
      return scopedExpression;
    }

    TerminalNode thisNode = getTerminalNode(context, JavaParser.THIS);
    if (thisNode != null) {
      scopedExpression.setExpression(createNode(thisNode, ThisExpression.class));
      return scopedExpression;
    }

    TerminalNode newNode = getTerminalNode(context, JavaParser.NEW);
    if (newNode != null) {
      ClassInstantiation newInvocation = createNode(newNode, context, ClassInstantiation.class);

      NonWildcardTypeArgumentsContext nonWildcardTypeArgumentsContext =
          getChild(context, NonWildcardTypeArgumentsContext.class);
      if (nonWildcardTypeArgumentsContext != null) {
        newInvocation.setTypeArguments(getAdapter(NonWildcardTypeArgumentsAdapter.class).adapt(
            nonWildcardTypeArgumentsContext));
      }

      InnerCreatorContext innerCreatorContext = getChild(context, InnerCreatorContext.class);
      if (innerCreatorContext != null) {
        TerminalNode typeIdentifierNode =
            getTerminalNode(innerCreatorContext, JavaParser.Identifier);
        NonWildcardTypeArgumentsOrDiamondContext nonWildcardTypeArgumentsOrDiamondContext =
            getChild(innerCreatorContext, NonWildcardTypeArgumentsOrDiamondContext.class);
        TypeWithArguments typeWithArguments =
            createNode(typeIdentifierNode,
                nonWildcardTypeArgumentsOrDiamondContext == null
                    ? typeIdentifierNode
                    : nonWildcardTypeArgumentsOrDiamondContext,
                TypeWithArguments.class);
        typeWithArguments.setIdentifiersWithTypeArguments(
            convertIdentifiersWithTypeArguments(innerCreatorContext));
        newInvocation.setType(typeWithArguments);

        ClassCreatorRestContext classCreatorRestContext =
            getChild(innerCreatorContext, ClassCreatorRestContext.class);
        if (classCreatorRestContext != null) {
          ArgumentsContext arguments = getChild(classCreatorRestContext, ArgumentsContext.class);
          if (arguments != null) {
            newInvocation.setArguments(getAdapter(ArgumentsAdapter.class).adapt(arguments));
          }

          ClassBodyContext classBodyContext =
              getChild(classCreatorRestContext, ClassBodyContext.class);
          if (classBodyContext != null) {
            newInvocation.setMemberDeclarations(
                getAdapter(ClassBodyAdapter.class).adapt(classBodyContext));
          }
        }
      }

      scopedExpression.setExpression(newInvocation);
      return scopedExpression;
    }

    TerminalNode superNode = getTerminalNode(context, JavaParser.SUPER);
    if (superNode != null) {
      SuperInvocation superInvocation = createNode(superNode, context, SuperInvocation.class);

      SuperSuffixContext superSuffixContext = getChild(context, SuperSuffixContext.class);
      if (superSuffixContext != null) {
        superInvocation =
            getAdapter(SuperSuffixAdapter.class).adapt(superSuffixContext, superInvocation);
      }

      scopedExpression.setExpression(superInvocation);
      return scopedExpression;
    }

    ExplicitGenericInvocationContext explicitGenericInvocationContext =
        getChild(context, ExplicitGenericInvocationContext.class);
    if (explicitGenericInvocationContext != null) {
      List<Type> typeList = Collections.emptyList();

      NonWildcardTypeArgumentsContext nonWildcardTypeArgumentsContext =
          getChild(explicitGenericInvocationContext, NonWildcardTypeArgumentsContext.class);
      if (nonWildcardTypeArgumentsContext != null) {
        typeList = getAdapter(NonWildcardTypeArgumentsAdapter.class).adapt(
            nonWildcardTypeArgumentsContext);
      }

      ExplicitGenericInvocationSuffixContext explicitGenericInvocationSuffixContext =
          getChild(explicitGenericInvocationContext, ExplicitGenericInvocationSuffixContext.class);
      if (explicitGenericInvocationSuffixContext != null) {
        scopedExpression.setExpression((ExpressionInScope)
            getAdapter(ExplicitGenericInvocationSuffixAdapter.class).adapt(
                explicitGenericInvocationSuffixContext, typeList));
      }

      return scopedExpression;
    }

    return scopedExpression;
  }
}
