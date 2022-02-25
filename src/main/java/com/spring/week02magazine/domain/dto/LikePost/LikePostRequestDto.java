package com.spring.week02magazine.domain.dto.LikePost;

import com.spring.week02magazine.domain.entity.Account;
import com.spring.week02magazine.domain.entity.Board;
import com.spring.week02magazine.domain.entity.LikePost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LikePostRequestDto {

    @Builder
    public LikePostRequestDto() {
    }

    public static LikePost toEntity() {
        return LikePost.builder()
                .build();
    }
}
