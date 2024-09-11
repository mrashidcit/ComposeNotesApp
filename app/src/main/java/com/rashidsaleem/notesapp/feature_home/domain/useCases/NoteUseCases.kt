package com.rashidsaleem.notesapp.feature_home.domain.useCases

data class NoteUseCases(
    val getAll: GetAllNoteUseCase,
    val listen: ListenNoteUseCase,
    val add: AddNoteUseCase,
    val update: UpdateNoteUseCase,
) {

    companion object {

        private var _instance: NoteUseCases? = null

        fun getInstance(): NoteUseCases {
            if (_instance == null) {
                _instance = NoteUseCases(
                    getAll = GetAllNoteUseCase(),
                    listen = ListenNoteUseCase(),
                    add = AddNoteUseCase(),
                    update = UpdateNoteUseCase(),
                )
            }

            return _instance!!
        }

    }
}
