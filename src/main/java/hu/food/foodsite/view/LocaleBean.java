package hu.food.foodsite.view;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@ViewScoped
@Named(LocaleBean.BEAN_NAME)
public class LocaleBean implements Serializable {

    protected static final String BEAN_NAME = "localeBean";

    private String selectedLang;

    public Cookie getCookieByName(String cookieName) {
        Cookie retCookie = null;
        Cookie[] cookies = getServletRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    retCookie = cookie;
                }
            }
        }
        return retCookie;
    }

    public boolean checkLangaugeDialogVisibility() {
        Cookie localeCookie = getCookieByName("locale");
        boolean ret = false;
        if (localeCookie == null) {
            ret = true;
        }
        return ret;
    }

    private HttpServletRequest getServletRequest() {
        FacesContext context = FacesContext.getCurrentInstance();
        return (HttpServletRequest) context.getExternalContext().getRequest();
    }

    public void saveLocale() {
        String locale = getSelectedLang();
        if (locale != null) {
            Map<String, Object> cookieProps = new HashMap<>();
            cookieProps.put("maxAge", 365 * 24 * 60 * 60);
            cookieProps.put("path", "/");
            FacesContext.getCurrentInstance().getExternalContext().addResponseCookie("locale", locale, cookieProps);
        }
    }

    public String getSelectedLang() {
        return selectedLang;
    }

    public void setSelectedLang(String selectedLang) {
        this.selectedLang = selectedLang;
    }

    public String getMessageByKey(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages.Messages");
        return bundle.getString(key);
    }

}
