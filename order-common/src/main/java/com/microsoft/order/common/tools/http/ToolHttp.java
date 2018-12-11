package com.microsoft.order.common.tools.http;

import com.google.common.collect.Maps;
import com.microsoft.order.common.tools.data.text.ToolJson;
import com.microsoft.order.common.tools.format.ToolFormat;
import com.microsoft.order.common.tools.http.okhttp.ToolHttpBuilder;
import com.microsoft.order.common.tools.http.pojo.HttpException;
import com.microsoft.order.common.tools.http.pojo.HttpMethod;
import com.microsoft.order.common.tools.http.pojo.HttpReceive;
import com.microsoft.order.common.tools.http.pojo.HttpSend;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.Map;
import java.util.Set;

/**
 * Created on 17/9/3 13:57 星期日.
 *
 * @author sd
 */
@Component
public class ToolHttp {

    private static final Logger log = LoggerFactory.getLogger(ToolHttp.class);

    public HttpReceive get(String url) {
        return send(HttpSend.of(url).setMethod(HttpMethod.GET));
    }

    public HttpReceive post(String url) {
        return send(HttpSend.of(url).setMethod(HttpMethod.POST));
    }

    public HttpReceive post(String url, Map<String, Object> params) {
        return send(HttpSend.of(url, params).setMethod(HttpMethod.POST));
    }

    public HttpReceive get(String url, Map<String, Object> headers) {
        return send(HttpSend.of(url).setMethod(HttpMethod.GET).setHeaders(headers));
    }

    public HttpReceive post(String url, Map<String, Object> params, Map<String, Object> headers) {
        return send(HttpSend.of(url, params).setMethod(HttpMethod.POST).setHeaders(headers));
    }

    public HttpReceive post(String url, String jsonParams, Map<String, Object> headers) {
        return send(HttpSend.of(url, jsonParams).setMethod(HttpMethod.POST).setHeaders(headers));
    }

    public HttpReceive send(HttpSend httpSend) {
        return send(httpSend, ToolHttpBuilder.getDefaultClient());
    }

    public HttpReceive send(HttpSend httpSend, OkHttpClient.Builder okHttpClientBuilder) {
        return send(httpSend, okHttpClientBuilder.build());
    }

    public HttpReceive send(HttpSend httpSend, OkHttpClient okHttpClient) {
        final HttpReceive httpReceive = new HttpReceive();
        httpReceive.setHaveError(true);
        try {
            Request request = convertRequest(httpSend);

            //创建/Call
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            ResponseBody body = response.body();

            if (body == null) {
                throw new HttpException("发送http失败，响应体为空");
            }

            final Map<String, String> responseHeaders = Maps.newHashMap();
            if (httpSend.getNeedReceiveHeaders()) {
                final Headers headers = response.headers();
                final Set<String> headerNameSet = headers.names();
                headerNameSet.forEach(oneHeaderName -> {
                    final String oneHeaderValue = headers.get(oneHeaderName);
                    responseHeaders.put(oneHeaderName, oneHeaderValue);
                });
            }

            int responseStatusCode = response.code();
            if (responseStatusCode != 200) {
                log.error("httpcode_error:"+request.url().toString());
                throw new HttpException("本次请求响应码不是200，是" + responseStatusCode);
            }

            String responseBody = body.string();
            if (log.isDebugEnabled()) {
                log.debug(responseBody);
            }
            httpReceive.setResponseBody(responseBody)
                    .setHaveError(false)
                    .setStatusCode(responseStatusCode)
                    .setStatusText(responseStatusCode + "")
                    .setResponseHeader(responseHeaders)
            ;

            response.close();
            okHttpClient.dispatcher().executorService().shutdown();
        } catch (IOException e) {
            httpReceive.setErrMsg("获取返回内容失败:" + e.getMessage())
                    .setThrowable(e)
            ;
        } catch (HttpException e) {
            httpReceive.setErrMsg(e.getMessage())
                    .setThrowable(e)
            ;
        }

        if (httpReceive.getHaveError()) {
            if (log.isWarnEnabled()) {
                Throwable throwable = httpReceive.getThrowable();
                log.warn(ToolFormat.toException(throwable), throwable);
            }
        }

        httpReceive.setIsDone(true);

        return httpReceive;
    }

    private Request convertRequest(HttpSend httpSend) {
        final Request.Builder requestBuilder = new Request.Builder();
        final FormBody.Builder formBodyBuilder = new FormBody.Builder();

        final Map<String, Object> params = httpSend.getParams();
        final String url = httpSend.getUrl();
        final Map<String, Object> headers = httpSend.getHeaders();
        final String jsonParams = httpSend.getJsonParams();

        if (headers != null) {
            headers.forEach((key, value) -> {
                if (value instanceof AbstractCollection
                        || value instanceof Map
                        || value instanceof Number
                        || value instanceof String) {
                    requestBuilder.addHeader(key, value.toString());
                } else {
                    requestBuilder.addHeader(key, ToolJson.modelToJson(value));
                }
            });
        }

        if (jsonParams != null) {
            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
            return requestBuilder.url(url).method(httpSend.getMethod().name(), requestBody).build();
        } else {
            if (params != null) {
                params.forEach((key, value) -> {
                    String v;
                    if (value instanceof AbstractCollection
                            || value instanceof Map
                            || value instanceof Number
                            || value instanceof String) {
                        v = value.toString();
                    } else {
                        v = ToolJson.modelToJson(value);
                    }
                    formBodyBuilder.add(key, v);
                });
            }
        }

        final FormBody requestBody = formBodyBuilder.build();

        if (httpSend.getMethod() == HttpMethod.GET) {
            return requestBuilder.url(url).build();
        } else {
            return requestBuilder.url(url).method(httpSend.getMethod().name(), requestBody).build();
        }
    }
}
