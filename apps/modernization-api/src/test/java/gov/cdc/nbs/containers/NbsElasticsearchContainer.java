package gov.cdc.nbs.containers;

import java.io.IOException;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

public class NbsElasticsearchContainer extends ElasticsearchContainer {
  private static final String ELASTIC_SEARCH_DOCKER = "elasticsearch:7.17.7";

  private static final String CLUSTER_NAME = "cluster.name";

  private static final String ELASTIC_SEARCH = "elasticsearch";

  public NbsElasticsearchContainer() {
    super(
        DockerImageName.parse(ELASTIC_SEARCH_DOCKER)
            .asCompatibleSubstituteFor("docker.elastic.co/elasticsearch/elasticsearch"));
    this.addEnv(CLUSTER_NAME, ELASTIC_SEARCH);
    this.addEnv("ES_JAVA_OPTS", "-Xms256m -Xmx512m -XX:MaxDirectMemorySize=536870912");
  }

  public void startWithPlugins()
      throws UnsupportedOperationException, IOException, InterruptedException {
    start();
    execInContainer(
        "/usr/share/elasticsearch/bin/elasticsearch-plugin", "install", "analysis-phonetic");
  }
}
