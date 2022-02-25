package com.spring.week02magazine.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikePostSuccess {
    private String result;
    private String msg;
    private int likeCount;
}
