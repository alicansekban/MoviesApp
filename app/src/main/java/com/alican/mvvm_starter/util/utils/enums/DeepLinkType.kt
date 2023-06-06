package com.alican.mvvm_starter.util.utils.enums

enum class DeepLinkType(val id: Int) {
    PRODUCT(1),
    BRAND(2);

    companion object {
        fun findById(id: Int): DeepLinkType? = values().find { it.id == id }
    }
}