package triangle.little.potatoes.presentation.view.home;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import triangle.little.potatoes.R;

import java.util.ArrayList;
import java.util.List;

/**
 * description :
 * Created by Flynn
 * 2017/4/15
 */

public class HomeAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {


    public HomeAdapter(List<HomeItem> data) {
        super(R.layout.home_recycle_view_item, data != null ? data : new ArrayList<HomeItem>());
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeItem item) {
        baseViewHolder.setImageResource(R.id.home_image, item.getIconIdRes());
        baseViewHolder.setText(R.id.home_name, item.getStringCnIdRes());
        baseViewHolder.setText(R.id.home_name_en, item.getStringEnIdRes());
    }
}
