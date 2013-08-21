package com.bacoder.parser.javaproperties.adapter;

import com.bacoder.parser.core.Adapter;
import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.javaproperties.JavaPropertiesParser.CommentContext;
import com.bacoder.parser.javaproperties.api.Comment;

public class CommentAdapter extends Adapter<CommentContext, Comment> {

  public CommentAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public Comment adapt(CommentContext context) {
    Comment comment = createData(context);
    comment.setText(context.getText());
    return comment;
  }
}
