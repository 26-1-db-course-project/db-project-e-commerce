package db.project.ecommerce.member.domain;

import db.project.ecommerce.global.domain.BaseEntity;
import db.project.ecommerce.member.enums.MemberRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "login_id", unique = true)
    private String loginId;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_status_id")
    private ActivityStatus activityStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    private MemberGrade grade;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @Builder
    public Member(String loginId, String email, String password,
                  String phoneNumber, MemberRole role, ActivityStatus activityStatus, MemberGrade grade) {
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.activityStatus = activityStatus;
        this.grade = grade;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public void updateActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
    }
}
