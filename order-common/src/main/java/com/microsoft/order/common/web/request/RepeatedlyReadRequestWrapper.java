package com.microsoft.order.common.web.request;

import org.apache.commons.lang.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class RepeatedlyReadRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;
    private final String bodystr;

    public RepeatedlyReadRequestWrapper(HttpServletRequest request)
            throws IOException {
        super(request);
        //body = readBytes(request.getReader(), "utf-8");
        bodystr = readStr(request.getReader());
        if (StringUtils.isNotBlank(bodystr)) {
            body = bodystr.getBytes(Charset.forName("utf-8"));
        } else {
            body = null;
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() {
                return bais.read();
            }
        };
    }

    /**
     *  读取字符串
     *
     * @param br
     * @return
     * @throws IOException
     */
    private String readStr(BufferedReader br) throws IOException {
        String str, retStr = "";
        StringBuilder stringBuilder = new StringBuilder();

        while ((str = br.readLine()) != null) {
            stringBuilder.append(str);
        }
        retStr = stringBuilder.toString();
        return retStr;
    }


    public String getbodystr() {
        return bodystr;
    }
}
