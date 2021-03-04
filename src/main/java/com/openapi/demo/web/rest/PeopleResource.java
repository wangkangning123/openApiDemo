package com.openapi.demo.web.rest;

import com.openapi.demo.domain.People;
import com.openapi.demo.domain.QPeople;
import com.openapi.demo.repository.PeopleRepository;
import com.openapi.demo.web.api.PeopleApiDelegate;
import com.openapi.demo.web.api.model.PeopleListDto;
import com.openapi.demo.web.api.model.PeopleVM;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.annotations.Api;
import io.undertow.predicate.PredicateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link com.openapi.demo.domain.People}.
 */
@RestController
@RequestMapping("/api")
@Transactional
@Api(tags = "123")
public class PeopleResource implements PeopleApiDelegate {

    private final Logger log = LoggerFactory.getLogger(PeopleResource.class);

    private final QPeople qPeople = QPeople.people;

    private final NativeWebRequest request;
    private final JPAQueryFactory queryFactory;
    private final PeopleRepository peopleRepository;

    public PeopleResource(NativeWebRequest request, JPAQueryFactory queryFactory, PeopleRepository peopleRepository) {
        this.request = request;
        this.queryFactory = queryFactory;
        this.peopleRepository = peopleRepository;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Void> addPeople(PeopleVM peopleVM) {
        People people = new People();
        people.setUsername(peopleVM.getUsername());
        people.setPassword(peopleVM.getPassword());
        people.setSex(peopleVM.getSex().toString());
        people.setPhone(peopleVM.getPhone());
        people.setHobby(peopleVM.getHobby().toString());
        people.setDate(peopleVM.getDate());
        people.setEmail(peopleVM.getEmail());
        peopleRepository.save(people);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PeopleListDto> getPeople(Pageable pageable) {
        PeopleListDto peopleListDto = new PeopleListDto();
        peopleListDto.setPageNum(pageable.getPageNumber());
        peopleListDto.setPageSize(pageable.getPageSize());
        List<PeopleVM> list = queryFactory.selectFrom(qPeople)
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch().stream().map(people -> {
                PeopleVM peopleVM = new PeopleVM();
                peopleVM.setUsername(people.getUsername());
                peopleVM.setPassword(people.getPassword());
                peopleVM.setSex(PeopleVM.SexEnum.fromValue(people.getSex()));
                peopleVM.setHobby(Arrays.asList(people.getHobby().split(",").clone()));
                peopleVM.setDate(people.getDate());
                peopleVM.setEmail(people.getEmail());
                peopleVM.setPhone(people.getPhone());
                return peopleVM;
            }).collect(Collectors.toList());
        peopleListDto.setPeopleList(list);

        return ResponseEntity.ok(peopleListDto);
    }
}
