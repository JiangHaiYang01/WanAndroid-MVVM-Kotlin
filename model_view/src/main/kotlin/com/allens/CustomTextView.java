package com.allens;

import android.graphics.Color;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.BaseMovementMethod;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class CustomTextView {


    private static final String TAG = "CustomTextView";

    public Builder create() {
        return new Builder();
    }

    private OnClickMsgListener listener1;

    public class Builder {

        private StringBuilder builder;
        private List<ColorConfig> configList;


        private Builder() {
            builder = new StringBuilder();
            configList = new ArrayList<>();
        }

        public Builder addSection(@NonNull String section) {
            builder.append(section);
            ColorConfig colorConfig = new ColorConfig();
            colorConfig.setMsg(section);
            configList.add(colorConfig);
            return this;
        }

        public Builder addSection(@NonNull String section, int color) {
            return configColor(color, section);
        }


        private Builder configColor(int color, String section) {
            ColorConfig colorConfig = new ColorConfig();
            colorConfig.setColor(color);
            int length = builder.toString().length();
            colorConfig.setStart(length);
            colorConfig.setEnd(length + section.length());
            colorConfig.setMsg(section);
            configList.add(colorConfig);
            builder.append(section);
            return this;
        }


        public void init(TextView textView) {
            SpannableStringBuilder style = new SpannableStringBuilder(builder.toString());
            for (ColorConfig colorConfig : configList) {
                if (TextUtils.isEmpty(colorConfig.getMsg())) {
                    continue;
                }
                style.setSpan(new ForegroundColorSpan(colorConfig.getColor()), colorConfig.getStart(), colorConfig.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            builder = null;
            configList = null;
            listener1 = null;
            textView.setText(style);
        }


        public void init(TextView textView, OnClickMsgListener listener) {
            listener1 = listener;

            SpannableString str = new SpannableString(builder.toString());
            int startIndex = 0;

            int index = 0;
            for (ColorConfig colorConfig : configList) {
                String msg = colorConfig.getMsg();
                if (TextUtils.isEmpty(msg)) {
                    startIndex += msg.length();
                    continue;
                }
                Log.e(TAG, "start: " + startIndex + " end:" + (startIndex + msg.length())
                        + "\n"
                        + " msg1 " + builder.toString().substring(startIndex, (startIndex + msg.length()))
                        + "\n"
                        + " msg2:" + msg);
                str.setSpan(new MyClickText(msg, colorConfig.getColor(), index), startIndex, startIndex + msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                startIndex += msg.length();
                index++;
            }
            textView.setText(str);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setMovementMethod(new MovementMethod(android.R.color.transparent, android.R.color.transparent));

        }
    }


    public interface OnClickMsgListener {
        void onClick(String msg, int index);
    }

    private static class ColorConfig {
        private int start;

        private int end;

        private int color = Color.BLACK;

        private String msg;

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }


    private class MyClickText extends ClickableSpan {

        private String msg;

        private int color;

        private int index;

        private MyClickText(String msg) {
            this.msg = msg;
        }

        private MyClickText(String msg, int color) {
            this.msg = msg;
            this.color = color;
        }

        private MyClickText(String msg, int color, int index) {
            this.msg = msg;
            this.color = color;
            this.index = index;
        }


        @Override
        public void updateDrawState(@NotNull TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(color);
            ds.setUnderlineText(false);

        }

        @Override
        public void onClick(@NotNull View widget) {
            if (listener1 != null) {
                listener1.onClick(msg,index);
            }
        }
    }


    private class MovementMethod extends BaseMovementMethod {
        public final String TAG = MovementMethod.class.getSimpleName();
        /**
         * 整个textView的背景色
         */
        private int textViewBgColor;
        /**
         * 点击部分文字时部分文字的背景色
         */
        private int clickableSpanBgColor;
        private BackgroundColorSpan mBgSpan;
        private ClickableSpan[] mClickLinks;
        /**
         * true：响应textView的点击事件， false：响应设置的clickableSpan事件
         */
        private boolean isPassToTv = true;

        public boolean isPassToTv() {
            return isPassToTv;
        }

        private void setPassToTv(boolean isPassToTv) {
            this.isPassToTv = isPassToTv;
        }

        /**
         * @param clickableSpanBgColor 点击选中部分时的背景色
         * @param textViewBgColor      整个textView点击时的背景色
         */
        private MovementMethod(int clickableSpanBgColor, int textViewBgColor) {
            this.textViewBgColor = textViewBgColor;
            this.clickableSpanBgColor = clickableSpanBgColor;
        }

        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {

            // 这些代码 直接从LinkMovementMethod 拿过来就可以了
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();
                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                mClickLinks = buffer.getSpans(off, off, ClickableSpan.class);
                if (mClickLinks.length > 0) {
                    // 点击的是Span区域，不要把点击事件传递
                    setPassToTv(false);
                    Selection.setSelection(buffer, buffer.getSpanStart(mClickLinks[0]), buffer.getSpanEnd(mClickLinks[0]));
                    // 设置点击区域的背景色
                    mBgSpan = new BackgroundColorSpan(clickableSpanBgColor);
                    buffer.setSpan(mBgSpan, buffer.getSpanStart(mClickLinks[0]), buffer.getSpanEnd(mClickLinks[0]), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    setPassToTv(true);
                    // textView选中效果
                    widget.setBackgroundColor(textViewBgColor);
                }

            } else if (action == MotionEvent.ACTION_UP) {
                if (mClickLinks.length > 0) {
                    mClickLinks[0].onClick(widget);
                    if (mBgSpan != null) {// 移除点击时设置的背景span
                        buffer.removeSpan(mBgSpan);
                    }
                }
                Selection.removeSelection(buffer);
                widget.setBackgroundResource(android.R.color.transparent);
            } else {
                if (mBgSpan != null) {// 移除点击时设置的背景span
                    buffer.removeSpan(mBgSpan);
                }
                widget.setBackgroundResource(android.R.color.transparent);
            }
            return super.onTouchEvent(widget, buffer, event);
        }
    }
}
