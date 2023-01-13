package com.haier.uhome.h5container.bean;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/9 16:58
 */
public class UrlParams {
    private boolean adShow;
    private String adText;
    private String adButton;
    private boolean adShowClose = true;
    private String adAction;
    private String adUrl;
    private boolean isShowTitle;
    private String pageTitle;
    //是否使用沉浸式状态栏
    private boolean openWitchTranslucentStatusBar;
    //是否使用深色状态栏样式，默认是深色
    private boolean darkMode = true;

    public UrlParams() {
    }

    public boolean isAdShow() {
        return this.adShow;
    }

    public void setAdShow(boolean adShow) {
        this.adShow = adShow;
    }

    public String getAdText() {
        return this.adText;
    }

    public void setAdText(String adText) {
        this.adText = adText;
    }

    public String getAdButton() {
        return this.adButton;
    }

    public void setAdButton(String adButton) {
        this.adButton = adButton;
    }

    public boolean isAdShowClose() {
        return this.adShowClose;
    }

    public void setAdShowClose(boolean adShowClose) {
        this.adShowClose = adShowClose;
    }

    public String getAdAction() {
        return this.adAction;
    }

    public void setAdAction(String adAction) {
        this.adAction = adAction;
    }

    public String getAdUrl() {
        return this.adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public boolean isShowTitle() {
        return this.isShowTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.isShowTitle = showTitle;
    }

    public String toString() {
        return "UrlParams{adShow=" + this.adShow + ", adText='" + this.adText + '\'' + ", adButton='" + this.adButton + '\'' + ", adShowClose=" + this.adShowClose + ", adAction='" + this.adAction + '\'' + ", adUrl='" + this.adUrl + '\'' + ", isShowTitle=" + this.isShowTitle + '}';
    }

    public String getPageTitle() {
        return this.pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public boolean isOpenWitchTranslucentStatusBar() {
        return openWitchTranslucentStatusBar;
    }

    public void setOpenWitchTranslucentStatusBar(boolean openWitchTranslucentStatusBar) {
        this.openWitchTranslucentStatusBar = openWitchTranslucentStatusBar;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }
}
