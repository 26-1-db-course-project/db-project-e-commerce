package db.project.ecommerce.statistic.service;

import db.project.ecommerce.statistic.dto.request.SalesSearchRequest;
import db.project.ecommerce.statistic.dto.response.SalesResponse;
import db.project.ecommerce.statistic.enums.PeriodType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final OrderRepository orderRepository;
    public SalesResponse getSalesStats(@Valid SalesSearchRequest request) {
        PeriodType period = request.getPeriod();

    }
}
