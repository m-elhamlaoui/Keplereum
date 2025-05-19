package com.keplereum.backend.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.keplereum.backend.domain.ValidatorAction;
import com.keplereum.backend.repository.ValidatorActionRepository;
import java.util.stream.Stream;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

/**
 * Spring Data Elasticsearch repository for the {@link ValidatorAction} entity.
 */
public interface ValidatorActionSearchRepository
    extends ElasticsearchRepository<ValidatorAction, Long>, ValidatorActionSearchRepositoryInternal {}

interface ValidatorActionSearchRepositoryInternal {
    Stream<ValidatorAction> search(String query);

    Stream<ValidatorAction> search(Query query);

    @Async
    void index(ValidatorAction entity);

    @Async
    void deleteFromIndexById(Long id);
}

class ValidatorActionSearchRepositoryInternalImpl implements ValidatorActionSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final ValidatorActionRepository repository;

    ValidatorActionSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, ValidatorActionRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Stream<ValidatorAction> search(String query) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery);
    }

    @Override
    public Stream<ValidatorAction> search(Query query) {
        return elasticsearchTemplate.search(query, ValidatorAction.class).map(SearchHit::getContent).stream();
    }

    @Override
    public void index(ValidatorAction entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), ValidatorAction.class);
    }
}
