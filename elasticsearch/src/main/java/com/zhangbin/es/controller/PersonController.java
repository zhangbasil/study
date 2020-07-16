package com.zhangbin.es.controller;

import com.zhangbin.es.pojo.Person;
import com.zhangbin.es.respository.PersonRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@RestController
public class PersonController {

    @Resource
    private PersonRepository personRepository;
    @Resource
    private ElasticsearchOperations elasticsearchOperations;



    @GetMapping("/search")
    public Object search() {
        return personRepository.findAll();
    }

    @GetMapping("/query")
    public Object queryByKeyword(@RequestParam(value = "keyword", required = false) String keyword) {

        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withFields("message")
                .withPageable(PageRequest.of(0, 10))
                .build();


        Query searchQuery = new NativeSearchQueryBuilder()
                .withIds(Arrays.asList("person"))
                .withQuery(QueryBuilders.fuzzyQuery("address", keyword))
//                .withFilter(QueryBuilders.fuzzyQuery("address", keyword))
                .build();
        return elasticsearchOperations.multiGet(searchQuery, Person.class, IndexCoordinates.of("person"));
    }


    @GetMapping("/add")
    public Object add() {
        people().forEach(item -> personRepository.save(item));
        return "success";
    }


    private List<Person> people() {
        List<Person> people = new ArrayList<>();

        people.add(Person.builder()
                .id(10000L)
                .mobile("17687364930")
                .address("上海市闵行区莘庄镇秀文路999弄100号")
                .lastName("zhao")
                .birthDate(LocalDate.now())
                .created(Instant.now())
                .build());

        people.add(Person.builder()
                .id(20000L)
                .mobile("186121232345")
                .address("北京市朝阳区莘庄镇秀文路999弄100号")
                .lastName("fei")
                .birthDate(LocalDate.now())
                .created(Instant.now())
                .build());

        people.add(Person.builder()
                .id(30000L)
                .mobile("132324234340")
                .address("上海市徐汇区龙华路1234弄99号")
                .lastName("hahah")
                .birthDate(LocalDate.now())
                .created(Instant.now())
                .build());

        people.add(Person.builder()
                .id(40000L)
                .mobile("13132443423")
                .address("上海市闵行区莘庄镇秀文路888弄99号")
                .lastName("zzzz")
                .birthDate(LocalDate.now())
                .created(Instant.now())
                .build());

        return people;

    }


}
