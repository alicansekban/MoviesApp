package com.alican.mvvm_starter.util.utils.enums

enum class AdvertisementType(val id: Int) {
    PRODUCT(1),
    BRAND(2),
    ADVERTISEMENT_DETAIL(3),
    WEBVIEW_URL(4),
    BROWSER_URL(5);

    companion object {
        fun findById(id: Int): AdvertisementType? = values().find { it.id == id }
    }
}