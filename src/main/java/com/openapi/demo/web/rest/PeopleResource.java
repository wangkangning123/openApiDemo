package com.openapi.demo.web.rest;

import com.openapi.demo.domain.People;
import com.openapi.demo.domain.QPeople;
import com.openapi.demo.repository.PeopleRepository;
import com.openapi.demo.web.api.PeopleApiDelegate;
import com.openapi.demo.web.api.model.Ypeople;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.jhipster.web.util.PaginationUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link com.openapi.demo.domain.People}.
 */
@RestController
@RequestMapping("/api")
@Transactional
@Api(tags = "openApi测试用")
public class PeopleResource implements PeopleApiDelegate {

    private final Logger log = LoggerFactory.getLogger(PeopleResource.class);

    private final QPeople qPeople = QPeople.people;

    private final JPAQueryFactory queryFactory;
    private final NativeWebRequest request;
    private final PeopleRepository peopleRepository;

    public PeopleResource(JPAQueryFactory queryFactory, NativeWebRequest request, PeopleRepository peopleRepository) {
        this.queryFactory = queryFactory;
        this.request = request;
        this.peopleRepository = peopleRepository;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Object> peopleGet(Long page, Long size, List<String> sort) {
        JPAQuery<People> query = queryFactory.selectFrom(qPeople)
            .where(qPeople.id.isNotNull())
            .limit(size)
            .offset(page);
        List<Ypeople> list = query.fetch().stream().map(people -> {
            Ypeople newPeople = new Ypeople();
            newPeople.setEmail(people.getEmail());
            newPeople.setPassowrd(people.getPassword());
            newPeople.setPhone(people.getPhone());
            newPeople.setUsername(people.getUsername());
            return newPeople;
        }).collect(Collectors.toList());
        Page<Ypeople> yPage = new PageImpl<>(list, new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return Integer.parseInt(String.valueOf(size));
            }

            @Override
            public long getOffset() {
                return page;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        }, query.fetchCount());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), yPage);

        return ResponseEntity.ok().headers(headers).body(yPage);
    }

    @Override
    public ResponseEntity<Ypeople> peoplePost(Ypeople ypeople, Long page, Long size, List<String> sort) {
        People people = new People();
        people.setEmail(ypeople.getEmail());
        people.setPhone(ypeople.getPhone());
        people.setUsername(ypeople.getUsername());
        people.setPassword(ypeople.getPassowrd());
        peopleRepository.save(people);

        return ResponseEntity.ok(ypeople);
    }
}
