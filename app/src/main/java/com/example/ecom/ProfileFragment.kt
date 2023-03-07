package com.example.ecom

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ecom.activities.SignInActivity
import com.example.ecom.data.User
import com.example.ecom.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

import com.google.firebase.storage.FirebaseStorage
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var uri: Uri? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth= FirebaseAuth.getInstance()
        FirebaseFirestore.getInstance().collection("users").document(auth.uid!!).get()
            .addOnSuccessListener {

                Glide.with(requireContext()).load(it.get("profile pic"))
                    .into(binding.profilePicAccSection)

                binding.userNameAccSection.text = it.getString("name")

                binding.phoneNumAccSection.text = it.getString("phone")

            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(auth.uid!!).get().addOnSuccessListener {

            Glide.with(requireContext()).load(it.get("profile pic"))
                .into(binding.profilePicAccSection)

            binding.userNameAccSection.text = it.getString("name")

            binding.phoneNumAccSection.text = it.getString("phone")

        }

        binding.home.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        binding.editprofile.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    SignInActivity.READ_PERMISSION
                )
            }

            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)

        }

        binding.editname.setOnClickListener {

            dialog()

        }

        return binding.root
    }

    private fun dialog() {
        val mDialog = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.edit_name, null)
        mDialog.setView(mDialogView)
        val addEmailbtn: Button = mDialogView.findViewById(R.id.addBtn)
        val addEmail: TextView = mDialogView.findViewById(R.id.addEmail)
        mDialog.setTitle("Edit name")
        val alertDialog = mDialog.create()
        alertDialog.show()

        addEmailbtn.setOnClickListener {

            val mail = addEmail.text.toString()
            if (mail.length.equals(0)){
                Toast.makeText(requireContext(), "Enter name", Toast.LENGTH_SHORT).show()
            }
            else{
                FirebaseFirestore.getInstance().collection("users").document(auth.uid!!)
                    .update("name", mail).addOnSuccessListener {
                        Toast.makeText(requireContext(), "Name updated Succesfully", Toast.LENGTH_SHORT)
                            .show()

                        FirebaseFirestore.getInstance().collection("users").document(auth.uid!!).get()
                            .addOnSuccessListener {

                                binding.userNameAccSection.text = it.getString("name")

                            }

                        alertDialog.dismiss()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                            .show()
                    }
            }


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (data.data != null) {
                uri = data.data!!
                binding.profilePicAccSection.setImageURI(data.data!!)
            }
        }

        Toast.makeText(
            requireContext(),
            "Profile pic updated",
            Toast.LENGTH_SHORT
        ).show()

        val sReference =
            FirebaseStorage.getInstance().reference.child("Images").child("user_folder").child(
                Date().time.toString()
            )

        uri?.let { it1 ->
            sReference.putFile(it1).addOnCompleteListener {
                if (it.isSuccessful) {
                    sReference.downloadUrl.addOnSuccessListener { task ->
                        FirebaseFirestore.getInstance().collection("users").document(auth.uid!!)
                            .update("profile pic", task).addOnSuccessListener {

                            }
                    }
                }
            }

                .addOnFailureListener {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

    }
}
