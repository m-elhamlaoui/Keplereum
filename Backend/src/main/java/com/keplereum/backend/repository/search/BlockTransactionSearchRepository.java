package com.keplereum.backend.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.keplereum.backend.domain.BlockTransaction;
import com.keplereum.backend.repository.BlockTransactionRepository;
import java.util.stream.Stream;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

/**
 * Spring Data Elasticsearch repository for the {@link BlockTransaction} entity.
 */
public interface BlockTransactionSearchRepository
    extends ElasticsearchRepository<BlockTransaction, Long>, BlockTransactionSearchRepositoryInternal {}

interface BlockTransactionSearchRepositoryInternal {
    Stream<BlockTransaction> search(String query);

    Stream<BlockTransaction> search(Query query);

    @Async
    void index(BlockTransaction entity);

    @Async
    void deleteFromIndexById(Long id);
}

class BlockTransactionSearchRepositoryInternalImpl implements BlockTransactionSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final BlockTransactionRepository repository;

    BlockTransactionSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, BlockTransactionRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Stream<BlockTransaction> search(String query) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery);
    }

    @Override
    public Stream<BlockTransaction> search(Query query) {
        return elasticsearchTemplate.search(query, BlockTransaction.class).map(SearchHit::getContent).stream();
    }

    @Override
    public void index(BlockTransaction entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), BlockTransaction.class);
    }
}
