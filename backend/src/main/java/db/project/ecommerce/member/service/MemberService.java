package db.project.ecommerce.member.service;

import db.project.ecommerce.global.exception.CustomException;
import db.project.ecommerce.global.exception.ErrorCode;
import db.project.ecommerce.member.domain.*;
import db.project.ecommerce.member.dto.request.CreateMemberRequest;
import db.project.ecommerce.member.dto.request.UpdateAddressRequest;
import db.project.ecommerce.member.dto.response.*;
import db.project.ecommerce.member.repository.ActivityStatusRepository;
import db.project.ecommerce.member.repository.AddressRepository;
import db.project.ecommerce.member.repository.MemberGradeRepository;
import db.project.ecommerce.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final MemberGradeRepository memberGradeRepository;
    private final ActivityStatusRepository activityStatusRepository;

    @Transactional
    public CreateMemberResponse createMember(CreateMemberRequest request) {
        if (memberRepository.existsByLoginId(request.getId())) {
            throw new IllegalArgumentException("멤버 생성에 실패하였습니다");
        }
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("멤버 생성에 실패하였습니다");
        }

        MemberGrade grade = memberGradeRepository.findByGradeName(request.getMemberType().name())
                .orElseThrow(() -> new IllegalArgumentException("멤버 생성에 실패하였습니다"));

        ActivityStatus activeStatus = activityStatusRepository.findByStatusName("ACTIVE")
                .orElseThrow(() -> new IllegalArgumentException("멤버 생성에 실패하였습니다"));

        Member member = request.toEntity(grade, activeStatus);
        memberRepository.save(member);

        Address address = request.getAddress().toEntity(member);
        addressRepository.save(address);
        member.addAddress(address);

        return new CreateMemberResponse(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return new MemberResponse(member);
    }

    @Transactional
    public UpdateAddressResponse updateAddress(Long memberId, Long addressId, UpdateAddressRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("주소 수정에 실패하였습니다"));

        boolean belongsToMember = member.getAddresses().stream()
                .anyMatch(a -> a.getAddressId().equals(addressId));
        if (!belongsToMember) {
            throw new IllegalArgumentException("주소 수정에 실패하였습니다");
        }

        address.update(request.getCity(), request.getDistrict(), request.getDetailAddress());
        return new UpdateAddressResponse(member, address);
    }

    @Transactional
    public DeleteMemberResponse deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        ActivityStatus deletedStatus = activityStatusRepository.findByStatusName("DELETED")
                .orElseThrow(() -> new IllegalArgumentException("회원 삭제에 실패하였습니다"));

        String loginId = member.getLoginId();
        LocalDateTime deletedDate = LocalDateTime.now();
        member.updateActivityStatus(deletedStatus);

        return new DeleteMemberResponse(loginId, deletedDate);
    }
}
