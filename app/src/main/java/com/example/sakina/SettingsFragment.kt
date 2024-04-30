
package com.example.sakina

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController

import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant.EXP_ON
import com.example.sakina.core.util.Constant.JWT_TOKEN
import com.example.sakina.core.util.Constant.LOGGED_IN
import com.example.sakina.core.util.Constant.REFRESH_TOKEN
import com.example.sakina.core.util.Constant.USER_EMAIL
import com.example.sakina.databinding.FragmentSettingsBinding
import java.util.Locale


@Suppress("DEPRECATION")
class SettingsFragment :Fragment() {
    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.loAccount.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_accountFragment)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_homeFragment)
        }

        binding.loLogout.setOnClickListener {
            MySharedPref.clearValue(USER_EMAIL)
            MySharedPref.clearValue(JWT_TOKEN)
            MySharedPref.clearValue(REFRESH_TOKEN)
            MySharedPref.clearValue(EXP_ON)
            MySharedPref.putBool(LOGGED_IN, false)
            findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
        }

        //Language
        binding.loLanguage.setOnClickListener {
             showLanguageDialog()
        }

        //theme
        binding.loTheme.setOnClickListener {
            showThemeDialog()
        }

        //contact
        binding.loContact.setOnClickListener {
            showContactOptions()
        }


    }
////////////////////////////////////language
    private fun showLanguageDialog() {
    val languageOptions = resources.getStringArray(R.array.language_options)
    val currentLanguage = getCurrentLanguage()

    val builder = AlertDialog.Builder(requireActivity())
    builder.setTitle("Select Language")
        .setSingleChoiceItems(
            languageOptions,
            languageOptions.indexOf(currentLanguage)
        ) { dialog, which ->
            val selectedLanguage = languageOptions[which]
            if (selectedLanguage != currentLanguage) {
                setNewLanguage(selectedLanguage)
                changeLanguage(requireContext(), selectedLanguage)
                dialog.dismiss()
                restartActivity()
            }
        }
        .setNegativeButton("Cancel", null)
    val dialog = builder.create()
    dialog.show()
}

    private fun getCurrentLanguage(): String {
        return MySharedPref.getLanguage("ar")
    }

    private fun setNewLanguage(language: String) {
        MySharedPref.putLanguage(language)
    }

    private fun restartActivity() {
        activity?.let {
            val intent = it.intent
            it.finish()
            it.startActivity(intent)
        }
    }

    private fun changeLanguage(context: Context, languageCode: String) {
        val locale = when (languageCode) {
            "en" -> Locale.ENGLISH
            "fr" -> Locale.FRENCH
            "es" -> Locale("es")
            else -> Locale.getDefault()
        }

        Locale.setDefault(locale)

        val configuration = Configuration()
        configuration.setLocale(locale)

        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }


        //////////////////////////////////////theme
    private fun showThemeDialog() {
        val themeOptions = arrayOf("Light Mode", "Dark Mode")

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Theme")
            .setItems(themeOptions) { dialog, which ->
                val selectedTheme = themeOptions[which]
                applyTheme(selectedTheme)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }
    private fun applyTheme(selectedTheme: String) {
        when (selectedTheme) {
            "Light Mode" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "Dark Mode" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }




////////////////////////////////////contact
    private fun showContactOptions() {
        val items = arrayOf("Call", "Email", "Visit Website")

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Contact Us")
            .setItems(items) { dialog, which ->
                when (which) {
                    0 -> {
                        val phoneNumber = "01001563144" // Replace with your phone number
                        phoneNumber.dialPhoneNumber()
                    }
                    1 -> {
                        val emailAddress = "contact@example.com" // Replace with your email address
                        emailAddress.sendEmail()
                    }
                    2 -> {
                        val websiteUrl = "https://www.example.com" // Replace with your website URL
                        websiteUrl.openWebsite()
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }

    private fun String.dialPhoneNumber() {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${this@dialPhoneNumber}")
        }
        startActivity(intent)
    }

    private fun String.sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(this@sendEmail))
        }
        startActivity(Intent.createChooser(intent, "Send Email"))
    }

    private fun String.openWebsite() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(this@openWebsite)
        }
        startActivity(intent)
    }
}
