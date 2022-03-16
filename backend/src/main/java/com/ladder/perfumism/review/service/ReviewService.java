package com.ladder.perfumism.review.service;

import com.ladder.perfumism.global.exception.BusinessException;
import com.ladder.perfumism.global.exception.ErrorCode;
import com.ladder.perfumism.member.domain.Member;
import com.ladder.perfumism.member.domain.MemberRepository;
import com.ladder.perfumism.review.controller.dto.request.ReviewWriteRequest;
import com.ladder.perfumism.review.controller.dto.response.ReviewPageResponse;
import com.ladder.perfumism.perfume.domain.Perfume;
import com.ladder.perfumism.perfume.domain.PerfumeRepository;
import com.ladder.perfumism.review.domain.Review;
import com.ladder.perfumism.review.domain.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PerfumeRepository perfumeRepository;
    private final MemberRepository memberRepository;

    public ReviewService(ReviewRepository reviewRepository, PerfumeRepository perfumeRepository,
        MemberRepository memberRepository) {
        this.reviewRepository = reviewRepository;
        this.perfumeRepository = perfumeRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long writeReview(String email, Long perfumeId, ReviewWriteRequest request) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND_BY_EMAIL));
        Perfume perfume = perfumeRepository.findById(perfumeId)
            .orElseThrow(() -> new BusinessException(ErrorCode.PERFUME_NOT_FOUND_BY_ID));

        alreadyWritten(member, perfume);

        Review review = reviewRepository.save(Review.createReview(perfume, member, request));

        averageGrade(perfume);
        perfume.increaseTotalSurvey();

        return review.getId();
    }

    private void averageGrade(Perfume perfume) {
        //TODO: 향수 평균 평점 구하는 로직
        // 데이터가 모두 삽입되면 구현할 예정
    }

    private void alreadyWritten(Member member, Perfume perfume) {
        if (reviewRepository.existsByMemberIdAndPerfumeId(member, perfume)) {
            throw new BusinessException(ErrorCode.REVIEW_ALREADY_WRITTEN);
        }
    }

    @Transactional(readOnly = true)
    public ReviewPageResponse getReviewPage(Long perfumeId, Pageable pageable) {
        Perfume perfume = perfumeRepository.findById(perfumeId)
            .orElseThrow(() -> new BusinessException(ErrorCode.PERFUME_NOT_FOUND_BY_ID));

        Page<Review> reviewList = reviewRepository.findByPerfumeId(perfume, pageable);

        return ReviewPageResponse.from(reviewList);
    }

    @Transactional
    public void changeReview(String email, Long reviewId, ReviewWriteRequest request) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND_BY_ID));

        isYourReview(email, review);

        if (!request.getGrade().equals(review.getGrade())) {
            review.changeGrade(request.getGrade());

            averageGrade(review.getPerfumeId());

        }

        review.changeContent(request.getContent());

        reviewRepository.save(review);
    }

    private void isYourReview(String email, Review review) {
        if (!review.getMemberId().getEmail().equals(email)) {
            throw new BusinessException(ErrorCode.REVIEW_NOT_YOUR_REVIEW);
        }
    }

    @Transactional
    public void removeReview(String email, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND_BY_ID));

        isYourReview(email, review);

        review.saveDeletedTime();
        averageGrade(review.getPerfumeId());
        review.getPerfumeId().decreaseTotalSurvey();
    }
}
