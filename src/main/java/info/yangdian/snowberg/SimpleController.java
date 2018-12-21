package info.yangdian.snowberg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import java.nio.charset.Charset;

public class SimpleController extends Controller
{
    @Override
    public String desc()
    {
        return "a simple demo";
    }

    @Override
    public HttpResponse execute(HttpRequestContext request)
    {
        ByteBuf content = Unpooled.copiedBuffer("hello,world!", Charset.forName("utf-8"));

        FullHttpResponse response =
                new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);

        response.headers().set("Content-Type", "text/plain;charset=UTF-8");
        response.headers().set("Content_Length", content.readableBytes());
        response.headers().set("Server", "snowberg/1.0");

        return response;
    }
}
