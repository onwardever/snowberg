package info.yangdian.snowberg;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;

public abstract class Controller
{
    public HttpResponse doGet(FullHttpRequest request)
    {
        return execute(request);
    }

    public HttpResponse doPost(FullHttpRequest request)
    {
        return execute(request);
    }

    public HttpResponse doPut(FullHttpRequest request)
    {
        return execute(request);
    }

    public HttpResponse doDelete(FullHttpRequest request)
    {
        return execute(request);
    }

    public HttpResponse doRequest(FullHttpRequest request)
    {
        if(request.method()==HttpMethod.GET)
            return doGet(request);

        if(request.method()==HttpMethod.POST)
            return doPost(request);

        if(request.method()==HttpMethod.PUT)
            return doPut(request);

        if(request.method()==HttpMethod.DELETE)
            return doDelete(request);

        return execute(request);
    }

    public abstract HttpResponse execute(FullHttpRequest request);
}
