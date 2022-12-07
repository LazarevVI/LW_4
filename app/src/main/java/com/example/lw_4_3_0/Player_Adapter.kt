package com.example.lw_4_3_0

import android.content.Context
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class Player_Adapter(val context: Context, val playersList: ArrayList<Player>) :
    RecyclerView.Adapter<Player_Adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }


    fun removeAt(position: Int) {
        playersList.removeAt(position)
        notifyDataSetChanged()
    }

    fun updateAt(position: Int, footballer: Player) {
        playersList.removeAt(position)
        playersList.add(position, footballer)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.playerNameTV.setText(playersList[position].playerName)
        holder.playerCheckBox.setChecked(playersList[position].check())
        holder.playerImgV.setImageResource(playersList[position].playerImg)

        if(playersList[position].team == 1){
            holder.itemLayout.setCardBackgroundColor(
                ContextCompat.getColor(context,
                R.color.t1))
        }

        if(playersList[position].team == 2){
            holder.itemLayout.setCardBackgroundColor(
                ContextCompat.getColor(context,
                    R.color.t2))
        }

        holder.playerCheckBox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
                playersList[position].setChecked(b)
                return@OnCheckedChangeListener
            })
    }

    override fun getItemCount(): Int {
        return playersList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), OnCreateContextMenuListener {
        var playerNameTV: TextView
        var playerImgV: ImageView
        var playerCheckBox: CheckBox
        var itemLayout: CardView

        init {
            playerImgV = itemView.findViewById(R.id.playerImgV)
            playerNameTV = itemView.findViewById(R.id.playerNameTV)
            playerCheckBox = itemView.findViewById(R.id.checkBox)
            itemLayout = itemView.findViewById(R.id.itemLayout)
            itemLayout.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            contextMenu: ContextMenu,
            v: View?,
            contextMenuInfo: ContextMenuInfo?
        ) {
            contextMenu.add(adapterPosition, R.id.action_delete, 0, "Delete")
            contextMenu.add(adapterPosition, R.id.action_edit, 0, "Edit")
        }
    }
}