package info.yangdian.snowberg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import java.nio.charset.Charset;

public class SimpleController extends Controller
{
    @Override
    public HttpResponse execute(HttpRequest request)
    {
        ByteBuf content = Unpooled.copiedBuffer("hello,world!", Charset.forName("utf-8"));

        FullHttpResponse response =
                new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);

        response.headers().set("Content-Type", "text/plain;charset=UTF-8");
        response.headers().set("Content_Length", content.readableBytes());

        return response;
    }
}
