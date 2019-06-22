package triangle.little.potatoes.presentation.view.activity;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.protocol.user.NoticeResp;
import triangle.little.potatoes.presentation.utils.DateUtils;
import triangle.little.potatoes.presentation.utils.GlideUtils;

import java.util.Date;

/**
 * 合作活动adapter
 * Created by dell on 2017/4/15.
 */

public class CollaborateActivityAdapter extends BaseQuickAdapter<NoticeResp.DataBean.PagerBean.ListBean, BaseViewHolder> {

    public CollaborateActivityAdapter() {
        super(R.layout.collaborate_activity_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeResp.DataBean.PagerBean.ListBean item) {
        helper.setText(R.id.title_tv, item.getTitle());
        helper.setText(R.id.date_tv, DateUtils.format(new Date(item.getCreatorTime())));
        ImageView imageView = helper.getView(R.id.content_img);
        GlideUtils.loadImageDoNoAni(imageView.getContext(), item.getImageUrl(), imageView
                , R.drawable.ic_activity_loading, R.drawable.ic_activity_loading);
        //        helper.getView(R.id.state_tv).setSelected(1 == item.getStatus());
        //        if (1 == item.getStatus()) {
        //            helper.setText(R.id.state_tv, R.string.activity_in_progress);
        //        } else {
        //            helper.setText(R.id.state_tv, R.string.activity_finished);
        //        }
    }
}
