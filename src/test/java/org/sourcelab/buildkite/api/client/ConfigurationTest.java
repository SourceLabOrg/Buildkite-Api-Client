package org.sourcelab.buildkite.api.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    /**
     * Sanity check that we can create an instance with minimum required properties.
     */
    @Test
    void sanityCheck() {
        final ConfigurationBuilder builder = Configuration.newBuilder();
        assertNotNull(builder);

        // Set minimum required properties.
        builder
            .withApiToken("MyFakeToken");

        final Configuration configuration = builder.build();
        assertNotNull(configuration);
        assertEquals("MyFakeToken", configuration.getApiToken());
    }
}