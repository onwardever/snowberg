package info.yangdian.snowberg;

// TODO: 2018/12/24 异步处理用户逻辑
public abstract class Controller implements Describable
{
    public ResponseContext doGet(RequestContext request)
    {
        return execute(request);
    }

    public ResponseContext doPost(RequestContext request)
    {
        return execute(request);
    }

    public ResponseContext doPut(RequestContext request)
    {
        return execute(request);
    }

    public ResponseContext doDelete(RequestContext request)
    {
        return execute(request);
    }

    public ResponseContext doRequest(RequestContext request)
    {
        if(request.getType()== RequestContext.RequestType.GET)
            return doGet(request);

        if(request.getType()== RequestContext.RequestType.POST)
            return doPost(request);

        if(request.getType()== RequestContext.RequestType.PUT)
            return doPut(request);

        if(request.getType()== RequestContext.RequestType.DELETE)
            return doDelete(request);

        return execute(request);
    }

    public abstract ResponseContext execute(RequestContext request);
}
