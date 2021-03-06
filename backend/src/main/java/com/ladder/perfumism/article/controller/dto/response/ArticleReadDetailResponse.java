package com.ladder.perfumism.article.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ladder.perfumism.article.domain.Article;
import com.ladder.perfumism.article.domain.ArticleImage;
import com.ladder.perfumism.article.domain.ArticleSubject;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ArticleReadDetailResponse  {

    @JsonProperty("article_id")
    @ApiModelProperty(position = 0, notes = "게시글 ID",example = "1")
    private Long articleId;

    @JsonProperty("member_id")
    @ApiModelProperty(position = 1, notes = "멤버 ID", example = "1")
    private Long memberId;

    @JsonProperty("member_name")
    @ApiModelProperty(position = 2, notes = "멤버 이름", example = "우사앙주운")
    private String memberName;

    @JsonProperty("member_image")
    @ApiModelProperty(position = 3, notes = "멤버 사진", example = "잘생김.jpg")
    private String memberImage;

    @JsonProperty("subject")
    @ApiModelProperty(position = 4, notes = "말머리", example = "TALK")
    private ArticleSubject subject;

    @JsonProperty("title")
    @ApiModelProperty(position = 5, notes = "제목", example = "제목입니다")
    private String title;

    @JsonProperty("content")
    @ApiModelProperty(position = 6, notes = "내용", example = "내용이네요")
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonProperty("createAt")
    @ApiModelProperty(position = 7, notes = "생성 시간", example = "2022-3-13 14:59:51")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonProperty("updateAt")
    @ApiModelProperty(position = 8, notes = "수정 시간", example = "2023-4-14 14:59:51")
    private LocalDateTime updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonProperty("deleteAt")
    @ApiModelProperty(position = 9, notes = "삭제 시간", example = "2023-4-15 14:59:51")
    private LocalDateTime deletedAt;

    @JsonProperty("vote_exist")
    @ApiModelProperty(position = 10, notes = "투표 존재 유무 true(있음) or false(없음)", example = "false")
    private Boolean voteExist;

    @JsonProperty("image_url_list")
    @ApiModelProperty(position = 11, notes = "이미지 URL 리스트", dataType = "List")
    private List<ArticleImageResponse> imageUrlList;

    public ArticleReadDetailResponse(){

    }

    public ArticleReadDetailResponse(
        Long articleId, Long memberId, String memberName, String memberImage,
        ArticleSubject subject, String title, String content,
        LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, Boolean voteExist,
        List<ArticleImageResponse> imageUrlList){
        this.articleId = articleId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberImage = memberImage;
        this.subject = subject;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.voteExist = voteExist;
        this.imageUrlList = imageUrlList;
    }

    public static ArticleReadDetailResponse from(Article article, List<ArticleImage> articleImages){
        return new ArticleReadDetailResponse(
            article.getId(),
            article.getMember().getId(),
            article.getMember().getUsername(),
            article.getMember().getImage(),
            article.getSubject(),
            article.getTitle(),
            article.getContent(),
            article.getCreatedAt(),
            article.getUpdatedAt(),
            article.getDeletedAt(),
            article.getVoteExist(),
            articleImages.stream()
                .map(ArticleImageResponse::from)
                .collect(Collectors.toList())

        );
    }

}
