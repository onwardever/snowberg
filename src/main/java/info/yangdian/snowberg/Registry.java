package info.yangdian.snowberg;

import io.netty.handler.codec.http.HttpRequest;

public class Registry
{
    public Controller find(HttpRequest request)
    {
        return new SimpleController();
    }
}
