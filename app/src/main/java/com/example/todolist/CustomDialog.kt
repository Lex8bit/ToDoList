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
import com.example.todolist.data.PrefsManagerImpl.Companion.PREFS_DESCRIPTION_KEY
import com.example.todolist.data.PrefsManagerImpl.Companion.PREFS_TITLE_KEY

class CustomDialog(
    private val isNewItem: Boolean,
    private val item: ItemsViewModel?) : DialogFragment(), View.OnClickListener {

    private val customDialogViewModel : CustomDialogViewModel by activityViewModels()
    private val mainViewModel : MainViewModel by activityViewModels()


    private lateinit var okButton : Button
    private lateinit var cancelButton : Button
    private lateinit var inputFieldTitle : EditText
    private lateinit var inputFieldDescription : EditText
    private lateinit var dialogLabel : TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.dialog_template,container,false)

        initViews(view)

        if (isNewItem){
            customDialogViewModel.getToDoItemFromPrefs()
        }else{
            dialogLabel.text = getString(R.string.update_item)
            inputFieldTitle.setText(item?.title)
            inputFieldDescription.setText(item?.description)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        dialogSizeControl()
        observers()
    }

    private fun observers() {
        customDialogViewModel.todoItemResult.observe(this, Observer {
            if (isNewItem){
                inputFieldTitle.setText(it.title)
                inputFieldDescription.setText(it.description)
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
        dialogLabel = view.findViewById(R.id.dialog_lable)
        okButton = view.findViewById(R.id.dialog_ok_button)
        cancelButton = view.findViewById(R.id.dialog_cancel_button)
        okButton.setOnClickListener(this)
        cancelButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (isNewItem){
            when(view){
                okButton -> okNewItemBeenClicked(view)
                cancelButton -> dismiss()
            }
        }else{
            when(view){
                okButton -> okUpdateItemBeenClicked(view)
                cancelButton -> dismiss()
            }
        }
        dismiss()//Закрытие диалогового окна
    }

    private fun okUpdateItemBeenClicked(view: View) {

        val inputTitleResult = inputFieldTitle.text.toString()
        val inputDescriptionResult = inputFieldDescription.text.toString()
        mainViewModel.updateItem(
            ItemsViewModel(
                item?.id ?: 0,
                inputTitleResult,
                inputDescriptionResult
            )
        )

    }

    private fun okNewItemBeenClicked(view: View) {

        val inputTitleResult = inputFieldTitle.text.toString()
        val inputDescriptionResult = inputFieldDescription.text.toString()
        mainViewModel.insertItem(
            ItemsViewModel(
                0,
                inputTitleResult,
                inputDescriptionResult
            )
        )
        /**Зачищаем поля для Prefs, чтобы после добавления они были пустыми*/
        inputFieldTitle.text.clear()
        inputFieldDescription.text.clear()
    }
    override fun onStop() {
        super.onStop()
        if (isNewItem) {
            val inputTitleResult = inputFieldTitle.text.toString()
            val inputDescriptionResult = inputFieldDescription.text.toString()
            customDialogViewModel.saveDataInPrefs(PREFS_TITLE_KEY,inputTitleResult)
            customDialogViewModel.saveDataInPrefs(PREFS_DESCRIPTION_KEY,inputDescriptionResult)
        }
    }
}