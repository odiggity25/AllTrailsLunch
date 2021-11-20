package com.orrie.alltrailslunch.shared.views

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun initUi()
    protected abstract fun subscribeToViewModelObservables()
}