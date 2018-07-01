package org.okky.comment.application;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.okky.comment.TestMother;
import org.okky.comment.application.command.WriteReplyCommentCommand;
import org.okky.comment.domain.model.ReplyComment;
import org.okky.share.event.ReplyCommented;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@FieldDefaults(level = PRIVATE)
public class ModelMapperTest extends TestMother {
    ModelMapper mapper = new ModelMapper();

    @Test
    public void toModel() {
        WriteReplyCommentCommand cmd = new WriteReplyCommentCommand("r", "c", "n", "bb");
        ReplyComment comment = mapper.toModel(cmd);

        assertThat("replierId가 다르다.", comment.getReplyId(), is("r"));
        assertThat("body가 다르다.", comment.getBody(), is("bb"));
        assertThat("commenterId가 다르다.", comment.getCommenterId(), is("c"));
        assertThat("commenterName가 다르다.", comment.getCommenterName(), is("n"));
        assertThat("commentedOn가 다르다.", comment.getCommentedOn(), is(notNullValue()));
        assertThat("updatedOn가 다르다.", comment.getUpdatedOn(), is(nullValue()));
    }

    @Test
    public void toEvent() {
        ReplyComment comment = ReplyComment.sample();
        ReplyCommented event = mapper.toEvent(comment);

        assertThat("id가 다르다.", event.getId(), is(comment.getId()));
        assertThat("replierId가 다르다.", event.getReplyId(), is(comment.getReplyId()));
        assertThat("body가 다르다.", event.getBody(), is(comment.getBody()));
        assertThat("commenterId가 다르다.", event.getCommenterId(), is(comment.getCommenterId()));
        assertThat("commenterName이 다르다.", event.getCommenterName(), is(comment.getCommenterName()));
        assertThat("commentedOn가 다르다.", event.getCommentedOn(), is(comment.getCommentedOn()));
        assertThat("updatedOn가 다르다.", event.getUpdatedOn(), is(comment.getUpdatedOn()));
    }
}