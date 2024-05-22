package com.example;

import org.junit.jupiter.api.extension.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import java.util.List;

public class CalculatorTestTemplateProvider implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return Stream.of(
                invocationContext(1, 1, 2),
                invocationContext(2, 2, 4),
                invocationContext(3, 3, 6)
        );
    }

    private TestTemplateInvocationContext invocationContext(int a, int b, int expected) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return String.format("Test with %d + %d = %d", a, b, expected);
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return List.of(new ParameterResolver() {
                    @Override
                    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
                        return true;
                    }

                    @Override
                    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
                        if (parameterContext.getIndex() == 0) {
                            return a;
                        } else if (parameterContext.getIndex() == 1) {
                            return b;
                        } else {
                            return expected;
                        }
                    }
                });
            }
        };
    }
}
