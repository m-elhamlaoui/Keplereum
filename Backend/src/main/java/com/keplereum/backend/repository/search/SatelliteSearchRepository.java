package com.keplereum.backend.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.keplereum.backend.domain.Satellite;
import com.keplereum.backend.repository.SatelliteRepository;
import java.util.stream.Stream;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

/**
 * Spring Data Elasticsearch repository for the {@link Satellite} entity.
 */
public interface SatelliteSearchRepository extends ElasticsearchRepository<Satellite, Long>, SatelliteSearchRepositoryInternal {}

interface SatelliteSearchRepositoryInternal {
    Stream<Satellite> search(String query);

    Stream<Satellite> search(Query query);

    @Async
    void index(Satellite entity);

    @Async
    void deleteFromIndexById(Long id);
}

class SatelliteSearchRepositoryInternalImpl implements SatelliteSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final SatelliteRepository repository;

    SatelliteSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, SatelliteRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Stream<Satellite> search(String query) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery);
    }

    @Override
    public Stream<Satellite> search(Query query) {
        return elasticsearchTemplate.search(query, Satellite.class).map(SearchHit::getContent).stream();
    }

    @Override
    public void index(Satellite entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), Satellite.class);
    }
}
