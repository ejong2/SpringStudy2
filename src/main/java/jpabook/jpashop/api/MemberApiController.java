package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.*;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;
    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }
    @GetMapping("/api/v2/members")
    public ResultDto memberV2(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new ResultDto(collect.size(), collect);
    }
    @PostMapping("/api/v1/members")
    public CreateMemberResponseDto saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponseDto(id);
    }
    @PostMapping("/api/v2/members")
    public CreateMemberResponseDto saveMemberV2(@RequestBody @Valid CreateMemberRequestDto request){
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponseDto(id);
    }
    @PutMapping("api/v2/members/{id}")
    public UpdateMemberResponseDto updateMemberResponseV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequestDto request) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponseDto(findMember.getId(), findMember.getName());
    }
}
