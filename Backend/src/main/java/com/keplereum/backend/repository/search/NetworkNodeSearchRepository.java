package com.keplereum.backend.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.keplereum.backend.domain.NetworkNode;
import com.keplereum.backend.repository.NetworkNodeRepository;
import java.util.stream.Stream;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

/**
 * Spring Data Elasticsearch repository for the {@link NetworkNode} entity.
 */
public interface NetworkNodeSearchRepository extends ElasticsearchRepository<NetworkNode, Long>, NetworkNodeSearchRepositoryInternal {}

interface NetworkNodeSearchRepositoryInternal {
    Stream<NetworkNode> search(String query);

    Stream<NetworkNode> search(Query query);

    @Async
    void index(NetworkNode entity);

    @Async
    void deleteFromIndexById(Long id);
}

class NetworkNodeSearchRepositoryInternalImpl implements NetworkNodeSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final NetworkNodeRepository repository;

    NetworkNodeSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, NetworkNodeRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Stream<NetworkNode> search(String query) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery);
    }

    @Override
    public Stream<NetworkNode> search(Query query) {
        return elasticsearchTemplate.search(query, NetworkNode.class).map(SearchHit::getContent).stream();
    }

    @Override
    public void index(NetworkNode entity) {
        repository.findOneWithEagerRelationships(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), NetworkNode.class);
    }
}
