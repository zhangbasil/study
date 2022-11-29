package com.zhangbin.tool.consul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:bin.zhang@itiaoling.com">zhangbin</a>
 */
public class ConsulDemo {


    public static void main(String[] args) {

        //http://10.238.237.130:8500/ui/dc1/services

        ConsulClient client = new ConsulClient("10.238.237.130", 8500);
//        client.setKVValue("name/demo","zhangsan");
        //取出k-v
//        config/magnus-application dummy-hk/configuration/return.notice.returnRemoteTemplateUrlPrefix
//        String key = "config/magnus-application dummy-hk/configuration/return.notice.returnRemoteTemplateUrlPrefix";
        Response<GetValue> kvValue = client.getKVValue("config/magnus-application,dummy-hk/configuration");
        Yaml yaml = new Yaml();
        String decodedValue = kvValue.getValue().getDecodedValue();
        Map<String, Object> map = yaml.load(decodedValue);
        System.out.println("map = " + map);
        String key = "return.notice.returnUrl";

        for (String item : key.split("\\.")) {
            Object o = map.get(item);
        }
    }

    private Object getValue(String key, Map<String, Object> valueMap) {
        Object value = valueMap.get(key);
        if (value instanceof Map) {
            return ((Map) value).get(key);
        } else if (value instanceof List) {
            ((List) value).forEach(item ->{

            });
        }
        return null;
    }


}
