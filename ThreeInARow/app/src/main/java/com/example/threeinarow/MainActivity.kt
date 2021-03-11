package com.example.threeinarow

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainActivity : Activity() {

    var currentColor: ImageView? = null
    var block: ImageView? = null
    var pointsView: TextView? = null
    var colorNumber: Int = 0
    var Points: Int = 0
    var arrayOfColors =Array(3,{Array(3,{0})}) //двумерный массив который хранит инф-ию о цветах
    val arrayofId = Array(3, { Array(3, {0}) }) //Массив содержащий индексы элементов


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currentColor = findViewById(R.id.imageView4) //присваиваем currentColor конкретную переменную
        pointsView = findViewById(R.id.textView2) //присваиваем полю с очками конкретную переменную

        //заполняем массив ID элементов (блоков)
        arrayofId[0] = arrayOf(R.id.Block1,R.id.Block2 , R.id.Block3)
        arrayofId[1] = arrayOf(R.id.Block4, R.id.Block5, R.id.Block6)
        arrayofId[2] = arrayOf(R.id.Block7, R.id.Block8, R.id.Block9)
        colorNumber = (1..3).shuffled().last()

        // устанавливаем начальное значение индикатора
        when (colorNumber) {
            1 -> currentColor?.setImageResource(R.drawable.shapered)
            2 -> currentColor?.setImageResource(R.drawable.shapeblue)
            else -> currentColor?.setImageResource(R.drawable.shapeyellow)
        }


    }
    //функция для кнопки return
    fun return_btn(view: View) {
        val builder = AlertDialog.Builder(this) //создаем Alert
        builder.setTitle("Restart?") //титл алерта
        builder.setMessage("Your game score ($Points) will be reset")


        builder.setNegativeButton("Cancel"){ dilogInterface, i -> //негативный ответ

        }
        builder.setPositiveButton("Restart"){dialog, i -> //положительный ответ
            UpdateActivity()
        }
        val alert: AlertDialog = builder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    fun UpdateActivity(){
        Points = 0
        pointsView?.setText(Points.toString()) //отправляем кол-во очков в View

        //ставим индексы цветов в серый
        for(i in 0..arrayOfColors.size-1){
            for(j in 0..arrayOfColors.size-1){
                arrayOfColors[i][j] = 0
            }
        }
        //меняем цвета на серый
        for(i in 0..arrayofId.size-1){
            for(j in 0..arrayofId.size-1){
                block = findViewById(arrayofId[i][j])
                block?.setImageResource(R.drawable.blockstyle)
            }
        }

    }


    //функция для установки цвета блока
    fun setBlockColor(view: View){
        block =  findViewById(view.id) // определяем какая кнопка вызвала функцию


        when (colorNumber) { // смотрим текущий цвет индикатора и устанавливаем его в блок
            1 -> block?.setImageResource(R.drawable.blockstylered)
            2 -> block?.setImageResource(R.drawable.blockstyleblue)
            else -> block?.setImageResource(R.drawable.blockstyleyellow)
        }

    }

    fun onClick_BlockBtn(view: View) { //обработчик для основных блоков
        var xi : Int = 0
        var xj : Int = 0

        var yi : Int = 0
        var yj : Int = 0

        if(arrayOfColors[view.rotationX.toInt()][view.rotationY.toInt()] == 0){ //если элемент пустой

            setBlockColor(view) //вызываем ф-ию для смены цвета кнопки
            arrayOfColors[view.rotationX.toInt()][view.rotationY.toInt()] = colorNumber //записываем индекс цвета в массив

            //определяем индексы соседних элементов по X
             when(view.rotationX.toInt()){
                0 -> {xi =1
                     xj = 2}
                 1-> {xi =2
                     xj =0
                 }
                 2-> {xi =1
                     xj =0

                 }
            }
            //определяем индексы соседних элементов по Y
            when(view.rotationY.toInt()){
                0 -> {yi =1
                    yj = 2}
                1-> {yi =2
                    yj =0
                }
                2-> {yi =1
                    yj =0
                }
            }


            if(arrayOfColors[xi][view.rotationY.toInt()] == colorNumber && arrayOfColors[xj][view.rotationY.toInt()]== colorNumber  ){ //смотрим свопадения по строке
                block =  findViewById(view.id) //меняем последний блок
                block?.setImageResource(R.drawable.blockstyle)
                arrayOfColors[view.rotationX.toInt()][view.rotationY.toInt()] = 0


                block = findViewById(arrayofId[xi][view.rotationY.toInt()])  //меняем соседа
                block?.setImageResource(R.drawable.blockstyle)
                arrayOfColors[xi][view.rotationY.toInt()]= 0

                block = findViewById(arrayofId[xj][view.rotationY.toInt()]) //меняем соседа
                block?.setImageResource(R.drawable.blockstyle)
                arrayOfColors[xj][view.rotationY.toInt()]= 0

                Points += 3
                pointsView?.setText(Points.toString()) //передаем очки в TextView

            }
            if (arrayOfColors[view.rotationX.toInt()][yi]== colorNumber && arrayOfColors[view.rotationX.toInt()][yj] == colorNumber){ //смотрим совпадение по столбцу
                block =  findViewById(view.id)
                block?.setImageResource(R.drawable.blockstyle)
                arrayOfColors[view.rotationX.toInt()][view.rotationY.toInt()] = 0

                block = findViewById(arrayofId[view.rotationX.toInt()][yi]) //меняем соседа
                block?.setImageResource(R.drawable.blockstyle)
                arrayOfColors[view.rotationX.toInt()][yi]= 0

                block = findViewById(arrayofId[view.rotationX.toInt()][yj]) //меняем соседа
                block?.setImageResource(R.drawable.blockstyle)
                arrayOfColors[view.rotationX.toInt()][yj] = 0

                Points += 3
                pointsView?.setText(Points.toString()) //передаем очки в TextView
            }

            colorNumber = (1..3).shuffled().last() //выбираем новый цвет индикатора

            //меняем цвет индикатора
            when (colorNumber) {
                1 -> currentColor?.setImageResource(R.drawable.shapered)
                2 -> currentColor?.setImageResource(R.drawable.shapeblue)
                else -> currentColor?.setImageResource(R.drawable.shapeyellow)
            }
            var isArrayFull = 0
            for(i in arrayOfColors){
                for(j in i){

                    if (j != 0) isArrayFull++
                }
            }
            //Если все элементы цветные, вызываем Alert и обновляем экран
            if(isArrayFull == 9) {
                val builder = AlertDialog.Builder(this) //создаем Alert
                builder.setTitle("Game Over!") //титл алерта
                builder.setMessage("Your  score: ($Points) ")
                builder.setPositiveButton("Restart"){ dilogInterface, i ->
                    UpdateActivity()
                }
                builder.show()
            }

        }else { //Если нажимаем на закрашенный элемент - вызывается вибратор
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(500)
        }

    }

}