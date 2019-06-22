package triangle.little.potatoes.presentation.view.product.search;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.protocol.game.GameDataResp;
import triangle.little.potatoes.presentation.utils.GlideUtils;

import java.util.ArrayList;

/**
 * 产品搜索》热门产品adapter
 * Created by dell on 2017/4/26.
 */

public class ProductSearchHotAdapter extends BaseQuickAdapter<GameDataResp.DataBean.HotGameBean, BaseViewHolder> {

    public ProductSearchHotAdapter() {
        super(R.layout.product_search_hot_item_layout, new ArrayList<GameDataResp.DataBean.HotGameBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, GameDataResp.DataBean.HotGameBean item) {
        GlideUtils.loadImageViewLoading(mContext, item.getTITTLEIMG_URL(), (ImageView) helper.getView(R.id.game_logo_img),
                R.drawable.ic_product_action_game_loading, R.drawable.ic_product_action_game_loading);
        helper.setText(R.id.game_name_tv, item.getNAME());
    }
}
