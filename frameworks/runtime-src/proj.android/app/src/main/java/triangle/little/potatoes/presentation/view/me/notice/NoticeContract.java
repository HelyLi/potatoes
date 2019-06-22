package triangle.little.potatoes.presentation.view.me.notice;

import triangle.little.potatoes.data.net.protocol.user.NoticeResp;
import triangle.little.potatoes.presentation.view.BasePresenter;
import triangle.little.potatoes.presentation.view.BaseView;

import java.util.List;

/**
 * 通知contract
 * Created by dell on 2017/5/2.
 */

public interface NoticeContract {
    interface Presenter extends BasePresenter {
        void getNotice();
    }

    interface View extends BaseView<Presenter> {
        void getNoticeSuccess(List<NoticeResp.DataBean.PagerBean.ListBean> list, int totalPages);

        void getNoticeFailure(String msg);
    }
}
