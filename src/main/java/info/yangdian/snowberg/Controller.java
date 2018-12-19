package info.yangdian.snowberg;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public abstract class Controller
{
    public abstract HttpResponse execute(FullHttpRequest request);
}
