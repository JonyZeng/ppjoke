package com.nice.ppjoke.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.nice.ppjoke.R;
import com.nice.ppjoke.model.BottomBar;
import com.nice.ppjoke.model.Destination;
import com.nice.ppjoke.utils.AppConfig;

import java.util.List;

public class AppBottomBar extends BottomNavigationView {
    private static int[] sIcons = new int[]{R.drawable.icon_tab_home, R.drawable.icon_tab_sofa, R.drawable.icon_tab_publish, R.drawable.icon_tab_find, R.drawable.icon_tab_mine};

    private final BottomBar mBottomBar;
    private static final String TAG = "AppBottomBar";

    public AppBottomBar(Context context) {
        this(context, null);
    }

    public AppBottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("RestrictedApi")
    public AppBottomBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //mBottomBar获取
        mBottomBar = AppConfig.getBottomBar();
        //获取tabs对象
        List<BottomBar.Tab> tabs = mBottomBar.tabs;
        //定义二维数组，表示选中状态和颜色
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{};
        //选中和未选中颜色数组
        int[] colors = new int[]{Color.parseColor(mBottomBar.activeColor), Color.parseColor(mBottomBar.inActiveColor)};
        //返回从颜色到状态的指定映射
        ColorStateList colorStateList = new ColorStateList(states, colors);
        setItemIconTintList(colorStateList);
        setItemTextColor(colorStateList);
        setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        setSelectedItemId(mBottomBar.selectTab);

        for (int i = 0; i < tabs.size(); i++) {
            BottomBar.Tab tab = tabs.get(i);
            Log.i(TAG, "AppBottomBar: " + tab);
            if (!tab.enable)
                return;
            int id = getId(tab.pageUrl);
            Log.i(TAG, "AppBottomBar: id=" + id);
            if (id < 0) return;
            MenuItem menuItem = getMenu().add(0, id, tab.index, tab.title);
            menuItem.setIcon(sIcons[tab.index]);

        }
        //按钮设置大小
        for (int i = 0; i < tabs.size(); i++) {
            BottomBar.Tab tab = tabs.get(i);
            int iconSize = dp2px(tab.size);
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) getChildAt(0);
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
            itemView.setIconSize(iconSize);
            if (TextUtils.isEmpty(tab.title)) {
                int tintColor = TextUtils.isEmpty(tab.tintColor) ? Color.parseColor("#ff678f") : Color.parseColor(tab.tintColor);
                itemView.setIconTintList(ColorStateList.valueOf(tintColor));
                //禁止掉点按时 上下浮动的效果
                itemView.setShifting(false);
                /**
                 * 如果想要禁止掉所有按钮的点击浮动效果。
                 * 那么还需要给选中和未选中的按钮配置一样大小的字号。
                 *
                 *  在MainActivity布局的AppBottomBar标签增加如下配置，
                 *  @style/active，@style/inActive 在style.xml中
                 *  app:itemTextAppearanceActive="@style/active"
                 *  app:itemTextAppearanceInactive="@style/inActive"
                 */
            }
        }
        if (mBottomBar.selectTab!=0){

        }
    }

    private int dp2px(int size) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return (int) (metrics.density * size + 0.5f);
    }


    //通过pageUrl获取menuId
    private int getId(String pageUrl) {
        Destination destination = AppConfig.getDestConfig().get(pageUrl);
        if (destination == null) {
            return -1;
        }
        return destination.id;
    }
}
