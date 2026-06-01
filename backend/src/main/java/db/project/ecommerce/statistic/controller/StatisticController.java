package db.project.ecommerce.statistic.controller;

import db.project.ecommerce.statistic.dto.request.SalesSearchRequest;
import db.project.ecommerce.statistic.dto.response.SalesResponse;
import db.project.ecommerce.statistic.service.StatisticService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/stats")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping("/sales")
    public ResponseEntity<SalesResponse> getSalesStats(@Valid @RequestBody SalesSearchRequest request) {
        SalesResponse response = statisticService.getSalesStats(request);

        return ResponseEntity.ok(response);
    }
}
