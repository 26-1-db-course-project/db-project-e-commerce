package db.project.ecommerce.statistic.dto.request;

import db.project.ecommerce.statistic.enums.PeriodType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SalesSearchRequest {
    @NotNull
    private PeriodType period = PeriodType.MONTHLY;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
