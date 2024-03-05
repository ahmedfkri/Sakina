package com.example.sakina

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.databinding.ActivityMainBinding
import com.example.sakina.feature_account.data.repository.AccountRepo
import com.example.sakina.feature_account.domain.use_case.AccountUseCase
import com.example.sakina.feature_account.domain.use_case.ChangeAccountNameUseCase
import com.example.sakina.feature_account.domain.use_case.ChangeAccountPasswordUseCase
import com.example.sakina.feature_account.presentation.view_model.AccountViewModel
import com.example.sakina.feature_account.presentation.view_model.AccountViewModelFactory
import com.example.sakina.feature_advice.data.repository.AdviceRepositoryImpl
import com.example.sakina.feature_advice.domain.use_case.GetAdviceByIdUseCase
import com.example.sakina.feature_advice.domain.use_case.GetTotalAdvicesCountUseCase
import com.example.sakina.feature_advice.presentation.view_model.AdviceViewModel
import com.example.sakina.feature_advice.presentation.view_model.AdviceViewModelFactory
import com.example.sakina.feature_authentication.data.repository.AuthRepositoryImpl
import com.example.sakina.feature_authentication.domain.use_case.AuthUseCases
import com.example.sakina.feature_authentication.domain.use_case.AuthenticateUserUseCase
import com.example.sakina.feature_authentication.domain.use_case.CheckEmailDuplicationUseCase
import com.example.sakina.feature_authentication.domain.use_case.RegisterUseCase
import com.example.sakina.feature_authentication.domain.use_case.SendEmailConfirmationUseCase
import com.example.sakina.feature_authentication.domain.use_case.ValidateSignUpUseCase
import com.example.sakina.feature_authentication.presentation.view_model.AuthViewModel
import com.example.sakina.feature_authentication.presentation.view_model.AuthViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var authViewModel: AuthViewModel
    lateinit var adviceViewModel: AdviceViewModel
    lateinit var navController: NavController
    lateinit var bottomNavigationView: BottomNavigationView


    lateinit var accountViewModel: AccountViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Handler().postDelayed({
            navController = findNavController(R.id.fragmentContainerView)
            bottomNavigationView = binding.bottomNav
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeFragment, R.id.profileFragment, R.id.settingsFragment -> {
                        setupBottomBarNavigation()
                    }

                    else -> {
                        bottomNavigationView.isVisible = false
                        binding.shadow.isVisible = false
                    }
                }
            }


        }, 1)


        //Authentication
        val authRepository = AuthRepositoryImpl()

        val authenticateUserUseCase = AuthenticateUserUseCase(authRepository)
        val checkEmailDuplicationUseCase = CheckEmailDuplicationUseCase(authRepository)
        val registerUseCase = RegisterUseCase(authRepository)
        val sendEmailConfirmationUseCase = SendEmailConfirmationUseCase(authRepository)
        val validateSignUpUseCase = ValidateSignUpUseCase()

        val authUseCases = AuthUseCases(
            authenticateUserUseCase = authenticateUserUseCase,
            checkEmailDuplicationUseCase = checkEmailDuplicationUseCase,
            registerUseCase = registerUseCase,
            sendEmailConfirmationUseCase = sendEmailConfirmationUseCase,
            validateSignUpUseCase = validateSignUpUseCase
        )
        authViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(authUseCases)
        ).get(AuthViewModel::class.java)


        val repo=AccountRepo()
        val changeAccountNameUseCase=ChangeAccountNameUseCase(repo)
        val changeAccountPasswordUseCase=ChangeAccountPasswordUseCase(repo)
        val accountUseCase = AccountUseCase(
            changeNameUseCase = changeAccountNameUseCase,
            changePasswordUseCase = changeAccountPasswordUseCase
        )
        accountViewModel = ViewModelProvider(
            this,
            AccountViewModelFactory(accountUseCase)
        )[AccountViewModel::class.java]

        //Advice
        val adviceRepository = AdviceRepositoryImpl()

        val getTotalAdvicesCountUseCase = GetTotalAdvicesCountUseCase(adviceRepository)

        val getAdviceByIdUseCase = GetAdviceByIdUseCase(adviceRepository)

        adviceViewModel = ViewModelProvider(
            this, AdviceViewModelFactory(getTotalAdvicesCountUseCase, getAdviceByIdUseCase)
        ).get(AdviceViewModel::class.java)


    }

    override fun onDestroy() {
        MySharedPref.clearValue("advice")
        super.onDestroy()
    }


    private fun setupBottomBarNavigation() {

        bottomNavigationView.menu.findItem(R.id.homeFragment).isChecked = true
        bottomNavigationView.visibility = View.VISIBLE
        binding.shadow.isVisible = true
        bottomNavigationView.selectedItemId

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.settingsFragment -> {
                    navController.navigate(R.id.settingsFragment)
                    true
                }

                R.id.profileFragment -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }

                else -> false
            }
        }

    }


}