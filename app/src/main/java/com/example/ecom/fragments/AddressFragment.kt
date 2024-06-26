package com.example.ecom.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ecom.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class AddressFragment : Fragment() {


    private lateinit var spinner: Spinner
    private lateinit var state_spinner: Spinner
    private lateinit var name: EditText
    private lateinit var line1: EditText
    private lateinit var line2: EditText
    private lateinit var city: EditText
    private lateinit var district: EditText
    private lateinit var pin: EditText
    private lateinit var confirm: Button
    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseAuthRef: FirebaseAuth
    private lateinit var st: String
    private lateinit var m: String
    private lateinit var anotherAddress: ImageView
    private lateinit var next: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name = view.findViewById(R.id.name)
        st = ""
        m = ""
        next = view.findViewById(R.id.next)
        line1 = view.findViewById(R.id.line1)
        line2 = view.findViewById(R.id.line2)
        city = view.findViewById(R.id.city)
        district = view.findViewById(R.id.district)
        pin = view.findViewById(R.id.pinCode)
        spinner = view.findViewById(R.id.spinner)
        state_spinner = view.findViewById(R.id.s_spinner)
        confirm = view.findViewById(R.id.confirm)
        anotherAddress = view.findViewById(R.id.add_another_address)
        firebaseAuthRef = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


        val collectionReference =
            firestore.collection("users").document(firebaseAuthRef.uid!!).collection("Addresses")
        collectionReference.get().addOnSuccessListener { task ->
            if (task.size().toString() == "0") {
                next.visibility = View.GONE
            } else {
                next.visibility = View.VISIBLE

            }
        }

        next.setOnClickListener {
            findNavController().navigate(R.id.action_addressFragment_to_addressesListFragment)
        }


        val options =
            arrayOf(
                "select your mode of payment",
                "credit card",
                "debit card",
                "VISA",
                "UPI",
                "Paytm"
            )
        val states = arrayOf(
            "select your state",
            "Andhra Pradesh",
            "Arunachal Pradesh ",
            "Assam",
            "Bihar",
            "Chhattisgarh",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jammu and Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Odisha",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telangana",
            "Tripura",
            "Uttar Pradesh",
            "Uttarakhand",
            "West Bengal",
            "Andaman and Nicobar Islands",
            "Chandigarh",
            "Dadra and Nagar Haveli",
            "Daman and Diu",
            "Lakshadweep",
            "National Capital Territory of Delhi",
            "Puducherry"
        )

        val s =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, states)

        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, options)

        spinner.adapter = arrayAdapter
        state_spinner.adapter = s

        state_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //
                st = states.get(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }

        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                m = options.get(position).toString()
                //
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }

        }

        anotherAddress.setOnClickListener {
            findNavController().navigate(R.id.action_addressFragment_self)
        }

        var coun: Int = 0
        confirm.setOnClickListener {

            val nam = name.text.toString()
            val l1 = line1.text.toString()
            val l2 = line2.text.toString()
            val cit = city.text.toString()
            val d = district.text.toString()
            val p = pin.text.toString()
            val s = st.toString()
            val mode = m.toString()

            coun += 1

            val data = hashMapOf(
                "name" to nam,
                "line1" to l1,
                "line2" to l2,
                "city" to cit,
                "district" to d,
                "pincode" to p,
                "state" to s,
                "mode" to "Cash On Delivery"
            )

            if (nam.isNotEmpty() && l1.isNotEmpty() && l2.isNotEmpty() && cit.isNotEmpty() && d.isNotEmpty() && p.isNotEmpty() && d.isNotEmpty() && s != "select your state") {

                val collectionReference =
                    firestore.collection("users").document(firebaseAuthRef.uid!!)
                        .collection("Addresses")
                collectionReference.get().addOnSuccessListener { task ->
                    if (task.size().toString() <= "2") {
                        firestore.collection("users").document(firebaseAuthRef.uid!!)
                            .collection("Addresses")
                            .add(data).addOnSuccessListener {
                                Toast.makeText(
                                    requireContext(),
                                    " Address data added",
                                    Toast.LENGTH_SHORT
                                ).show()
                                findNavController().navigate(R.id.action_addressFragment_to_addressesListFragment)

                            }.addOnFailureListener {
                                Toast.makeText(
                                    requireContext(),
                                    "Error while adding address to the database",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "You've already added three addresses! You are not supposed to add more than three addresses",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } else {
                Toast.makeText(
                    requireContext(),
                    "Enter all the details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

}
