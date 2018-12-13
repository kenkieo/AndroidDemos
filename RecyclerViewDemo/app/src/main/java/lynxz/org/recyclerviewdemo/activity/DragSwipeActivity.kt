package lynxz.org.recyclerviewdemo.activity

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import cn.soundbus.sdx.showToast
import kotlinx.android.synthetic.main.activity_rv.*
import lynxz.org.recyclerviewdemo.OnRecyclerItemClickListener
import lynxz.org.recyclerviewdemo.R
import lynxz.org.recyclerviewdemo.adapter.RvAdapter
import java.util.*


/**
 * Created by Lynxz on 2016/11/18.
 * description : 拖拽-滑动 item 示例
 */
class DragSwipeActivity : BaseActivity() {
    override fun getLayoutRes() = R.layout.activity_rv

    override fun afterCreate() {
        val data = (1..10).map { "pos $it" }.toMutableList()
        //        data.map { logi("$it") }
        rv_main.layoutManager = GridLayoutManager(this, 3)
        rv_main.adapter = RvAdapter(this, data)

        // 添加点击事件
        val clickListener: OnRecyclerItemClickListener = object : OnRecyclerItemClickListener(rv_main) {
            override fun <T : RecyclerView.ViewHolder> onItemClick(vh: T) {
                if (vh is RvAdapter.VHolder) {
                    showToast("${vh.tv.text}")
                }
            }

            override fun <T : RecyclerView.ViewHolder> onItemLongClick(vh: T) {
            }
        }

        rv_main.removeOnItemTouchListener(clickListener)
        rv_main.addOnItemTouchListener(clickListener)

        // 添加滑动/拖拽功能
        // java的匿名内部类对应过来就是object对象表达式了
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            /**
             * 设置itemView可以移动的方向
             * */
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                // 拖拽的标记，这里允许上下左右四个方向
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or
                        ItemTouchHelper.RIGHT
                // 滑动的标记，这里允许左右滑动
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            /**
             * 当一个Item被另外的Item替代时回调,也就是数据集的内容顺序改变
             * 返回true, onMoved()才会进行
             * */
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            /**
             *  当onMove返回true的时候回调,刷新列表
             * */
            override fun onMoved(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, fromPos: Int, target: RecyclerView.ViewHolder, toPos: Int, x: Int, y: Int) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
                // 移动完成后修改列表位置并刷新列表
                if (fromPos < toPos) {
                    for (i in fromPos..toPos - 1) {
                        Collections.swap(data, i, i + 1)
                    }
                } else {
                    for (i in fromPos downTo toPos + 1) {
                        Collections.swap(data, i, i - 1)
                    }
                }
                rv_main.adapter?.notifyItemMoved(viewHolder!!.adapterPosition, target!!.adapterPosition)
            }

            /**
             * 滑动完成时回调,这里设置为滑动删除,删除相应数据后刷新列表
             * */
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                data.removeAt(viewHolder.adapterPosition)
                rv_main.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                showToast("删除成功")
            }

            /**
             * Item是否可以滑动
             * */
            override fun isItemViewSwipeEnabled() = true

            /**
             * Item是否可以长按,默认返回true,所有itemView都可以拖拽
             * 如果需要自定义拖拽的item,则可以返回false,然后手动调用 startDrag(ViewHolder) 即可
             * */
            override fun isLongPressDragEnabled() = true

        }).attachToRecyclerView(rv_main)
    }
}