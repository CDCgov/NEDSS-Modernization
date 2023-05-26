package gov.cdc.nbs.questionbank.container;



import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.images.PullPolicy;


class NbsTestDatabaseInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    @SuppressWarnings({"resource"}) // We don't want to close the container
    public void initialize(final ConfigurableApplicationContext context) {
        final String image = context.getEnvironment().getProperty("testing.database.image");
        NbsDatabaseContainer container = new NbsDatabaseContainer(image)
                .withImagePullPolicy(PullPolicy.defaultPolicy());

        container.start();

        TestPropertyValues.of("spring.datasource.url=" + container.url())
                .applyTo(context.getEnvironment());

    }
}
