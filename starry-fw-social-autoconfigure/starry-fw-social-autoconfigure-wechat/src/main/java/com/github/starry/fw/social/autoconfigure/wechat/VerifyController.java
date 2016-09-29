package com.github.starry.fw.social.autoconfigure.wechat;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;

@Controller
@RequestMapping("/verify")
public class VerifyController  {

    private String viewPath = "verify/";

    /**
     * Render the status of the connections to the service provider to the user as HTML in their web browser.
     * @param providerId the ID of the provider to show connection status
     * @param request the request
     * @param model the model
     * @return the view name of the connection status page for all providers
     */
    @RequestMapping(value="/{providerId}", method=RequestMethod.GET)
    public String connectionStatus(@PathVariable String providerId, NativeWebRequest request, Model model) {
        setNoCache(request);
        return verifyView(providerId);
    }

    private void setNoCache(NativeWebRequest request) {
        HttpServletResponse response = request.getNativeResponse(HttpServletResponse.class);
        if (response != null) {
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 1L);
            response.setHeader("Cache-Control", "no-cache");
            response.addHeader("Cache-Control", "no-store");
        }
    }

    /**
     * Sets the path to connection status views.
     * Prepended to provider-specific views (e.g., "connect/facebookConnected") to create the complete view name.
     * Defaults to "connect/".
     * @param viewPath The path to connection status views.
     */
    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    private String getViewPath() {
        return viewPath;
    }

    protected String verifyView(String providerId) {
        return getViewPath() + providerId;
    }

}
