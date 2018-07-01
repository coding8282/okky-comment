package org.okky.comment.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.comment.application.command.ModifyReplyCommentCommand;
import org.okky.comment.application.command.WriteReplyCommentCommand;
import org.okky.comment.domain.model.ReplyComment;
import org.okky.comment.domain.repository.ReplyCommentRepository;
import org.okky.comment.domain.service.ReplyCommentConstraint;
import org.okky.comment.domain.service.ReplyCommentProxy;
import org.okky.share.event.ReplyCommented;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ReplyCommentService {
    ModelMapper mapper;
    ReplyCommentRepository repository;
    ReplyCommentConstraint constraint;
    ReplyCommentProxy proxy;

    public ReplyComment write(WriteReplyCommentCommand cmd) {
        constraint.checkReplyExists(cmd.getReplyId());
        ReplyComment comment = mapper.toModel(cmd);
        repository.save(comment);
        ReplyCommented event = mapper.toEvent(comment);
        proxy.sendEvent(event);
        return comment;
    }

    @PreAuthorize("@replyCommentSecurityInspector.isMe(#cmd.commentId)")
    public void modify(ModifyReplyCommentCommand cmd) {
        ReplyComment comment = constraint.checkExistsAndGet(cmd.getCommentId());
        comment.modify(cmd.getBody());
    }

    @PreAuthorize("@replyCommentSecurityInspector.isMe(#commentId)")
    public void remove(String commentId) {
        ReplyComment comment = constraint.checkExistsAndGet(commentId);
        repository.delete(comment);
    }

    public void removeForce(String replyId) {
        remove(replyId);
    }
}
