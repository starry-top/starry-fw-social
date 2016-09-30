package com.github.starry.fw.social.autoconfigure.wechat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

public class WechatVerifyView extends AbstractView {

    private String token = null;

    public WechatVerifyView(String token) {
        this.token = token;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String signature = (String)request.getParameter("signature");
        String timestamp = (String)request.getParameter("timestamp");
        String nonce =(String)request.getParameter("nonce");
        String echostr =(String)request.getParameter("echostr");

        List<String> list = new ArrayList<String>();

        list.add(token);
        list.add(timestamp);
        list.add(nonce);

        Collections.sort(list);

        String sha1 = DigestUtils.sha1Hex(StringUtils.join(list.toArray(new String[]{})));

        if(StringUtils.equals(signature, sha1)) {
            response.getWriter().write(echostr);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }

}
