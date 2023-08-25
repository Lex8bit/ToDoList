package com.example.todolist

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemClick {

    private val mainViewModel : MainViewModel by viewModels()

    private lateinit var stubContainer : LinearLayout
    private lateinit var fab : FloatingActionButton
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : CustomAdapter
    private lateinit var data: List<ItemsViewModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        swipeImplementation()
        observers()

        fab.setOnClickListener() {
            val dialogFragment = CustomDialog( true, null)
            dialogFragment.show(supportFragmentManager, getString(R.string.custom_dialog))
        }

        mainViewModel.getAllData()
    }

    private fun observers() {
        mainViewModel.todoItemListResult.observe(this, Observer {
            data = it
            adapter.updateList(it)
            screenDataValidation(it)
        })
    }

    private fun swipeImplementation() {
        /**НАЧАЛО инициализация переменных для СВАЙПА с красным фоном 1*/
        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete_white_24)
        val intrinsicWidth = deleteIcon?.intrinsicWidth
        val intrinsicHeight = deleteIcon?.intrinsicHeight
        val background = ColorDrawable()
        val backgroundColor = Color.parseColor(getString(R.string.red))
        val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }
        /**КОНЕЦ инициализации переменных для СВАЙПА с красным фоном 1*/

        /**Начало Удаление по свайпу без фона 1 */
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }
            /**Конец Удаление по свайпу без фона 1*/

            /**Начало Удаление по СВАЙПУ С КРАСНЫМ ФОНОМ 2*/
            // Let's draw our delete view
            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top
                val isCanceled = dX == 0f && !isCurrentlyActive   //1
                if (isCanceled) {
                    clearCanvas(
                        canvas,
                        itemView.right + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat()
                    )
                    super.onChildDraw(
                        canvas,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                    return
                } //2

                // Draw the red delete background
                background.color = backgroundColor
                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                background.draw(canvas)

                // Calculate position of delete icon
                val iconTop = itemView.top + (itemHeight - intrinsicHeight!!) / 2
                val iconMargin = (itemHeight - intrinsicHeight) / 2
                val iconLeft = itemView.right - iconMargin - intrinsicWidth!!
                val iconRight = itemView.right - iconMargin
                val iconBottom = iconTop + intrinsicHeight

                // Draw the delete icon
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                deleteIcon.draw(canvas)

                super.onChildDraw(
                    canvas,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                ) //3
            }

            private fun clearCanvas(
                c: Canvas?,
                left: Float,
                top: Float,
                right: Float,
                bottom: Float
            ) {
                c?.drawRect(left, top, right, bottom, clearPaint)
            }   //4
            /**КОНЕЦ Удаление по СВАЙПУ С КРАСНЫМ ФОНОМ 2*/

            /**Начало Удаление по свайпу без фона 2 */
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                val deletedToDoItem: ItemsViewModel =
                    data.get(viewHolder.adapterPosition)

                // below line is to get the position
                // of the item at that position.
                val position = viewHolder.adapterPosition

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                //  data.toMutableList().removeAt(position)    //не нужно, тк наш лист при каждом изменении обновляется сам из ЛАйфДата

                // below line is to notify our item is removed from adapter.
                adapter.notifyItemRemoved(position)

                mainViewModel.deleteItem(deletedToDoItem)
                /**ТУТ УДАЛЯЕМ ЯЧЕЙКУ*/
                // below line is to display our snackbar with action.
                // below line is to display our snackbar with action.
                // below line is to display our snackbar with action.
                Snackbar.make(
                    recyclerView,
                    "Deleted " + deletedToDoItem.title,
                    Snackbar.LENGTH_LONG
                )
                    .setAction(
                        "Undo",
                        View.OnClickListener {
                            // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            mainViewModel.insertItem(deletedToDoItem)
                            //data.toMutableList().add(position, deletedCourse) - строчка которая была до этого. не работала, тк мы добавляли в лист
                            // below line is to notify item is
                            // added to our adapter class.
                            adapter.notifyItemInserted(position)
                        }).show()
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(recyclerView)
        /**Конец Удаление по свайпу без фона 2 */
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recyclerview)
        stubContainer = findViewById(R.id.main_no_items_container)
        fab = findViewById(R.id.main_fab)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CustomAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter
    }

    private fun screenDataValidation(list: List<ItemsViewModel>) {
        if (list.isNullOrEmpty()){
            setupStub(showStub = true, showRecycler = false)
        }else{
            setupStub(showStub = false, showRecycler = true)
        }
    }
    private fun setupStub (showStub:Boolean, showRecycler: Boolean){
        stubContainer.isVisible = showStub
        recyclerView.isVisible = showRecycler
    }

    override fun itemClicked(item: ItemsViewModel) {
        val dialogFragment = CustomDialog(false, item)
        dialogFragment.show(supportFragmentManager, getString(R.string.custom_dialog))
    }
}
