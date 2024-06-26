package com.example.ecom.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ecom.R
import com.example.ecom.databinding.FragmentPlaceOrderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

class PlaceOrderFragment : Fragment() {

    private lateinit var mName: String
    private lateinit var addressStr: String
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth


    private lateinit var binding: FragmentPlaceOrderBinding

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceOrderBinding.inflate(layoutInflater, container, false)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()



        Toast.makeText(requireContext(), "Enable Cash on delivery to continue", Toast.LENGTH_SHORT)
            .show()
        binding.payment.visibility = View.GONE
        binding.check.setOnCheckedChangeListener { buttonView, isChecked ->
            // Handle the checkbox state change here
            if (buttonView.isChecked) {
                binding.payment.visibility = View.VISIBLE
            } else {
                Toast.makeText(
                    requireContext(),
                    "Enable Cash on delivery to continue",
                    Toast.LENGTH_SHORT
                ).show()
                binding.payment.visibility = View.INVISIBLE
            }
        }


        val data = arguments

        val n = data?.get("name")
        val ln1 = data?.get("l1")
        val ln2 = data?.get("l2")
        val d = data?.get("dist")
        val c = data?.get("city")
        val s = data?.get("stat")
        val p = data?.get("pin")

        addressStr =
            ln1.toString() + ", " + ln2.toString() + " \n" + c.toString() + " (" + d.toString() + " district)" + "\n " + s.toString() + " - " + p.toString()
        mName = n.toString()
        binding.name.setText("$n")
        binding.l1.setText("$ln1")
        binding.l2.setText("$ln2")
        binding.dis.setText("$d")
        binding.cit.setText("$c")
        binding.st.setText("$s")
        binding.pi.setText("$p")

        val collectionReference =
            db.collection("users").document(auth.uid!!)
                .collection("cart")
        collectionReference.get().addOnSuccessListener { task ->
            binding.tvQty.text = "${task.size()} Items"
        }

        var pr = 0

        val coll =
            db.collection("users").document(auth.uid!!)
                .collection("cart")
        coll.whereEqualTo("ref", "9014").get().addOnSuccessListener { task ->
            for (document in task) {
                // Access the data of the matching document
                val name = document.getString("price")
                try {
                    pr += name.toString().toDouble().toInt()
                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }
            binding.tvTotal.text = "₹ " + pr.toString()
            binding.tvCostt.text = "₹ " + pr.toString()

        }
            .addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }



        binding.payment.setOnClickListener {

            val party = Party(
                speed = 0f,
                maxSpeed = 30f,
                damping = 0.9f,
                spread = 360,
                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
                position = Position.Relative(0.5, 0.3)
            )


            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_dialog)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            binding.konfettiView.start(party)

            binding.payment.visibility = View.GONE
            Handler().postDelayed({
                dialog.dismiss()
            }, 3000)

            //Adding data to the Orders collection

            val db = FirebaseFirestore.getInstance()
            val auth = FirebaseAuth.getInstance()

            val collectionRef = db.collection("users").document(auth.uid!!).collection("cart")
            collectionRef.get()
                .addOnSuccessListener { query ->
                    // handle querySnapshot here
                    query.forEach { documentSnapshot ->
                        val data = documentSnapshot.data
                        // process data here
                        val destinationCollectionRef =
                            db.collection("users").document(auth.uid!!).collection("Orders")
                        destinationCollectionRef.document().set(data)
                            .addOnSuccessListener {
                                // document added successfully
                                findNavController().navigate(R.id.action_placeOrderFragment_to_homeFragment)
                            }
                            .addOnFailureListener { exception ->
                                // handle exception here
                            }
                    }

                }
                .addOnFailureListener { exception ->
                    // handle exception here
                }


            //deleting cart data

            val coll =
                FirebaseFirestore.getInstance().collection("users")
                    .document(FirebaseAuth.getInstance().uid!!)
                    .collection("cart")
            coll.whereEqualTo("ref", "9014").get().addOnSuccessListener { task ->
                for (document in task) {
                    // Access the data of the matching document
                    val documentId = document.id
                    coll.document(documentId).delete().addOnSuccessListener {

                    }
                }


            }
                .addOnFailureListener {

                }


        }




        return binding.root
    }


}