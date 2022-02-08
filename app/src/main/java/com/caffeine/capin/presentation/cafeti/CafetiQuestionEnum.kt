package com.caffeine.capin.presentation.cafeti

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.caffeine.capin.R
import java.lang.IllegalArgumentException

enum class CafetiQuestionEnum(val number: Int, @DrawableRes val image: Int, @StringRes val question: Int, val answers: Int) {
    DrinkType(1, R.drawable.frame_137, R.string.question_drink_type, R.array.drink_type_answers),
    CoffeeTaste(2, R.drawable.frame_136, R.string.question_coffee_taste, R.array.coffee_taste_answers),
    NonCoffeeMenu(2, R.drawable.frame_135, R.string.question_noncoffe_menu, R.array.non_coffee_menu_answers),
    CafeStyle(3, R.drawable.frame_128, R.string.question_cafe_style, R.array.cafe_style_answers),
    CafeMood(4, R.drawable.frame_134, R.string.question_cafe_mood, R.array.cafe_mood_answers);

    companion object {
        fun findCafetiQuestion(number: Int): CafetiQuestionEnum? {
            return values().find { it.number == number } ?:
                throw IllegalArgumentException("Not Found Cafeti Question")
        }
    }
}