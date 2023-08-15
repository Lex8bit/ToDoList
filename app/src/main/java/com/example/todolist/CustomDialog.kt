package com.example.todolist

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

class CustomDialog(var activity: MainActivity, private val isNewItem: Boolean, private val item: ItemsViewModel?): DialogFragment(), View.OnClickListener {

    private val mCustomDialogViewModel : CustomDialogViewModel by activityViewModels()


    private lateinit var okButton : Button
    private lateinit var cancelButton : Button
    private lateinit var inputFieldTitle : EditText
    private lateinit var inputFieldDescription : EditText
    private lateinit var inputFieldNumber : EditText
    private lateinit var dialogLable : TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.dialog_template,container,false)

        initViews(view)

        if (isNewItem){
            createNewItem()
        }else{
            updateExistingItem()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        dialogSizeControl()
        mCustomDialogViewModel.todoItemResult.observe(this, Observer {
            if (isNewItem){
                inputFieldTitle.setText(it.title)
                inputFieldDescription.setText(it.description)
                inputFieldNumber.setText(it.number.toString())
            }
        })
    }

    /**
    Для контроля размера диалогового окна
     */
    private fun dialogSizeControl() {
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ActionBar.LayoutParams.MATCH_PARENT
        params.height = ActionBar.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as LayoutParams
    }
    private fun initViews(view: View) {
        inputFieldTitle = view.findViewById(R.id.dialog_input_title)
        inputFieldDescription = view.findViewById(R.id.dialog_input_descriptions)
        inputFieldNumber = view.findViewById(R.id.dialog_input_number)
        dialogLable = view.findViewById(R.id.dialog_lable)

        okButton = view.findViewById(R.id.dialog_ok_button)
        cancelButton = view.findViewById(R.id.dialog_cancel_button)
        okButton.setOnClickListener(this)
        cancelButton.setOnClickListener(this)
    }

    private fun updateExistingItem() {
        Log.d("testlog","updateExistingItem() has been cold")
        dialogLable.text = "Update item"
        inputFieldTitle.setText(item?.title)
        inputFieldDescription.setText(item?.description)
        inputFieldNumber.setText(item?.number.toString())

    }
    private fun createNewItem() {
        mCustomDialogViewModel.getToDoItemFromPrefs()
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
            val inputTitleResult = inputFieldTitle.text.toString()
            val inputDescriptionResult = inputFieldDescription.text.toString()
            val inputNumberResult = inputFieldNumber.text.toString()
            mCustomDialogViewModel.saveDataInPrefs("titleKey",inputTitleResult)
            mCustomDialogViewModel.saveDataInPrefs("descriptionKey",inputDescriptionResult)
            mCustomDialogViewModel.saveDataInPrefs("numberKey",inputNumberResult)
        }
    }
}