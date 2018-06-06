package org.okky.comment.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WriteReplyCommentCommand {
    String replyId;
    String commenterId;
    String commenterName;
    String body;
}
