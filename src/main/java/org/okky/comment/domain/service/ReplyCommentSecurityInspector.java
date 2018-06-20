package org.okky.comment.domain.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.comment.domain.model.ReplyComment;
import org.okky.comment.resource.ContextHolder;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ReplyCommentSecurityInspector {
    ReplyCommentConstraint constraint;
    ContextHolder holder;

    public boolean isMe(String commentId) {
        ReplyComment comment = constraint.checkExistsAndGet(commentId);
        String commenterId = comment.getCommenterId();
        String requesterId = holder.getId();
        return requesterId.equals(commenterId);
    }
}
