package com.greig.consumerapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_REMINDER = "extra_reminder"
    }

    private lateinit var alarmReminder: AlarmReminder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        if (sharedPreference.getBoolean(EXTRA_REMINDER, false)){
            alarmSwitch.isChecked = true
        }

        alarmReminder = AlarmReminder()

        alarmSwitch.setOnCheckedChangeListener { _ , isChecked ->
            if (isChecked) {
                val editor = sharedPreference.edit()
                editor.putBoolean(EXTRA_REMINDER, true)
                editor.apply()
                alarmReminder.setRepeatingAlarm(
                    this,
                    AlarmReminder.TYPE_REPEATING,
                    "09:00",
                    resources.getString(R.string.alarm_settings))
            } else {
                val editor = sharedPreference.edit()
                alarmReminder.cancelAlarm(this, AlarmReminder.TYPE_REPEATING)
                editor.clear()
                editor.apply()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}