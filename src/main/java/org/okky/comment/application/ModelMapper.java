package org.okky.comment.application;

import org.okky.comment.application.command.WriteReplyCommentCommand;
import org.okky.comment.domain.model.ReplyComment;
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
}
