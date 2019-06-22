package triangle.little.potatoes.presentation.view.property.details;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import triangle.little.potatoes.R;
import triangle.little.potatoes.data.net.protocol.user.AccPriceInfoResp;

import java.util.ArrayList;
import java.util.List;

/**
 * description :
 * Created by Flynn
 * 2017/4/27
 */

public class IncomeDetailsAdapter extends BaseQuickAdapter<AccPriceInfoResp.DataBeanX.DataBean, BaseViewHolder> {


    public IncomeDetailsAdapter(List<AccPriceInfoResp.DataBeanX.DataBean> data) {
        super(R.layout.transaction_recycle_view_item, data != null ? data : new ArrayList<AccPriceInfoResp.DataBeanX.DataBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, AccPriceInfoResp.DataBeanX.DataBean item) {
        helper.setText(R.id.transaction_time, item.getRECHARGE_TIME())
                .setText(R.id.transaction_type, item.getMOBILE())
                .setText(R.id.transaction_money, item.getRECHARGE_AMOUNT());
    }
}
