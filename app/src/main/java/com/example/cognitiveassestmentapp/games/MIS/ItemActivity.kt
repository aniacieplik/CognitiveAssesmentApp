package com.example.cognitiveassestmentapp.games.MIS

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R
import com.example.cognitiveassestmentapp.registration.MenuActivity

class ItemActivity : AppCompatActivity() {

    private val words = listOf(
        "Word1" to "Category1",
        "Word2" to "Category2",
        "Word3" to "Category3",
        "Word4" to "Category4"
    ).shuffled()

    private var matchedWords = mutableMapOf<String, Boolean>()
    private var selectedWordIndex: Int? = null
    private var selectedCategoryIndex: Int? = null
    private var previouslyMatchedIndices = mutableListOf<Int>()
    private var incorrectGuesses = 0
    private val maxIncorrectGuesses = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        val wordTextViews = listOf<TextView>(
            findViewById(R.id.word1_textview),
            findViewById(R.id.word2_textview),
            findViewById(R.id.word3_textview),
            findViewById(R.id.word4_textview)
        ).shuffled()

        val categoryTextViews = listOf<TextView>(
            findViewById(R.id.category1_textview),
            findViewById(R.id.category2_textview),
            findViewById(R.id.category3_textview),
            findViewById(R.id.category4_textview)
        ).shuffled()

        val finishButton: Button = findViewById(R.id.finish_button)
        finishButton.isEnabled = false
        finishButton.setBackgroundColor(Color.GRAY)

        words.forEachIndexed { index, pair ->
            wordTextViews[index].text = pair.first
        }

        words.forEachIndexed { index, pair ->
            categoryTextViews[index].text = pair.second
        }

        categoryTextViews.forEach { textView ->
            textView.setBackgroundColor(Color.TRANSPARENT)
        }

        wordTextViews.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                if (textView.currentBackgroundColor != Color.RED && textView.isEnabled) {
                    selectedWordIndex?.let { prevIndex ->
                        if (!previouslyMatchedIndices.contains(prevIndex)) {
                            wordTextViews[prevIndex].setBackgroundColor(Color.TRANSPARENT)
                        }
                    }
                    textView.setBackgroundColor(Color.YELLOW)
                    selectedWordIndex = index
                    selectedCategoryIndex?.let { categoryIndex ->
                        checkMatch(selectedWordIndex!!, categoryIndex, wordTextViews, categoryTextViews, finishButton)
                    }
                }
            }
        }

        categoryTextViews.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                if (textView.currentBackgroundColor != Color.RED && textView.isEnabled) {
                    selectedCategoryIndex?.let { prevIndex ->
                        if (!previouslyMatchedIndices.contains(prevIndex)) {
                            categoryTextViews[prevIndex].setBackgroundColor(Color.TRANSPARENT)
                        }
                    }
                    textView.setBackgroundColor(Color.YELLOW)
                    selectedCategoryIndex = index
                    selectedWordIndex?.let { wordIndex ->
                        checkMatch(wordIndex, selectedCategoryIndex!!, wordTextViews, categoryTextViews, finishButton)
                    }
                }
            }
        }

        matchedWords.forEach { (word, isMatched) ->
            if (isMatched) {
                val index = words.indexOfFirst { it.first == word }
                if (index != -1) {
                    categoryTextViews[index].setBackgroundColor(Color.GREEN)
                    wordTextViews[index].setBackgroundColor(Color.GREEN)
                    previouslyMatchedIndices.add(index)
                    categoryTextViews[index].isEnabled = false
                    wordTextViews[index].isEnabled = false
                }
            }
        }

        finishButton.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }

    private fun checkMatch(wordIndex: Int, categoryIndex: Int, wordTextViews: List<TextView>, categoryTextViews: List<TextView>, finishButton: Button) {
        val (word, category) = words[wordIndex]
        if (categoryTextViews[categoryIndex].text == category) {
            categoryTextViews[categoryIndex].setBackgroundColor(Color.GREEN)
            wordTextViews[wordIndex].setBackgroundColor(Color.GREEN)
            matchedWords[word] = true
            previouslyMatchedIndices.add(wordIndex)
            if (matchedWords.size == words.size && matchedWords.all { it.value }) {
                finishButton.isEnabled = true
                finishButton.setBackgroundColor(Color.parseColor("#DD6587"))
                showCompletionMessage()
            }
            categoryTextViews[categoryIndex].isEnabled = false
            wordTextViews[wordIndex].isEnabled = false
        } else {
            categoryTextViews[categoryIndex].setBackgroundColor(Color.RED)
            wordTextViews[wordIndex].setBackgroundColor(Color.RED)
            matchedWords[word] = false
            incorrectGuesses++
            Toast.makeText(this, "Incorrect match. Please try again", Toast.LENGTH_SHORT).show()

            if (incorrectGuesses >= maxIncorrectGuesses) {
                showFailureMessage()
            } else {
                categoryTextViews[categoryIndex].postDelayed({
                    if (!matchedWords.any { it.value && it.key == word }) {
                        categoryTextViews[categoryIndex].setBackgroundColor(Color.TRANSPARENT)
                        wordTextViews[wordIndex].setBackgroundColor(Color.TRANSPARENT)
                    }
                }, 2000)
            }
        }
        selectedWordIndex = null
        selectedCategoryIndex = null
    }

    private fun showCompletionMessage() {
        val messageTextView = TextView(this).apply {
            text = "Task completed successfully. Please remember those words"
            textSize = 20f
            setTextColor(Color.BLACK)
            setBackgroundColor(Color.WHITE)
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
            setPadding(32, 16, 32, 16)
        }

        val messageContainer = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.CENTER
            setBackgroundColor(Color.parseColor("#80000000"))
            addView(messageTextView)
        }

        messageContainer.setOnClickListener {
            val rootLayout = findViewById<ViewGroup>(R.id.activity_item_layout)
            rootLayout.removeView(messageContainer)
        }

        val rootLayout = findViewById<ViewGroup>(R.id.activity_item_layout)
        rootLayout.addView(messageContainer)
    }

    private fun showFailureMessage() {
        val messageTextView = TextView(this).apply {
            text = "Test failed. You've reached the maximum incorrect guesses."
            textSize = 20f
            setTextColor(Color.BLACK)
            setBackgroundColor(Color.WHITE)
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
            setPadding(32, 16, 32, 16)
        }

        val messageContainer = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.CENTER
            setBackgroundColor(Color.parseColor("#80000000"))
            addView(messageTextView)
        }

        messageContainer.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

        val rootLayout = findViewById<ViewGroup>(R.id.activity_item_layout)
        rootLayout.addView(messageContainer)
    }

    private val TextView.currentBackgroundColor: Int
        get() {
            val drawable = background
            if (drawable is ColorDrawable) {
                return drawable.color
            }
            return Color.TRANSPARENT
        }
}
