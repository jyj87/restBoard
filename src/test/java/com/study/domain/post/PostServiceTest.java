package com.study.domain.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Test
    @Transactional
    @DisplayName("게시글 저장 테스트")
    void savePost() {
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("저장테스트 게시글 제목");
        postRequest.setContent("저장테스트 게시글 내용");
        postRequest.setWriter("저장테스트");
        postRequest.setNoticeYn(false);

        Long id = postService.savePost(postRequest);
        System.out.println("id = " + id);

        List<PostResponse> allPost = postService.findAllPost();

        PostResponse savePost= new PostResponse();

        for(PostResponse post:allPost){
            if(post.getTitle().equals("저장테스트 게시글 제목")){
                savePost=post;
            }
        }

        assertEquals(postRequest.getTitle(),savePost.getTitle());
    }

}