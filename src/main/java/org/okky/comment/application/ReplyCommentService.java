package org.okky.comment.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.comment.application.command.ModifyReplyCommentCommand;
import org.okky.comment.application.command.WriteReplyCommentCommand;
import org.okky.comment.domain.model.ReplyComment;
import org.okky.comment.domain.repository.ReplyCommentRepository;
import org.okky.comment.domain.service.ReplyCommentConstraint;
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

    public ReplyComment write(WriteReplyCommentCommand cmd) {
        constraint.checkReplyExists(cmd.getReplyId());
        ReplyComment comment = mapper.toModel(cmd);
        repository.save(comment);
        return comment;
    }

    @PreAuthorize("@replyCommentSecurityInspector.isMe(#cmd.commentId)")
    public void modify(ModifyReplyCommentCommand cmd) {
        ReplyComment reply = constraint.checkExistsAndGet(cmd.getCommentId());
        reply.modify(cmd.getBody());
    }

    @PreAuthorize("@replyCommentSecurityInspector.isMe(#commentId)")
    public void remove(String commentId) {
        ReplyComment reply = constraint.checkExistsAndGet(commentId);
        repository.delete(reply);
    }

    public void removeForce(String replyId) {
        remove(replyId);
    }
}
