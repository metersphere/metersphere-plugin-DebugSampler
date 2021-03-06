package io.metersphere.plugin.DebugSampler;

import com.alibaba.fastjson.JSON;
import io.metersphere.plugin.DebugSampler.sampler.MsDebugSampler;
import io.metersphere.plugin.DebugSampler.sampler.SelectParams;
import io.metersphere.plugin.core.api.UiScriptApi;
import io.metersphere.plugin.core.ui.PluginResource;
import io.metersphere.plugin.core.ui.UiScript;
import io.metersphere.plugin.core.utils.LogUtil;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class UiScriptApiImpl extends UiScriptApi {

    public boolean xpack() {
        return false;
    }

    @Override
    public PluginResource init() {
        LogUtil.info("开始初始化脚本内容 ");
        List<UiScript> uiScripts = new LinkedList<>();
        String script = getJson("/json/ui.json");
        UiScript uiScript = new UiScript("DebugSampler", "调试请求", MsDebugSampler.class.getCanonicalName(), script);
        uiScript.setJmeterClazz("AbstractSampler");
        // 添加可选参数
        uiScript.setFormOption(getJson("/json/ui_form.json"));

        uiScripts.add(uiScript);
        LogUtil.info("初始化脚本内容结束 ");
        return new PluginResource("DebugSampler-v1.0.1", uiScripts);
    }

    @Override
    public String customMethod(String req) {
        LogUtil.info("进入自定义方法 ,开始写自己的逻辑吧");
        List<SelectParams> list = new LinkedList<>();
        SelectParams argsParams = new SelectParams();
        argsParams.setLabel("Test");
        argsParams.setValue("Test");
        list.add(argsParams);
        return JSON.toJSONString(list);
    }

    public String getJson(String path) {
        try {
            InputStream in = UiScriptApiImpl.class.getResourceAsStream(path);
            String json = org.apache.commons.io.IOUtils.toString(in);
            return json;
        } catch (Exception ex) {
            LogUtil.error(ex.getMessage());
        }
        return null;
    }

    /**
     * 测试用
     *
     * @return
     */
    public static String test() {
        return "success";
    }
}