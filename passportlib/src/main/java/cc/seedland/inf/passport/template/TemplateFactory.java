package cc.seedland.inf.passport.template;

import java.util.HashSet;
import java.util.Set;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.template.hachired.HachiRedTemplate;
import cc.seedland.inf.passport.template.seedblue.SeedBlueTemplate;
import cc.seedland.inf.passport.util.Constant;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 10:56
 * 描述 ：
 **/
public class TemplateFactory {

    private static final Set<String> SUPPORTED_TEMPLATES = new HashSet<>();
    private static final String TEMPLATE_SEED_BLUE = "seedblue";
    private static final String TEMPLATE_HACHI_RED = "hachired";
    private static ITemplate sTemplate;

    static {
        SUPPORTED_TEMPLATES.add(TEMPLATE_SEED_BLUE); // 实地蓝
        SUPPORTED_TEMPLATES.add(TEMPLATE_HACHI_RED); // 哈奇红
    }

    public static ITemplate getTemplate() {
        if(sTemplate == null) {
            String name = Constant.getString(R.string.template);
            if(TEMPLATE_SEED_BLUE.equalsIgnoreCase(name)) {
                sTemplate = new SeedBlueTemplate();
            }else if(TEMPLATE_HACHI_RED.equalsIgnoreCase(name)) {
                sTemplate = new HachiRedTemplate();
            }
            sTemplate = new SeedBlueTemplate();
        }
        return sTemplate;
    }
}
