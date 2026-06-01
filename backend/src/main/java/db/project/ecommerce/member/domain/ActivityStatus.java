package db.project.ecommerce.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "activity_status")
public class ActivityStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_status_id")
    private Long id;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "report_count")
    private int reportCount;
}
