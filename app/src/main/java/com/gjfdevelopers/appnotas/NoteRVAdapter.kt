package com.gjfdevelopers.appnotas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteRVAdapter(
    val context: Context,
    val noteClickDeleteInterface: NoteClickDeleteInterface,
    val noteClickInterface: NoteClickInterface
) :
    RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    // na linha abaixo estamos criando uma variável para nossa lista de todas as notas.
    private val allNotes = ArrayList<Note>()

    // na linha abaixo estamos criando uma classe viewholder.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // na linha abaixo estamos criando uma inicialização de todas as nossas variáveis que adicionamos no arquivo de layout.
        val noteTV = itemView.findViewById<TextView>(R.id.idTVNote)
        val dateTV = itemView.findViewById<TextView>(R.id.idTVDate)
        val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflando nosso arquivo de layout para cada item da visualização do reciclador.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_rv_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // na linha abaixo, estamos definindo os dados para o item da visualização do reciclador.
        holder.noteTV.setText(allNotes.get(position).noteTitle)
        holder.dateTV.setText("Nota Atualizada : "+allNotes.get(position).timeStamp)
        // na linha abaixo, estamos adicionando o click listner ao nosso ícone de exibição de imagem de exclusão.
        holder.deleteIV.setOnClickListener {
            // na linha abaixo, estamos chamando uma interface de clique de nota e estamos passando uma posição para ela.
            noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
        }

        // na linha abaixo, estamos adicionando o click listner ao nosso item de visualização do reciclador.
        holder.itemView.setOnClickListener {
            // na linha abaixo, estamos chamando uma interface de clique de nota e estamos passando uma posição para ela.
            noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {
        // na linha abaixo estamos retornando o tamanho da nossa lista.
        return allNotes.size
    }

    // abaixo é usado para atualizar nossa lista de notas.
    fun updateList(newList: List<Note>) {
        // na linha abaixo, estamos limpando nossa lista de matriz de notas
        allNotes.clear()
        // na linha abaixo, estamos adicionando uma nova lista à nossa lista de todas as notas.
        allNotes.addAll(newList)
        // na linha abaixo, estamos chamando o método notify data change para notificar nosso adaptador.
        notifyDataSetChanged()
    }

}

interface NoteClickDeleteInterface {
    // criando um método para ação de clique na exibição de imagem de exclusão.
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface {
    // criando um método para ação de clique no item de visualização do reciclador para atualizá-lo.
    fun onNoteClick(note: Note)
}