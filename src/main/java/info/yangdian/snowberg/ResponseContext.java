package info.yangdian.snowberg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import java.nio.charset.Charset;

public class ResponseContext
{
    private DefaultFullHttpResponse response;

    public ResponseContext(HttpVersion version, HttpResponseStatus status)
    {
        response = new DefaultFullHttpResponse(version, status);
    }

    public ResponseContext(HttpResponseStatus status)
    {
        this(HttpVersion.HTTP_1_1, status);
    }

    public ResponseContext()
    {
        this(HttpResponseStatus.OK);
    }

    public HttpHeaders headers()
    {
        return this.response.headers();
    }

    HttpResponse getResponse()
    {
        return this.response;
    }

    public ResponseContext setContent(String content)
    {
        ByteBuf byteBuf = Unpooled.copiedBuffer(content, Charset.forName("utf-8"));
        this.response = (DefaultFullHttpResponse) (this.response.replace(byteBuf));
        return this;
    }
}

