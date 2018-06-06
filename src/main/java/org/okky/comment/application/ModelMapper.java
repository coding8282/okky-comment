package org.okky.comment.application;

import org.okky.comment.application.command.WriteReplyCommentCommand;
import org.okky.comment.domain.model.ReplyComment;

public interface ModelMapper {
    static ReplyComment toReplyComment(WriteReplyCommentCommand cmd) {
        return new ReplyComment(
                cmd.getReplyId(),
                cmd.getBody(),
                cmd.getCommenterId(),
                cmd.getCommenterName());
    }
}
