/*
 * Copyright 2019 The Android Open Source Project
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

package io.material.catalog.bottomsheet;

import io.material.catalog.R;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.internal.ViewUtils;

import io.material.catalog.feature.DemoFragment;
import io.material.catalog.windowpreferences.WindowPreferencesManager;

/**
 * A fragment that displays the a BottomSheet demo with vertical scrollable content for the Catalog
 * app.
 */
public class BottomSheetScrollableContentDemoFragment extends DemoFragment {
  @Override
  public View onCreateDemoView(
      LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
    View view = layoutInflater.inflate(getDemoContent(), viewGroup, false /* attachToRoot */);
    View button = view.findViewById(R.id.bottomsheet_button);
    button.setOnClickListener(v -> new BottomSheet().show(getParentFragmentManager(), ""));
    return view;
  }

  @LayoutRes
  protected int getDemoContent() {
    return R.layout.cat_bottomsheet_scrollable_content_fragment;
  }

  /**
   * A custom bottom sheet dialog fragment.
   */
  @SuppressWarnings("RestrictTo")
  public static class BottomSheet extends BottomSheetDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      // Set up BottomSheetDialog
      BottomSheetDialog bottomSheetDialog =
          new BottomSheetDialog(
              getContext(), R.style.ThemeOverlay_Catalog_BottomSheetDialog_Scrollable);
      //这个很强大，一句话就是沉浸全屏了
      new WindowPreferencesManager(requireContext()).applyEdgeToEdgePreference(bottomSheetDialog.getWindow());

      bottomSheetDialog.setContentView(R.layout.cat_bottomsheet_scrollable_content);
      View bottomSheetInternal = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);

      /**
       * 这个是默认展示的高度，因为没有show所以不展示
       * BottomSheetMainDemoFragment 里面的view就是直接展示出来了，因为毕竟add上了才能被我们看见ma
       *
       * 其次这个属性受到   app:behavior_hideable="true" 影响，就是允许这个最小之后还是否允许向下滑动
       * 针对view就是最小展示高度
       *
       * 这个属性也会影响dialog
       */
      BottomSheetBehavior<View> sheetBehavior = BottomSheetBehavior.from(bottomSheetInternal);
      sheetBehavior.setPeekHeight(400);

//      sheetBehavior.setHideable(false);

      //这个就是简单的
      View bottomSheetContent = bottomSheetInternal.findViewById(R.id.bottom_drawer_2);
      ViewUtils.doOnApplyWindowInsets(bottomSheetContent, (v, insets, initialPadding) -> {
        // Add the inset in the inner NestedScrollView instead to make the edge-to-edge behavior
        // consistent - i.e., the extra padding will only show at the bottom of all content, i.e.,
        // only when you can no longer scroll down to show more content.
        bottomSheetContent.setPaddingRelative(
            initialPadding.start,
            initialPadding.top,
            initialPadding.end,
            initialPadding.bottom + insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
        return insets;
      });
      return bottomSheetDialog;
    }
  }
}
