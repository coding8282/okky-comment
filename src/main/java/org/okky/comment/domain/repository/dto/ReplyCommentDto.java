package org.okky.comment.domain.repository.dto;

import lombok.Builder;
import lombok.Getter;
import org.okky.comment.domain.model.ReplyComment;

@Builder
@Getter
public class ReplyCommentDto {
    String id;
    String replyId;
    String body;
    String commenterId;
    String commenterName;
    Long commentedOn;
    Long updatedOn;

    public static ReplyCommentDto toDto(ReplyComment c) {
        return ReplyCommentDto
                .builder()
                .id(c.getId())
                .replyId(c.getReplyId())
                .body(c.getBody())
                .commenterId(c.getCommenterId())
                .commenterName(c.getCommenterName())
                .commentedOn(c.getCommentedOn())
                .updatedOn(c.getUpdatedOn())
                .build();
    }
}
