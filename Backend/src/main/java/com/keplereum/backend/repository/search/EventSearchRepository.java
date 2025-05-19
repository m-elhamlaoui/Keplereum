package com.keplereum.backend.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.keplereum.backend.domain.Event;
import com.keplereum.backend.repository.EventRepository;
import java.util.stream.Stream;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

/**
 * Spring Data Elasticsearch repository for the {@link Event} entity.
 */
public interface EventSearchRepository extends ElasticsearchRepository<Event, Long>, EventSearchRepositoryInternal {}

interface EventSearchRepositoryInternal {
    Stream<Event> search(String query);

    Stream<Event> search(Query query);

    @Async
    void index(Event entity);

    @Async
    void deleteFromIndexById(Long id);
}

class EventSearchRepositoryInternalImpl implements EventSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final EventRepository repository;

    EventSearchRepositoryInternalImpl(ElasticsearchTemplate elasticsearchTemplate, EventRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Stream<Event> search(String query) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery);
    }

    @Override
    public Stream<Event> search(Query query) {
        return elasticsearchTemplate.search(query, Event.class).map(SearchHit::getContent).stream();
    }

    @Override
    public void index(Event entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(Long id) {
        elasticsearchTemplate.delete(String.valueOf(id), Event.class);
    }
}
