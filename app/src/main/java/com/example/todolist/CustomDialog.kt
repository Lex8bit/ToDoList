package com.example.todolist

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText

class CustomDialog(var activity: MainActivity, private val isNewItem: Boolean): Dialog(activity), View.OnClickListener {

    private lateinit var okButton : Button
    private lateinit var cancelButton : Button
    private lateinit var inputFieldTitle : EditText
    private lateinit var inputFieldDescription : EditText
    private lateinit var inputFieldNumber : EditText


    /**
    Для контроля размера диалогового окна
     */
    private fun dialogSizeControl() {
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(this.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        this.window?.attributes = lp
    }
    private fun initViews() {
        okButton = findViewById(R.id.dialog_ok_button)
        cancelButton = findViewById(R.id.dialog_cancel_button)
        okButton.setOnClickListener(this)
        cancelButton.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_template)
        inputFieldTitle = findViewById(R.id.dialog_input_title)
        inputFieldDescription = findViewById(R.id.dialog_input_descriptions)
        inputFieldNumber = findViewById(R.id.dialog_input_number)
        if (isNewItem){
            createNewItem()
        }else{
            updateExistingItem()
        }
        dialogSizeControl()
        initViews()
    }

    private fun updateExistingItem() {
        Log.d("testlog","updateExistingItem() has been cold")
    }

    private fun createNewItem() {
        Log.d("testlog","createNewItem() has been cold")
    }

    override fun onClick(view: View) {
        /**ШАГ 2. Отправляем данные в БД
         * 2.1 Вытаскиваем данные из полей ввода*/
        when (view.id){
            R.id.dialog_ok_button -> {
                val inputTitleResult = inputFieldTitle.text.toString()
                val inputDescriptionResult = inputFieldDescription.text.toString()
                val inputNumberResult = inputFieldNumber.text.toString().toIntOrNull()?: 0
                activity.addItem(ItemsViewModel(0,inputTitleResult,inputDescriptionResult,inputNumberResult))
                dismiss()
            }
            R.id.dialog_cancel_button -> dismiss()
            else ->{
            }
        }
        dismiss()//Закрытие диалогового окна
    }

}