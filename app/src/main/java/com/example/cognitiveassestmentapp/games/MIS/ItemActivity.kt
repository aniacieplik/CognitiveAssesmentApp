package com.example.cognitiveassestmentapp.games.MIS

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class ItemActivity : AppCompatActivity() {

    // Define your list of words and their categories here
    private val words = listOf(
        "Word1" to "Category1",
        "Word2" to "Category2",
        "Word3" to "Category3",
        "Word4" to "Category4"
    )

    private var matchedWords = mutableMapOf<String, Boolean>()
    private var selectedWordIndex: Int? = null
    private var previouslyMatchedIndices = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        val wordTextViews = listOf<TextView>(
            findViewById(R.id.word1_textview),
            findViewById(R.id.word2_textview),
            findViewById(R.id.word3_textview),
            findViewById(R.id.word4_textview)
        )

        val categoryTextViews = listOf<TextView>(
            findViewById(R.id.category1_textview),
            findViewById(R.id.category2_textview),
            findViewById(R.id.category3_textview),
            findViewById(R.id.category4_textview)
        )

        // Initialize category TextViews background to transparent
        categoryTextViews.forEach { textView ->
            textView.setBackgroundColor(Color.TRANSPARENT)
        }

        wordTextViews.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                // Reset background color of previously selected word
                selectedWordIndex?.let { prevIndex ->
                    if (!previouslyMatchedIndices.contains(prevIndex)) {
                        wordTextViews[prevIndex].setBackgroundColor(Color.TRANSPARENT)
                    }
                }

                // Apply yellow background color to the clicked word
                textView.setBackgroundColor(Color.YELLOW)
                selectedWordIndex = index
            }
        }

        categoryTextViews.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                val selectedWordIndex = selectedWordIndex
                if (selectedWordIndex != null && selectedWordIndex < words.size) {
                    val (word, category) = words[selectedWordIndex]
                    if (textView.text == category) {
                        // Correct match
                        textView.setBackgroundColor(Color.GREEN)
                        wordTextViews[selectedWordIndex].setBackgroundColor(Color.GREEN)
                        matchedWords[word] = true
                        previouslyMatchedIndices.add(selectedWordIndex)
                        if (matchedWords.all { it.value }) {
                            // All words matched successfully
                            Toast.makeText(
                                this,
                                "Task completed successfully. Please remember those words",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        // Incorrect match
                        textView.setBackgroundColor(Color.RED)
                        wordTextViews[selectedWordIndex].setBackgroundColor(Color.RED)
                        matchedWords[word] = false
                        Toast.makeText(this, "Incorrect match. Please try again", Toast.LENGTH_SHORT).show()

                        // Revert colors after 3 seconds
                        textView.postDelayed({
                            if (!matchedWords.any { it.value && it.key == word }) {
                                // Revert color only if the word was not previously matched
                                textView.setBackgroundColor(Color.TRANSPARENT)
                                wordTextViews[selectedWordIndex].setBackgroundColor(Color.TRANSPARENT)
                            }
                        }, 2000)
                    }
                }
            }
        }

        // Set words to green if already matched
        matchedWords.forEach { (word, isMatched) ->
            if (isMatched) {
                val index = words.indexOfFirst { it.first == word }
                if (index != -1) {
                    categoryTextViews[index].setBackgroundColor(Color.GREEN)
                    wordTextViews[index].setBackgroundColor(Color.GREEN)
                    previouslyMatchedIndices.add(index)
                }
            }
        }
    }
}
