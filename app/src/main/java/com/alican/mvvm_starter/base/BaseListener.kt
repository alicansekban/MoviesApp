
package com.alican.mvvm_starter.base

class BaseListener {
    interface PagingAdapterListener {
        fun itemClicked(inboxItem: Nothing)

        fun itemLongClicked(inboxItem: Nothing)
    }
}