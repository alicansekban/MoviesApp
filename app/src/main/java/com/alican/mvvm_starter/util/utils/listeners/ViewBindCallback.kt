package com.alican.mvvm_starter.util.utils.listeners

interface ViewBindCallback<Model> {
    fun itemBind(item: Model?, position: Int)
}