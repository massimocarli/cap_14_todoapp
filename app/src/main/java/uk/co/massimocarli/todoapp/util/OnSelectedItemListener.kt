package uk.co.massimocarli.todoapp.util

interface OnSelectedItemListener<Item> {
    fun onSelected(item: Item, isLongClick: Boolean = false)
}