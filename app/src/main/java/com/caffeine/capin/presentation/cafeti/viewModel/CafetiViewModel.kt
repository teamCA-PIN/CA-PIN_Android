package com.caffeine.capin.presentation.cafeti.viewModel

import android.widget.RadioButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caffeine.capin.data.dto.cafeti.RequestCafetiData
import com.caffeine.capin.data.dto.cafeti.ResponseCafetiData
import com.caffeine.capin.domain.usecase.GetCafetiResultUseCase
import com.caffeine.capin.presentation.base.BaseViewModel
import com.caffeine.capin.presentation.cafeti.CafetiQuestionEnum
import com.caffeine.capin.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CafetiViewModel @Inject constructor(
    private val getCafetiResultUseCase: GetCafetiResultUseCase
): BaseViewModel() {
    private val _questionNumber = MutableLiveData<Int>(1)
    val questionNumber: LiveData<Int> = _questionNumber

    private val _answerButtons = MutableLiveData<List<RadioButton>>()
    val answerButtons: LiveData<List<RadioButton>> = _answerButtons

    private val _currentQuestion = MutableLiveData<CafetiQuestionEnum>()
    val currentQuestion: LiveData<CafetiQuestionEnum> =_currentQuestion

    private val _questionText = MutableLiveData<String>()
    val questionText: LiveData<String> = _questionText

    private val _checkedButtonId = MutableLiveData<Int>()
    val checkedButtonId: LiveData<Int> = _checkedButtonId

    private val _answers = MutableLiveData<List<Int>>()
    val answers: LiveData<List<Int>> = _answers

    private val _cafetiResult = MutableLiveData<UiState<ResponseCafetiData>>()
    val cafetiResult: LiveData<UiState<ResponseCafetiData>> = _cafetiResult

    fun nextQuestion() {
        var number = questionNumber.value ?: 1
        _questionNumber.value = ++number
    }

    fun changeAnswerButtons(buttons: List<RadioButton>) {
        _answerButtons.value = buttons
    }

    fun changeQuestionText(question: String) {
        _questionText.value = question
    }

    fun previousQuestion() {
        var number = questionNumber.value ?: 1
        _questionNumber.value = --number
    }

    fun changeCurrentQuestion(question: CafetiQuestionEnum) {
        _currentQuestion.value = question
    }

    fun addAnswer(answer: Int) {
        val answerList = answers.value?.toMutableList() ?: mutableListOf()
        answerList.add(answer)
        _answers.value = answerList
    }

    fun changeCheckedButtonId(id: Int) {
        var checkedId = checkedButtonId.value ?: -1
        checkedId = id
        _checkedButtonId.value = checkedId
    }

    fun removeLastAnswer() {
        val answerList = answers.value?.toMutableList() ?: mutableListOf()
        if (answerList.isNotEmpty()) {
            answerList.removeLast()
            _answers.value = answerList
        }
    }

    fun getCafetiResult() {
        answers.value?.let {
            _cafetiResult.postValue(UiState.loading(null))
            getCafetiResultUseCase(RequestCafetiData(it))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _cafetiResult.postValue(UiState.success(it))
                }, {
                    _cafetiResult.postValue(UiState.error(null, null))
                    it.printStackTrace()
                })
        }
    }
}