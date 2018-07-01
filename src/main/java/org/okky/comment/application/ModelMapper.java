package org.okky.comment.application;

import org.okky.comment.application.command.WriteReplyCommentCommand;
import org.okky.comment.domain.model.ReplyComment;
import org.okky.share.event.ReplyCommented;
import org.springframework.stereotype.Service;

@Service
class ModelMapper {
    ReplyComment toModel(WriteReplyCommentCommand cmd) {
        return new ReplyComment(
                cmd.getReplyId(),
                cmd.getBody(),
                cmd.getCommenterId(),
                cmd.getCommenterName());
    }

    ReplyCommented toEvent(ReplyComment c) {
        return new ReplyCommented(
                c.getId(),
                c.getReplyId(),
                c.getBody(),
                c.getCommenterId(),
                c.getCommenterName(),
                c.getCommentedOn(),
                c.getUpdatedOn()
        );
    }
}
