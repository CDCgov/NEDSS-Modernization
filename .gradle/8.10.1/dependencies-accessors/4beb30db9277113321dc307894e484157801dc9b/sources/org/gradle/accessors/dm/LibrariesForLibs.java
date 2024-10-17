package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final AssertjLibraryAccessors laccForAssertjLibraryAccessors = new AssertjLibraryAccessors(owner);
    private final CucumberLibraryAccessors laccForCucumberLibraryAccessors = new CucumberLibraryAccessors(owner);
    private final ElasticsearchLibraryAccessors laccForElasticsearchLibraryAccessors = new ElasticsearchLibraryAccessors(owner);
    private final JacksonLibraryAccessors laccForJacksonLibraryAccessors = new JacksonLibraryAccessors(owner);
    private final JavaLibraryAccessors laccForJavaLibraryAccessors = new JavaLibraryAccessors(owner);
    private final JunitLibraryAccessors laccForJunitLibraryAccessors = new JunitLibraryAccessors(owner);
    private final MssqlLibraryAccessors laccForMssqlLibraryAccessors = new MssqlLibraryAccessors(owner);
    private final OpenLibraryAccessors laccForOpenLibraryAccessors = new OpenLibraryAccessors(owner);
    private final QueryDSLLibraryAccessors laccForQueryDSLLibraryAccessors = new QueryDSLLibraryAccessors(owner);
    private final SpringLibraryAccessors laccForSpringLibraryAccessors = new SpringLibraryAccessors(owner);
    private final SpringBootLibraryAccessors laccForSpringBootLibraryAccessors = new SpringBootLibraryAccessors(owner);
    private final TestcontainersLibraryAccessors laccForTestcontainersLibraryAccessors = new TestcontainersLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Dependency provider for <b>awaitility</b> with <b>org.awaitility:awaitility</b> coordinates and
     * with version <b>4.2.1</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getAwaitility() {
        return create("awaitility");
    }

    /**
     * Dependency provider for <b>graphql</b> with <b>com.graphql-java:graphql-java</b> coordinates and
     * with version <b>20.9</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getGraphql() {
        return create("graphql");
    }

    /**
     * Dependency provider for <b>guava</b> with <b>com.google.guava:guava</b> coordinates and
     * with version <b>33.3.0-jre</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getGuava() {
        return create("guava");
    }

    /**
     * Dependency provider for <b>itextpdf</b> with <b>com.itextpdf:itextpdf</b> coordinates and
     * with version <b>5.5.13.4</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getItextpdf() {
        return create("itextpdf");
    }

    /**
     * Dependency provider for <b>lombok</b> with <b>org.projectlombok:lombok</b> coordinates and
     * with version <b>1.18.34</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getLombok() {
        return create("lombok");
    }

    /**
     * Dependency provider for <b>mockito</b> with <b>org.mockito:mockito-core</b> coordinates and
     * with version <b>5.12.0</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getMockito() {
        return create("mockito");
    }

    /**
     * Dependency provider for <b>parsson</b> with <b>org.eclipse.parsson:parsson</b> coordinates and
     * with version <b>1.1.5</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getParsson() {
        return create("parsson");
    }

    /**
     * Group of libraries at <b>assertj</b>
     */
    public AssertjLibraryAccessors getAssertj() {
        return laccForAssertjLibraryAccessors;
    }

    /**
     * Group of libraries at <b>cucumber</b>
     */
    public CucumberLibraryAccessors getCucumber() {
        return laccForCucumberLibraryAccessors;
    }

    /**
     * Group of libraries at <b>elasticsearch</b>
     */
    public ElasticsearchLibraryAccessors getElasticsearch() {
        return laccForElasticsearchLibraryAccessors;
    }

    /**
     * Group of libraries at <b>jackson</b>
     */
    public JacksonLibraryAccessors getJackson() {
        return laccForJacksonLibraryAccessors;
    }

    /**
     * Group of libraries at <b>java</b>
     */
    public JavaLibraryAccessors getJava() {
        return laccForJavaLibraryAccessors;
    }

    /**
     * Group of libraries at <b>junit</b>
     */
    public JunitLibraryAccessors getJunit() {
        return laccForJunitLibraryAccessors;
    }

    /**
     * Group of libraries at <b>mssql</b>
     */
    public MssqlLibraryAccessors getMssql() {
        return laccForMssqlLibraryAccessors;
    }

    /**
     * Group of libraries at <b>open</b>
     */
    public OpenLibraryAccessors getOpen() {
        return laccForOpenLibraryAccessors;
    }

    /**
     * Group of libraries at <b>queryDSL</b>
     */
    public QueryDSLLibraryAccessors getQueryDSL() {
        return laccForQueryDSLLibraryAccessors;
    }

    /**
     * Group of libraries at <b>spring</b>
     */
    public SpringLibraryAccessors getSpring() {
        return laccForSpringLibraryAccessors;
    }

    /**
     * Group of libraries at <b>springBoot</b>
     */
    public SpringBootLibraryAccessors getSpringBoot() {
        return laccForSpringBootLibraryAccessors;
    }

    /**
     * Group of libraries at <b>testcontainers</b>
     */
    public TestcontainersLibraryAccessors getTestcontainers() {
        return laccForTestcontainersLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class AssertjLibraryAccessors extends SubDependencyFactory {

        public AssertjLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>org.assertj:assertj-core</b> coordinates and
         * with version reference <b>assertj</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("assertj.core");
        }

    }

    public static class CucumberLibraryAccessors extends SubDependencyFactory {
        private final CucumberJunitLibraryAccessors laccForCucumberJunitLibraryAccessors = new CucumberJunitLibraryAccessors(owner);

        public CucumberLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>java</b> with <b>io.cucumber:cucumber-java</b> coordinates and
         * with version reference <b>cucumber</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJava() {
            return create("cucumber.java");
        }

        /**
         * Dependency provider for <b>spring</b> with <b>io.cucumber:cucumber-spring</b> coordinates and
         * with version reference <b>cucumber</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSpring() {
            return create("cucumber.spring");
        }

        /**
         * Group of libraries at <b>cucumber.junit</b>
         */
        public CucumberJunitLibraryAccessors getJunit() {
            return laccForCucumberJunitLibraryAccessors;
        }

    }

    public static class CucumberJunitLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {
        private final CucumberJunitPlatformLibraryAccessors laccForCucumberJunitPlatformLibraryAccessors = new CucumberJunitPlatformLibraryAccessors(owner);

        public CucumberJunitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>junit</b> with <b>io.cucumber:cucumber-junit</b> coordinates and
         * with version reference <b>cucumber</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("cucumber.junit");
        }

        /**
         * Group of libraries at <b>cucumber.junit.platform</b>
         */
        public CucumberJunitPlatformLibraryAccessors getPlatform() {
            return laccForCucumberJunitPlatformLibraryAccessors;
        }

    }

    public static class CucumberJunitPlatformLibraryAccessors extends SubDependencyFactory {

        public CucumberJunitPlatformLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>engine</b> with <b>io.cucumber:cucumber-junit-platform-engine</b> coordinates and
         * with version reference <b>cucumber</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getEngine() {
            return create("cucumber.junit.platform.engine");
        }

    }

    public static class ElasticsearchLibraryAccessors extends SubDependencyFactory {

        public ElasticsearchLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>java</b> with <b>co.elastic.clients:elasticsearch-java</b> coordinates and
         * with version <b>7.17.23</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJava() {
            return create("elasticsearch.java");
        }

    }

    public static class JacksonLibraryAccessors extends SubDependencyFactory {
        private final JacksonDatatypeLibraryAccessors laccForJacksonDatatypeLibraryAccessors = new JacksonDatatypeLibraryAccessors(owner);

        public JacksonLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>com.fasterxml.jackson.core:jackson-core</b> coordinates and
         * with version reference <b>jackson</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("jackson.core");
        }

        /**
         * Group of libraries at <b>jackson.datatype</b>
         */
        public JacksonDatatypeLibraryAccessors getDatatype() {
            return laccForJacksonDatatypeLibraryAccessors;
        }

    }

    public static class JacksonDatatypeLibraryAccessors extends SubDependencyFactory {

        public JacksonDatatypeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>jsr310</b> with <b>com.fasterxml.jackson.datatype:jackson-datatype-jsr310</b> coordinates and
         * with version reference <b>jackson</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJsr310() {
            return create("jackson.datatype.jsr310");
        }

    }

    public static class JavaLibraryAccessors extends SubDependencyFactory {

        public JavaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>jwt</b> with <b>com.auth0:java-jwt</b> coordinates and
         * with version <b>4.4.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJwt() {
            return create("java.jwt");
        }

    }

    public static class JunitLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public JunitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>junit</b> with <b>org.junit.jupiter:junit-jupiter</b> coordinates and
         * with version reference <b>junit.jupiter</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("junit");
        }

        /**
         * Dependency provider for <b>platform</b> with <b>org.junit.platform:junit-platform-suite</b> coordinates and
         * with version <b>1.10.2</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPlatform() {
            return create("junit.platform");
        }

    }

    public static class MssqlLibraryAccessors extends SubDependencyFactory {

        public MssqlLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>jdbc</b> with <b>com.microsoft.sqlserver:mssql-jdbc</b> coordinates and
         * with version <b>12.4.2.jre11</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJdbc() {
            return create("mssql.jdbc");
        }

    }

    public static class OpenLibraryAccessors extends SubDependencyFactory {

        public OpenLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>org.springdoc:springdoc-openapi-starter-webmvc-ui</b> coordinates and
         * with version <b>2.6.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("open.api");
        }

    }

    public static class QueryDSLLibraryAccessors extends SubDependencyFactory {

        public QueryDSLLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>apt</b> with <b>com.querydsl:querydsl-apt</b> coordinates and
         * with version reference <b>queryDSL</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApt() {
            return create("queryDSL.apt");
        }

        /**
         * Dependency provider for <b>jpa</b> with <b>com.querydsl:querydsl-jpa</b> coordinates and
         * with version reference <b>queryDSL</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJpa() {
            return create("queryDSL.jpa");
        }

    }

    public static class SpringLibraryAccessors extends SubDependencyFactory {
        private final SpringKafkaLibraryAccessors laccForSpringKafkaLibraryAccessors = new SpringKafkaLibraryAccessors(owner);
        private final SpringSecurityLibraryAccessors laccForSpringSecurityLibraryAccessors = new SpringSecurityLibraryAccessors(owner);

        public SpringLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>test</b> with <b>org.springframework.boot:spring-boot-starter-test</b> coordinates and
         * with version reference <b>springBoot</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getTest() {
            return create("spring.test");
        }

        /**
         * Dependency provider for <b>validation</b> with <b>org.springframework.boot:spring-boot-starter-validation</b> coordinates and
         * with version reference <b>springBoot</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getValidation() {
            return create("spring.validation");
        }

        /**
         * Group of libraries at <b>spring.kafka</b>
         */
        public SpringKafkaLibraryAccessors getKafka() {
            return laccForSpringKafkaLibraryAccessors;
        }

        /**
         * Group of libraries at <b>spring.security</b>
         */
        public SpringSecurityLibraryAccessors getSecurity() {
            return laccForSpringSecurityLibraryAccessors;
        }

    }

    public static class SpringKafkaLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public SpringKafkaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>kafka</b> with <b>org.springframework.kafka:spring-kafka</b> coordinates and
         * with version reference <b>spring.kafka</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("spring.kafka");
        }

        /**
         * Dependency provider for <b>test</b> with <b>org.springframework.kafka:spring-kafka-test</b> coordinates and
         * with version reference <b>spring.kafka</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getTest() {
            return create("spring.kafka.test");
        }

    }

    public static class SpringSecurityLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {
        private final SpringSecurityOauth2LibraryAccessors laccForSpringSecurityOauth2LibraryAccessors = new SpringSecurityOauth2LibraryAccessors(owner);

        public SpringSecurityLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>security</b> with <b>org.springframework.boot:spring-boot-starter-security</b> coordinates and
         * with version reference <b>springBoot</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("spring.security");
        }

        /**
         * Dependency provider for <b>config</b> with <b>org.springframework.security:spring-security-config</b> coordinates and
         * with version reference <b>spring.security</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getConfig() {
            return create("spring.security.config");
        }

        /**
         * Dependency provider for <b>test</b> with <b>org.springframework.security:spring-security-test</b> coordinates and
         * with version reference <b>spring.security</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getTest() {
            return create("spring.security.test");
        }

        /**
         * Dependency provider for <b>web</b> with <b>org.springframework.security:spring-security-web</b> coordinates and
         * with version reference <b>spring.security</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getWeb() {
            return create("spring.security.web");
        }

        /**
         * Group of libraries at <b>spring.security.oauth2</b>
         */
        public SpringSecurityOauth2LibraryAccessors getOauth2() {
            return laccForSpringSecurityOauth2LibraryAccessors;
        }

    }

    public static class SpringSecurityOauth2LibraryAccessors extends SubDependencyFactory {
        private final SpringSecurityOauth2ResourceLibraryAccessors laccForSpringSecurityOauth2ResourceLibraryAccessors = new SpringSecurityOauth2ResourceLibraryAccessors(owner);

        public SpringSecurityOauth2LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>jose</b> with <b>org.springframework.security:spring-security-oauth2-jose</b> coordinates and
         * with version reference <b>spring.security</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJose() {
            return create("spring.security.oauth2.jose");
        }

        /**
         * Group of libraries at <b>spring.security.oauth2.resource</b>
         */
        public SpringSecurityOauth2ResourceLibraryAccessors getResource() {
            return laccForSpringSecurityOauth2ResourceLibraryAccessors;
        }

    }

    public static class SpringSecurityOauth2ResourceLibraryAccessors extends SubDependencyFactory {

        public SpringSecurityOauth2ResourceLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>server</b> with <b>org.springframework.security:spring-security-oauth2-resource-server</b> coordinates and
         * with version reference <b>spring.security</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getServer() {
            return create("spring.security.oauth2.resource.server");
        }

    }

    public static class SpringBootLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public SpringBootLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>springBoot</b> with <b>org.springframework.boot:spring-boot-starter</b> coordinates and
         * with version reference <b>springBoot</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("springBoot");
        }

        /**
         * Dependency provider for <b>graphql</b> with <b>org.springframework.boot:spring-boot-starter-graphql</b> coordinates and
         * with version reference <b>springBoot</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGraphql() {
            return create("springBoot.graphql");
        }

        /**
         * Dependency provider for <b>jdbc</b> with <b>org.springframework.boot:spring-boot-starter-jdbc</b> coordinates and
         * with version reference <b>springBoot</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJdbc() {
            return create("springBoot.jdbc");
        }

        /**
         * Dependency provider for <b>jpa</b> with <b>org.springframework.boot:spring-boot-starter-data-jpa</b> coordinates and
         * with version reference <b>springBoot</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJpa() {
            return create("springBoot.jpa");
        }

        /**
         * Dependency provider for <b>web</b> with <b>org.springframework.boot:spring-boot-starter-web</b> coordinates and
         * with version reference <b>springBoot</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getWeb() {
            return create("springBoot.web");
        }

    }

    public static class TestcontainersLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public TestcontainersLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>testcontainers</b> with <b>org.testcontainers:testcontainers</b> coordinates and
         * with version reference <b>testcontainers</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("testcontainers");
        }

        /**
         * Dependency provider for <b>elasticsearch</b> with <b>org.testcontainers:elasticsearch</b> coordinates and
         * with version reference <b>testcontainers</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getElasticsearch() {
            return create("testcontainers.elasticsearch");
        }

        /**
         * Dependency provider for <b>junit</b> with <b>org.testcontainers:junit-jupiter</b> coordinates and
         * with version reference <b>testcontainers</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJunit() {
            return create("testcontainers.junit");
        }

        /**
         * Dependency provider for <b>mssqlserver</b> with <b>org.testcontainers:mssqlserver</b> coordinates and
         * with version reference <b>testcontainers</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMssqlserver() {
            return create("testcontainers.mssqlserver");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        private final JunitVersionAccessors vaccForJunitVersionAccessors = new JunitVersionAccessors(providers, config);
        private final SpringVersionAccessors vaccForSpringVersionAccessors = new SpringVersionAccessors(providers, config);
        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>assertj</b> with value <b>3.25.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAssertj() { return getVersion("assertj"); }

        /**
         * Version alias <b>cucumber</b> with value <b>7.18.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCucumber() { return getVersion("cucumber"); }

        /**
         * Version alias <b>jackson</b> with value <b>2.17.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJackson() { return getVersion("jackson"); }

        /**
         * Version alias <b>queryDSL</b> with value <b>5.1.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getQueryDSL() { return getVersion("queryDSL"); }

        /**
         * Version alias <b>springBoot</b> with value <b>3.3.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSpringBoot() { return getVersion("springBoot"); }

        /**
         * Version alias <b>testcontainers</b> with value <b>1.20.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getTestcontainers() { return getVersion("testcontainers"); }

        /**
         * Group of versions at <b>versions.junit</b>
         */
        public JunitVersionAccessors getJunit() {
            return vaccForJunitVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.spring</b>
         */
        public SpringVersionAccessors getSpring() {
            return vaccForSpringVersionAccessors;
        }

    }

    public static class JunitVersionAccessors extends VersionFactory  {

        public JunitVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>junit.jupiter</b> with value <b>5.10.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJupiter() { return getVersion("junit.jupiter"); }

    }

    public static class SpringVersionAccessors extends VersionFactory  {

        public SpringVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>spring.kafka</b> with value <b>3.2.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKafka() { return getVersion("spring.kafka"); }

        /**
         * Version alias <b>spring.security</b> with value <b>6.3.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSecurity() { return getVersion("spring.security"); }

    }

    public static class BundleAccessors extends BundleFactory {
        private final TestcontainersBundleAccessors baccForTestcontainersBundleAccessors = new TestcontainersBundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

        /**
         * Dependency bundle provider for <b>cucumber</b> which contains the following dependencies:
         * <ul>
         *    <li>io.cucumber:cucumber-java</li>
         *    <li>io.cucumber:cucumber-spring</li>
         *    <li>io.cucumber:cucumber-junit</li>
         *    <li>io.cucumber:cucumber-junit-platform-engine</li>
         *    <li>org.junit.jupiter:junit-jupiter</li>
         *    <li>org.junit.platform:junit-platform-suite</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getCucumber() {
            return createBundle("cucumber");
        }

        /**
         * Dependency bundle provider for <b>elasticsearch</b> which contains the following dependencies:
         * <ul>
         *    <li>org.eclipse.parsson:parsson</li>
         *    <li>co.elastic.clients:elasticsearch-java</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getElasticsearch() {
            return createBundle("elasticsearch");
        }

        /**
         * Dependency bundle provider for <b>graphql</b> which contains the following dependencies:
         * <ul>
         *    <li>com.graphql-java:graphql-java</li>
         *    <li>org.springframework.boot:spring-boot-starter-graphql</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getGraphql() {
            return createBundle("graphql");
        }

        /**
         * Dependency bundle provider for <b>jackson</b> which contains the following dependencies:
         * <ul>
         *    <li>com.fasterxml.jackson.core:jackson-core</li>
         *    <li>com.fasterxml.jackson.datatype:jackson-datatype-jsr310</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getJackson() {
            return createBundle("jackson");
        }

        /**
         * Dependency bundle provider for <b>jdbc</b> which contains the following dependencies:
         * <ul>
         *    <li>org.springframework.boot:spring-boot-starter</li>
         *    <li>org.springframework.boot:spring-boot-starter-jdbc</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getJdbc() {
            return createBundle("jdbc");
        }

        /**
         * Dependency bundle provider for <b>jpa</b> which contains the following dependencies:
         * <ul>
         *    <li>org.springframework.boot:spring-boot-starter</li>
         *    <li>org.springframework.boot:spring-boot-starter-data-jpa</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getJpa() {
            return createBundle("jpa");
        }

        /**
         * Dependency bundle provider for <b>kafka</b> which contains the following dependencies:
         * <ul>
         *    <li>org.springframework.kafka:spring-kafka</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getKafka() {
            return createBundle("kafka");
        }

        /**
         * Dependency bundle provider for <b>oidc</b> which contains the following dependencies:
         * <ul>
         *    <li>org.springframework.security:spring-security-oauth2-resource-server</li>
         *    <li>org.springframework.security:spring-security-oauth2-jose</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getOidc() {
            return createBundle("oidc");
        }

        /**
         * Dependency bundle provider for <b>security</b> which contains the following dependencies:
         * <ul>
         *    <li>com.auth0:java-jwt</li>
         *    <li>org.springframework.boot:spring-boot-starter-security</li>
         *    <li>org.springframework.security:spring-security-config</li>
         *    <li>org.springframework.security:spring-security-web</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getSecurity() {
            return createBundle("security");
        }

        /**
         * Dependency bundle provider for <b>swagger</b> which contains the following dependencies:
         * <ul>
         *    <li>org.springdoc:springdoc-openapi-starter-webmvc-ui</li>
         *    <li>org.springframework.boot:spring-boot-starter-validation</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getSwagger() {
            return createBundle("swagger");
        }

        /**
         * Dependency bundle provider for <b>testing</b> which contains the following dependencies:
         * <ul>
         *    <li>org.assertj:assertj-core</li>
         *    <li>org.mockito:mockito-core</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getTesting() {
            return createBundle("testing");
        }

        /**
         * Group of bundles at <b>bundles.testcontainers</b>
         */
        public TestcontainersBundleAccessors getTestcontainers() {
            return baccForTestcontainersBundleAccessors;
        }

    }

    public static class TestcontainersBundleAccessors extends BundleFactory  implements BundleNotationSupplier{

        public TestcontainersBundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

        /**
         * Dependency bundle provider for <b>testcontainers</b> which contains the following dependencies:
         * <ul>
         *    <li>org.testcontainers:testcontainers</li>
         *    <li>org.testcontainers:junit-jupiter</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> asProvider() {
            return createBundle("testcontainers");
        }

        /**
         * Dependency bundle provider for <b>testcontainers.elasticsearch</b> which contains the following dependencies:
         * <ul>
         *    <li>org.testcontainers:testcontainers</li>
         *    <li>org.testcontainers:junit-jupiter</li>
         *    <li>org.testcontainers:elasticsearch</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getElasticsearch() {
            return createBundle("testcontainers.elasticsearch");
        }

        /**
         * Dependency bundle provider for <b>testcontainers.mssqlserver</b> which contains the following dependencies:
         * <ul>
         *    <li>org.testcontainers:testcontainers</li>
         *    <li>org.testcontainers:junit-jupiter</li>
         *    <li>org.testcontainers:mssqlserver</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getMssqlserver() {
            return createBundle("testcontainers.mssqlserver");
        }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>springBoot</b> with plugin id <b>org.springframework.boot</b> and
         * with version reference <b>springBoot</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getSpringBoot() { return createPlugin("springBoot"); }

    }

}
