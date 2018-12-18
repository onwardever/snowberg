package info.yangdian.snowberg;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public abstract class Controller
{
    public abstract HttpResponse execute(HttpRequest request);
}
