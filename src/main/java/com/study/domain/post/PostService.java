package com.study.domain.post;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    /**
     * 게시글 저장
     * @param postRequest - 게시글 DB등록 정보
     * @return Generated PK
     */
    @Transactional
    public Long savePost(PostRequest postRequest){
        postMapper.save(postRequest);
        return postRequest.getId();
    }

    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세 정보
     */
    @Transactional(readOnly = true)
    public PostResponse findPostById(Long id){
        return postMapper.findById(id);
    }

    /**
     * 게시글 수정
     * @param postRequest - 게시글 DB수정 정보
     * @return PK
     */
    @Transactional
    public Long updatePost(PostRequest postRequest){
        postMapper.update(postRequest);
        return postRequest.getId();
    }

    /**
     * 게시글 삭제
     * @param id - PK
     * @return PK
     */
    @Transactional
    public Long deletePost(Long id){
        postMapper.deleteById(id);
        return id;
    }

    /**
     * 게시글 리스트 조회
     * @return 게시글 리스트
     */
    @Transactional(readOnly = true)
    public List<PostResponse> findAllPost(){
        return postMapper.findAll();
    }



}
