package org.jeecg.modules.rider.post.dto;

import lombok.Data;
import org.jeecg.modules.rider.post.entity.Post;
import org.jeecg.modules.rider.post.entity.PostDetail;

@Data
public class PostDTO extends Post {
    private PostDetail postDetail;
}
