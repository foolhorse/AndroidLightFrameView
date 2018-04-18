package me.machao.lightframeview.sample

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_vaporwave.*

class VaporwaveActivity : AppCompatActivity() {

    private lateinit var shadowAnimator: ValueAnimator
    private lateinit var cameraAnimatorSet: AnimatorSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaporwave)

        initView()
        initAnim()
        shadowAnimator.start()
        cameraAnimatorSet.start()
    }

    private fun initView() {


    }

    private fun initAnim() {
        shadowAnimator = ValueAnimator.ofFloat(-8f, 8f)
        shadowAnimator.duration = 1800
        shadowAnimator.repeatCount = ValueAnimator.INFINITE
        shadowAnimator.repeatMode = ValueAnimator.REVERSE
        shadowAnimator.addUpdateListener { animation: ValueAnimator? ->
            val d = animation!!.animatedValue as Float
            tvMobian.setShadowLayer(16f, d, d, tvMobian.shadowColor)
        }

        val cameraAnimator1 = ObjectAnimator.ofFloat(lfvYixianqian, "rotationX", 0f, 360f);
        cameraAnimator1.duration = 6000
        cameraAnimator1.repeatCount = ValueAnimator.INFINITE
        cameraAnimator1.repeatMode = ValueAnimator.RESTART


        val cameraAnimator2 = ObjectAnimator.ofFloat(lfvYixianqian, "rotationY", 0f, 360f);
        cameraAnimator2.duration = 6000
        cameraAnimator2.repeatCount = ValueAnimator.INFINITE
        cameraAnimator2.repeatMode = ValueAnimator.RESTART

        cameraAnimatorSet = AnimatorSet()
        cameraAnimatorSet.playTogether(cameraAnimator1, cameraAnimator2)

    }

}
