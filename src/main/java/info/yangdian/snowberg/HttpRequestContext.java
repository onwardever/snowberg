package info.yangdian.snowberg;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HttpRequestContext
{
    private RequestType type;
    private String uri;
    private String rawPath;
    private String path;
    private String rawQuery;
    private Map<String, List<String>> parameters;
    private String content;

    public HttpRequestContext() {}

    public HttpRequestContext(FullHttpRequest request)
    {
        this.type = RequestType.valueOf(request.method());
        this.uri = request.uri();
        QueryStringDecoder decoder = new QueryStringDecoder(this.uri);
        this.rawPath = decoder.rawPath();
        this.path = decoder.path();
        this.rawQuery = decoder.rawQuery();
        this.parameters = decoder.parameters();
        this.content = getStringValue(request.content());
    }

    public RequestType getType()
    {
        return type;
    }

    public void setType(RequestType type)
    {
        this.type = type;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getRawPath()
    {
        return rawPath;
    }

    public void setRawPath(String rawPath)
    {
        this.rawPath = rawPath;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getRawQuery()
    {
        return rawQuery;
    }

    public void setRawQuery(String rawQuery)
    {
        this.rawQuery = rawQuery;
    }

    public Map<String, List<String>> getParameters()
    {
        return parameters;
    }

    public void setParameters(Map<String, List<String>> parameters)
    {
        this.parameters = parameters;
    }

    public void addParameter(String key, String value)
    {
        this.parameters.put(key, Arrays.asList(value.split(",")));
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    private String getStringValue(ByteBuf byteBuf)
    {
        byte[] bytes = new byte[byteBuf.readableBytes()];

        byteBuf.getBytes(byteBuf.readerIndex(), bytes);

        return new String(bytes);
    }

    public enum RequestType
    {
        GET,
        POST,
        PUT,
        DELETE,
        OTHER;

        public static RequestType valueOf(HttpMethod method)
        {
            if(method==HttpMethod.GET)
                return GET;

            if(method==HttpMethod.POST)
                return POST;

            if(method==HttpMethod.PUT)
                return PUT;

            if(method==HttpMethod.DELETE)
                return DELETE;

            return OTHER;
        }
    }

    public static void main(String[] args)
    {

    }
}
