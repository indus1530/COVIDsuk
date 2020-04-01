package edu.aku.hassannaqvi.COVIDsuk.viewmodel

import android.content.Context
import android.content.Intent
import edu.aku.hassannaqvi.COVIDsuk.contracts.FamilyMembersContract
import edu.aku.hassannaqvi.COVIDsuk.core.MainApp
import edu.aku.hassannaqvi.COVIDsuk.ui.sections.SectionK2Activity
import kotlinx.coroutines.*

class MainRepository(val context: Context, val item: MutableList<FamilyMembersContract>) {

    init {

        val result = GlobalScope.async { populateList() }

        runBlocking {
            context.startActivity(Intent(context, SectionK2Activity::class.java))
        }

    }

    private suspend fun populateList() = withContext(Dispatchers.IO) {
        MainApp.mwraChildrenAnthro = Triple(item.map { it.serialno.toInt() }, item.map { it.name }, item.map { it })
    }
}