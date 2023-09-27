package com.frey.msu.geoquiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.frey.msu.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0
    private var numberCorrect = 0
    private var userScore = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.trueButton.setOnClickListener {
            checkAnswer(true)
            binding.trueButton.isEnabled = ! (binding.trueButton.isEnabled)
            binding.falseButton.isEnabled = ! (binding.falseButton.isEnabled)
            computeScore()
        }

        binding.falseButton.setOnClickListener{
            checkAnswer(false)
            binding.falseButton.isEnabled = ! (binding.falseButton.isEnabled)
            binding.trueButton.isEnabled = ! (binding.trueButton.isEnabled)
            computeScore()
        }

        binding.nextButton.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
            updateQuestion()
        }

        binding.questionTextview.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
            updateQuestion()
        }

        binding.prevButton.setOnClickListener{
            currentIndex = (currentIndex - 1) % questionBank.size
            binding.trueButton.isEnabled = ! (binding.trueButton.isEnabled)
            binding.falseButton.isEnabled = ! (binding.falseButton.isEnabled)
            updateQuestion()
        }

        updateQuestion()

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextview.setText(questionTextResId)

    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            numberCorrect++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT) .show()
    }

    private fun computeScore() {
        if (currentIndex == questionBank.size - 1) {
            userScore = (numberCorrect * 100.0 /questionBank.size).toInt()
            val formattedScore = String.format("%.1f%%", userScore.toDouble())
            Toast.makeText(this, formattedScore, Toast.LENGTH_SHORT) .show()
            numberCorrect = 0
        }

    }
}