package com.example.sakina

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant.ADVICE
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.core.util.UIPrefrences
import com.example.sakina.databinding.ActivityMainBinding
import com.example.sakina.feature_account.data.repository.AccountRepositoryImpl
import com.example.sakina.feature_account.domain.use_case.AccountUseCases
import com.example.sakina.feature_account.domain.use_case.ChangeAccountNameUseCase
import com.example.sakina.feature_account.domain.use_case.ChangeAccountPasswordUseCase
import com.example.sakina.feature_account.domain.use_case.GetAccountDataUseCase
import com.example.sakina.feature_account.domain.use_case.GetInformationUseCase
import com.example.sakina.feature_account.domain.use_case.PersonalInfoUseCase
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
import com.example.sakina.feature_medicine.data.local.MedicineDataBase
import com.example.sakina.feature_medicine.data.repository.MedicineRepositoryImpl
import com.example.sakina.feature_medicine.domain.use_case.DeleteMedicineUseCase
import com.example.sakina.feature_medicine.domain.use_case.GetLastMedicineUseCase
import com.example.sakina.feature_medicine.domain.use_case.GetMedicineByIdUseCase
import com.example.sakina.feature_medicine.domain.use_case.GetMedicinesUseCase
import com.example.sakina.feature_medicine.domain.use_case.MedicineUseCases
import com.example.sakina.feature_medicine.domain.use_case.SetRemindersUseCase
import com.example.sakina.feature_medicine.domain.use_case.UpsertMedicineUseCase
import com.example.sakina.feature_medicine.presentation.view_model.MedicineViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var authViewModel: AuthViewModel
    lateinit var adviceViewModel: AdviceViewModel
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView


    lateinit var accountViewModel: AccountViewModel

    lateinit var medicineViewModel: MedicineViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({

            navController = findNavController(R.id.fragmentContainerView)
            bottomNavigationView = binding.bottomNav

            // Listen for destination changes to update bottom nav state
            navController.addOnDestinationChangedListener { _, destination, _ ->

                when (destination.id) {
                    R.id.homeFragment, R.id.profileFragment, R.id.settingsFragment, R.id.medicineListFragment -> {
                        setupBottomBarNavigation()
                    }

                    else -> {
                        bottomNavigationView.isVisible = false
                    }
                }

                updateBottomNavigationState(destination.id)
            }

            val fragmentArgs = intent?.getBundleExtra("medicineId")
            if (fragmentArgs != null) {
                val medicineId = fragmentArgs.getLong("medicineId", 0)
                Log.d(TAG, "Opening MedicineDetailsFragment with medicineId: $medicineId")
                navController.navigate(R.id.medicineDetailsFragment, fragmentArgs)
            }

        }, 10)

        UIPrefrences().applyTheme(MySharedPref.getString("theme", "Light"))
        UIPrefrences().changeLanguage(
            this,
            MySharedPref.getString("language", "English")
        )


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
            this, AuthViewModelFactory(authUseCases)
        )[AuthViewModel::class.java]


        val repo = AccountRepositoryImpl()
        val changeAccountNameUseCase = ChangeAccountNameUseCase(repo)
        val changeAccountPasswordUseCase = ChangeAccountPasswordUseCase(repo)
        val personalInfoUseCase = PersonalInfoUseCase(repo)
        val getInfoUseCase = GetInformationUseCase(repo)
        val getAccountData = GetAccountDataUseCase(repo)
        val accountUseCase = AccountUseCases(
            changeNameUseCase = changeAccountNameUseCase,
            changePasswordUseCase = changeAccountPasswordUseCase,
            personalInfoUseCase = personalInfoUseCase,
            getInformationUseCase = getInfoUseCase,
            getAccountDataUseCase = getAccountData
        )
        accountViewModel = ViewModelProvider(
            this, AccountViewModelFactory(accountUseCase)
        )[AccountViewModel::class.java]

        //Advice
        val adviceRepository = AdviceRepositoryImpl()

        val getTotalAdvicesCountUseCase = GetTotalAdvicesCountUseCase(adviceRepository)

        val getAdviceByIdUseCase = GetAdviceByIdUseCase(adviceRepository)

        adviceViewModel = ViewModelProvider(
            this, AdviceViewModelFactory(getTotalAdvicesCountUseCase, getAdviceByIdUseCase)
        )[AdviceViewModel::class.java]


        // Medicine

        val repository = MedicineRepositoryImpl(MedicineDataBase(this))

        val getMedicinesUseCase = GetMedicinesUseCase(repository)
        val upsertMedicineUseCase = UpsertMedicineUseCase(repository)
        val deleteMedicineUseCase = DeleteMedicineUseCase(repository)
        val getMedicineByIdUseCase = GetMedicineByIdUseCase(repository)
        val getLastMedicineUseCase = GetLastMedicineUseCase(repository)
        val setRemindersUseCase = SetRemindersUseCase(this)
        val medicineUseCases = MedicineUseCases(
            getMedicinesUseCase,
            deleteMedicineUseCase,
            upsertMedicineUseCase,
            getMedicineByIdUseCase,
            getLastMedicineUseCase,
            setRemindersUseCase
        )
        medicineViewModel = MedicineViewModel(medicineUseCases)


    }

    override fun onDestroy() {
        MySharedPref.clearValue(ADVICE)
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.profileFragment || navController.currentDestination?.id == R.id.settingsFragment) {
            navController.navigate(R.id.homeFragment)
        } else {
            super.onBackPressed()
        }
    }

    private fun setupBottomBarNavigation() {
        bottomNavigationView.visibility = View.VISIBLE
        updateBottomNavigationState(navController.currentDestination?.id ?: R.id.homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.medicineListFragment -> {
                    navController.navigate(R.id.medicineListFragment)
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

    private fun updateBottomNavigationState(destinationId: Int) {
        when (destinationId) {
            R.id.homeFragment -> {
                bottomNavigationView.menu.findItem(R.id.homeFragment)?.isChecked = true
            }

            R.id.settingsFragment -> {
                bottomNavigationView.menu.findItem(R.id.settingsFragment)?.isChecked = true
            }

            R.id.profileFragment -> {
                bottomNavigationView.menu.findItem(R.id.profileFragment)?.isChecked = true
            }
        }
    }
}


