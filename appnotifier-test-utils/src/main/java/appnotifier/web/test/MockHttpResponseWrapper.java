package appnotifier.web.test;

import java.util.List;

import org.jboss.resteasy.mock.MockHttpResponse;

public class MockHttpResponseWrapper<T> {

    private T returnedParsedContent;

    private List<T> returnedParsedContentList;

    private MockHttpResponse response;

    public MockHttpResponseWrapper(T returnedParsedContent, List<T> returnedParsedContentList, MockHttpResponse response) {
        super();
        this.returnedParsedContent = returnedParsedContent;
        this.returnedParsedContentList = returnedParsedContentList;
        this.response = response;
    }

    public Object getReturnedParsedContent() {
        return returnedParsedContent;
    }

    public MockHttpResponse getResponse() {
        return response;
    }

    public List<T> getReturnedParsedContentList() {
        return returnedParsedContentList;
    }
}
