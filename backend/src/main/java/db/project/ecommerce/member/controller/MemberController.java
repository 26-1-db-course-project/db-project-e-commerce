package db.project.ecommerce.member.controller;

import db.project.ecommerce.member.dto.request.AddressRequest;
import db.project.ecommerce.member.dto.request.CreateMemberRequest;
import db.project.ecommerce.member.dto.request.UpdateAddressRequest;
import db.project.ecommerce.member.dto.response.*;
import db.project.ecommerce.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<CreateMemberResponse> createMember(@Valid @RequestBody CreateMemberRequest request) {
        CreateMemberResponse response = memberService.createMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable("memberId") Long memberId) {
        MemberResponse response = memberService.getMember(memberId);
        return ResponseEntity.ok(response);
    }

    // 주소 수정
    @PatchMapping("/{memberId}/{addressId}")
    public ResponseEntity<UpdateAddressResponse> updateAddress(@PathVariable("memberId") Long memberId,
                                                               @PathVariable("addressId") Long addressId,
                                                               @RequestBody UpdateAddressRequest request) {
        UpdateAddressResponse response = memberService.updateAddress(memberId, addressId, request);
        return ResponseEntity.ok(response);
    }

    // 주소 추가 (다중주소)
    @PostMapping("/{memberId}/addresses")
    public ResponseEntity<AddressResponse> addAddress(@PathVariable("memberId") Long memberId,
                                                      @RequestBody AddressRequest request) {
        AddressResponse response = memberService.addAddress(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<DeleteMemberResponse> deleteMember(@PathVariable("memberId") Long memberId) {
        DeleteMemberResponse response = memberService.deleteMember(memberId);
        return ResponseEntity.ok(response);
    }
}
