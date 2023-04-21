package gov.cdc.nbs.containers.database;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.images.PullPolicy;


class NbsTestDatabaseInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(final ConfigurableApplicationContext context) {
        NbsDatabaseContainer container =
                Holder.initialize(context.getEnvironment().getProperty("testing.database.image"));

        TestPropertyValues.of("spring.datasource.url=" + container.url())
                .applyTo(context.getEnvironment());

    }

    private final static class Holder {

        @SuppressWarnings({"resource"})
        private static NbsDatabaseContainer initialize(final String image) {
            NbsDatabaseContainer container = new NbsDatabaseContainer()
                    .withImagePullPolicy(PullPolicy.defaultPolicy());

            container.start();
            return container;
        }

    }
}
