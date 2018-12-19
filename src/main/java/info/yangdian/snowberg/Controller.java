package info.yangdian.snowberg;

import io.netty.handler.codec.http.HttpResponse;

public abstract class Controller
{
    public HttpResponse doGet(HttpRequestContext request)
    {
        return execute(request);
    }

    public HttpResponse doPost(HttpRequestContext request)
    {
        return execute(request);
    }

    public HttpResponse doPut(HttpRequestContext request)
    {
        return execute(request);
    }

    public HttpResponse doDelete(HttpRequestContext request)
    {
        return execute(request);
    }

    public HttpResponse doRequest(HttpRequestContext request)
    {
        if(request.getType()== HttpRequestContext.RequestType.GET)
            return doGet(request);

        if(request.getType()== HttpRequestContext.RequestType.POST)
            return doPost(request);

        if(request.getType()== HttpRequestContext.RequestType.PUT)
            return doPut(request);

        if(request.getType()== HttpRequestContext.RequestType.DELETE)
            return doDelete(request);

        return execute(request);
    }

    public abstract HttpResponse execute(HttpRequestContext request);
}
