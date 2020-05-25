package zzz.zzzorgo.charter.data.repo

import androidx.lifecycle.LiveData
import zzz.zzzorgo.charter.data.dao.SettingsDao
import zzz.zzzorgo.charter.data.model.Settings
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val settingsDao: SettingsDao) {
    val settings: LiveData<Settings> = settingsDao.getSettings()
}
