package com.keplereum.backend.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.keplereum.backend.domain.Alert;
import com.keplereum.backend.repository.AlertRepository;
import java.util.stream.Stream;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

/**
 * Spring Data Elasticsearch repository for the {@link Alert} entity.
 */
public interface AlertSearchRepository extends ElasticsearchRepository<Alert, Long>, AlertSearchRepositoryInternal {}

interface AlertSearchRepositoryInternal {
    Stream<Alert> search(String query);

    Stream<Alert> search(Query query);

    @Async
    void index(Alert entity);

    @Async
    void deleteFromIndexById(Long id);
}

class AlertSearchRepositoryInternalImpl implements AlertSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final AlertRepository repository;

    AlertSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, AlertRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Stream<Alert> search(String query) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery);
    }

    @Override
    public Stream<Alert> search(Query query) {
        return elasticsearchTemplate.search(query, Alert.class).map(SearchHit::getContent).stream();
    }

    @Override
    public void index(Alert entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), Alert.class);
    }
}
