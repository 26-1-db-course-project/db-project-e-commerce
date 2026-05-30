package db.project.ecommerce.member.service;

import db.project.ecommerce.global.exception.CustomException;
import db.project.ecommerce.global.exception.ErrorCode;
import db.project.ecommerce.member.domain.Member;
import db.project.ecommerce.member.domain.MemberRole;
import db.project.ecommerce.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    public Member getActiveMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Member validateRole(Long memberId, MemberRole requiredRole) {
        Member member = getActiveMember(memberId);
        if (member.getRole() != requiredRole) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
        return member;
    }
}
