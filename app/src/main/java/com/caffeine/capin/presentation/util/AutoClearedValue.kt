package com.caffeine.capin.presentation.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import java.lang.IllegalStateException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class AutoClearedValue<T: Any>: ReadWriteProperty<Fragment, T>, LifecycleObserver {
    private var _value:T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
        _value ?: throw  IllegalStateException("AutoClearedValue is not available")


    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        thisRef.viewLifecycleOwner.lifecycle.removeObserver(this)
        _value = value
        thisRef.viewLifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        _value = null
    }
}