package com.example.todolist

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.viewmodel.viewModelFactory

class CustomDialog(var activity: MainActivity, private val isNewItem: Boolean, private val item: ItemsViewModel?): Dialog(activity), View.OnClickListener {

    private lateinit var okButton : Button
    private lateinit var cancelButton : Button
    private lateinit var inputFieldTitle : EditText
    private lateinit var inputFieldDescription : EditText
    private lateinit var inputFieldNumber : EditText
    private lateinit var dialogLable : TextView


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
        dialogLable = findViewById(R.id.dialog_lable)
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
        dialogLable.text = "Update item"
        inputFieldTitle.setText(item?.title)
        inputFieldDescription.setText(item?.description)
        inputFieldNumber.setText(item?.number.toString())

    }
    private fun createNewItem() {
        Log.d("testlog","createNewItem() has been cold")

        //Доставать данные из SharedPreferences
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val titilefromPrefs = sharedPref.getString("titleKey","")
        val descriptionfromPrefs = sharedPref.getString("descriptionKey","")
        val numberfromPrefs = sharedPref.getString("numberKey","")
        Log.d("prefstesting","clickData $titilefromPrefs, $descriptionfromPrefs, $numberfromPrefs")

        inputFieldTitle.setText(titilefromPrefs)
        inputFieldDescription.setText(descriptionfromPrefs)
        inputFieldNumber.setText(numberfromPrefs.toString())
    }

    override fun onClick(view: View) {
        if (isNewItem){
            when(view){
                okButton -> okNewItemBeenClicked(view)
                cancelButton -> dismiss()
            }
            //okNewItemBeenClicked(view)
        }else{
            when(view){
                okButton -> okUpdateItemBeenClicked(view)
                cancelButton -> dismiss()
            }
           // okUpdateItemBeenClicked(view)
        }
        dismiss()//Закрытие диалогового окна
    }

    private fun okUpdateItemBeenClicked(view: View) {

        val inputTitleResult = inputFieldTitle.text.toString()
        val inputDescriptionResult = inputFieldDescription.text.toString()
        val inputNumberResult = inputFieldNumber.text.toString().toIntOrNull() ?: 0
        activity.updateItem(
            ItemsViewModel(
                item?.id ?: 0,
                inputTitleResult,
                inputDescriptionResult,
                inputNumberResult
            )
        )

    }

    /**ШАГ 2. Отправляем данные в БД
     * 2.1 Вытаскиваем данные из полей ввода*/
    private fun okNewItemBeenClicked(view: View) {

        val inputTitleResult = inputFieldTitle.text.toString()
        val inputDescriptionResult = inputFieldDescription.text.toString()
        val inputNumberResult = inputFieldNumber.text.toString().toIntOrNull() ?: 0
        activity.addItem(
            ItemsViewModel(
                0,
                inputTitleResult,
                inputDescriptionResult,
                inputNumberResult
            )
        )
        // Для SharedPref чтобы когда нажимали на Ок у нас очищались поля
        inputFieldTitle.text.clear()
        inputFieldDescription.text.clear()
        inputFieldNumber.text.clear()
    }

    //SharedPreferences записываем данные когда экран не активен
    override fun onStop() {
        super.onStop()
        if (isNewItem) {
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            Log.d("prefstesting", "onStop has been called")
            with(sharedPref.edit()) {
                val inputTitleResult = inputFieldTitle.text.toString()
                val inputDescriptionResult = inputFieldDescription.text.toString()
                val inputNumberResult = inputFieldNumber.text.toString()
                putString("titleKey", inputTitleResult)
                putString("descriptionKey", inputDescriptionResult)
                putString("numberKey", inputNumberResult)
                apply()
                Log.d("prefstesting", "sharedPref has been applyed")
            }
        }
    }
}