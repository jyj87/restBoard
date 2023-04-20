package com.study.domain.post;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PostRequest {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private Boolean noticeYn; //공지글 여부
}
