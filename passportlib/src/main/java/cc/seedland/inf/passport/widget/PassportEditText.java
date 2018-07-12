package cc.seedland.inf.passport.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;

import cc.seedland.inf.passport.R;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/07/06 10:50
 * 描述 ：
 **/
public class PassportEditText extends AppCompatEditText {

    private boolean emoji;
    private boolean character;

    public PassportEditText(Context context) {
        this(context, null);
    }

    public PassportEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PassportEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PassportEditText);
        if(a.hasValue(R.styleable.PassportEditText_filters)) {
            int filters = a.getInt(R.styleable.PassportEditText_filters, 0);
            emoji = (filters & 0x01) == 0x01;
            character = (filters & 0x02) == 0x02;
        }
        a.recycle();

        if(emoji || character) {
            setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        }
    }

    private class EmojiExcludeFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if(Character.isWhitespace(c)) {
                    return "";
                }
                int type = Character.getType(c);
                if(emoji) {
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                        return "";
                    }
                }
                if(character) {
                    if(type == Character.OTHER_LETTER) {
                        return "";
                    }
                }
            }
            return null;
        }

        /**
         * 判定输入汉字
         * @param c
         * @return
         */
        public  boolean isChinese(char c) {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
            if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                    || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                    || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                    || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                return true;
            }
            return false;
        }
    }

}
