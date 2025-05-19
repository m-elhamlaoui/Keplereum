package com.keplereum.backend.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.keplereum.backend.domain.Confirmation;
import com.keplereum.backend.repository.ConfirmationRepository;
import java.util.stream.Stream;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

/**
 * Spring Data Elasticsearch repository for the {@link Confirmation} entity.
 */
public interface ConfirmationSearchRepository extends ElasticsearchRepository<Confirmation, Long>, ConfirmationSearchRepositoryInternal {}

interface ConfirmationSearchRepositoryInternal {
    Stream<Confirmation> search(String query);

    Stream<Confirmation> search(Query query);

    @Async
    void index(Confirmation entity);

    @Async
    void deleteFromIndexById(Long id);
}

class ConfirmationSearchRepositoryInternalImpl implements ConfirmationSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final ConfirmationRepository repository;

    ConfirmationSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, ConfirmationRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Stream<Confirmation> search(String query) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery);
    }

    @Override
    public Stream<Confirmation> search(Query query) {
        return elasticsearchTemplate.search(query, Confirmation.class).map(SearchHit::getContent).stream();
    }

    @Override
    public void index(Confirmation entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), Confirmation.class);
    }
}
