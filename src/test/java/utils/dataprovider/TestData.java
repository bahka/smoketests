package utils.dataprovider;

import org.testng.annotations.DataProvider;

public class TestData {

    @DataProvider
    public Object[][] incorrectTokens() {
        return new Object[][]{
                { null },
                { "" },
                { "sometoken" }
        };
    }
}
