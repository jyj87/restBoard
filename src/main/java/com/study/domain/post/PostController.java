package com.study.domain.post;

import com.study.common.dto.MessageDto;
import com.study.common.dto.SearchDto;
import com.study.common.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @GetMapping("/post/write.do")
    public String openPostWrite(
            @RequestParam(value = "id", required = false)
            Long id, Model model) {
        if (id != null) {
            PostResponse post = postService.findPostById(id);
            model.addAttribute("post", post);
        }
        return "post/write";
    }

    // 게시글 등록
    @PostMapping("/post/save.do")
    public String savePost(PostRequest postRequest, Model model) {
        postService.savePost(postRequest);
        MessageDto message = new MessageDto(
                "게시글 생성 완료",
                "/post/list.do",
                RequestMethod.GET,
                null);
        return showMessageAndRedirect(message, model);

    }

    // 게시글 리스트
    @GetMapping("/post/list.do")
    public String openPostList(@ModelAttribute("params") SearchDto params, Model model) {
        PagingResponse<PostResponse> response = (PagingResponse<PostResponse>) postService.findAllPost(params);
        model.addAttribute("response", response);
        return "post/list";
    }

    // 게시판 상세 페이지
    @GetMapping("/post/view.do")
    public String openPostView(@RequestParam(value = "id") Long id,
                               Model model) {
        PostResponse post = postService.findPostById(id);
        model.addAttribute("post", post);
        return "post/view";
    }

    // 기존 글 수정
    @PostMapping("/post/update.do")
    public String updatePost(PostRequest postRequest, SearchDto queryParams, Model model) {
        postService.updatePost(postRequest);
        MessageDto message = new MessageDto(
                "게시글 수정 완료",
                "/post/list.do",
                RequestMethod.GET,
                queryParamsToMap(queryParams));
        return showMessageAndRedirect(message, model);
    }

    // 게시글 삭제
    @PostMapping("/post/delete.do")
    public String deletePost(@RequestParam(value = "id") Long id, SearchDto queryParams, Model model) {
        postService.deletePost(id);
        MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.",
                "/post/list.do",
                RequestMethod.GET,
                queryParamsToMap(queryParams));
        return showMessageAndRedirect(message, model);
    }

    // 쿼리 스트링 파라미터를 Map에 담아 반환
    private Map<String, Object> queryParamsToMap(final SearchDto queryParams) {
        Map<String, Object> data = new HashMap<>();
        data.put("page", queryParams.getPage());
        data.put("recordSize", queryParams.getRecordSize());
        data.put("pageSize", queryParams.getPageSize());
        data.put("keyword", queryParams.getKeyword());
        data.put("searchType", queryParams.getSearchType());
        return data;
    }

    // 사용자에게 메세지 전달 이후, 페이지를 리다이렉트 한다.
    private String showMessageAndRedirect(MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
    }
}
