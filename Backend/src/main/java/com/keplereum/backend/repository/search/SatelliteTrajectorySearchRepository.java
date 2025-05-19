package com.keplereum.backend.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.keplereum.backend.domain.SatelliteTrajectory;
import com.keplereum.backend.repository.SatelliteTrajectoryRepository;
import java.util.stream.Stream;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

/**
 * Spring Data Elasticsearch repository for the {@link SatelliteTrajectory} entity.
 */
public interface SatelliteTrajectorySearchRepository
    extends ElasticsearchRepository<SatelliteTrajectory, Long>, SatelliteTrajectorySearchRepositoryInternal {}

interface SatelliteTrajectorySearchRepositoryInternal {
    Stream<SatelliteTrajectory> search(String query);

    Stream<SatelliteTrajectory> search(Query query);

    @Async
    void index(SatelliteTrajectory entity);

    @Async
    void deleteFromIndexById(Long id);
}

class SatelliteTrajectorySearchRepositoryInternalImpl implements SatelliteTrajectorySearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final SatelliteTrajectoryRepository repository;

    SatelliteTrajectorySearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, SatelliteTrajectoryRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Stream<SatelliteTrajectory> search(String query) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery);
    }

    @Override
    public Stream<SatelliteTrajectory> search(Query query) {
        return elasticsearchTemplate.search(query, SatelliteTrajectory.class).map(SearchHit::getContent).stream();
    }

    @Override
    public void index(SatelliteTrajectory entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), SatelliteTrajectory.class);
    }
}
