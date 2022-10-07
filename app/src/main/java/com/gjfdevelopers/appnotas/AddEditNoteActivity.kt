package com.gjfdevelopers.appnotas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    // na linha abaixo, estamos criando variáveis para nossos componentes de interface do usuário.
    lateinit var noteTitleEdt: EditText
    lateinit var noteEdt: EditText
    lateinit var saveBtn: Button

    // na linha abaixo, estamos criando uma variável para viewmodal e integer para nosso ID de nota.
    lateinit var viewModal: NoteViewModal
    var noteID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        // na linha abaixo estamos iniciando nosso modal de visualização.
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModal::class.java)
        //  na linha abaixo estamos inicializando todas as nossas variáveis.
        noteTitleEdt = findViewById(R.id.idEdtNoteName)
        noteEdt = findViewById(R.id.idEdtNoteDesc)
        saveBtn = findViewById(R.id.idBtn)

        // na linha abaixo, estamos recebendo dados transmitidos por meio de uma intenção.
        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            //  na linha abaixo estamos definindo dados para editar o texto.
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteId", -1)
            saveBtn.setText("Atualizar Nota")
            noteTitleEdt.setText(noteTitle)
            noteEdt.setText(noteDescription)
        } else {
            saveBtn.setText("Salvar")
        }

        // na linha abaixo, estamos adicionando o click listner ao nosso botão salvar.
        saveBtn.setOnClickListener {
            //  na linha abaixo, estamos obtendo o título e a descrição do texto de edição.
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteEdt.text.toString()
            //  na linha abaixo estamos verificando o tipo e salvando ou atualizando os dados.
            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedNote = Note(noteTitle, noteDescription, currentDateAndTime)
                    updatedNote.id = noteID
                    viewModal.updateNote(updatedNote)
                    Toast.makeText(this, "Atualizado", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    // se a string não estiver vazia, estamos chamando um método add note para adicionar dados ao nosso banco de dados da sala.
                    viewModal.addNote(Note(noteTitle, noteDescription, currentDateAndTime))
                    Toast.makeText(this, "$noteTitle Adicionado", Toast.LENGTH_LONG).show()
                }
            }
            // abrindo a nova atividade na linha abaixo
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}