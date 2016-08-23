package com.demo.fuzhucheng.colourfulFontOrNeonTextView;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.demo.fuzhucheng.R;
import com.demo.fuzhucheng.colourfulFontOrNeonTextView.SpannableStringFont;

public class ColourfulFontOrNeonAcitivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colourful_font_or_neon_acitivity);
        initView();
    }

    private void initView() {
        TextView textView = (TextView) findViewById(R.id.tv_content);
        String content = textView.getText().toString();
        textView.setText(SpannableStringFont.changeFont(ColourfulFontOrNeonAcitivity.this, content));
        textView.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
