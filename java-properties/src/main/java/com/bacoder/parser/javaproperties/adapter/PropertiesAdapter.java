package com.bacoder.parser.javaproperties.adapter;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

import com.bacoder.parser.core.Adapter;
import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.javaproperties.JavaPropertiesParser.CommentContext;
import com.bacoder.parser.javaproperties.JavaPropertiesParser.KeyValueContext;
import com.bacoder.parser.javaproperties.JavaPropertiesParser.LineContext;
import com.bacoder.parser.javaproperties.JavaPropertiesParser.PropertiesContext;
import com.bacoder.parser.javaproperties.api.Comment;
import com.bacoder.parser.javaproperties.api.KeyValue;
import com.bacoder.parser.javaproperties.api.Properties;
import com.google.common.base.Function;

public class PropertiesAdapter extends Adapter<PropertiesContext, Properties> {

  public PropertiesAdapter() {
    this(new PropertiesAdapters());
  }

  public PropertiesAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public Properties adapt(PropertiesContext context) {
    LineContext lineContext = getChild(context, LineContext.class);
    if (lineContext == null) {
      return null;
    }

    List<Comment> comments =
        transform(context, LineContext.class, new Function<LineContext, Comment>() {
          @Override
          public Comment apply(LineContext context) {
            for (ParseTree child : context.children) {
              if (child instanceof CommentContext) {
                return getAdapter(CommentAdapter.class).adapt((CommentContext) child);
              }
            }
            return null;
          }
        });

    List<KeyValue> keyValues =
        transform(context, LineContext.class, new Function<LineContext, KeyValue>() {
          @Override
          public KeyValue apply(LineContext context) {
            for (ParseTree child : context.children) {
              if (child instanceof KeyValueContext) {
                return getAdapter(KeyValueAdapter.class).adapt((KeyValueContext) child);
              }
            }
            return null;
          }
        });

    Properties properties = createData(context);
    properties.setComments(comments);
    properties.setKeyValues(keyValues);
    return properties;
  }
}
