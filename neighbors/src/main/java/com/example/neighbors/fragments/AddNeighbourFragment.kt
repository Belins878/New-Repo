package com.example.neighbors.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.fragment.app.Fragment
import com.example.neighbors.NavigationListener
import com.example.neighbors.R
import com.example.neighbors.data.NeighborRepository
import com.example.neighbors.models.Neighbor
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


class AddNeighbourFragment: Fragment(), TextWatcher {

    private lateinit var nameField: TextInputEditText
    private lateinit var emailAddressField: TextInputEditText
    private lateinit var phoneField: TextInputEditText
    private lateinit var websiteField: TextInputEditText
    private lateinit var aboutMeField: TextInputEditText
    private lateinit var imageField: TextInputEditText

    private lateinit var saveButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.add_neighbour_fragment, container, false)

        (activity as? NavigationListener)?.let {
            it.updateTitle(R.string.add_neighbour_toolbar_name)
        }

        nameField = view.findViewById(R.id.name)
        emailAddressField = view.findViewById(R.id.emailAddress)
        phoneField = view.findViewById(R.id.phone)
        websiteField = view.findViewById(R.id.website)
        aboutMeField = view.findViewById(R.id.about_me)
        imageField = view.findViewById(R.id.image)
        saveButton = view.findViewById(R.id.save_button)

        saveButton.setOnClickListener{

            // On récupère la taille de la liste des voisins pour incrémenter automatiquement l'ID
            val newNeighborId = NeighborRepository.getInstance().getNeighbours().size + 1

            val name: String = nameField.text.toString()
            val address:String = emailAddressField.text.toString()
            val phone: String = phoneField.text.toString()
            val website: String = websiteField.text.toString()
            val aboutMe: String = aboutMeField.text.toString()
            val image: String = imageField.text.toString()

            val newNeighbour = Neighbor(
                id = newNeighborId.toLong(),
                name = name,
                address = address,
                phoneNumber = phone,
                webSite = website,
                aboutMe = aboutMe,
                avatarUrl = image,
                favorite = false
            )
            NeighborRepository.getInstance().createNeighbour(newNeighbour)

            // dès que le voisin est créé, on revient sur la liste des voisins
            (activity as? NavigationListener)?.let {
                it.showFragment(ListNeighborsFragment())
            }
        }
        nameField.addTextChangedListener(this)
        emailAddressField.addTextChangedListener(this)
        phoneField.addTextChangedListener(this)
        websiteField.addTextChangedListener(this)
        aboutMeField.addTextChangedListener(this)
        imageField.addTextChangedListener(this)

        return view
    }

    private fun verifButton() {
        val name_field_not_null: Boolean = !nameField.text.isNullOrEmpty()
        val emailAddress_field_not_null: Boolean = !emailAddressField.text.isNullOrEmpty()
        val phone_field_not_null: Boolean = !phoneField.text.isNullOrEmpty()
        val website_field_not_null: Boolean = !websiteField.text.isNullOrEmpty()
        val aboutMe_field_not_null: Boolean = !aboutMeField.text.isNullOrEmpty()
        val image_field_not_null: Boolean = !imageField.text.isNullOrEmpty()

        val emailValid: Boolean = isValidEmail(emailAddressField.text)
        if(!emailValid && emailAddress_field_not_null){
            emailAddressField.setError("Invalid email address")
        }

        val phoneNumberValid: Boolean = isValidPhoneNumber(phoneField.text)
        if(!phoneNumberValid && phone_field_not_null){
            phoneField.setError("Format must be 0X XX XX XX XX")
        }

        val imageUrlValid: Boolean = isValidUrl(imageField.text)
        if(!imageUrlValid && image_field_not_null){
            imageField.setError("Invalid image URL")
        }

        val websiteUrlValid: Boolean = isValidUrl(websiteField.text)
        if(!websiteUrlValid && website_field_not_null){
            websiteField.setError("Invalid URL")
        }

        saveButton.isEnabled =
            name_field_not_null
                    && emailAddress_field_not_null
                    && phone_field_not_null
                    && website_field_not_null
                    && aboutMe_field_not_null
                    && image_field_not_null
                    && emailValid
                    && phoneNumberValid
                    && imageUrlValid
                    && websiteUrlValid
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun isValidPhoneNumber(target: CharSequence?): Boolean {
        return (((target.toString()).startsWith("07")
                || (target.toString()).startsWith("06"))
                && target.toString().length == 10)
    }

    fun isValidUrl(target: CharSequence?): Boolean{
        return URLUtil.isValidUrl(target.toString())
    }

    // TextWatcher

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        verifButton()
    }

}