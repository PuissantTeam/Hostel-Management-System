package com.example.hostelmanagementsystem.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.hostelmanagementsystem.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {
    private lateinit var fragmentOnBoardingBinding: FragmentOnBoardingBinding
    private var title: String? = null
    private var description: String? = null
    private var imageResource = 0
    private lateinit var tvTitle: AppCompatTextView
    private lateinit var tvDescription: AppCompatTextView
    private lateinit var image: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            title = requireArguments().getString(ARG_PARAM1)
            description = requireArguments().getString(ARG_PARAM2)
            imageResource = requireArguments().getInt(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentOnBoardingBinding = FragmentOnBoardingBinding.inflate(layoutInflater)

        val rootView = fragmentOnBoardingBinding.root

        tvTitle = fragmentOnBoardingBinding.textOnboardingTitle
        tvDescription = fragmentOnBoardingBinding.textOnboardingDescription
        image = fragmentOnBoardingBinding.imageOnboarding
        tvTitle.text = title
        tvDescription.text = description
        image.setAnimation(imageResource)
        return rootView
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_PARAM3 = "param3"
        fun newInstance(
            title: String?,
            description: String?,
            imageResource: Int
        ): OnBoardingFragment {
            val fragment = OnBoardingFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, title)
            args.putString(ARG_PARAM2, description)
            args.putInt(ARG_PARAM3, imageResource)
            fragment.arguments = args
            return fragment
        }
    }
}