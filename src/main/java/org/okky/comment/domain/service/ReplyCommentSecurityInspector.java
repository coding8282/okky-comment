package org.okky.comment.domain.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.comment.domain.model.ReplyComment;
import org.okky.comment.resource.ContextHelper;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ReplyCommentSecurityInspector {
    ReplyCommentConstraint constraint;

    public boolean isMe(String commentId) {
        ReplyComment comment = constraint.checkExistsAndGet(commentId);
        String commenterId = comment.getCommenterId();
        String requesterId = ContextHelper.getId();
        return requesterId.equals(commenterId);
    }
}
