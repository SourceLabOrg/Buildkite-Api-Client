/**
 * Copyright 2023 SourceLab.org https://github.com/SourceLabOrg/Buildkite-Api-Client
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.sourcelab.buildkite.api.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class TestTokenLoader {
    private static final String ENV_NAME = "TEST_TOKEN";

    public String getTestToken() {
        Optional<String> token = loadFromEnv();
        if (token.isPresent()) {
            return token.get();
        }

        token = loadFromProperties("test.env");
        if (token.isPresent()) {
            return token.get();
        }

        token = loadFromProperties("test.properties");
        if (token.isPresent()) {
            return token.get();
        }
        throw new RuntimeException("Unable to find token to run integration tests!");
    }

    private Optional<String> loadFromProperties(final String filename)
    {
        try (final InputStream inputStream = TestTokenLoader.class.getClassLoader().getResourceAsStream(filename)) {
            if (inputStream == null) {
                return Optional.empty();
            }

            // Look in test.env file
            final Properties properties = new Properties();
            properties.load(inputStream);

            if (properties.containsKey(ENV_NAME)) {
                final Object token = properties.get(ENV_NAME);
                if (token instanceof String && ((String) token).trim().length() > 0) {
                    return Optional.of((String) ((String) token).trim());
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return Optional.empty();
    }

    private Optional<String> loadFromEnv()
    {
        // Look in environment.
        final String envToken = System.getenv(ENV_NAME);
        if (envToken != null && envToken.trim().length() > 0) {
            return Optional.of(envToken.trim());
        }
        return Optional.empty();
    }
}
