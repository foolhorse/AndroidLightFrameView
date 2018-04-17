package me.machao.lightframeview.sample

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_vaporwave.*

class VaporwaveActivity : AppCompatActivity() {

    private lateinit var shadowAnimator: ValueAnimator
    private lateinit var cameraAnimator: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaporwave)

        initView()
        initAnim()
        shadowAnimator.start()
        cameraAnimator.start()
    }

    private fun initView() {


    }

    private fun initAnim() {
//        val shadowAnimator = ObjectAnimator.ofFloat(tv,"shadowLayer",4f,16f);
        shadowAnimator = ValueAnimator.ofFloat(-8f, 8f)
        shadowAnimator.duration = 1800
        shadowAnimator.repeatCount = ValueAnimator.INFINITE
        shadowAnimator.repeatMode = ValueAnimator.REVERSE
        shadowAnimator.addUpdateListener { animation: ValueAnimator? ->
            val d = animation!!.animatedValue as Float
            tvMobian.setShadowLayer(16f, d, d, tvMobian.shadowColor)
        }

        cameraAnimator = ObjectAnimator.ofFloat(lfvYixianqian,"rotationY",0f,360f);
        cameraAnimator.duration = 6000
        cameraAnimator.repeatCount = ValueAnimator.INFINITE
        cameraAnimator.repeatMode = ValueAnimator.RESTART
    }
}
