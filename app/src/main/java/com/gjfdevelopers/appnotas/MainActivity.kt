package com.gjfdevelopers.appnotas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {

    // na linha abaixo estamos criando uma variável para nossa view do reciclador, texto de saída, botão e viewmodal.
    lateinit var viewModal: NoteViewModal
    lateinit var notesRV: RecyclerView
    lateinit var addFAB: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // na linha abaixo estamos inicializando todas as nossas variáveis.
        notesRV = findViewById(R.id.notesRV)
        addFAB = findViewById(R.id.idFAB)
        // na linha abaixo, estamos configurando o gerenciador de layout para nossa visualização de reciclador.
        notesRV.layoutManager = LinearLayoutManager(this)
        // na linha abaixo estamos inicializando nossa classe de adaptador.
        val noteRVAdapter = NoteRVAdapter(this, this, this)
        // na linha abaixo, estamos configurando o adaptador para nossa visualização de reciclador.
        notesRV.adapter = noteRVAdapter
        // na linha abaixo estamos inicializando nosso modal de visualização.
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModal::class.java)
        // na linha abaixo, estamos chamando todos os métodos de notas da nossa classe modal de visualização para observar as alterações na lista.
        viewModal.allNotes.observe(this, Observer { list ->
            list?.let {
                // na linha abaixo estamos atualizando nossa lista.
                noteRVAdapter.updateList(it)
            }
        })
        addFAB.setOnClickListener {
            // adicionando um botão de lista de cliques para fab e abrindo uma nova intenção para adicionar uma nova nota.
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onNoteClick(note: Note) {
        // abrindo um novo intent e passando dados para ele.
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(note: Note) {
        // no método de clique em nota, estamos chamando o método delete do nosso modal viw para excluir nosso not.
        viewModal.deleteNote(note)
        // exibindo uma mensagem de brinde
        Toast.makeText(this, "${note.noteTitle} Apagado", Toast.LENGTH_LONG).show()
    }

}