package com.keplereum.backend.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.keplereum.backend.domain.Sensor;
import com.keplereum.backend.repository.SensorRepository;
import java.util.stream.Stream;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

/**
 * Spring Data Elasticsearch repository for the {@link Sensor} entity.
 */
public interface SensorSearchRepository extends ElasticsearchRepository<Sensor, Long>, SensorSearchRepositoryInternal {}

interface SensorSearchRepositoryInternal {
    Stream<Sensor> search(String query);

    Stream<Sensor> search(Query query);

    @Async
    void index(Sensor entity);

    @Async
    void deleteFromIndexById(Long id);
}

class SensorSearchRepositoryInternalImpl implements SensorSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final SensorRepository repository;

    SensorSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, SensorRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Stream<Sensor> search(String query) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery);
    }

    @Override
    public Stream<Sensor> search(Query query) {
        return elasticsearchTemplate.search(query, Sensor.class).map(SearchHit::getContent).stream();
    }

    @Override
    public void index(Sensor entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), Sensor.class);
    }
}
