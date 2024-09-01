package com.example.test.best_travel.infrastructure.services;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.best_travel.api.models.responses.FlyResponse;
import com.example.test.best_travel.domain.entities.jpa.FlyEntity;
import com.example.test.best_travel.domain.repositories.jpa.FlyRepository;
import com.example.test.best_travel.infrastructure.abstract_services.IFlyService;
import com.example.test.best_travel.util.enums.SortType;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class FlyService implements IFlyService {

    private final FlyRepository flyRepository;

    /*
    private final WebClient webClient;

    public FlyService(FlyRepository flyRepository,@Qualifier(value = "base") WebClient webClient) {
        this.flyRepository = flyRepository;
        this.webClient = webClient;
    }
   */
    @Override
    public Page<FlyResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType) {
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size,Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size,Sort.by(FIELD_BY_SORT).descending());
        }
        return flyRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        return flyRepository.selectLessPrice(price)
            .stream()
            .map(this::entityToResponse)
            .collect(Collectors.toSet());
    }

    @Override
    public Set<FlyResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return flyRepository.selectBetweenPrice(min, max)
            .stream()
            .map(this::entityToResponse)
            .collect(Collectors.toSet());
    }

    @Override
    public Set<FlyResponse> readByOriginDestiny(String origin, String destiny) {
        return flyRepository.selectOriginDestiny(origin, destiny)
            .stream()
            .map(this::entityToResponse)
            .collect(Collectors.toSet());
    }

    private FlyResponse entityToResponse(FlyEntity entity) {
        FlyResponse response = new FlyResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

}
