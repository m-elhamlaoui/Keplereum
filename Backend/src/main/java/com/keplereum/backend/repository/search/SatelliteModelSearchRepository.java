package com.keplereum.backend.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.keplereum.backend.domain.SatelliteModel;
import com.keplereum.backend.repository.SatelliteModelRepository;
import java.util.stream.Stream;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

/**
 * Spring Data Elasticsearch repository for the {@link SatelliteModel} entity.
 */
public interface SatelliteModelSearchRepository
    extends ElasticsearchRepository<SatelliteModel, Long>, SatelliteModelSearchRepositoryInternal {}

interface SatelliteModelSearchRepositoryInternal {
    Stream<SatelliteModel> search(String query);

    Stream<SatelliteModel> search(Query query);

    @Async
    void index(SatelliteModel entity);

    @Async
    void deleteFromIndexById(Long id);
}

class SatelliteModelSearchRepositoryInternalImpl implements SatelliteModelSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final SatelliteModelRepository repository;

    SatelliteModelSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, SatelliteModelRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Stream<SatelliteModel> search(String query) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery);
    }

    @Override
    public Stream<SatelliteModel> search(Query query) {
        return elasticsearchTemplate.search(query, SatelliteModel.class).map(SearchHit::getContent).stream();
    }

    @Override
    public void index(SatelliteModel entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), SatelliteModel.class);
    }
}
