package com.zhangbin.es.respository;

import com.zhangbin.es.pojo.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public interface PersonRepository extends ElasticsearchRepository<Person, Long> {

}
