package com.study.domain.post;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private int viewCnt;
    private Boolean noticeYn; //공지글 여부
    private Boolean deleteYn; //삭제 여부
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

}
