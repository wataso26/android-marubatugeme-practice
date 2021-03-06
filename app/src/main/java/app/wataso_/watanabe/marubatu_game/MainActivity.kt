package app.wataso_.watanabe.marubatu_game

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import app.wataso_.watanabe.marubatu_game.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    //バインディングクラスの変数
    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }



        binding.restartButton.isVisible=false
        binding.finishTextView.isVisible=false

        //使い方について
        val shr = getSharedPreferences("beginner", Context.MODE_PRIVATE)
        var Number = shr.getInt("number",0)

        if (Number==0){
            android.app.AlertDialog.Builder(this)
                .setMessage("コンピュータは即レスだけど、驚かないで！")
                .setPositiveButton("対決", { dialog, which ->
                })
                .show()
            android.app.AlertDialog.Builder(this)
                .setMessage("公平性を意識して、最後の枠は埋められないよ。")
                .setPositiveButton("次へ", { dialog, which ->
                })
                .show()
            AlertDialog.Builder(this)
                .setMessage("これはマルバツゲーム！")
                .setPositiveButton("次へ",{dialog,which ->
                })
                .show()


        }

        val editor=shr.edit()
        var Number2=shr.getInt("number",0)
        if(Number2==0){
            editor.putInt("number",1)
            editor.apply()

        }


        binding.restartButton.setOnClickListener {
            restartGame()
            binding.restartButton.isVisible=false
            binding.finishTextView.isVisible=false
        }
    }



    fun buClick(view: View){
        val buSelected = view as Button

        var cellId =0

        when (buSelected.id){
            R.id.bu1 -> cellId = 1
            R.id.bu2 -> cellId = 2
            R.id.bu3 -> cellId = 3
            R.id.bu4 -> cellId = 4
            R.id.bu5 -> cellId = 5
            R.id.bu6 -> cellId = 6
            R.id.bu7 -> cellId = 7
            R.id.bu8 -> cellId = 8
            R.id.bu9 -> cellId = 9

        }
        Log.d("buClick", "クリックされている？")
        playGame(cellId,buSelected)
    }
    var activityPlayer =1

    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()

    fun playGame(cellId:Int,buSelected:Button){
        if (activityPlayer ==1){
            buSelected.setBackgroundResource(R.drawable.batuimage)
            Log.d("playGame","自分のターン")
            player1.add(cellId)
            activityPlayer = 2
            autoPlay()
        }else{

            buSelected.setBackgroundResource(R.drawable.maruimage)
            Log.d("playGame","相手のターン")
            player2.add(cellId)
            activityPlayer = 1

            checkWinner()
        }
        buSelected.isEnabled = false
    }
    fun checkWinner(){
        var winner = -1

        if(player1.contains(1) &&player1.contains(2) &&player1.contains(3)){
            winner = 1
        }
        if(player2.contains(1) &&player2.contains(2) &&player2.contains(3)){
            winner = 2
        }
        //row2
        if(player1.contains(4) && player1.contains(5) && player1.contains(6)) {
            winner = 1
        }

        if(player2.contains(4) && player2.contains(5) && player2.contains(6)){
            winner = 2
        }
        //row3
        if(player1.contains(7) && player1.contains(8) && player1.contains(9)) {
            winner = 1
        }

        if(player2.contains(7) && player2.contains(8) && player2.contains(9)){
            winner = 2
        }

        //col1
        if(player1.contains(1) && player1.contains(4) && player1.contains(7)) {
            winner = 1
        }

        if(player2.contains(1) && player2.contains(4) && player2.contains(7)){
            winner = 2
        }

        //col2
        if(player1.contains(2) && player1.contains(5) && player1.contains(8)) {
            winner = 1
        }

        if(player2.contains(2) && player2.contains(5) && player2.contains(8)){
            winner = 2
        }

        //col3
        if(player1.contains(3) && player1.contains(6) && player1.contains(9)) {
            winner = 1
        }

        if(player2.contains(3) && player2.contains(6) && player2.contains(9)){
            winner = 2
        }

        //cross1
        if(player1.contains(1) && player1.contains(5) && player1.contains(9)) {
            winner = 1
        }

        if(player2.contains(1) && player2.contains(5) && player2.contains(9)){
            winner = 2
        }

        //cross2
        if(player1.contains(3) && player1.contains(5) && player1.contains(7)) {
            winner = 1
        }

        if(player2.contains(3) && player2.contains(5) && player2.contains(7)){
            winner = 2
        }

        //何も

        if(winner ==1){
            player1WinnerCount += 1
            Toast.makeText(this,"Player 1 win the game",Toast.LENGTH_SHORT).show()
            binding.restartButton.isVisible=true
            buttonBehind()

            //restartGame()
        }else if(winner==2){
            player2WinnerCount += 1
            Toast.makeText(this, "Player 2 win the game", Toast.LENGTH_SHORT).show()
            binding.restartButton.isVisible=true
            buttonBehind()

            //restartGame()

        }

    }

    fun autoPlay(){
        var emptyCells =ArrayList<Int>()

        for (cellId in 1..9){
            if(!(player1.contains(cellId)||player2.contains(cellId))){
                emptyCells.add(cellId)
            }
            Log.d("cellId in 1..9","一からゼロの処理")
        }
        if(emptyCells.size ==2){//ボタンが押せなくなったときの処理はここで書かれている
            Log.d("size==0","リスタートしている")
            buttonBehind()
            binding.restartButton.isVisible=true
            binding.finishTextView.isVisible=true

            //ここの次にボタンが押せなくなっているのが原因だと思うが、どうすればいいか分からない。
            //size==0のデバックはとれているが、次の,autoPlayデバックがとれない。、


        }
        val r = Random()
        val randIndex =r.nextInt(emptyCells.size)
        val cellId = emptyCells[randIndex]

        var buSelected:Button?
        buSelected =when(cellId){
            1 -> binding.bu1
            2 -> binding.bu2
            3 -> binding.bu3
            4 -> binding.bu4
            5 -> binding.bu5
            6 -> binding.bu6
            7 -> binding.bu7
            8 -> binding.bu8
            9 -> binding.bu9
            else -> {binding.bu1}
        }
        Log.d("autoPlay","playGame前")
        playGame(cellId,buSelected)// ここが原因。
    }




    var player1WinnerCount =0
    var player2WinnerCount =0

    fun restartGame(){

        activityPlayer =1
        player1.clear()
        player2.clear()
//        val bu1 =findViewById<Button>(R.id.bu1)
//        val bu2 =findViewById<Button>(R.id.bu2)
//        val bu3 =findViewById<Button>(R.id.bu3)
//        val bu4 =findViewById<Button>(R.id.bu4)
//        val bu5 =findViewById<Button>(R.id.bu5)
//        val bu6 =findViewById<Button>(R.id.bu6)
//        val bu7 =findViewById<Button>(R.id.bu7)
//        val bu8 =findViewById<Button>(R.id.bu8)
//        val bu9 =findViewById<Button>(R.id.bu9)

        for(index in 1..9){
           val buSelected:Button
           buSelected =when(index){
               1 -> binding.bu1
               2 -> binding.bu2
               3 -> binding.bu3
               4 -> binding.bu4
               5 -> binding.bu5
               6 -> binding.bu6
               7 -> binding.bu7
               8 -> binding.bu8
               9 -> binding.bu9
               else -> {binding.bu1}
           }
            buSelected!!.text=""
            buSelected!!.setBackgroundResource(R.color.whiteBu)
            buSelected!!.isEnabled =true
        }
        binding.resultTextView3.text =player1WinnerCount.toString()
        binding.resultTextView5.text = player2WinnerCount.toString()
        //Toast.makeText(this,"Player1: $player1WinnerCount ,Player2: $player2WinnerCount", Toast.LENGTH_SHORT).show()

    }


    fun buttonBehind(){
        binding.bu1.isEnabled =false
        binding.bu2.isEnabled =false
        binding.bu3.isEnabled =false
        binding.bu4.isEnabled =false
        binding.bu5.isEnabled =false
        binding.bu6.isEnabled =false
        binding.bu7.isEnabled =false
        binding.bu8.isEnabled =false
        binding.bu9.isEnabled =false



    }

}