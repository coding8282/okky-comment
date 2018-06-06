package org.okky.comment.domain.service;

import lombok.AllArgsConstructor;
import org.okky.comment.domain.model.ReplyComment;
import org.okky.comment.resource.ContextHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReplyCommentSecurityInspector {
    private ReplyCommentConstraint constraint;

    public boolean isMe(String commentId) {
        ReplyComment comment = constraint.checkExistsAndGet(commentId);
        String commenterId = comment.getCommenterId();
        String requesterId = ContextHelper.getId();
        return requesterId.equals(commenterId);
    }
}
