package com.keplereum.backend.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.keplereum.backend.domain.Block;
import com.keplereum.backend.repository.BlockRepository;
import java.util.stream.Stream;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

/**
 * Spring Data Elasticsearch repository for the {@link Block} entity.
 */
public interface BlockSearchRepository extends ElasticsearchRepository<Block, Long>, BlockSearchRepositoryInternal {}

interface BlockSearchRepositoryInternal {
    Stream<Block> search(String query);

    Stream<Block> search(Query query);

    @Async
    void index(Block entity);

    @Async
    void deleteFromIndexById(Long id);
}

class BlockSearchRepositoryInternalImpl implements BlockSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final BlockRepository repository;

    BlockSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, BlockRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Stream<Block> search(String query) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery);
    }

    @Override
    public Stream<Block> search(Query query) {
        return elasticsearchTemplate.search(query, Block.class).map(SearchHit::getContent).stream();
    }

    @Override
    public void index(Block entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), Block.class);
    }
}
