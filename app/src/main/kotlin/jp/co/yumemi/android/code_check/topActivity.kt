/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TopActivity : AppCompatActivity(R.layout.top_activity) {

    companion object {
        lateinit var lastSearchDate: Date
    }
}
