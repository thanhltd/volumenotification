/*
 * Copyright (C) 2017 Seht (R) Hyx Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.hyx.app.volumenotification.dialog;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.hyx.app.volumenotification.R;
import net.hyx.app.volumenotification.model.SettingsModel;

public class PrefCustomThemeDialog extends DialogPreference {

    private SettingsModel settings;

    private EditText backgroundColorInput;
    private EditText iconColorInput;

    public PrefCustomThemeDialog(Context context, AttributeSet attrs) {
        super(context, attrs);

        settings = new SettingsModel(context);

        setDialogLayoutResource(R.layout.view_dialog_pref_custom_theme);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        backgroundColorInput = (EditText) view.findViewById(R.id.pref_custom_theme_background_color);
        iconColorInput = (EditText) view.findViewById(R.id.pref_custom_theme_icon_color);
        backgroundColorInput.setText(settings.getCustomThemeBackgroundColor());
        iconColorInput.setText(settings.getCustomThemeIconColor());
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            String backgroundColorValue = backgroundColorInput.getText().toString();
            String iconColorValue = iconColorInput.getText().toString();
            int backgroundColor = settings.getColor(backgroundColorValue);
            int iconColor = settings.getColor(iconColorValue);

            if (backgroundColor != 0) {
                settings.edit().putString("pref_custom_theme_background_color", backgroundColorValue).commit();
            }
            if (iconColor != 0) {
                settings.edit().putString("pref_custom_theme_icon_color", iconColorValue).commit();
            }
            if (backgroundColor == 0 || iconColor == 0) {
                Toast.makeText(getContext(), settings.resources.getString(R.string.pref_custom_theme_color_error_message), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
