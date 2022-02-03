package com.example.memorygame

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val isImageOpen = arrayOf(false, false, false, false, false, false)
    private val imageIndex = arrayOfNulls<Int>(2)
    private val imageId = arrayOfNulls<Int>(2)
    private var openedImages = 0
    private var animationWorking = false
    private var isFinish = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.image_1).setOnClickListener {   imageClick(findViewById(R.id.image_1), R.drawable.moto_rasm, 0)      }
        findViewById<ImageView>(R.id.image_4).setOnClickListener {   imageClick(findViewById(R.id.image_4), R.drawable.moto_rasm, 3)      }
        findViewById<ImageView>(R.id.image_2).setOnClickListener {   imageClick(findViewById(R.id.image_2), R.drawable.tabiat_rasm_2, 1)  }
        findViewById<ImageView>(R.id.image_3).setOnClickListener {   imageClick(findViewById(R.id.image_3), R.drawable.tabiat_rasm_3, 2)  }
        findViewById<ImageView>(R.id.image_5).setOnClickListener {   imageClick(findViewById(R.id.image_5), R.drawable.tabiat_rasm_2, 4)  }
        findViewById<ImageView>(R.id.image_6).setOnClickListener {   imageClick(findViewById(R.id.image_6), R.drawable.tabiat_rasm_3, 5)  }

    }

    private fun imageClick(imageView: ImageView, image: Int, index: Int) {
        if (!animationWorking) {
            if (!isImageOpen[index]) {
                animationOpener(imageView, image, index)
            } else {
                animationCloser(imageView, index)
            }
        }
    }

    private fun animationOpener(imageView: ImageView, image: Int, index: Int) {

        val animation = AnimationUtils.loadAnimation(this, R.anim.anim_1)

        imageView.startAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {
                animationWorking = true
            }

            override fun onAnimationEnd(animation: Animation?) {

                val animation2 = AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim_2)

                imageView.startAnimation(animation2)

                imageView.setImageResource(image)

                animation2.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {

                        isImageOpen[index] = true
                        imageIndex[openedImages] = index
                        imageId[openedImages] = image
                        openedImages++

                        if (openedImages == 2) {
                            if (imageId[0] == imageId[1]) {
                                imageViewFinder(imageIndex[0]!!).visibility = View.INVISIBLE
                                imageViewFinder(imageIndex[1]!!).visibility = View.INVISIBLE
                                openedImages-=2
                                isFinish+=2

                                if(isFinish == 6) {
                                    val view = View.inflate(this@MainActivity,R.layout.finish_game,null)
                                    val builder = AlertDialog.Builder(this@MainActivity)
                                    builder.setView(view)
                                    val dialog = builder.create()
                                    dialog.show()
                                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                                    view.findViewById<LinearLayout>(R.id.btn_ok).setOnClickListener{
                                        dialog.hide()
                                    }

                                }

                            } else {
                                animationCloser(imageViewFinder(imageIndex[0]!!), imageIndex[0]!!)
                                animationCloser(imageViewFinder(imageIndex[1]!!), imageIndex[1]!!)
                            }
                        }
                        animationWorking = false
                    }

                    override fun onAnimationRepeat(animation: Animation?) {

                    }

                })
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
    }

    private fun animationCloser(imageView: ImageView, index: Int) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.anim_1)
        imageView.startAnimation(animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                animationWorking = true
            }

            override fun onAnimationEnd(animation: Animation?) {
                val animation2 = AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim_2)
                imageView.startAnimation(animation2)
                imageView.setImageResource(R.drawable.yulduzcha)
                animation2.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        animationWorking = false
                    }

                    override fun onAnimationRepeat(animation: Animation?) {

                    }

                })
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
        isImageOpen[index] = false
        openedImages--
    }

    private fun imageViewFinder(index: Int): ImageView {

        var imageView: ImageView? = null

        when (index) {
            0 -> imageView = findViewById(R.id.image_1)
            1 -> imageView = findViewById(R.id.image_2)
            2 -> imageView = findViewById(R.id.image_3)
            3 -> imageView = findViewById(R.id.image_4)
            4 -> imageView = findViewById(R.id.image_5)
            5 -> imageView = findViewById(R.id.image_6)
        }

        return imageView!!
    }

}