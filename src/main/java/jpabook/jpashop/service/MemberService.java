package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
    회원 가입
    */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    @Transactional(readOnly = true)
    public Page<Member> pageList(Pageable pageable){
        return memberRepository.findAll(pageable);
    }
    /**
    회원 전체 조회
    */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Member findOne(Long memberId){
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}
