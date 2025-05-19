package com.keplereum.backend.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.keplereum.backend.domain.GroundStation;
import com.keplereum.backend.repository.GroundStationRepository;
import java.util.stream.Stream;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

/**
 * Spring Data Elasticsearch repository for the {@link GroundStation} entity.
 */
public interface GroundStationSearchRepository
    extends ElasticsearchRepository<GroundStation, Long>, GroundStationSearchRepositoryInternal {}

interface GroundStationSearchRepositoryInternal {
    Stream<GroundStation> search(String query);

    Stream<GroundStation> search(Query query);

    @Async
    void index(GroundStation entity);

    @Async
    void deleteFromIndexById(Long id);
}

class GroundStationSearchRepositoryInternalImpl implements GroundStationSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final GroundStationRepository repository;

    GroundStationSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, GroundStationRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Stream<GroundStation> search(String query) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery);
    }

    @Override
    public Stream<GroundStation> search(Query query) {
        return elasticsearchTemplate.search(query, GroundStation.class).map(SearchHit::getContent).stream();
    }

    @Override
    public void index(GroundStation entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), GroundStation.class);
    }
}
