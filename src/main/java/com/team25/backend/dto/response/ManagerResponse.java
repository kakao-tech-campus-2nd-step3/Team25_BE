package com.team25.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerResponse {
    private Long managerId;
    private String name;
    private String profileImage;
    private String career;
    private String comment;
}
