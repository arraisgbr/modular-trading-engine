package com.usp.mac0499.modulartradingengine.portfolio.internal.api.controller;

import com.usp.mac0499.modulartradingengine.portfolio.internal.api.dtos.request.MoneyRequest;
import com.usp.mac0499.modulartradingengine.portfolio.internal.api.dtos.response.PortfolioResponse;
import com.usp.mac0499.modulartradingengine.portfolio.internal.api.mappers.MoneyMapper;
import com.usp.mac0499.modulartradingengine.portfolio.internal.api.mappers.PortfolioMapper;
import com.usp.mac0499.modulartradingengine.portfolio.internal.service.interfaces.IPortfolioServiceInternal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final IPortfolioServiceInternal portfolioService;

    private final PortfolioMapper portfolioMapper;
    private final MoneyMapper moneyMapper;

    @PostMapping
    public ResponseEntity<PortfolioResponse> createPortfolio() {
        var portfolio = portfolioService.createPortfolio();
        var portfolioResponse = portfolioMapper.toResponse(portfolio);
        var location = fromCurrentRequest().path("/{id}").buildAndExpand(portfolioResponse.id()).toUri();
        return ResponseEntity.created(location).body(portfolioResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioResponse> readPortfolio(@PathVariable UUID id) {
        var portfolio = portfolioService.readPortfolio(id);
        var portfolioResponse = portfolioMapper.toResponse(portfolio);
        return ResponseEntity.ok(portfolioResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable UUID id) {
        portfolioService.deletePortfolio(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Void> depositValue(@PathVariable UUID id, @Validated @RequestBody MoneyRequest moneyRequest) {
        var moneyEntity = moneyMapper.toEntity(moneyRequest);
        portfolioService.depositValue(id, moneyEntity);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/withdrawal")
    public ResponseEntity<Void> withdrawalValue(@PathVariable UUID id, @Validated @RequestBody MoneyRequest moneyRequest) {
        var moneyEntity = moneyMapper.toEntity(moneyRequest);
        portfolioService.withdrawalValue(id, moneyEntity);
        return ResponseEntity.noContent().build();
    }

}