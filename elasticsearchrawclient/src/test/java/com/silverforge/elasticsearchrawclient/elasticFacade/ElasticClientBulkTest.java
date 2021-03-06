package com.silverforge.elasticsearchrawclient.elasticFacade;

import com.silverforge.elasticsearchrawclient.BuildConfig;
import com.silverforge.elasticsearchrawclient.model.BulkActionResult;
import com.silverforge.elasticsearchrawclient.model.BulkTuple;
import com.silverforge.elasticsearchrawclient.testModel.SimpleCity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ElasticClientBulkTest extends ElasticClientBaseTest {

    @Test
    public void bulkTest() {
        ArrayList<BulkTuple> bulkItems = new ArrayList<>();

        bulkItems.add(BulkTuple
                        .builder()
                        .indexName("cities")
                        .typeName("city")
                        .id("szekesfehervar")
                        .entity(new SimpleCity("Székesfehérvár"))
                        .operationType(OperationType.CREATE)
                        .build()
        );

        bulkItems.add(BulkTuple
                        .builder()
                        .indexName("cities")
                        .typeName("city")
                        .id("bekescsaba")
                        .entity(new SimpleCity("Békéscsaba"))
                        .operationType(OperationType.CREATE)
                        .build()
        );

        bulkItems.add(BulkTuple
                        .builder()
                        .indexName("cities")
                        .typeName("city")
                        .id("bekescsaba")
                        .operationType(OperationType.DELETE)
                        .build()
        );

        bulkItems.add(BulkTuple
                        .builder()
                        .indexName("cities")
                        .typeName("city")
                        .id("debrecen")
                        .entity(new SimpleCity("Debrecen"))
                        .operationType(OperationType.INDEX)
                        .build()
        );

        List<BulkActionResult> actionResults = client.bulk(bulkItems);

        assertThat(actionResults, not(nullValue()));
        assertThat(actionResults.size(), is(equalTo(bulkItems.size())));

        for (BulkActionResult actionResult : actionResults) {
            if (actionResult.getOperation() == OperationType.CREATE)
                client.removeDocument("cities", "city", actionResult.getId());
        }
    }
}
