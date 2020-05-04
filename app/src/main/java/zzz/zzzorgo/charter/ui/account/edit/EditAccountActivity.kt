package zzz.zzzorgo.charter.ui.account.edit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import zzz.zzzorgo.charter.R

class EditAccountActivity : AppCompatActivity() {

    private lateinit var editAccountNameField: EditText
    private lateinit var editAccountTotalField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)

        editAccountNameField = findViewById(R.id.edit_account_name_field)
        editAccountTotalField = findViewById(R.id.edit_account_total_field)
        val saveButton = findViewById<Button>(R.id.save_account_button)

        saveButton.setOnClickListener {
            val replyIntent = Intent()

            if (!TextUtils.isEmpty(editAccountNameField.text)) {
                val name = editAccountNameField.text.toString()
                val total = editAccountTotalField.text.toString().toInt()
                replyIntent.putExtra(EXTRA_NAME, name)
                replyIntent.putExtra(EXTRA_TOTAL, total)
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_NAME = "zzz.zzzorgo.charter.editAccount.NAME"
        const val EXTRA_TOTAL = "zzz.zzzorgo.charter.editAccount.TOTAL"
    }
}
