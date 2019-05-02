package datawave.query.planner;

import datawave.query.CloseableIterable;
import datawave.query.exceptions.DatawaveQueryException;
import datawave.query.index.lookup.CreateUidsIterator;
import datawave.query.index.lookup.IndexInfo;
import datawave.query.index.lookup.UidIntersector;
import datawave.query.planner.pushdown.PushDownPlanner;
import datawave.query.tables.ScannerFactory;
import datawave.webservice.query.Query;
import datawave.webservice.query.configuration.GenericQueryConfiguration;
import datawave.webservice.query.configuration.QueryData;

import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.iterators.SortedKeyValueIterator;

public abstract class QueryPlanner implements PushDownPlanner {
    
    protected Class<? extends SortedKeyValueIterator<Key,Value>> createUidsIteratorClass = CreateUidsIterator.class;
    
    protected UidIntersector uidIntersector = new IndexInfo();

    /**
     * Process the {@code query} with the provided {@code config} to generate an {@link Iterable}&lt;QueryData&gt; to apply each to a BatchScanner.
     *
     * @param config
     * @param query
     * @param settings
     * @param scannerFactory
     * @return
     * @throws DatawaveQueryException
     */
    public abstract CloseableIterable<QueryData> process(GenericQueryConfiguration config, String query, Query settings, ScannerFactory scannerFactory)
                    throws DatawaveQueryException;
    
    public abstract long maxRangesPerQueryPiece();
    
    public abstract void close(GenericQueryConfiguration config, Query settings);
    
    public abstract void setQueryIteratorClass(Class<? extends SortedKeyValueIterator<Key,Value>> clazz);
    
    public abstract Class<? extends SortedKeyValueIterator<Key,Value>> getQueryIteratorClass();
    
    /**
     * @return
     */
    public abstract String getPlannedScript();
    
    public abstract QueryPlanner clone();
    
    public Class<? extends SortedKeyValueIterator<Key,Value>> getCreateUidsIteratorClass() {
        return createUidsIteratorClass;
    }
    
    public void setCreateUidsIteratorClass(Class<? extends SortedKeyValueIterator<Key,Value>> createUidsIteratorClass) {
        this.createUidsIteratorClass = createUidsIteratorClass;
    }
    
    public UidIntersector getUidIntersector() {
        return uidIntersector;
    }
    
    public void setUidIntersector(UidIntersector uidIntersector) {
        this.uidIntersector = uidIntersector;
    }
}
