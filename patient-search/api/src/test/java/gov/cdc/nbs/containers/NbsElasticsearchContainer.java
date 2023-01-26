package gov.cdc.nbs.containers;

import java.io.IOException;

import org.opensearch.testcontainers.OpensearchContainer;
import org.testcontainers.utility.DockerImageName;

public class NbsElasticsearchContainer extends OpensearchContainer {
    private static final String OPENSEARCH_DOCKER = "opensearchproject/opensearch:2.4.1";

    private static final String CLUSTER_NAME = "cluster.name";

    private static final String OPENSEARCH = "opensearch";

    public NbsElasticsearchContainer() {
        super(DockerImageName.parse(OPENSEARCH_DOCKER)
                .asCompatibleSubstituteFor("opensearchproject/opensearch"));
        this.addEnv(CLUSTER_NAME, OPENSEARCH);
    }

    public void startWithPlugins() throws UnsupportedOperationException, IOException, InterruptedException {
        start();
        execInContainer(
            "/usr/share/opensearch/bin/opensearch-plugin",
            "install",
            "analysis-phonetic"
        );
    }
}
