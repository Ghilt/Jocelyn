package se.agency.adccor.jocelyn.views

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.AttributeSet
import android.widget.ImageView
import se.agency.adccor.jocelyn.R


class TwinStateAnimationView : ImageView {

    private lateinit var state1: AnimatedVectorDrawable
    private lateinit var state2: AnimatedVectorDrawable
    private var isState1: Boolean = true

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet, defStyle: Int = 0) {

        val attr = context.obtainStyledAttributes(attrs, R.styleable.TwinStateAnimationView, defStyle, 0)

        if (attr.getDrawable(R.styleable.TwinStateAnimationView_startState) == null || attr.getDrawable(R.styleable.TwinStateAnimationView_endState) == null) {
            throw Exception("TwinStateAnimationView not properly initialized with two animated vector drawables")
        }

        state1 = attr.getDrawable(R.styleable.TwinStateAnimationView_startState) as AnimatedVectorDrawable
        state2 = attr.getDrawable(R.styleable.TwinStateAnimationView_endState) as AnimatedVectorDrawable

        attr.recycle()

        setImageDrawable(state1)
    }

    private fun swap() {
        val drawable = if (isState1) state1 else state2
        setImageDrawable(drawable)
        drawable.start()
        isState1 = !isState1
    }

    fun toStartState() {
        if (isState1) swap()
    }

    fun toEndState() {
        if (!isState1) swap()
    }

}