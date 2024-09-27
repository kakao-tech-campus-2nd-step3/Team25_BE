package com.team25.backend.dto.request;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class ManagerCreateRequest {

    @NotBlank(message = "이름은 1자 이상 입력해야합니다.")
    private String name;

    private String profileImage;
    private String career;
    private String comment;
    private String certificateImage;
}
