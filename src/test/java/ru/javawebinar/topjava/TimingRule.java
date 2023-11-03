package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TimingRule implements TestRule {
    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long startTime = System.currentTimeMillis();
                base.evaluate();
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                System.out.println("Test '" + description.getMethodName() + "' took " + executionTime + " ms");
            }
        };
    }
}
