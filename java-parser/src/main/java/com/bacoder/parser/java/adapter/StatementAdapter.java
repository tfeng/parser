package com.bacoder.parser.java.adapter;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.ArrayType;
import com.bacoder.parser.java.api.AssertStatement;
import com.bacoder.parser.java.api.BlockStatement;
import com.bacoder.parser.java.api.BreakStatement;
import com.bacoder.parser.java.api.CatchClause;
import com.bacoder.parser.java.api.ContinueStatement;
import com.bacoder.parser.java.api.DefaultSwitchLabel;
import com.bacoder.parser.java.api.DoWhileStatement;
import com.bacoder.parser.java.api.Expression;
import com.bacoder.parser.java.api.ExpressionStatement;
import com.bacoder.parser.java.api.ForEachControl;
import com.bacoder.parser.java.api.ForStatement;
import com.bacoder.parser.java.api.IfStatement;
import com.bacoder.parser.java.api.LoopControl;
import com.bacoder.parser.java.api.QualifiedName;
import com.bacoder.parser.java.api.Resource;
import com.bacoder.parser.java.api.ReturnStatement;
import com.bacoder.parser.java.api.Statement;
import com.bacoder.parser.java.api.SwitchBlock;
import com.bacoder.parser.java.api.SwitchLabel;
import com.bacoder.parser.java.api.SwitchStatement;
import com.bacoder.parser.java.api.SynchronizedStatement;
import com.bacoder.parser.java.api.ThrowStatement;
import com.bacoder.parser.java.api.TryStatement;
import com.bacoder.parser.java.api.Type;
import com.bacoder.parser.java.api.WhileStatement;
import com.google.common.base.Function;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.BlockContext;
import com.srctran.backend.parser.java.JavaParser.BlockStatementContext;
import com.srctran.backend.parser.java.JavaParser.CatchClauseContext;
import com.srctran.backend.parser.java.JavaParser.CatchTypeContext;
import com.srctran.backend.parser.java.JavaParser.ClassOrInterfaceTypeContext;
import com.srctran.backend.parser.java.JavaParser.ConstantExpressionContext;
import com.srctran.backend.parser.java.JavaParser.EnhancedForControlContext;
import com.srctran.backend.parser.java.JavaParser.EnumConstantNameContext;
import com.srctran.backend.parser.java.JavaParser.ExpressionContext;
import com.srctran.backend.parser.java.JavaParser.ExpressionListContext;
import com.srctran.backend.parser.java.JavaParser.FinallyBlockContext;
import com.srctran.backend.parser.java.JavaParser.ForControlContext;
import com.srctran.backend.parser.java.JavaParser.ForInitContext;
import com.srctran.backend.parser.java.JavaParser.ForUpdateContext;
import com.srctran.backend.parser.java.JavaParser.LocalVariableDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.ParExpressionContext;
import com.srctran.backend.parser.java.JavaParser.QualifiedNameContext;
import com.srctran.backend.parser.java.JavaParser.ResourceContext;
import com.srctran.backend.parser.java.JavaParser.ResourceSpecificationContext;
import com.srctran.backend.parser.java.JavaParser.ResourcesContext;
import com.srctran.backend.parser.java.JavaParser.StatementContext;
import com.srctran.backend.parser.java.JavaParser.StatementExpressionContext;
import com.srctran.backend.parser.java.JavaParser.SwitchBlockStatementGroupContext;
import com.srctran.backend.parser.java.JavaParser.SwitchLabelContext;
import com.srctran.backend.parser.java.JavaParser.TypeContext;
import com.srctran.backend.parser.java.JavaParser.VariableDeclaratorIdContext;

public class StatementAdapter extends JavaAdapter<StatementContext, Statement> {

  public StatementAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public Statement adapt(StatementContext context) {
    TerminalNode firstTerminal = getChild(context, TerminalNode.class);
    if (firstTerminal != null) {
      switch (firstTerminal.getSymbol().getType()) {
      case JavaParser.ASSERT:
        return processAssertStatement(context);
      case JavaParser.IF:
        return processIfStatement(context);
      case JavaParser.FOR:
        return processForStatement(context);
      case JavaParser.WHILE:
        return processWhileStatement(context);
      case JavaParser.DO:
        return processDoWhileStatement(context);
      case JavaParser.TRY:
        return processTryStatement(context);
      case JavaParser.SWITCH:
        return processSwitchStatement(context);
      case JavaParser.SYNCHRONIZED:
        return processSynchronizedStatement(context);
      case JavaParser.RETURN:
        return processReturnStatement(context);
      case JavaParser.THROW:
        return processThrowStatement(context);
      case JavaParser.BREAK:
        return processBreakStatement(context);
      case JavaParser.CONTINUE:
        return processContinueStatement(context);
      case JavaParser.Identifier: {
        if (context.children.size() > 1
            && context.children.get(1) instanceof TerminalNode
            && ((TerminalNode) context.children.get(1)).getSymbol().getType() == JavaParser.COLON) {
          StatementContext statementContext = getChild(context, StatementContext.class);
          if (statementContext != null) {
            Statement statement = adapt(statementContext);
            statement.setLabel(getAdapter(IdentifierAdapter.class).adapt(firstTerminal));
            setNodeAttributes(statement, firstTerminal, statementContext);
            return statement;
          }
        }
        break;
      }
      default:
      }
    }

    BlockContext blockContext = getChild(context, BlockContext.class);
    if (blockContext != null) {
      return getAdapter(BlockAdapter.class).adapt(blockContext);
    }

    StatementExpressionContext statementExpressionContext =
        getChild(context, StatementExpressionContext.class);
    if (statementExpressionContext != null) {
      ExpressionStatement expressionStatement =
          createData(statementExpressionContext, ExpressionStatement.class);

      ExpressionContext expressionContext =
          getChild(statementExpressionContext, ExpressionContext.class);
      if (expressionContext != null) {
        expressionStatement.setExpression(
            getAdapter(ExpressionAdapter.class).adapt(expressionContext));
      }

      return expressionStatement;
    }

    return null;
  }

  protected BreakStatement processBreakStatement(StatementContext context) {
    BreakStatement breakStatement = createData(context, BreakStatement.class);

    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      breakStatement.setIdentifier(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    return breakStatement;
  }

  protected ContinueStatement processContinueStatement(StatementContext context) {
    ContinueStatement continueStatement = createData(context, ContinueStatement.class);

    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      continueStatement.setIdentifier(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    return continueStatement;
  }

  protected List<SwitchLabel> getSwitchLabels(ParserRuleContext context) {
    return transform(context, SwitchLabelContext.class,
        new Function<SwitchLabelContext, SwitchLabel>() {
          @Override
          public SwitchLabel apply(SwitchLabelContext context) {
            ConstantExpressionContext constantExpressionContext =
                getChild(context, ConstantExpressionContext.class);
            if (constantExpressionContext != null) {
              ExpressionContext expressionContext =
                  getChild(constantExpressionContext, ExpressionContext.class);
              if (expressionContext != null) {
                return getAdapter(ExpressionAdapter.class).adapt(expressionContext);
              }
            }

            EnumConstantNameContext enumConstantNameContext =
                getChild(constantExpressionContext, EnumConstantNameContext.class);
            if (enumConstantNameContext != null) {
              TerminalNode identifierNode =
                  getTerminalNode(enumConstantNameContext, JavaParser.Identifier);
              if (identifierNode != null) {
                return getAdapter(IdentifierAdapter.class).adapt(identifierNode);
              }
            }

            TerminalNode defaultNode = getTerminalNode(context, JavaParser.DEFAULT);
            if (defaultNode != null) {
              return createData(context, DefaultSwitchLabel.class);
            }

            return null;
          }
        });
  }

  protected AssertStatement processAssertStatement(StatementContext context) {
    AssertStatement assertStatement = createData(context, AssertStatement.class);

    List<ExpressionContext> expressionContexts = getChildren(context, ExpressionContext.class);
    if (expressionContexts.size() > 0) {
      assertStatement.setExpression(
          getAdapter(ExpressionAdapter.class).adapt(expressionContexts.get(0)));
    }

    if (expressionContexts.size() > 1) {
      assertStatement.setErrorMessage(
          getAdapter(ExpressionAdapter.class).adapt(expressionContexts.get(1)));
    }

    return assertStatement;
  }

  protected DoWhileStatement processDoWhileStatement(StatementContext context) {
    DoWhileStatement doWhileStatement = createData(context, DoWhileStatement.class);

    StatementContext statementContext = getChild(context, StatementContext.class);
    if (statementContext != null) {
      doWhileStatement.setStatement(getAdapter(StatementAdapter.class).adapt(statementContext));
    }

    ParExpressionContext parExpressionContext = getChild(context, ParExpressionContext.class);
    if (parExpressionContext != null) {
      ExpressionContext expressionContext = getChild(parExpressionContext, ExpressionContext.class);
      if (expressionContext != null) {
        doWhileStatement.setCondition(getAdapter(ExpressionAdapter.class).adapt(expressionContext));
      }
    }

    return doWhileStatement;
  }

  protected ForStatement processForStatement(StatementContext context) {
    ForStatement forStatement = createData(context, ForStatement.class);

    ForControlContext forControlContext = getChild(context, ForControlContext.class);
    if (forControlContext != null) {
      EnhancedForControlContext enhancedForControlContext =
          getChild(forControlContext, EnhancedForControlContext.class);
      if (enhancedForControlContext == null) {
        LoopControl loopControl = createData(forControlContext, LoopControl.class);

        ForInitContext forInitContext = getChild(forControlContext, ForInitContext.class);
        if (forInitContext != null) {
          LocalVariableDeclarationContext localVariableDeclarationContext =
              getChild(forInitContext, LocalVariableDeclarationContext.class);
          if (localVariableDeclarationContext != null) {
            loopControl.setVariableDeclaration(
                getAdapter(LocalVariableDeclarationAdapter.class).adapt(
                    localVariableDeclarationContext));
          }

          ExpressionListContext expressionListContext =
              getChild(forControlContext, ExpressionListContext.class);
          if (expressionListContext != null) {
            loopControl.setInitExpressions(
                getAdapter(ExpressionListAdapter.class).adapt(expressionListContext));
          }
        }

        ExpressionContext expressionContext = getChild(forControlContext, ExpressionContext.class);
        if (expressionContext != null) {
          loopControl.setCondition(getAdapter(ExpressionAdapter.class).adapt(expressionContext));
        }

        ForUpdateContext forUpdateContext = getChild(forControlContext, ForUpdateContext.class);
        if (forUpdateContext != null) {
          ExpressionListContext expressionListContext =
              getChild(forUpdateContext, ExpressionListContext.class);
          if (expressionListContext != null) {
            loopControl.setUpdateExpressions(
                getAdapter(ExpressionListAdapter.class).adapt(expressionListContext));
          }
        }

        forStatement.setControl(loopControl);

      } else {
        ForEachControl forEachControl =
            createData(enhancedForControlContext, ForEachControl.class);
        setModifiers(enhancedForControlContext, forEachControl);

        TypeContext typeContext = getChild(enhancedForControlContext, TypeContext.class);
        if (typeContext != null) {
          forEachControl.setType(getAdapter(TypeAdapter.class).adapt(typeContext));
        }

        TerminalNode identifierNode =
            getTerminalNode(enhancedForControlContext, JavaParser.Identifier);
        if (identifierNode != null) {
          forEachControl.setVariable(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
        }

        ExpressionContext expressionContext =
            getChild(enhancedForControlContext, ExpressionContext.class);
        if (expressionContext != null) {
          forEachControl.setIterable(
              getAdapter(ExpressionAdapter.class).adapt(expressionContext));
        }

        forStatement.setControl(forEachControl);
      }
    }

    StatementContext statementContext = getChild(context, StatementContext.class);
    if (statementContext != null) {
      forStatement.setStatement(getAdapter(StatementAdapter.class).adapt(statementContext));
    }

    return forStatement;
  }

  protected IfStatement processIfStatement(StatementContext context) {
    IfStatement ifStatement = createData(context, IfStatement.class);

    ParExpressionContext parExpressionContext = getChild(context, ParExpressionContext.class);
    ifStatement.setCondition(processParExpression(parExpressionContext));

    List<StatementContext> statementContexts = getChildren(context, StatementContext.class);
    if (statementContexts.size() > 0) {
      ifStatement.setThenStatement(
          getAdapter(StatementAdapter.class).adapt(statementContexts.get(0)));
    }

    if (statementContexts.size() > 1) {
      ifStatement.setElseStatement(
          getAdapter(StatementAdapter.class).adapt(statementContexts.get(1)));
    }

    return ifStatement;
  }

  protected ReturnStatement processReturnStatement(StatementContext context) {
    ReturnStatement returnStatement = createData(context, ReturnStatement.class);

    ExpressionContext expressionContext = getChild(context, ExpressionContext.class);
    if (expressionContext != null) {
      returnStatement.setExpression(getAdapter(ExpressionAdapter.class).adapt(expressionContext));
    }

    return returnStatement;
  }

  protected SwitchStatement processSwitchStatement(StatementContext context) {
    SwitchStatement switchStatement = createData(context, SwitchStatement.class);

    ParExpressionContext parExpressionContext = getChild(context, ParExpressionContext.class);
    switchStatement.setExpression(processParExpression(parExpressionContext));

    List<SwitchBlock> switchBlocks = transform(context, SwitchBlockStatementGroupContext.class,
        new Function<SwitchBlockStatementGroupContext, SwitchBlock>() {
          @Override
          public SwitchBlock apply(SwitchBlockStatementGroupContext context) {
            SwitchBlock switchBlock = createData(context, SwitchBlock.class);
            switchBlock.setLabels(getSwitchLabels(context));
            switchBlock.setStatements(transform(context, BlockStatementContext.class,
                new Function<BlockStatementContext, BlockStatement>() {
                  @Override
                  public BlockStatement apply(BlockStatementContext context) {
                    return getAdapter(BlockStatementAdapter.class).adapt(context);
                  }
                }));
            return switchBlock;
          }
        });

    List<SwitchLabel> extraSwitchLabels = getSwitchLabels(context);
    if (!extraSwitchLabels.isEmpty()) {
      List<SwitchLabelContext> switchLabelContexts = getChildren(context, SwitchLabelContext.class);
      SwitchBlock extraSwitchBlock =
          createData(switchLabelContexts.get(0),
              switchLabelContexts.get(switchLabelContexts.size() - 1), SwitchBlock.class);
      extraSwitchBlock.setLabels(extraSwitchLabels);
      switchBlocks.add(extraSwitchBlock);
    }

    switchStatement.setSwitchBlocks(switchBlocks);

    return switchStatement;
  }

  protected SynchronizedStatement processSynchronizedStatement(StatementContext context) {
    SynchronizedStatement synchronizedStatement = createData(context, SynchronizedStatement.class);

    ParExpressionContext parExpressionContext = getChild(context, ParExpressionContext.class);
    synchronizedStatement.setExpression(processParExpression(parExpressionContext));

    BlockContext blockContext = getChild(context, BlockContext.class);
    if (blockContext != null) {
      synchronizedStatement.setBody(getAdapter(BlockAdapter.class).adapt(blockContext));
    }

    return synchronizedStatement;
  }

  protected ThrowStatement processThrowStatement(StatementContext context) {
    ThrowStatement throwStatement = createData(context, ThrowStatement.class);

    ExpressionContext expressionContext = getChild(context, ExpressionContext.class);
    if (expressionContext != null) {
      throwStatement.setExpression(getAdapter(ExpressionAdapter.class).adapt(expressionContext));
    }

    return throwStatement;
  }

  protected TryStatement processTryStatement(StatementContext context) {
    TryStatement tryStatement = createData(context, TryStatement.class);

    ResourceSpecificationContext resourceSpecificationContext =
        getChild(context, ResourceSpecificationContext.class);
    if (resourceSpecificationContext != null) {
      ResourcesContext resourcesContext =
          getChild(resourceSpecificationContext, ResourcesContext.class);
      if (resourcesContext != null) {
        tryStatement.setResources(transform(resourcesContext, ResourceContext.class,
            new Function<ResourceContext, Resource>() {
              @Override
              public Resource apply(ResourceContext context) {
                Resource resource = createData(context, Resource.class);

                setModifiers(context, resource);

                ClassOrInterfaceTypeContext classOrInterfaceTypeContext =
                    getChild(context, ClassOrInterfaceTypeContext.class);
                Type type = null;
                if (classOrInterfaceTypeContext != null) {
                  type = getAdapter(ClassOrInterfaceTypeAdapter.class).adapt(
                      classOrInterfaceTypeContext);
                }

                VariableDeclaratorIdContext variableDeclaratorIdContext =
                    getChild(context, VariableDeclaratorIdContext.class);
                if (variableDeclaratorIdContext != null) {
                  TerminalNode identifierNode =
                      getTerminalNode(variableDeclaratorIdContext, JavaParser.Identifier);
                  if (identifierNode != null) {
                    resource.setName(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
                  }
                }

                for (ParseTree node : variableDeclaratorIdContext.children) {
                  if (node instanceof TerminalNode
                      && ((TerminalNode) node).getSymbol().getType() == JavaParser.RBRACK) {
                    ArrayType arrayType =
                        createData(classOrInterfaceTypeContext, node, ArrayType.class);
                    arrayType.setElementType(type);
                    type = arrayType;
                  }
                }
                resource.setType(type);

                ExpressionContext expressionContext = getChild(context, ExpressionContext.class);
                if (expressionContext != null) {
                  resource.setInitializer(
                      getAdapter(ExpressionAdapter.class).adapt(expressionContext));
                }

                return resource;
              }
            }));
      }
    }

    BlockContext blockContext = getChild(context, BlockContext.class);
    if (blockContext != null) {
      tryStatement.setBody(getAdapter(BlockAdapter.class).adapt(blockContext));
    }

    tryStatement.setCatchClauses(transform(context, CatchClauseContext.class,
        new Function<CatchClauseContext, CatchClause>() {
          @Override
          public CatchClause apply(CatchClauseContext context) {
            CatchClause catchClause = createData(context, CatchClause.class);

            setModifiers(context, catchClause);

            CatchTypeContext catchTypeContext = getChild(context, CatchTypeContext.class);
            if (catchTypeContext != null) {
              catchClause.setExceptions(transform(catchTypeContext, QualifiedNameContext.class,
                  new Function<QualifiedNameContext, QualifiedName>() {
                    @Override
                    public QualifiedName apply(QualifiedNameContext context) {
                      return getAdapter(QualifiedNameAdapter.class).adapt(context);
                    }
                  }));
            }

            TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
            if (identifierNode != null) {
              catchClause.setVariable(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
            }

            BlockContext blockContext = getChild(context, BlockContext.class);
            if (blockContext != null) {
              catchClause.setBody(getAdapter(BlockAdapter.class).adapt(blockContext));
            }

            return catchClause;
          }
        }));

    FinallyBlockContext finallyBlockContext = getChild(context, FinallyBlockContext.class);
    if (finallyBlockContext != null) {
      BlockContext finallyBodyContext = getChild(finallyBlockContext, BlockContext.class);
      if (finallyBodyContext != null) {
        tryStatement.setFinallyBlock(getAdapter(BlockAdapter.class).adapt(finallyBodyContext));
      }
    }

    return tryStatement;
  }

  protected WhileStatement processWhileStatement(StatementContext context) {
    WhileStatement whileStatement = createData(context, WhileStatement.class);

    ParExpressionContext parExpressionContext = getChild(context, ParExpressionContext.class);
    whileStatement.setCondition(processParExpression(parExpressionContext));

    StatementContext statementContext = getChild(context, StatementContext.class);
    if (statementContext != null) {
      whileStatement.setStatement(getAdapter(StatementAdapter.class).adapt(statementContext));
    }

    return whileStatement;
  }

  private Expression processParExpression(ParExpressionContext parExpressionContext) {
    if (parExpressionContext != null) {
      ExpressionContext expressionContext =
          getChild(parExpressionContext, ExpressionContext.class);
      if (expressionContext != null) {
        return getAdapter(ExpressionAdapter.class).adapt(expressionContext);
      }
    }
    return null;
  }
}
