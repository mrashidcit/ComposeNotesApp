package com.rashidsaleem.notesapp.core.data.local

class FakeNotesDao(
    val items: ArrayList<NoteEntity>
): NotesDao {
    override fun getAll(): List<NoteEntity> {
        return items
    }

    override fun insertItem(item: NoteEntity): Long {
        item.id?.let { itemId ->
            val itemIndex = items.indexOfFirst { it.id == itemId }

            if (itemIndex == -1) return@let

            items[itemIndex] = item

            return itemId.toLong()
        }

        val newItemId = items.size + 1
        val updatedItem = item.copy(
            id = newItemId
        )
        items.add(updatedItem)
        return newItemId.toLong()
    }

    override fun getItem(id: Int): NoteEntity? {
        val itemIndex = items.indexOfFirst { it.id == id }
        if (itemIndex == -1) return null
        return items.get(itemIndex)
    }

    override fun updateItem(item: NoteEntity) {
        val itemIndex = items.indexOfFirst { it.id == item.id }
        if (itemIndex == -1) return
        items[itemIndex] = item
    }

    override fun deleteItem(id: Int) {
        val itemIndex = items.indexOfFirst { it.id == id }
        if (itemIndex == -1) return
        items.removeAt(itemIndex)
    }
}