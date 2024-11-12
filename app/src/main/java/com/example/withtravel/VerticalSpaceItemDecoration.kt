package com.example.withtravel

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// MyPage에 리사이클러뷰 아이템들 사이에 공간을 주는 코드
class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int, private val horizontalSpaceWidth: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = horizontalSpaceWidth  // 왼쪽 간격
        outRect.right = horizontalSpaceWidth // 오른쪽 간격
        outRect.bottom = verticalSpaceHeight // 아래 간격

        // 첫 번째 아이템에 대해서는 상단 간격을 추가
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = verticalSpaceHeight
        }
    }
}