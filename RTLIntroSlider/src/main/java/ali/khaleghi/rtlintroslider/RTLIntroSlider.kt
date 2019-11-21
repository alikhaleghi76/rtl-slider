package ali.khaleghi.rtlintroslider

import ali.khaleghi.rtlintroslider.adapter.ViewPagerAdapter
import ali.khaleghi.rtlintroslider.callback.OnSkipClickListener
import ali.khaleghi.rtlintroslider.fragment.SampleFragment
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import java.util.*


class RTLIntroSlider @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    private val root: RelativeLayout
    private val viewPager: ViewPager
    private val indicatorLayout: LinearLayout
    private val nextButton: TextView
    private val skipButton: TextView
    private val separator: View
    private var viewPagerAdapter: ViewPagerAdapter

    private var onSkipClickListener: OnSkipClickListener? = null

    private var activeDotColor: String?
    private var inactiveDotColor: String?
    private var isRtl: Boolean = true

    init {

        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.sliderOptions, 0, 0)
        var nextText = typedArray.getString(R.styleable.sliderOptions_next_text)
        var skipText = typedArray.getString(R.styleable.sliderOptions_skip_text)
        var enterText = typedArray.getString(R.styleable.sliderOptions_enter_text)
        val showIndicator = typedArray.getBoolean(R.styleable.sliderOptions_show_indicator, true)

        var separatorColor = typedArray.getString(R.styleable.sliderOptions_separator_color)
        var nextColor = typedArray.getString(R.styleable.sliderOptions_next_color)
        var skipColor = typedArray.getString(R.styleable.sliderOptions_skip_color)

        isRtl = typedArray.getBoolean(R.styleable.sliderOptions_is_rtl, true)

        activeDotColor = typedArray.getString(R.styleable.sliderOptions_active_dot_color)
        inactiveDotColor = typedArray.getString(R.styleable.sliderOptions_inactive_dot_color)

        if (activeDotColor == null || activeDotColor!!.isEmpty()) {
            activeDotColor = "#CCFFFFFF"
        }
        if (inactiveDotColor == null || inactiveDotColor!!.isEmpty()) {
            inactiveDotColor = "#77FFFFFF"
        }
        if (separatorColor == null || separatorColor!!.isEmpty()) {
            separatorColor = "#88FFFFFF"
        }
        if (nextColor == null || nextColor!!.isEmpty()) {
            nextColor = "#FFFFFFFF"
        }
        if (skipColor == null || skipColor!!.isEmpty()) {
            skipColor = "#FFFFFFFF"
        }

        if (nextText == null) nextText = context.getString(R.string.intro_next)
        if (skipText == null) skipText = context.getString(R.string.intro_skip)
        if (enterText == null) enterText = context.getString(R.string.intro_enter)

        typedArray.recycle()


        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.slider, this, true)

        root = getChildAt(0) as RelativeLayout

        viewPager = root.findViewById(R.id.view_pager)
        indicatorLayout = root.findViewById(R.id.layoutDots)
        nextButton = root.findViewById(R.id.btn_next)
        skipButton = root.findViewById(R.id.btn_skip)
        separator = root.findViewById(R.id.separator)

        nextButton.text = nextText
        skipButton.text = skipText

        indicatorLayout.visibility =
                if (showIndicator) VISIBLE
                else INVISIBLE

        viewPagerAdapter = ViewPagerAdapter((context as AppCompatActivity).supportFragmentManager, isRtl)
        viewPager.adapter = viewPagerAdapter
        if (isRtl) {
            viewPager.currentItem = viewPagerAdapter.count - 1
        } else {
            viewPager.currentItem = 0
        }

        if (isRtl) {
            if (viewPager.currentItem == 0) {
                nextButton.text = enterText
                skipButton.visibility = View.GONE
            } else {
                nextButton.text = nextText
                skipButton.visibility = View.VISIBLE
            }
        }
        else {
            if (viewPager.currentItem == viewPagerAdapter.count - 1) {
                nextButton.text = enterText
                skipButton.visibility = View.GONE
            } else {
                nextButton.text = nextText
                skipButton.visibility = View.VISIBLE
            }
        }

        val viewPagerPageChangeListener = object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                addBottomDots(position)
                if (isRtl) {
                    if (position == 0) {
                        nextButton.text = enterText
                        skipButton.visibility = View.GONE
                    } else {
                        nextButton.text = nextText
                        skipButton.visibility = View.VISIBLE
                    }
                }
                else {
                    if (position == viewPagerAdapter.count - 1) {
                        nextButton.text = enterText
                        skipButton.visibility = View.GONE
                    } else {
                        nextButton.text = nextText
                        skipButton.visibility = View.VISIBLE
                    }
                }

            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        }
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)


        skipButton.setOnClickListener {
            onSkipClickListener?.onSkipClicked()
        }

        nextButton.setOnClickListener {

            if (!isRtl) {
                val current = getItem(+1)
                if (current < viewPagerAdapter.count) {
                    viewPager.currentItem = current
                } else {
                    skipButton.performClick()
                }
            }
            else {
                val current = getItem(-1)
                if (current >= 0) {
                    viewPager.currentItem = current
                } else {
                    skipButton.performClick()
                }
            }
        }

        nextButton.setTextColor(Color.parseColor(nextColor))
        skipButton.setTextColor(Color.parseColor(skipColor))
        separator.setBackgroundColor(Color.parseColor(separatorColor))

        configButtonsPosition()

    }

    private fun addBottomDots(currentPage: Int) {
        val dots = ArrayList<TextView>()
        indicatorLayout.removeAllViews()
        for (i in 0 until viewPagerAdapter.count) {
            dots.add(i, TextView(context))
            dots[i].text = Html.fromHtml("&#8226;")
            dots[i].textSize = 35f
            dots[i].setTextColor(Color.parseColor(inactiveDotColor))
            indicatorLayout.addView(dots[i])
        }
        if (dots.isNotEmpty()) {
            dots[currentPage].setTextColor(Color.parseColor(activeDotColor))
        }

    }

    private fun getItem(position: Int): Int {
        return viewPager.currentItem + position
    }

    /**
     * Adds a custom fragment to intro slider
     * Each fragment will be added to a different page
     * @param fragment: Any fragment
     */
    fun addFragment(fragment: Fragment) {
        viewPagerAdapter.addFragment(fragment)
        viewPagerAdapter.notifyDataSetChanged()
        if (isRtl) {
            viewPager.currentItem = viewPagerAdapter.count - 1
        } else {
            viewPager.currentItem = 0
        }
        addBottomDots(viewPager.currentItem)
    }

    /**
     * Adds an array list of fragments to intro slider
     * Each fragment will be added to a different page
     * @param fragments: An array list of fragments
     */
    fun addFragments(fragments: ArrayList<Fragment>) {
        viewPagerAdapter.addFragmentList(fragments)
        viewPagerAdapter.notifyDataSetChanged()
        addBottomDots(viewPager.currentItem)
        if (isRtl) {
            viewPager.currentItem = viewPagerAdapter.count - 1
        } else {
            viewPager.currentItem = 0
        }
    }

    /**
     * Set text of next button
     * @param text: string
     */
    fun setNextText(text: String) {
        nextButton.text = text
    }

    /**
     * Set color of skip button
     * @param color: Int
     */
    fun setSkipColor(color: Int) {
        nextButton.setTextColor(color)
    }

    /**
     * Set color of next button
     * @param color: Int
     */
    fun setNextColor(color: Int) {
        nextButton.setTextColor(color)
    }

    /**
     * Set text of skip button
     * @param text: string
     */
    fun setSkipText(text: String) {
        skipButton.text = text
    }

    /**
     * Set visibility of skip button
     * @param visible: true -> visible, false -> invisible
     */
    fun setSkipVisibile(visible: Boolean) {
        skipButton.visibility =
                if (visible) View.VISIBLE
                else View.INVISIBLE
    }

    /**
     * Adds default page to intro slider
     * @param title: title of slide [can be empty]
     * @param text: detail of slide
     * @param textColor: Int - text color of slide
     * @param backgroundColor: Int - background color of slide
     * @param icon: Int - Res id of slide icon (pass -1 if want to load no image)
     */
    fun addPage(
            title: String?,
            text: String?,
            textColor: Int,
            backgroundColor: Int?,
            icon: Int
    ) {
        addFragment(SampleFragment(
                title,
                text,
                textColor!!,
                backgroundColor!!,
                icon
        ))
    }

    /**
     * defines callback to observe skip button click
     * @param onSkipClickListener: OnSkipClickListener -> implement instance of this interface to observe skip click
     */
    fun addOnSkipClickListener(onSkipClickListener: OnSkipClickListener) {
        this.onSkipClickListener = onSkipClickListener
    }


    /**
     * sets type face of slider
     * @param typeface
     */
    fun setTypeface(typeface: Typeface) {
        skipButton.typeface = typeface
        nextButton.typeface = typeface
        viewPagerAdapter.setTypeface(typeface)
    }

    /**
     * show/hide indicator
     * @param show: true -> visible, false -> invisible
     */
    fun setShowIndicator(show: Boolean) {
        if (show)
            indicatorLayout.visibility = View.VISIBLE
        else
            indicatorLayout.visibility = View.INVISIBLE
    }


    /**
     * rtl/ltr slider
     * @param isRtl: true -> rtl, false -> ltr
     */
    fun setRtl(isRtl: Boolean) {
        this.isRtl = isRtl
        viewPagerAdapter.isRtl = isRtl
        viewPagerAdapter.notifyDataSetChanged()
        addBottomDots(viewPager.currentItem)

        if (isRtl) {
            viewPager.currentItem = viewPagerAdapter.count - 1
        } else {
            viewPager.currentItem = 0
        }

        configButtonsPosition()
    }

    private fun configButtonsPosition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (isRtl) {
                val params = nextButton.layoutParams as LayoutParams
                params.addRule(ALIGN_PARENT_LEFT)
                params.removeRule(ALIGN_PARENT_RIGHT)
                nextButton.layoutParams = params //causes layout update
                val params2 = skipButton.layoutParams as LayoutParams
                params2.addRule(ALIGN_PARENT_RIGHT)
                params2.removeRule(ALIGN_PARENT_LEFT)
                skipButton.layoutParams = params2 //causes layout update
            } else {
                val params = nextButton.layoutParams as LayoutParams
                params.addRule(ALIGN_PARENT_RIGHT)
                params.removeRule(ALIGN_PARENT_LEFT)
                nextButton.layoutParams = params //causes layout update
                val params2 = skipButton.layoutParams as LayoutParams
                params2.addRule(ALIGN_PARENT_LEFT)
                params2.removeRule(ALIGN_PARENT_RIGHT)
                skipButton.layoutParams = params2 //causes layout update
            }
        }
    }
}
