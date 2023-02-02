package jpabook.jpashop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMemberResponseDto {
    private Long id;
    private String name;
}
