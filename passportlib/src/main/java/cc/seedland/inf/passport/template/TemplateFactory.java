package cc.seedland.inf.passport.template;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.template.def.DefaultTemplate;
import cc.seedland.inf.passport.template.hachi.HachiTemplate;
import cc.seedland.inf.passport.util.Constant;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 10:56
 * 描述 ：
 **/
public class TemplateFactory {

    private static final String TEMPLATE_DEFAULT = "default";
    private static final String TEMPLATE_HACHI = "hachi";
    private static ITemplate sTemplate;

    public static ITemplate getTemplate() {
        if(sTemplate == null) {
            String name = Constant.getString(R.string.template);
            if(TEMPLATE_DEFAULT.equalsIgnoreCase(name)) {
                sTemplate = new DefaultTemplate();
            }else if(TEMPLATE_HACHI.equalsIgnoreCase(name)) {
                sTemplate = new HachiTemplate();
            }else {
                sTemplate = new DefaultTemplate();
            }
        }
        return sTemplate;
    }
}
