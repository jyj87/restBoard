package com.study.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.study.domain.post.PostMapper;
import com.study.domain.post.PostRequest;
import com.study.domain.post.PostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostMapperTest {

    @Autowired
    private PostMapper postMapper;

    @Test
    @Transactional
    @DisplayName("게시글 저장 테스트")
    void save() {
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("3번글 제목");
        postRequest.setContent("3번글 내용");
        postRequest.setWriter("3번글 작성자");
        postRequest.setNoticeYn(false);
        postMapper.save(postRequest);

        List<PostResponse> postList = postMapper.findAll();
        assertThat(postList.get(0).getTitle()).isEqualTo("3번글 제목");
    }

    @Test
    @Transactional
    @DisplayName("게시글 검색 테스트")
    void findById() {
        PostResponse post = postMapper.findById(1L);
        try {
            String postJson = new ObjectMapper().
                    registerModule(new JavaTimeModule()).writeValueAsString(post);
            System.out.println(postJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        assertThat(post.getWriter()).isEqualTo("1번글 작성자");
    }

    @Test
    @Transactional
    @DisplayName("게시글 갱신 테스트")
    void update(){
        PostRequest postRequest = new PostRequest();
        postRequest.setId(1L);
        postRequest.setTitle("1번 게시글 제목 수정합니다.");
        postRequest.setContent("1번 게시글 내용 수정합니다.");
        postRequest.setWriter("도뎡이");
        postRequest.setNoticeYn(true);
        postMapper.update(postRequest);

        PostResponse post = postMapper.findById(1L);

        assertThat(post.getTitle()).isEqualTo("1번 게시글 제목 수정합니다.");
    }

    @Test
    @Transactional
    @DisplayName("게시글 삭제 테스트")
    void deleteById(){

        int beforeSize = 0;
        int afterSize = 0;
        List<PostResponse> beforeList = postMapper.findAll();
        beforeSize= beforeList.size();
        postMapper.deleteById(1L);
        List<PostResponse> afterList = postMapper.findAll();
        afterSize= afterList.size();

        assertThat(beforeSize).isEqualTo(afterSize+1);
    }
}
