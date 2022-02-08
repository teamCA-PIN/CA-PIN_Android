package com.caffeine.capin.presentation.cafeti.ui

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.caffeine.capin.R
import com.caffeine.capin.databinding.FragmentCafetiQuestionBinding
import com.caffeine.capin.presentation.cafeti.CafetiQuestionEnum
import com.caffeine.capin.presentation.cafeti.CafetiQuestionEnum.Companion.findCafetiQuestion
import com.caffeine.capin.presentation.cafeti.viewModel.CafetiViewModel
import com.caffeine.capin.presentation.util.AutoClearedValue
import com.caffeine.capin.presentation.util.UiState
import com.caffeine.capin.util.drawFinish
import com.caffeine.capin.util.toPx
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalArgumentException

@AndroidEntryPoint
class CafetiQuestionFragment: Fragment() {
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private var binding by AutoClearedValue<FragmentCafetiQuestionBinding>()
    private val viewModel: CafetiViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressedCallback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.questionNumber.value == 1) {
                    findNavController().popBackStack()
                } else {
                    viewModel.previousQuestion()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentCafetiQuestionBinding.inflate(inflater, container, false)?.let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cafetiViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observeQuestion()
        changeQuestionUI()
        moveToPreviousQuestion()
        moveToNextQuestion()
        setNextButtonEnabled()
        goToCafetiResult()
    }

    private fun observeQuestion() {
        viewModel.questionNumber.observe(viewLifecycleOwner) {
            changeIndicatorImage(it)
            viewModel.changeCurrentQuestion(
                when (it) {
                    2 -> if (viewModel.answers.value?.first() == 0) CafetiQuestionEnum.CoffeeTaste else CafetiQuestionEnum.NonCoffeeMenu
                    else -> findCafetiQuestion(it)!!
                }
            )
        }
    }

    private fun changeQuestionUI() {
        viewModel.currentQuestion.observe(viewLifecycleOwner) {
            viewModel.changeQuestionText(requireContext().getString(it.question))
            with(binding.radiogroupAnswer) {
                clearCheck()
                removeAllViews()
                drawFinish {
                    resources.getStringArray(it.answers).apply {
                        forEach { addView(createAnswerButton(indexOf(it), it)) }
                        forceLayout()
                    }
                }
            }
        }
    }

    private fun createAnswerButton(idx: Int, answer: String): RadioButton {
        val buttonLayoutParams = RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT).apply {
            setMargins(0, 8.toPx(),0,0)
        }

        return RadioButton(requireContext()).apply {
            id = idx
            buttonDrawable = null
            height = 48.toPx()
            text = answer
            textSize = 16f
            setPadding(0, 3.toPx(),0, 3.toPx())
            typeface = ResourcesCompat.getFont(requireContext(), R.font.noto_sans_kr_regular)
            width = binding.radiogroupAnswer.width
            background = ContextCompat.getDrawable(requireContext(), R.drawable.selector_cafeti_radiobutton)
            setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.selector_cafeti_radiobutton_text))
            gravity = Gravity.CENTER
            layoutParams = buttonLayoutParams
        }
    }

    private fun moveToPreviousQuestion() {
        binding.textviewButtonBefore.setOnClickListener {
            viewModel.removeLastAnswer()
            when(viewModel.questionNumber.value) {
                1 -> findNavController().popBackStack()
                else -> viewModel.previousQuestion()
            }
        }
    }

    private fun moveToNextQuestion() {
        binding.textviewButtonNext.setOnClickListener {
            viewModel.addAnswer(binding.radiogroupAnswer.checkedRadioButtonId)
            if (viewModel.answers.value?.count() != 4) {
                viewModel.changeCheckedButtonId(-1)
                viewModel.nextQuestion()
            } else {
                viewModel.getCafetiResult()
            }
        }
    }

    private fun goToCafetiResult() {
        viewModel.cafetiResult.observe(viewLifecycleOwner) { cafetiResult ->
            when(cafetiResult.status) {
                UiState.Status.SUCCESS -> {
                    cafetiResult.data?.let {
                        val action = CafetiQuestionFragmentDirections.actionCafetiQuestionFragmentToCafetiResultFragment(it)
                        findNavController().navigate(action)
                    }
                }
                UiState.Status.LOADING -> { binding.progressbar.visibility = View.VISIBLE }
                UiState.Status.ERROR -> { binding.progressbar.visibility = View.GONE }
            }
        }
    }

    private fun setNextButtonEnabled() {
        binding.radiogroupAnswer.setOnCheckedChangeListener { group, buttonId ->
            viewModel.changeCheckedButtonId(buttonId)
        }
        viewModel.checkedButtonId.observe(viewLifecycleOwner) {
            binding.textviewButtonNext.isEnabled = (it != -1)
        }
    }

    private fun changeIndicatorImage(idx: Int) {
        binding.imageviewIndicator.background = ContextCompat.getDrawable(requireContext(),
            when(idx) {
                1 -> R.drawable.property_1_cafeti_step_1
                2 -> R.drawable.property_1_cafeti_step_2
                3 -> R.drawable.property_1_cafeti_step_3
                4 -> R.drawable.property_1_cafeti_step_4
                else -> throw IllegalArgumentException("indicator ui error")
            }
        )
    }
}