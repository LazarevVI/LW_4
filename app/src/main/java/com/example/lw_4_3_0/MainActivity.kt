package com.example.lw_4_3_0

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView


class MainActivity : AppCompatActivity() {
    var playersList = ArrayList<Player>()
    var playersImgs = intArrayOf(
        R.drawable.cristiano_ronaldo,
        R.drawable.sadio_man_,
        R.drawable.karim_benzema,
        R.drawable.mohamed_salah,
        R.drawable.harry_kane,
        R.drawable.erling_haaland,
        R.drawable.kyilian_mbapp_,
        R.drawable.kevin_de_bruyne,
        R.drawable.robert_lewandowski,
        R.drawable.lionel_messi
    )

    private lateinit var playerImg:ImageView
    private val IMAGE_REQUEST_CODE = 100
    private lateinit var addBtn:FloatingActionButton
    private lateinit var formTeamsBtn:FloatingActionButton
    private lateinit var recV:RecyclerView
    private lateinit var adapter: Player_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Реализовать отображение Bitmap
        // Перевести ресурсы в Bitmap

        setUpPlayers()

        recV = findViewById(R.id.mRecyclerView)
        addBtn = findViewById(R.id.addBtn)
        formTeamsBtn = findViewById(R.id.formTeamsBtn)

        adapter = Player_Adapter(this, playersList)
        recV.adapter = adapter
        recV.layoutManager = LinearLayoutManager(this)

        addBtn.setOnClickListener{
            addPlayer()
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        super.onContextItemSelected(item)

        return when (item.itemId) {
            R.id.action_delete -> {
                adapter.removeAt(item.groupId)
                Toast.makeText(this, "Player deleted", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.action_edit -> {
                val dialog = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
                dialog.setTitle("Enter name")
                val inflater = LayoutInflater.from(this)
                val dialogWindow: View = inflater.inflate(R.layout.add_edit_item, null)

                dialog.setView(dialogWindow)
                val okBtn = dialogWindow.findViewById<Button>(R.id.okBtn)
                val avatar = dialogWindow.findViewById<ShapeableImageView>(R.id.newPlayerImgV)
                val editText = dialogWindow.findViewById<EditText>(R.id.newPlayerName)

                editText.setSelectAllOnFocus(true)
                editText.setText(playersList.get(item.groupId).playerName)
                avatar.setImageResource(playersList[item.groupId].playerImg)


                okBtn.setOnClickListener {
                    adapter.updateAt(
                        item.groupId,
                        Player(
                            editText.text.toString(),
                            playersList[item.groupId].playerImg,
                            playersList[item.groupId].check(),
                            0
                        )
                    )
                    Toast.makeText(this, "Player updated", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }

                dialog.show()
                true
            }
            else -> false
        }
    }

    private fun addPlayer() {

        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.add_edit_item, null)

        val playerName = view.findViewById<EditText>(R.id.newPlayerName)
        playerImg = view.findViewById(R.id.newPlayerImgV)

        val addDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        addDialog.setView(view)

        playerImg.setOnClickListener{
            pickImageGallery()
        }

        val okBtn = view.findViewById<Button>(R.id.okBtn)
        okBtn.setOnClickListener {
            val name = playerName.text.toString()
//            val bitmap = (playerImg.getDrawable() as BitmapDrawable).bitmap
//            val drawable: Drawable = BitmapDrawable(resources, bitmap)
            playersList.add(Player(name, R.drawable.cristiano_ronaldo, false, 0))
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Added new player", Toast.LENGTH_SHORT).show()
            addDialog.dismiss()
        }
        addDialog.setCanceledOnTouchOutside(true)
        addDialog.show()
    }

    private fun pickImageGallery() {
       val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==IMAGE_REQUEST_CODE && resultCode== RESULT_OK){
            playerImg.setImageURI(data?.data)
        }
    }

    private fun setUpPlayers() {
        val playerNames = resources.getStringArray(R.array.players_names)
        for (i in playerNames.indices) {
            playersList.add(
                Player(
                    playerNames[i],
                    playersImgs[i],
                    false,
                    0
                )
            )
        }
    }

    fun onBtnMakeTeams(view: View) {

        val intent = Intent(this, teams::class.java)
        var countPresent = 0
        for (i in playersList.indices) {
            val footballer: Player = playersList[i]
            if (footballer.check()) {
                intent.putExtra("footballername$countPresent", footballer.playerName)
                intent.putExtra("footballerimg$countPresent", footballer.playerImg.toString())
                countPresent++
            }
        }
        intent.putExtra("countPresent", countPresent)
        startActivity(intent)
    }
}