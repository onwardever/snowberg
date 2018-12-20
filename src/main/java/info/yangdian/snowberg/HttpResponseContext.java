package info.yangdian.snowberg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import java.nio.charset.Charset;

public class HttpResponseContext
{
    private DefaultFullHttpResponse response;

    public HttpResponseContext(HttpVersion version, HttpResponseStatus status)
    {
        response = new DefaultFullHttpResponse(version, status);
    }

    public HttpResponseContext(HttpResponseStatus status)
    {
        this(HttpVersion.HTTP_1_1, status);
    }

    public HttpResponseContext()
    {
        this(HttpResponseStatus.OK);
    }

    public HttpHeaders headers()
    {
        return this.response.headers();
    }

    public HttpResponseContext setContent(String content)
    {
        ByteBuf byteBuf = Unpooled.copiedBuffer(content, Charset.forName("utf-8"));
        this.response.replace(byteBuf);
        return this;
    }
}

